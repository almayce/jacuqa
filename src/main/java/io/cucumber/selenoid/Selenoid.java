package io.cucumber.selenoid;

import com.codeborne.selenide.WebDriverProvider;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class Selenoid implements WebDriverProvider {

    private static String videoUUID = null;
    private static DesiredCapabilities capabilities = null;

    private static String host() {
        if (System.getProperty("user.name").contains("jenkins"))
            return System.getProperty("leadium.selenoid.host");
        else
            return System.getProperty("leadium.selenoid.localhost");
    }

    @Nonnull
    @Override
    public WebDriver createDriver(DesiredCapabilities desiredCapabilities) {

        videoUUID = UUID.randomUUID().toString();

        LoggingPreferences loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        loggingPreferences.enable(LogType.CLIENT, Level.ALL);
        loggingPreferences.enable(LogType.DRIVER, Level.ALL);

        int dimensionWidth = Integer.parseInt(System.getProperty("leadium.selenoid.dimensionWidth", "1280"));
        int dimensionHeight = Integer.parseInt(System.getProperty("leadium.selenoid.dimensionHeight", "1024"));

        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("enableVNC", Boolean.parseBoolean(System.getProperty("leadium.selenoid.enableVNC", "true")));
        selenoidOptions.put("enableVideo", Boolean.parseBoolean(System.getProperty("leadium.selenoid.enableVideo", "true")));
        selenoidOptions.put("screenResolution", "${dimensionWidth}x${dimensionHeight}x24");
        selenoidOptions.put("videoScreenSize", "${dimensionWidth}x${dimensionHeight}");
        selenoidOptions.put("videoName", "video_$videoUUID.mp4");

        capabilities.setCapability("browserName", System.getProperty("leadium.selenoid.browserName", "chrome"));
        capabilities.setCapability("browserVersion", System.getProperty("leadium.selenoid.version", "91.0"));
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);
        capabilities.setCapability("selenoid:options", selenoidOptions);

        try {
            RemoteWebDriver driver = new RemoteWebDriver(
                    URI.create("${host()!!}/wd/hub").toURL(),
                    capabilities
            );
            driver.manage().window().setSize(new Dimension(dimensionWidth, dimensionHeight));
            driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
            driver.setFileDetector(new LocalFileDetector());
            return driver;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inner Allure object that provides correct display name for VNC
     * and attached video with the specified host.
     */
    public static class Allure extends TestWatcher {

        private void attachHtml(String name, String body) {
            io.qameta.allure.Allure.addAttachment(name, "HTML", ".html", body);
        }

        @Override
        protected void starting(Description description) {
            capabilities.setCapability("name", description.getDisplayName());
        }

        @Override
        protected void finished(Description description) {
            Boolean parallel = Boolean.parseBoolean(System.getProperty("parallel", "false"));
            Boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
            if (videoUUID != null && parallel && !headless)
                attachHtml("Video", host() + "/video/video_$videoUUID.mp4");
        }
    }
}
