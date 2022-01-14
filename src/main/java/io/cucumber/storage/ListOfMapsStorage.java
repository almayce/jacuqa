package io.cucumber.storage;

import io.qameta.allure.Allure;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

public class ListOfMapsStorage<K, L> extends HashMap<String, List<HashMap<String, Object>>> {

    Logger log = Logger.getLogger(this.getClass().getSimpleName());

    @Override
    public List<HashMap<String, Object>> put(String path, List<HashMap<String, Object>> value) {
        log.info(path + " = " + value);
        Allure.addAttachment(path.toString(), value.toString());
        return super.put(path, value);
    }
}
