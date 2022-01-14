package io.cucumber.table_type;

public class DatabaseCellPlaceholder {

    String placeholder;
    String columnName;
    int rowNumber;

    public DatabaseCellPlaceholder(String placeholder, String columnName, int rowNumber) {
        this.placeholder = placeholder;
        this.columnName = columnName;
        this.rowNumber = rowNumber;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
}
