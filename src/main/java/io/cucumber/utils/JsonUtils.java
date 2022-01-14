package io.cucumber.utils;

import com.github.wnameless.json.unflattener.JsonUnflattener;
import io.cucumber.storage.GlobalStorage;
import io.cucumber.table_type.JsonAttribute;
import io.cucumber.table_type.JsonData;
import io.cucumber.table_type.JsonDataPlaceholder;
import io.qameta.allure.Allure;
import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class JsonUtils {

    Logger log = Logger.getLogger(this.getClass().getSimpleName());

    private JSONObject rootObject = new JSONObject();
    private StringUtils stringUtils = new StringUtils();
    private BigDecimalUtils bigDecimalUtils = new BigDecimalUtils();

    public String buildJson(List<JsonAttribute> jsonAttributes) throws Exception {
        if (jsonAttributes.size() == 0) {
            log.info("nestedJson: {}");
            return "{}";
        }
        for (JsonAttribute jsonAttribute : jsonAttributes) {
            tryToPut(jsonAttribute);
        }
        String nestedJson = JsonUnflattener.unflatten(rootObject.toString());
        log.info("nestedJson: " + nestedJson);
        return nestedJson;
    }

    public void storeJsonData(String jsonName, List<JsonDataPlaceholder> jsonDataPlaceholders) {
        String jsonData = GlobalStorage.getStringStorage().get(jsonName);
        log.info(jsonData);
        for (JsonDataPlaceholder jsonDataPlaceholder : jsonDataPlaceholders) {
            try {
                List<Object> values = com.jayway.jsonpath.JsonPath.read(jsonData, "$." + jsonDataPlaceholder.getPath());
                for (Object actual : values) {
                    GlobalStorage.getStringStorage().put(jsonDataPlaceholder.getPlaceholder(), actual.toString());
                }
            } catch (ClassCastException e) {
                Object actual = com.jayway.jsonpath.JsonPath.read(jsonData, "$." + jsonDataPlaceholder.getPath());
                GlobalStorage.getStringStorage().put(jsonDataPlaceholder.getPlaceholder(), actual.toString());
            }
        }
//            String jsonPointer = generateJsonPointer(jsonDataPlaceholder.getValue()); //todo remove?
//            Object value = jsonObject.query(jsonPointer);
//            GlobalStorage.getStringStorage().put(jsonDataPlaceholder.getPlaceholder(), value.toString());
//        }
    }

    private void tryToPut(JsonAttribute jsonAttribute) throws Exception {
        String path = jsonAttribute.getPath();
        Object value;
        String type = jsonAttribute.getType();
        try {
            value = stringUtils.setPlaceholders(jsonAttribute.getValue().toString());
        } catch (NullPointerException npe) {
            rootObject.put(path, ""); //empty values handling
            return;
        }
        if (Objects.equals(type, "boolean")) {
            rootObject.put(path, Boolean.parseBoolean(value.toString()));
        } else if (Objects.equals(type, "number")) {
            rootObject.put(path, new BigDecimal(value.toString()));
        } else if (Objects.equals(type, "string")) {
            rootObject.put(path, value.toString());
        } else if (Objects.equals(type, "datetime")) {
            rootObject.put(path, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value.toString()));
        } else if (Objects.equals(type, "date")) {
            rootObject.put(path, new SimpleDateFormat("yyyy-MM-dd").parse(value.toString()));
        } else {
            throw new Exception("Unknown type: " + type);
        }
    }

    public void compareJsonData(String left, String right, List<JsonData> jsonDataList) {
        JSONObject leftJson = new JSONObject(left);
        JSONObject rightJson = new JSONObject(right);
        for (JsonData jsonData : jsonDataList) {
            String leftJsonPointer = generateJsonPointer(jsonData.getLeft().toString());
            String rightJsonPointer = generateJsonPointer(jsonData.getRight().toString());
            Object expected = leftJson.query(leftJsonPointer);
            String expectedString = stringUtils.setPlaceholders(expected.toString());
            Object actual = rightJson.query(rightJsonPointer);
            String actualString = stringUtils.setPlaceholders(actual.toString());
            String type = jsonData.getType();
            String message = jsonData.getLeft() + " expected: " + expectedString + ", " +
                    jsonData.getRight() + " actual: " + actualString;
            log.info(message);
            Allure.addAttachment(jsonData.getLeft() + " == " + jsonData.getRight(), message);
            if (Objects.equals(type, "number")) {
                bigDecimalUtils.assertEquals(jsonData.getLeft().toString(), expectedString, jsonData.getRight().toString(), actualString);
            } else {
                Assertions
                        .assertThat(actualString)
                        .withFailMessage(() -> jsonData.getLeft() + " expected: " + expectedString + ", " +
                                jsonData.getRight() + " actual: " + actualString)
                        .isEqualTo(expectedString);
            }
        }
    }

    public void compareData(List<JsonData> jsonDataList) {
        for (JsonData jsonData : jsonDataList) {
            Object left = jsonData.getLeft();
            Object right = jsonData.getRight();
            String leftString = stringUtils.setPlaceholders(left.toString());
            String rightString = stringUtils.setPlaceholders(right.toString());
            String type = jsonData.getType();
            String message = jsonData.getLeft() + " expected: " + leftString + ", " +
                    jsonData.getRight() + " actual: " + rightString;
            log.info(message);
            Allure.addAttachment(jsonData.getLeft() + " == " + jsonData.getRight(), message);
            if (Objects.equals(type, "number")) {
                bigDecimalUtils.assertEquals(jsonData.getLeft().toString(), leftString, jsonData.getRight().toString(), rightString);
            } else if (Objects.equals(type, "datetime")) {
                try {
                    Date leftDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(leftString);
                    Date rightDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rightString);
                    Assertions
                            .assertThat(leftDate.compareTo(rightDate) == (0))
                            .withFailMessage(() -> jsonData.getLeft() + " left: " + leftDate + ", " +
                                    jsonData.getRight() + " right: " + rightDate)
                            .isEqualTo(true);
                } catch (Exception e) {
                    // throw new Exception("Failed to parse Date");
                }
            } else if (Objects.equals(type, "date")) {
                try {
                    Date leftDateTime = new SimpleDateFormat("yyyy-MM-dd").parse(leftString);
                    Date rightDateTime = new SimpleDateFormat("yyyy-MM-dd").parse(rightString);
                    Assertions
                            .assertThat(leftDateTime.compareTo(rightDateTime) == (0))
                            .withFailMessage(() -> jsonData.getLeft() + " left: " + leftDateTime + ", " +
                                    jsonData.getRight() + " right: " + rightDateTime)
                            .isEqualTo(true);
                } catch (Exception e) {
                    // throw new Exception("Failed to parse Date");
                }
            } else {
                Assertions
                        .assertThat(leftString)
                        .withFailMessage(() -> jsonData.getLeft() + " left: " + leftString + ", " +
                                jsonData.getRight() + " right: " + rightString)
                        .isEqualTo(rightString);
            }
        }
    }

    private void assertEquality(String path, Object actual, Object expected) {
        log.info("assertEquality: " + path + " expected: " + expected + ", actual: " + actual);
        Assertions
                .assertThat(actual)
                .withFailMessage(() -> path + " expected: " + expected + ", actual: " + actual)
                .isEqualTo(expected);
    }

    public void checkJsonData(String jsonData, List<JsonAttribute> jsonAttributes) {
        for (JsonAttribute jsonAttribute : jsonAttributes) {
            try {
                List<Object> values = com.jayway.jsonpath.JsonPath.read(jsonData, "$." + jsonAttribute.getPath());
                for (Object actual : values) {
                    assertJsonData(jsonAttribute.getPath(), jsonAttribute.getType(), jsonAttribute.getValue(), actual);
                }
            } catch (ClassCastException e) {
                Object actual = com.jayway.jsonpath.JsonPath.read(jsonData, "$." + jsonAttribute.getPath());
                assertJsonData(jsonAttribute.getPath(), jsonAttribute.getType(), jsonAttribute.getValue(), actual);
            }
        }
    }

    private void assertJsonData(String key, String type, Object expected, Object actual) {
        String expectedString;
        try {
            expectedString = stringUtils.setPlaceholders(expected.toString());
        } catch (NullPointerException npe) {
            assertEquality(key, actual, expected); //empty values handling
            return;
        }
        if (stringUtils.isAlternativeExists(expectedString)) {
            assertAlternativeValues(key, expectedString, actual, type);
        } else {
            assertValuesEquality(key, expectedString, actual, type);
        }
    }

    private void assertAlternativeValues(String path, String expectedString, Object actual, String type) {
        String alternativesString = expectedString.split("OR\\{")[1].split("}")[0];
        log.info("checkAlternativeValues alternatives: " + alternativesString);
        String[] alternatives = alternativesString.split(",");
        boolean commonResult = false;
        for (String alternative : alternatives) {
            boolean checkingResult = checkValuesEquality(alternative.trim(), actual, type);
            if (checkingResult) {
                commonResult = true;
            }
        }
        Assertions.assertThat(commonResult)
                .withFailMessage(() -> path + " expected: " + alternativesString + ", " +
                        path + " actual: " + actual)
                .isEqualTo(true);
    }

    public boolean checkValuesEquality(String expectedString, Object actual, String type) {
        if (Objects.equals(type, "boolean")) {
            return actual.equals(Boolean.parseBoolean(expectedString));
        } else if (Objects.equals(type, "number")) {
            return bigDecimalUtils.isEquals(expectedString, actual);
        } else if (Objects.equals(type, "string")) {
            return actual.equals(expectedString);
        } else {
            throw new AssertionError("Undefined type: " + type);
        }
    }

    public void assertValuesEquality(String key, String expectedString, Object actual, String type) {
        if (Objects.equals(type, "boolean")) {
            assertEquality(key, actual, Boolean.parseBoolean(expectedString));
        } else if (Objects.equals(type, "number")) {
            bigDecimalUtils.assertEquals(key, expectedString, key, actual.toString());
        } else if (Objects.equals(type, "string")) {
            assertEquality(key, actual, expectedString);
        } else {
            throw new AssertionError("Undefined type: " + type);
        }
    }

    private String generateJsonPointer(String path) {
        String jsonPointer = "/" + path
                .replace(".", "/")
                .replace("[", "/")
                .replace("]", "/")
                .replace("//", "/");
        return jsonPointer;
    }
}
