package io.cucumber.utils;

import com.github.wnameless.json.unflattener.JsonUnflattener;
import io.cucumber.storage.GlobalStorage;
import io.cucumber.table_type.Data;
import io.cucumber.table_type.JsonAttribute;
import io.cucumber.table_type.JsonData;
import io.cucumber.table_type.JsonDataPlaceholder;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static io.cucumber.utils.AllureUtils.allureStep;

public class JsonUtils {

    Logger log = Logger.getLogger(this.getClass().getSimpleName());

    private JSONObject rootObject = new JSONObject();
    private AssertionsUtils assertionsUtils = new AssertionsUtils();
    private StringUtils stringUtils = new StringUtils();

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
        } else {
            throw new Exception("Unknown type: " + type);
        }
    }

    public void defineJsonData(String jsonName, List<JsonDataPlaceholder> jsonDataPlaceholders) {
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
    }

    public void compareDateJsonData(String leftJson, String rightJson, JsonData jsonData) {
        JSONObject leftJsonObject = new JSONObject(leftJson);
        JSONObject rightJsonObject = new JSONObject(rightJson);
        String type = jsonData.getType();
        String leftPath = jsonData.getLeftPath().toString().split(";")[0].trim();
        String leftPattern = jsonData.getLeftPath().toString().split(";")[1].trim();
        String rightPath = jsonData.getRightPath().toString().split(";")[0].trim();
        String rightPattern = jsonData.getRightPath().toString().split(";")[1].trim();
        String leftJsonPointer = generateJsonPointer(leftPath);
        String rightJsonPointer = generateJsonPointer(rightPath);
        Object leftObject = leftJsonObject.query(leftJsonPointer);
        String left = leftObject.toString();
        Object rightObject = rightJsonObject.query(rightJsonPointer);
        String right = rightObject.toString();
        GlobalStorage.getAttachmentBuilder()
                .append(jsonData.getLeftPath().toString()).append(": ").append(left).append(" ")
                .append(jsonData.getRightPath().toString()).append(": ").append(right)
                .append("\n");
        try {
            assertionsUtils.assertDataEquals(left + "; " + leftPattern, right + "; " + rightPattern, type);
            allureStep("compare " + leftPath + " and " + rightPath, left + "; " + leftPattern + " == " + right + "; " + rightPattern, null);
        } catch (AssertionError assertionError) {
            allureStep("compare " + leftPath + " and " + rightPath, left + "; " + leftPattern + " == " + right + "; " + rightPattern, assertionError);
            throw assertionError;
        }
    }

    public void compareJsonData(String leftJson, String rightJson, JsonData jsonData) {
        JSONObject leftJsonObject = new JSONObject(leftJson);
        JSONObject rightJsonObject = new JSONObject(rightJson);
        String type = jsonData.getType();
        String leftPath = jsonData.getLeftPath().toString();
        String rightPath = jsonData.getRightPath().toString();
        String leftJsonPointer = generateJsonPointer(leftPath);
        String rightJsonPointer = generateJsonPointer(rightPath);
        Object leftObject = leftJsonObject.query(leftJsonPointer);
        String left = leftObject.toString();
        Object rightObject = rightJsonObject.query(rightJsonPointer);
        String right = rightObject.toString();
        GlobalStorage.getAttachmentBuilder()
                .append(jsonData.getLeftPath().toString()).append(": ").append(left).append(" ")
                .append(jsonData.getRightPath().toString()).append(": ").append(right)
                .append("\n");
        try {
            assertionsUtils.assertDataEquals(left, right, type);
            allureStep("compare " + leftPath + " and " + rightPath, left + " == " + right, null);
        } catch (AssertionError assertionError) {
            allureStep("compare " + leftPath + " and " + rightPath, left + " == " + right, assertionError);
            throw assertionError;
        }
    }

    public void compareData(Data data) {
        Object leftObject = data.getLeft();
        Object rightObject = data.getRight();
        String left, right;
        try {
            left = stringUtils.setPlaceholders(leftObject.toString());
        } catch (NullPointerException e) {
            left = "";
        }
        try {
            right = stringUtils.setPlaceholders(rightObject.toString());
        } catch (NullPointerException e) {
            right = "";
        }
        String type = data.getType();
        GlobalStorage.getAttachmentBuilder()
                .append("left: ").append(left).append(" ")
                .append("right: ").append(right).append(" ")
                .append("type: ").append(type)
                .append("\n");
        try {
            assertionsUtils.assertDataEquals(left, right, type);
            allureStep("compare", left + " == " + right, null);
        } catch (AssertionError assertionError) {
            allureStep("compare", left + " == " + right, assertionError);
            throw assertionError;
        }
    }

    public void assertDateJsonData(String jsonData, JsonAttribute jsonAttribute) {
        String left = jsonAttribute.getValue();
        String type = jsonAttribute.getType();
        String rightPath = jsonAttribute.getPath().split(";")[0].trim();
        String rightPattern = jsonAttribute.getPath().split(";")[1].trim();
        try {
            List<Object> values = com.jayway.jsonpath.JsonPath.read(jsonData, "$." + rightPath);
            for (Object right : values) {
                GlobalStorage.getAttachmentBuilder()
                        .append("left: ").append(left).append(" ")
                        .append("right: ").append(right).append(" ")
                        .append("type: ").append(type)
                        .append("\n");
                try {
                    assertionsUtils.assertDataEquals(left, right.toString() + "; " + rightPattern, type);
                    allureStep("compare", left + " == " + right + "; " + rightPattern, null);
                } catch (AssertionError assertionError) {
                    allureStep("compare", left + " == " + right + "; " + rightPattern, assertionError);
                    throw assertionError;
                }
            }
        } catch (ClassCastException e) {
            Object right = com.jayway.jsonpath.JsonPath.read(jsonData, "$." + rightPath);
            GlobalStorage.getAttachmentBuilder()
                    .append("left: ").append(left).append(" ")
                    .append("right: ").append(right).append(" ")
                    .append("type: ").append(type)
                    .append("\n");
            try {
                assertionsUtils.assertDataEquals(left, right.toString() + "; " + rightPattern, type);
                allureStep("compare", left + " == " + right + "; " + rightPattern, null);
            } catch (AssertionError assertionError) {
                allureStep("compare", left + " == " + right + "; " + rightPattern, assertionError);
                throw assertionError;
            }
        }
    }

    public void assertJsonData(String jsonData, JsonAttribute jsonAttribute) {
        String left = jsonAttribute.getValue();
        String type = jsonAttribute.getType();
        try {
            List<Object> values = com.jayway.jsonpath.JsonPath.read(jsonData, "$." + jsonAttribute.getPath());
            for (Object right : values) {
                GlobalStorage.getAttachmentBuilder()
                        .append("left: ").append(left).append(" ")
                        .append("right: ").append(right).append(" ")
                        .append("type: ").append(type)
                        .append("\n");
                try {
                    assertionsUtils.assertDataEquals(left, right.toString(), type);
                    allureStep("compare", left + " == " + right, null);
                } catch (AssertionError assertionError) {
                    allureStep("compare", left + " == " + right, assertionError);
                    throw assertionError;
                }
            }
        } catch (ClassCastException e) {
            Object right = com.jayway.jsonpath.JsonPath.read(jsonData, "$." + jsonAttribute.getPath());
            GlobalStorage.getAttachmentBuilder()
                    .append("left: ").append(left).append(" ")
                    .append("right: ").append(right).append(" ")
                    .append("type: ").append(type)
                    .append("\n");
            try {
                assertionsUtils.assertDataEquals(left, right.toString(), type);
                allureStep("compare", left + " == " + right.toString(), null);
            } catch (AssertionError assertionError) {
                allureStep("compare", left + " == " + right.toString(), assertionError);
                throw assertionError;
            }
        }
    }

    private String generateJsonPointer(String path) { //todo remove
        String jsonPointer = "/" + path
                .replace(".", "/")
                .replace("[", "/")
                .replace("]", "/")
                .replace("//", "/");
        return jsonPointer;
    }
}
