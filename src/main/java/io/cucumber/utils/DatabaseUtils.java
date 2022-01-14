package io.cucumber.utils;

import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseUtils {

    Logger log = Logger.getLogger(this.getClass().getSimpleName());

    private Connection connection = null;
    private Statement statement = null;

    public void initMySqlDriver(String url, String user, String password) {
        init("com.mysql.cj.jdbc.Driver", url, user, password);
    }

    public void initOracleDriver(String url, String user, String password) {
        init("oracle.jdbc.driver.OracleDriver", url, user, password);
    }

    private void init(String driverClassName, String url, String user, String password) {
        if (connection != null && statement != null) close();
        try {
            Class.forName(driverClassName).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(url + "?autoReconnect=true&useSSL=false", user, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            Assertions.fail(e.getMessage());
        }
    }

    public List<HashMap<String, Object>> executeQuery(String query, int seconds) {
        String preparedQuery = new StringUtils().setPlaceholders(query);
        log.info(preparedQuery);
        for (int i = seconds; i > 0; i--) {
            if (getResultListOfMaps(preparedQuery).isEmpty()) {
                sleep(1_000L);
                log.info("Waiting for DB response: " + i);
            } else {
                return getResultListOfMaps(preparedQuery);
            }
        }
        throw new AssertionError("DB response is empty!");
    }

    private ArrayList<HashMap<String, Object>> getResultListOfMaps(String query) {
        ArrayList<HashMap<String, Object>> rows = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData meta = resultSet.getMetaData();
            int columnCount = meta.getColumnCount();
            ArrayList<String> columnNames = new ArrayList<>();
            for (int index = 1; index <= columnCount; index++)
                columnNames.add(meta.getColumnName(index));
            while (resultSet.next()) {
                HashMap<String, Object> row = new HashMap<>();
                for (String name : columnNames) {
                    Object val = resultSet.getObject(name);
                    row.put(name, val);
                }
                rows.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    private void sleep(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
