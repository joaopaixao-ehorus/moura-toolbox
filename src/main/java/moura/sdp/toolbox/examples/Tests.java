package moura.sdp.toolbox.examples;

import com.mysql.cj.jdbc.MysqlDataSource;
import moura.sdp.toolbox.converter.Converter;
import moura.sdp.toolbox.orm.*;
import moura.sdp.toolbox.query.dialect.Dialect;
import moura.sdp.toolbox.query.dialect.MysqlDialect;

import java.util.List;

import static moura.sdp.toolbox.orm.RelationConfiguration.buildList;

public class Tests {

    public static void main(String[] args) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setDatabaseName("toolbox_test");
        dataSource.setUser("root");
        dataSource.setPassword("12345");
        dataSource.setServerName("localhost");

        Converter converter = new Converter();
        Dialect dialect = new MysqlDialect();
        EntityRegistry registry = new EntityRegistry();

        registry.register(new User.UserConfig());
        registry.register(new Compra.CompraConfig());

        Database database = DatabaseBuilder.create()
                .withConverter(converter)
                .withDataSource(dataSource)
                .withEntityRegistry(registry)
                .withDialect(dialect)
                .done();

        List<User> users = database.from(User.class).with("compras").all();

        System.out.println("Break");
    }

}

class User {

    public static class UserConfig extends BaseEntityConfiguration<User> {

        private static final List<RelationConfiguration<?, User>> relations = buildList(User.class)
                .hasMany(Compra.class)
                .belongsTo(Empresa.class)
                .build();

        @Override
        public List<RelationConfiguration<?, User>> getRelations() {
            return relations;
        }

    }

    private Long id;
    private String name;
    private Integer age;
    private List<Compra> compras;
    private Empresa empresa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}

class Compra {

    static class CompraConfig extends BaseEntityConfiguration<Compra> {}

    private Long id;
    private String name;
    private Double total;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

class Empresa {

    public static class EmpresaConfig extends BaseEntityConfiguration<Empresa> {}

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}