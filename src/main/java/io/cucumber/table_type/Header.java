package io.cucumber.table_type;

public class Header {

    private String header;
    private String value;

    public Header(String header, String value) {
        this.header = header;
        this.value = value;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
