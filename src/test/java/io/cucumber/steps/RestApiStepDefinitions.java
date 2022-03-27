package io.cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.storage.GlobalStorage;
import io.cucumber.table_type.*;
import io.cucumber.utils.JsonUtils;
import io.cucumber.utils.PropertyUtils;
import io.cucumber.utils.RestUtils;
import io.cucumber.utils.StringUtils;
import io.qameta.allure.Allure;
import org.junit.Assert;

import java.util.List;
import java.util.Properties;

public class RestApiStepDefinitions {

    private RestUtils restUtils = new RestUtils();
    private JsonUtils jsonUtils = new JsonUtils();
    private StringUtils stringUtils = new StringUtils();
    private PropertyUtils propertyUtils = new PropertyUtils();

    private String baseUrl, login, pass;

    private void init(String name) {
        String path = "env/" + System.getProperty("env") + "/" + name + ".properties";
        Properties properties = propertyUtils.readProperties(path);
        baseUrl = properties.getProperty("base_url");
        login = properties.getProperty("login");
        pass = properties.getProperty("pass");
    }

    @Given("API environment {string}")
    public void api_env(String name) {
        init(name);
        restUtils.initBaseUrl(baseUrl);
        restUtils.initRequestSpecification(true);
        if (!(login == null) && !login.isEmpty() && !(pass == null) && !pass.isEmpty()) {
            restUtils.initAuthentication(login, pass);
        } else restUtils.defaultAuth();
    }

    @Given("headers")
    public void headers(List<Header> headers) {
        restUtils.initHeaders(headers);
    }

    @Given("cookies")
    public void cookies(List<Cookie> cookies) {
        restUtils.initCookies(cookies);
    }

    @Given("body")
    public void body(List<JsonAttribute> jsonAttributes) throws Exception {
        restUtils.initBody(jsonAttributes);
        GlobalStorage.getStringStorage().put("body", jsonUtils.buildJson(jsonAttributes));
    }

    //todo doc
    @Given("SSL configuration")
    public void ssl_config() {
        restUtils.initSSLConfig();
    }

    @Then("send {word} {word} request to {string}")
    public void send_request(String protocol, String method, String path) {
        restUtils.httpRequest(protocol, method, stringUtils.setPlaceholders(path));
    }

    @And("store response as {string}")
    public void store_response(String responseName) {
        GlobalStorage.getResponseStorage().put(responseName, restUtils.getLastResponse());
        GlobalStorage.getStringStorage().put(responseName, restUtils.getLastResponse().asString());
    }

    @And("check {string} status code {int}")
    public void check_status_code(String responseName, int statusCode) {
        Assert.assertEquals(statusCode, GlobalStorage.getResponseStorage().get(responseName).statusCode());
    }

    @Given("json data {string}")
    public void json_data(String name, List<JsonAttribute> jsonAttributes) throws Exception {
        GlobalStorage.getStringStorage().put(name, jsonUtils.buildJson(jsonAttributes));
    }

    @And("compare {string} and {string} json data")
    public void compare_json_data(String left, String right, List<JsonData> jsonDataList) {
        for (JsonData jsonData : jsonDataList) {
            String type = jsonData.getType();
            if (type.equals("date")) {
                jsonUtils.compareDateJsonData(GlobalStorage.getStringStorage().get(left), GlobalStorage.getStringStorage().get(right), jsonData);
            } else {
                jsonUtils.compareJsonData(GlobalStorage.getStringStorage().get(left), GlobalStorage.getStringStorage().get(right), jsonData);
            }
        }
        Allure.addAttachment("Log", GlobalStorage.getAttachment());
    }

    @And("compare body and {string} json data")
    public void compare_body_json_data(String right, List<JsonData> jsonDataList) {
        for (JsonData jsonData : jsonDataList) {
            String type = jsonData.getType();
            if (type.equals("date")) {
                jsonUtils.compareDateJsonData(GlobalStorage.getStringStorage().get("body"), GlobalStorage.getStringStorage().get(right), jsonData);
            } else {
                jsonUtils.compareJsonData(GlobalStorage.getStringStorage().get("body"), GlobalStorage.getStringStorage().get(right), jsonData);
            }
        }
        Allure.addAttachment("Log", GlobalStorage.getAttachment());
    }

    @And("compare data")
    public void compare_data(List<Data> jsonDataList) {
        for (Data data : jsonDataList) {
            jsonUtils.compareData(data);
        }
        Allure.addAttachment("Log", GlobalStorage.getAttachment());
    }

    @And("check {string} json data")
    public void check_json_data(String requestName, List<JsonAttribute> jsonAttributes) throws Exception {
        for (JsonAttribute jsonAttribute : jsonAttributes) {
            String type = jsonAttribute.getType();
            if (type.equals("date")) {
                jsonUtils.assertDateJsonData(GlobalStorage.getStringStorage().get(requestName), jsonAttribute);
            } else {
                jsonUtils.assertJsonData(GlobalStorage.getStringStorage().get(requestName), jsonAttribute);
            }
        }
        Allure.addAttachment("Log", GlobalStorage.getAttachment());
    }

    @And("define {string} json data")
    public void define_json_data(String jsonName, List<JsonDataPlaceholder> jsonDataPlaceholders) {
        jsonUtils.defineJsonData(jsonName, jsonDataPlaceholders);
    }

    //todo doc
    @And("relax HTTPS validation")
    public void relax_https_validation() {
        restUtils.relaxHTTPSValidation();
    }
}
