package io.cucumber.table_type;

public class JsonData {

    private Object leftPath;
    private Object rightPath;
    private String type;

    public JsonData(Object leftPath, Object rightPath, String type) {
        this.leftPath = leftPath;
        this.rightPath = rightPath;
        this.type = type;
    }

    public Object getLeftPath() {
        return leftPath;
    }

    public void setLeftPath(Object leftPath) {
        this.leftPath = leftPath;
    }

    public Object getRightPath() {
        return rightPath;
    }

    public void setRightPath(Object rightPath) {
        this.rightPath = rightPath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
