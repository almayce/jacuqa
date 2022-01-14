package io.cucumber.table_type;

public class DatabaseCell {

    String column;
    Object value;
    int row;

    public DatabaseCell(String column, Object value, int row) {
        this.column = column;
        this.value = value;
        this.row = row;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
