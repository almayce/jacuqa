package io.cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.storage.GlobalStorage;
import io.cucumber.table_type.DataPlaceholder;
import io.cucumber.table_type.DatabaseCellPlaceholder;
import io.cucumber.table_type.JsonDataPlaceholder;
import io.cucumber.table_type.JsonDataRandomPlaceholder;
import io.cucumber.utils.PropertyUtils;
import io.cucumber.utils.RandomUtils;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Properties;

public class PlaceholderStepDefinitions {

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

    @And("define {string} db response data")
    public void define_db_response_data(String responseName, List<DatabaseCellPlaceholder> databaseCellPlaceholders) {
        for (DatabaseCellPlaceholder databaseCellPlaceholder : databaseCellPlaceholders) {
            String column = databaseCellPlaceholder.getColumnName();
            if (GlobalStorage.getListOfMapsStorage().get(responseName).size() == 0) {
                Assertions.fail("DB response: '" + responseName + "' is empty! check steps above!");
            }
            Object value = GlobalStorage.getListOfMapsStorage()
                    .get(responseName)
                    .get(databaseCellPlaceholder.getRowNumber() - 1)
                    .get(column);
            String valueString = String.valueOf(value);
            GlobalStorage.getStringStorage().put(databaseCellPlaceholder.getPlaceholder(), valueString);
        }
    }
}
