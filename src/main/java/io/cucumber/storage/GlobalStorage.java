package io.cucumber.storage;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;

public class GlobalStorage {

    private static HashMap<String, List<HashMap<String, Object>>> listOfMapsStorage = new ListOfMapsStorage<>();
    private static HashMap<String, String> stringStorage = new StringStorage<>();
    private static HashMap<String, Response> responseStorage = new HashMap<>();

    private GlobalStorage() {
        //
    }

    public static HashMap<String, String> getStringStorage() {
        return stringStorage;
    }

    public static HashMap<String, Response> getResponseStorage() {
        return responseStorage;
    }

    public static HashMap<String, List<HashMap<String, Object>>> getListOfMapsStorage() {
        return listOfMapsStorage;
    }
}
