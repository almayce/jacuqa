package io.cucumber.steps;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.selenoid.Selenoid;
import io.cucumber.utils.PropertyUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.Properties;

public class HookDefinitions {

    private PropertyUtils propertyUtils = new PropertyUtils();

    @Before
    public void before() {
        configLogs();
        initSelenide();
        initSelenoid();
    }

    @After
    public void after() {
        Selenide.closeWebDriver();
    }

    private void configLogs() {
        Properties props = new Properties();
        props.put("log4j.additivity.org.apache", "false");
        PropertyConfigurator.configure(props);
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.ALL);
    }

    private void initSelenide() {
        String path = "selenide.properties";
        Properties properties = propertyUtils.readProperties(path);
        Configuration.browser = properties.getProperty("selenide.browser", "chrome");
        if (Boolean.parseBoolean(System.getProperty("parallel", "false"))) {
            Configuration.browser = Selenoid.class.getCanonicalName();
        }
        Configuration.driverManagerEnabled = Boolean.parseBoolean(properties.getProperty("selenide.driverManagerEnabled", "true"));
        Configuration.startMaximized = Boolean.parseBoolean(properties.getProperty("selenide.startMaximized", "true"));
        Configuration.holdBrowserOpen = Boolean.parseBoolean(properties.getProperty("selenide.holdBrowserOpen", "false"));
        Configuration.savePageSource = Boolean.parseBoolean(properties.getProperty("selenide.savePageSource", "false"));
        Configuration.screenshots = Boolean.parseBoolean(properties.getProperty("selenide.screenshots", "false"));
        Configuration.timeout = Long.parseLong(properties.getProperty("selenide.timeout", "60000"));
    }

    private void initSelenoid() {
        String path = "selenoid.properties";
        Properties properties = propertyUtils.readProperties(path);
        System.setProperty("selenoid.browserName", properties.getProperty("selenoid.browserName","chrome"));
        System.setProperty("selenoid.version", properties.getProperty("selenoid.browserName","91.0"));
        System.setProperty("selenoid.enableVNC", properties.getProperty("selenoid.browserName","true"));
        System.setProperty("selenoid.enableVideo", properties.getProperty("selenoid.browserName","true"));
        System.setProperty("selenoid.host", properties.getProperty("selenoid.browserName","http://dev-jenkins-01.finto.io:4444"));
        System.setProperty("selenoid.localhost", properties.getProperty("selenoid.browserName","http://localhost:4444"));
        System.setProperty("selenoid.dimensionWidth", properties.getProperty("selenoid.browserName","1920"));
        System.setProperty("selenoid.dimensionHeight", properties.getProperty("selenoid.browserName","1080"));
    }

//    @After
//    public void attachLogs(Scenario scenario) {
//        try {
//            restUtils.initBaseUri(System.getProperty("consolePath"));
//            restUtils.initRequestSpecification(false);
//            restUtils.httpRequest("", "GET", "");
//            scenario.attach(restUtils.getLastResponse().asByteArray(), "text/plain;charset=utf-8", "Log");
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        }
//    }
}
