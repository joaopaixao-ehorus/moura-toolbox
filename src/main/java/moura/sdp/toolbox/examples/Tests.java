package moura.sdp.toolbox.examples;

import com.mysql.cj.jdbc.MysqlDataSource;
import moura.sdp.toolbox.converter.Converter;
import moura.sdp.toolbox.orm.*;
import moura.sdp.toolbox.query.dialect.Dialect;
import moura.sdp.toolbox.query.dialect.MysqlDialect;

import java.util.List;

public class Tests {

    public static class User extends BaseEntityConfiguration<User> {

        private Long id;
        private String name;
        private Integer age;
        private List<Compra> compras;
        private Empresa empresa;

        @Override
        public void configure() {
            addRelation(hasMany(Compra.class));
            addRelation(belongsTo(Empresa.class));
        }

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

    public static class Compra extends BaseEntityConfiguration<Compra> {

        private Long id;
        private String name;
        private Double total;

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
    }

    public static class Empresa extends BaseEntityConfiguration<Empresa> {

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

    public static void main(String[] args) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setDatabaseName("toolbox_test");
        dataSource.setUser("root");
        dataSource.setPassword("12345");
        dataSource.setServerName("localhost");

        Converter converter = new Converter();
        Dialect dialect = new MysqlDialect();
        EntityRegistry registry = new EntityRegistry();

        registry.register(new User());
        registry.register(new Compra());

        Database database = DatabaseBuilder.create()
                .withConverter(converter)
                .withDataSource(dataSource)
                .withEntityRegistry(registry)
                .withDialect(dialect)
                .done();

        database.deleteById(User.class, 7);

        System.out.println("Break");
    }

}