package io.cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.storage.GlobalStorage;
import io.cucumber.utils.StringUtils;

public class StepDefinitions {

    private StringUtils stringUtils = new StringUtils();

    @And("wait {int} seconds")
    public void wait(int seconds) {
        try {
            Thread.sleep(1000L * seconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @And("extract {string} by regex {string} from {string}")
    public void extract_by_regex(String name, String regex, String source) {
        GlobalStorage.getStringStorage().put(name, stringUtils.extractByRegex(regex, source));
    }
}
