package io.cucumber.utils;

import io.cucumber.core.exception.CucumberException;
import io.cucumber.table_type.Cookie;
import io.cucumber.table_type.Header;
import io.cucumber.table_type.JsonAttribute;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.security.*;
import java.util.List;

public class RestUtils {

    Logger log = Logger.getLogger(this.getClass().getSimpleName());

    private RequestSpecification requestSpecification = null;
    private Response response = null;

    private StringUtils stringUtils = new StringUtils();
    private JsonUtils jsonUtils = new JsonUtils();

    public void defaultAuth() {
        RestAssured.authentication = RestAssured.DEFAULT_AUTH;
        log.info("Default Auth");
    }

    public void initAuthentication(String login, String pass) {
        requestSpecification.auth().preemptive().basic(login, pass);
        log.info("Basic Auth");
    }

    public void initSSLConfig() {
        requestSpecification.config(RestAssured.config().sslConfig(getKeyStoreConfig()));
    }

    public void initBaseUrl(String baseUrl) {
        RestAssured.baseURI = baseUrl;
    }

    public void initRequestSpecification(boolean logsEnabled) {
        requestSpecification = RestAssured.given();
        setDefaultContentType();
        if (logsEnabled) {
            requestSpecification
                    .filter(new RequestLoggingFilter())
                    .filter(new ResponseLoggingFilter());
        }
    }

    private void setDefaultContentType() {
        requestSpecification.contentType("application/json; charset=utf-8");
    }

    public void initHeaders(List<Header> headers) {

        for (Header header : headers) {
            requestSpecification.header(header.getHeader(), stringUtils.setPlaceholders(header.getValue()));
        }
    }

    public void initCookies(List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            requestSpecification.cookie(cookie.getCookie(), stringUtils.setPlaceholders(cookie.getValue()));
        }
    }

    public void initBody(List<JsonAttribute> jsonAttributes) throws Exception {
        requestSpecification.body(jsonUtils.buildJson(jsonAttributes));
    }

    public void httpRequest(String protocol, String method, String endpointPath) {
        if (requestSpecification == null) {
            throw new CucumberException("Init API environment before each request!");
        }
        String absoluteUrl = RestAssured.baseURI + endpointPath;
        Allure.addAttachment("Absolute URL", absoluteUrl);
        response = requestSpecification
                .request(method, absoluteUrl);
        Allure.addAttachment("Response", response.prettyPrint());
        requestSpecification = null;
    }

    public Response getLastResponse() {
        return response;
    }

    public void relaxHTTPSValidation() {
        RestAssured.config = RestAssured.config().sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation());
    }

    public SSLConfig getKeyStoreConfig() {
        return getKeyStoreConfig("keystore.p12");
    }

    //todo refactoring
    public SSLConfig getKeyStoreConfig(String keystoreFileName) {
        relaxHTTPSValidation();
        KeyStore keyStore = null;
        KeyStore trustStore = null;
        String password = "changeit";
        try {
            String path = "env/" + System.getProperty("env") + "/keystore/" + keystoreFileName;
            URL resource = getClass().getClassLoader().getResource(path);
            log.info(resource.toURI());
            File file = new File(resource.toURI());
            FileInputStream fis = new FileInputStream(file);
            keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(
                    fis,
                    password.toCharArray()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String path = "env/" + System.getProperty("env") + "/keystore/truststore.jks";
            URL resource = getClass().getClassLoader().getResource(path);
            log.info(resource.toURI());
            File file = new File(resource.toURI());
            FileInputStream fis = new FileInputStream(file);
            trustStore = KeyStore.getInstance("JKS");
            trustStore.load(
                    fis,
                    password.toCharArray()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        SSLSocketFactory clientAuthFactory = null;
        try {
            clientAuthFactory = new SSLSocketFactory(keyStore, password, trustStore);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        SSLConfig sslConfig =
                RestAssuredConfig.config().getSSLConfig().with().sslSocketFactory(clientAuthFactory).and().allowAllHostnames();
        return sslConfig;
    }
}
