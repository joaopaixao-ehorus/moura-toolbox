package moura.sdp.toolbox.query.sql;

import moura.sdp.toolbox.query.sql.SqlSelect;
import moura.sdp.toolbox.query.sql.SqlSelectField;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlSelectImpl implements SqlSelect {

    private List<SqlSelectField> fields;

    @Override
    public List<SqlSelectField> getFields() {
        return fields != null ? Collections.unmodifiableList(fields) : null;
    }

    public void addField(SqlSelectField field) {
        if (fields == null) {
            fields = new ArrayList<>();
        }
        fields.add(field);
    }

    public void addFields(List<SqlSelectField> fields) {
        if (this.fields == null) {
            this.fields = new ArrayList<>();
        }
        this.fields.addAll(fields);
    }
}
