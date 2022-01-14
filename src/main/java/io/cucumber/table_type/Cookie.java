package io.cucumber.table_type;

public class Cookie {

    private String cookie;
    private String value;

    public Cookie(String cookie, String value) {
        this.cookie = cookie;
        this.value = value;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
