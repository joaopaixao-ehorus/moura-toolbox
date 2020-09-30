package moura.sdp.toolbox.orm;

import moura.sdp.toolbox.converter.Converter;
import moura.sdp.toolbox.converter.ConverterContext;
import moura.sdp.toolbox.converter.MutableConverterContext;
import moura.sdp.toolbox.query.*;
import moura.sdp.toolbox.query.Statement;
import moura.sdp.toolbox.query.dialect.Dialect;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Database {

    private final DataSource dataSource;
    private final Sql sql;
    private final EntityRegistry entityRegistry;
    private final Dialect dialect;
    private final Converter converter;
    private final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    public Database(
        DataSource dataSource,
        EntityRegistry entityRegistry,
        Dialect dialect,
        Converter converter
    ) {
        this.sql = new Sql().withDialect(dialect);
        this.dataSource = dataSource;
        this.entityRegistry = entityRegistry;
        this.dialect = dialect;
        this.converter = converter;
    }

    public Converter getConverter() {
        return converter;
    }

    public <T> EntityConfiguration<T> getConfiguration(Class<T> clazz) {
        return entityRegistry.getConfig(clazz);
    }

    public QueryBuilder query() {
        return sql.newQuery().database(this);
    }

    public UpdateBuilder update() {
        return sql.newUpdate().database(this);
    }

    public InsertBuilder insert() {
        return sql.newInsert().database(this);
    }

    public DeleteBuilder delete() {
        return sql.newDelete().database(this);
    }

    public <T> TypedQueryBuilder<T> from(Class<T> resultClass) {
        return sql.withDialect(dialect).newQuery(entityRegistry, resultClass).database(this);
    }

    public Query createRawQuery(String sql) {
        return createRawQuery(sql, null);
    }

    public Query createRawQuery(String sql, List<Object> params) {
        QueryImpl query = new QueryImpl();
        query.setDatabase(this);
        query.setString(sql);
        query.setParams(params);
        return query;
    }

    public StatementResult execute(Statement statement) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = StatementType.INSERT.equals(statement.getType())
                    ? connection.prepareStatement(statement.getSql(), java.sql.Statement.RETURN_GENERATED_KEYS)
                    : connection.prepareStatement(statement.getSql());
            setParameters(preparedStatement, statement.getParams());
            preparedStatement.executeUpdate();
            StatementResultImpl result = new StatementResultImpl();
            if (StatementType.INSERT.equals(statement.getType())) {
                List<Object> keys = new ArrayList<>();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next()) {
                    keys.add(resultSet.getObject(1));
                }
                result.setGeneratedKeys(keys);
            }
            result.setUpdatedRows(preparedStatement.getUpdateCount());
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public StatementResult execute(String sql) {
        return execute(sql, null);
    }

    public StatementResult execute(String sql, List<Object> params) {
        StatementImpl statement = new StatementImpl();
        statement.setSql(sql);
        statement.setParams(params);
        return execute(statement);
    }

    private Connection getConnection() throws SQLException {
        Connection connection;
        if ((connection = connectionThreadLocal.get()) == null) {
            connection = dataSource.getConnection();
            connectionThreadLocal.set(connection);
        }
        return connection;
    }

    public <T> List<T> getQueryResults(Query query, Class<T> resultClass) {
        try {
            MutableConverterContext context = new MutableConverterContext();
            context.setConverter(converter);
            context.setParams(query.getHints());
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query.getSql());
            setParameters(preparedStatement, query.getParams());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> columns = getColumns(preparedStatement);
            List<T> list = new ArrayList<>();
            List<Model> models = new ArrayList<>();
            while (resultSet.next()) {
                models.add(getModel(resultSet, columns, context));
            }
            EntityConfiguration<T> config = entityRegistry.getConfig(resultClass);
            Function<Model, T> converterFunction = config != null
                    ? m -> config.modelToEntity(m, context)
                    : m -> converter.convert(m, resultClass, query.getHints());
            for (Model model : models) {
                list.add(converterFunction.apply(model));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> getTypedQueryResults(TypedQuery<T> query, Class<T> resultClass) {
        List<T> result = getQueryResults(query, resultClass);
        entityRegistry.getConfig(resultClass).load(this, result, query.getNext());
        return result;
    }

    @SuppressWarnings("unchecked")
    public <T> void load(T entity, Consumer<RelationFetchBuilder<T>> consumer) {
        if (entity == null) {
            return;
        }
        Class<T> clazz = (Class<T>) entity.getClass();
        RelationFetchBuilder<T> builder = new RelationFetchBuilderImpl<>(clazz, entityRegistry);
        consumer.accept(builder);
        getConfiguration(clazz).load(this, Collections.singletonList(entity), builder.build());
    }

    @SuppressWarnings("unchecked")
    public <T> void load(List<T> entities, Consumer<RelationFetchBuilder<T>> consumer) {
        if (entities == null || entities.isEmpty()) {
            return;
        }
        Class<T> clazz = (Class<T>) entities.get(0).getClass();
        RelationFetchBuilder<T> builder = new RelationFetchBuilderImpl<>(clazz, entityRegistry);
        consumer.accept(builder);
        getConfiguration(clazz).load(this, entities, builder.build());
    }

    public <T> Optional<T> find(Class<T> clazz, Object id) {
        return getConfiguration(clazz).find(this, id);
    }

    @SuppressWarnings("unchecked")
    public <T> T save(T entity) {
        return getConfiguration((Class<T>) entity.getClass()).save(this, entity);
    }

    @SuppressWarnings("unchecked")
    public <T> void delete(T entity) {
        getConfiguration((Class<T>) entity.getClass()).delete(this, entity);
    }

    public <T> void deleteById(Class<T> clazz, Object id) {
        getConfiguration(clazz).deleteById(this, id);
    }

    private List<String> getColumns(PreparedStatement preparedStatement) throws SQLException {
        List<String> columns = new ArrayList<>();
        ResultSetMetaData meta = preparedStatement.getMetaData();
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            columns.add(meta.getColumnName(i));
        }
        return columns;
    }

    private Model getModel(ResultSet resultSet, List<String> columns, ConverterContext context) throws SQLException {
        Map<String, Object> attributes = new HashMap<>();
        for (String column : columns) {
            attributes.put(column, resultSet.getObject(column));
        }
        return Model.create().withConverter(context).withAttributes(attributes).done();
    }

    private void setParameters(PreparedStatement preparedStatement, List<Object> params) throws SQLException {
        for (int i = 1; i <= params.size(); i++) {
            preparedStatement.setObject(i, params.get(i - 1));
        }
    }
}
