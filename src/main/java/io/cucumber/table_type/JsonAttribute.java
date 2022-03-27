package io.cucumber.table_type;

public class JsonAttribute {

    private String path;
    private String value;
    private String type;

    public JsonAttribute(String path, String value, String type) {
        this.path = path;
        this.value = value;
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
