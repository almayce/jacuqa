package io.cucumber.table_type;

public class JsonDataPlaceholder {

    String placeholder;
    String path;

    public JsonDataPlaceholder(String placeholder, String path) {
        this.placeholder = placeholder;
        this.path = path;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
