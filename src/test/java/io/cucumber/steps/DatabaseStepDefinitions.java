package io.cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.storage.GlobalStorage;
import io.cucumber.table_type.DatabaseCell;
import io.cucumber.utils.DatabaseUtils;
import io.cucumber.utils.ListOfMapsUtils;
import io.cucumber.utils.PropertyUtils;
import io.cucumber.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class DatabaseStepDefinitions {

    private StringUtils stringUtils = new StringUtils();
    private PropertyUtils propertyUtils = new PropertyUtils();
    private ListOfMapsUtils listOfMapsUtils = new ListOfMapsUtils();
    private DatabaseUtils databaseUtils;
    private String driver, url, user, pass, timeout;

    private void init(String name) {
        String path = "env/" + System.getProperty("env") + "/" + name + ".properties";
        Properties properties = propertyUtils.readProperties(path);
        driver = properties.getProperty("type");
        url = properties.getProperty("url");
        user = properties.getProperty("user");
        pass = properties.getProperty("pass");
        timeout = properties.getProperty("timeout_seconds");
    }

    @Given("DB environment {string}")
    public void database(String name) throws Exception {
        init(name);
        databaseUtils = new DatabaseUtils();
        if (driver.equalsIgnoreCase("mysql")) {
            databaseUtils.initMySqlDriver(url, user, pass);
        } else if (driver.equalsIgnoreCase("oracle")) {
            databaseUtils.initOracleDriver(url, user, pass);
        } else throw new Exception("Please define known type of database: mysql or oracle");
    }

    @Then("execute query {string} and store response as {string}")
    public void execute_query(String query, String responseName) {
        GlobalStorage.getListOfMapsStorage().put(responseName, databaseUtils.executeQuery(query, Integer.parseInt(timeout)));
    }

    @And("check {string} db response data")
    public void check_db_response_data(String responseName, List<DatabaseCell> databaseCells) {
        List<HashMap<String, Object>> response = GlobalStorage.getListOfMapsStorage().get(responseName);
        for (DatabaseCell databaseCell : databaseCells) {
            String expectedString;
            try {
                expectedString = stringUtils.setPlaceholders(databaseCell.getValue().toString());
            } catch (NullPointerException npe) {
                listOfMapsUtils.checkResult(response, databaseCell.getColumn(), "", databaseCell.getRow()); //empty values handling
                continue;
            }
            listOfMapsUtils.checkResult(response, databaseCell.getColumn(), expectedString, databaseCell.getRow());
        }
    }
}
