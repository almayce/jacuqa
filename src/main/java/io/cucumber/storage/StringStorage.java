package io.cucumber.storage;

import io.qameta.allure.Allure;
import org.apache.log4j.Logger;

import java.util.HashMap;

public class StringStorage<K, V> extends HashMap<String, String> {

    Logger log = Logger.getLogger(this.getClass().getSimpleName());

    @Override
    public String put(String key, String value) {
        log.info(key + " = " + value);
        Allure.addAttachment(key.toString(), value.toString());
        return super.put(key, value);
    }
}
