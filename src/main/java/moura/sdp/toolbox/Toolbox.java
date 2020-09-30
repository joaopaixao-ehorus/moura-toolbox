package moura.sdp.toolbox;

import com.mysql.cj.jdbc.MysqlDataSource;
import moura.sdp.toolbox.converter.Converter;
import moura.sdp.toolbox.orm.Database;
import moura.sdp.toolbox.orm.DatabaseBuilder;
import moura.sdp.toolbox.orm.EntityRegistry;
import moura.sdp.toolbox.orm.Model;
import moura.sdp.toolbox.query.dialect.Dialect;
import moura.sdp.toolbox.query.dialect.MysqlDialect;

import java.util.List;

public class Toolbox {

    static class Pessoa {
        int id;
        String name;
        int age;
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

        converter.register(Toolbox::recordToPessoa, Model.class, Pessoa.class);

        Database database = DatabaseBuilder.create()
                .withConverter(converter)
                .withDataSource(dataSource)
                .withEntityRegistry(registry)
                .withDialect(dialect)
                .done();

        List<Pessoa> a = database.query().selectAll().from("user").where("age").greaterThanOrEqual(10).all(Pessoa.class);

        for (Pessoa pessoa : a) {
            System.out.println(pessoa.id + " " + pessoa.name + " " + pessoa.age);
        }

        List<Model> b = database.query().selectAll().from("user").where("age").greaterThanOrEqual(10).all();

        for (Model pessoa : b) {
            System.out.println(pessoa.getInt("id") + " " + pessoa.getString("name") + " " + pessoa.getInt("age"));
        }

    }

    public static Pessoa recordToPessoa(Model model, Object params) {
        Pessoa pessoa = new Pessoa();
        pessoa.id = model.getInt("id");
        pessoa.name = model.getString("name");
        pessoa.age = model.getInt("age");
        return pessoa;
    }

}
