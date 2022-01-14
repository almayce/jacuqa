package io.cucumber.utils;

import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;

import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {

    Logger log = Logger.getLogger(this.getClass().getSimpleName());

    public Properties readProperties(String path) {
        Properties properties = new Properties();
        log.info(path);
        InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        try {
            if (is != null)
                properties.load(is);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
        return properties;
    }
}
