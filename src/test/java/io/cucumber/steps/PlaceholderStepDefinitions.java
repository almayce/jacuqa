package io.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.storage.GlobalStorage;
import io.cucumber.table_type.DataPlaceholder;
import io.cucumber.table_type.JsonDataRandomPlaceholder;
import io.cucumber.utils.FileUtils;
import io.cucumber.utils.PropertyUtils;
import io.cucumber.utils.RandomUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class PlaceholderStepDefinitions {

    private FileUtils fileUtils = new FileUtils();
    private PropertyUtils propertyUtils = new PropertyUtils();

    @Given("data")
    public void data(List<DataPlaceholder> jsonDataPlaceholders) {
        for (DataPlaceholder jsonDataPlaceholder : jsonDataPlaceholders) {
            GlobalStorage.getStringStorage().put(jsonDataPlaceholder.getPlaceholder(), jsonDataPlaceholder.getValue());
        }
    }

    @Given("random data")
    public void random_data(List<JsonDataRandomPlaceholder> jsonDataRandomPlaceholders) {
        RandomUtils randomUtils = new RandomUtils();
        for (JsonDataRandomPlaceholder jsonDataRandomPlaceholder : jsonDataRandomPlaceholders) {
            String type = jsonDataRandomPlaceholder.getType();
            int length = Integer.parseInt(jsonDataRandomPlaceholder.getLength());
            GlobalStorage.getStringStorage().put(jsonDataRandomPlaceholder.getPlaceholder(), randomUtils.getRandomValue(type, length));
        }
    }

    @Given("properties data {string}")
    public void properties_data(String name) {
        String path = "data/" + System.getProperty("env") + "/" + name + ".properties";
        Properties properties = propertyUtils.readProperties(path);
        for (final String propertyName : properties.stringPropertyNames()) {
            GlobalStorage.getStringStorage().put(propertyName, properties.getProperty(propertyName));
        }
    }

    @Then("write properties data {string}")
    public void write_properties_data(String name) {
        File artifact = new File("src/test/resources/data/" + System.getProperty("env") + "/artifacts/" + name + ".properties");
        try {
            fileUtils.createNewFile(artifact);
        } catch (IOException e) {
            e.printStackTrace();
        }
        propertyUtils.writeProperties(artifact.getAbsolutePath(), GlobalStorage.getStringStorage());
    }
}
