package io.cucumber.table_type;

public class DataPlaceholder {

    String placeholder;
    String value;

    public DataPlaceholder(String placeholder, String value) {
        this.placeholder = placeholder;
        this.value = value;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
