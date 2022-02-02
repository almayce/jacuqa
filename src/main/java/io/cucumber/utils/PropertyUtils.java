package io.cucumber.utils;

import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Properties;

public class PropertyUtils {

    Logger log = Logger.getLogger(this.getClass().getSimpleName());

    public Properties readProperties(String path) {
        log.info(path);
        Properties properties = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {
            if (is != null) {
                properties.load(is);
            }
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
        return properties;
    }

    public void writeProperties(String path, HashMap<String, String> data) {
        log.info(path);
        Properties properties = new Properties();
        properties.putAll(data);
        try (OutputStream os = new FileOutputStream(path)) {
            properties.store(os, "write comment");
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}
