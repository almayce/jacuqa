package io.cucumber.table_type;

public class JsonData {

    private Object left;
    private Object right;
    private String type;

    public JsonData(Object left, Object right, String type) {
        this.left = left;
        this.right = right;
        this.type = type;
    }

    public Object getLeft() {
        return left;
    }

    public void setLeft(Object left) {
        this.left = left;
    }

    public Object getRight() {
        return right;
    }

    public void setRight(Object right) {
        this.right = right;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
