package moura.sdp.toolbox.orm;

import java.util.List;

public class StatementResultImpl implements StatementResult {

    private List<Object> generatedKeys;
    private int updatedRows;

    @Override
    public List<Object> getGeneratedKeys() {
        return generatedKeys;
    }

    public void setGeneratedKeys(List<Object> generatedKeys) {
        this.generatedKeys = generatedKeys;
    }

    @Override
    public int getUpdatedRows() {
        return updatedRows;
    }

    public void setUpdatedRows(int updatedRows) {
        this.updatedRows = updatedRows;
    }
}
