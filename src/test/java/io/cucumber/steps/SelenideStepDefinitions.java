package io.cucumber.steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.utils.PropertyUtils;
import io.cucumber.utils.StringUtils;
import org.openqa.selenium.By;

import java.util.Properties;

public class SelenideStepDefinitions {

    private StringUtils stringUtils = new StringUtils();
    private PropertyUtils propertyUtils = new PropertyUtils();

    private String baseUrl;

    private void init(String name) {
        String path = "env/" + System.getProperty("env") + "/" + name + ".properties";
        Properties properties = propertyUtils.readProperties(path);
        baseUrl = properties.getProperty("base_url");
    }

    @Given("UI environment {string}")
    public void ui_env(String name) {
        init(name);
        Configuration.baseUrl = this.baseUrl;
    }

    @Then("open base url")
    public void open_base_url() {
        Selenide.open();
    }

    @Then("open base url + {string}")
    public void open_base_url_plus_endpoint(String url) {
        Selenide.open(stringUtils.setPlaceholders(url));
    }

    @Then("open url {string}")
    public void open(String url) {
        Selenide.open(stringUtils.setPlaceholders(url));
    }

    @And("refresh")
    public void refresh() {
        Selenide.refresh();
    }

    @And("back")
    public void back() {
        Selenide.back();
    }

    @And("forward")
    public void forward() {
        Selenide.forward();
    }

    @And("confirm")
    public void confirm() {
        Selenide.confirm();
    }

    @And("prompt")
    public void prompt() {
        Selenide.prompt();
    }

    @And("dismiss")
    public void dismiss() {
        Selenide.dismiss();
    }

    @And("switch to window {int}")
    public void switch_to_window(int index) {
        Selenide.switchTo().window(index);
    }

    @Then("element by xpath {string} click")
    public void element_click(String xpath) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath))).click();
    }

    @Then("element by xpath {string} set value {string}")
    public void element_set_value(String xpath, String value) {
        SelenideElement selenideElement = Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath)));
        selenideElement.clear();
        selenideElement.sendKeys(stringUtils.setPlaceholders(value));
    }

    //

    @And("element by xpath {string} should be visible")
    public void element_should_be_visible(String xpath) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath))).shouldBe(Condition.visible);
    }

    @And("element by xpath {string} should be hidden")
    public void element_should_be_hidden(String xpath) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath))).shouldBe(Condition.hidden);
    }

    @And("element by xpath {string} should be exist")
    public void element_should_be_exist(String xpath) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath))).shouldBe(Condition.exist);
    }

    @And("element by xpath {string} should be empty")
    public void element_should_be_empty(String xpath) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath))).shouldBe(Condition.empty);
    }

    @And("element by xpath {string} should be focused")
    public void element_should_be_focused(String xpath) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath))).shouldBe(Condition.focused);
    }

    @And("element by xpath {string} should be enabled")
    public void element_should_be_enabled(String xpath) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath))).shouldBe(Condition.enabled);
    }

    @And("element by xpath {string} should be disabled")
    public void element_should_be_disabled(String xpath) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath))).shouldBe(Condition.disabled);
    }

    @And("element by xpath {string} should be selected")
    public void element_should_be_selected(String xpath) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath))).shouldBe(Condition.selected);
    }

    @And("element by xpath {string} should be checked")
    public void element_should_be_checked(String xpath) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath))).shouldBe(Condition.checked);
    }

    @And("element by xpath {string} should have image")
    public void element_should_have_image(String xpath) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath))).shouldHave(Condition.image);
    }

    @And("element by xpath {string} should have attribute key {string} value {string}")
    public void element_should_have_attribute(String xpath, String key, String value) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath)))
                .shouldHave(Condition.attribute(stringUtils.setPlaceholders(key), stringUtils.setPlaceholders(value)));
    }

    //

    @And("element by xpath {string} should not be visible")
    public void element_should_not_be_visible(String xpath) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath))).shouldNotBe(Condition.visible);
    }

    @And("element by xpath {string} should not be hidden")
    public void element_should_not_be_hidden(String xpath) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath))).shouldNotBe(Condition.hidden);
    }

    @And("element by xpath {string} should not be exist")
    public void element_should_not_be_exist(String xpath) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath))).shouldNotBe(Condition.exist);
    }

    @And("element by xpath {string} should not be empty")
    public void element_should_not_be_empty(String xpath) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath))).shouldNotBe(Condition.empty);
    }

    @And("element by xpath {string} should not be focused")
    public void element_should_not_be_focused(String xpath) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath))).shouldNotBe(Condition.focused);
    }

    @And("element by xpath {string} should not be enabled")
    public void element_should_not_be_enabled(String xpath) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath))).shouldNotBe(Condition.enabled);
    }

    @And("element by xpath {string} should not be disabled")
    public void element_should_not_be_disabled(String xpath) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath))).shouldNotBe(Condition.disabled);
    }

    @And("element by xpath {string} should not be selected")
    public void element_should_not_be_selected(String xpath) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath))).shouldNotBe(Condition.selected);
    }

    @And("element by xpath {string} should not be checked")
    public void element_should_not_be_checked(String xpath) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath))).shouldNotBe(Condition.checked);
    }

    @And("element by xpath {string} should not have image")
    public void element_should_not_have_image(String xpath) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath))).shouldNotHave(Condition.image);
    }

    @And("element by xpath {string} should not have attribute key {string} value {string}")
    public void element_should_not_have_attribute(String xpath, String key, String value) {
        Selenide.element(By.xpath(stringUtils.setPlaceholders(xpath)))
                .shouldNotHave(Condition.attribute(stringUtils.setPlaceholders(key), stringUtils.setPlaceholders(value)));
    }
}
