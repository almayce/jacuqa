package io.cucumber.table_type;

public class JsonDataRandomPlaceholder {

    String placeholder;
    String length;
    String type;

    public JsonDataRandomPlaceholder(String placeholder, String length, String type) {
        this.placeholder = placeholder;
        this.length = length;
        this.type = type;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
