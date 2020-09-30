package moura.sdp.toolbox.orm;

import moura.sdp.toolbox.converter.Converter;
import moura.sdp.toolbox.query.dialect.Dialect;

import javax.sql.DataSource;

public class DatabaseBuilder {

    private DataSource dataSource;
    private EntityRegistry entityRegistry;
    private Dialect dialect;
    private Converter converter;

    public static DatabaseBuilder create() {
        return new DatabaseBuilder();
    }

    public DatabaseBuilder withDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public DatabaseBuilder withEntityRegistry(EntityRegistry entityRegistry) {
        this.entityRegistry = entityRegistry;
        return this;
    }

    public DatabaseBuilder withDialect(Dialect dialect) {
        this.dialect = dialect;
        return this;
    }

    public DatabaseBuilder withConverter(Converter converter) {
        this.converter = converter;
        return this;
    }

    public Database done() {
        return new Database(dataSource, entityRegistry, dialect, converter);
    }

}
