package io.cucumber.utils;

import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.opentest4j.AssertionFailedError;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AssertionsUtils {

    Logger log = Logger.getLogger(this.getClass().getSimpleName());

    private StringUtils stringUtils = new StringUtils();
    private BigDecimalUtils bigDecimalUtils = new BigDecimalUtils();
    private DateUtils dateUtils = new DateUtils();

    public void assertDataEquals(String left, String right, String type) {
        log.info("assertDataEquals");
        log.info("left: " + left);
        log.info("right: " + right);
        log.info("type: " + type);
        String leftString;
        try {
            leftString = stringUtils.setPlaceholders(left);
        } catch (NullPointerException npe) {
            assertEquals(left, right, type); //empty values handling
            return;
        }
        if (stringUtils.isAlternativeExists(leftString)) {
            assertAltEquals(leftString, right, type);
        } else {
            assertEqualsByType(leftString, right, type);
        }
    }

    private void assertEqualsByType(String left, String right, String type) {
        log.info("assertEqualsByType");
        log.info("left: " + left);
        log.info("right: " + right);
        log.info("type: " + type);
        if (Objects.equals(type, "number")) {
            bigDecimalUtils.assertEquals(left, right, type);
        } else if (Objects.equals(type, "date")) {
            dateUtils.assertEquals(left, right, type);
        } else if (Objects.equals(type, "string")) {
            assertEquals(left, right, type);
        } else if (Objects.equals(type, "boolean")) {
            assertEquals(left.toLowerCase(Locale.ROOT), right, type);
        } else {
            throw new AssertionError("Undefined type: " + type);
        }
    }

    private void assertEquals(String left, String right, String type) {
        log.info("assertEquals");
        log.info("left: " + left);
        log.info("right: " + right);
        log.info("type: " + type);
        Assertions
                .assertThat(left)
                .withFailMessage(() -> "Assert that left: " + left + " is equal to right: " + right + ", type: " + type)
                .isEqualTo(right);
    }

    private void assertAltEquals(String left, String right, String type) {
        log.info("assertAltEquals");
        log.info("left: " + left);
        log.info("right: " + right);
        log.info("type: " + type);
        String alternativesString = left.split("OR\\{")[1].split("}")[0]; //todo regex
        log.info("alternatives: " + alternativesString);
        String[] alternatives = alternativesString.split(",");
        boolean commonResult = false;
        for (String alternative : alternatives) {
            boolean checkingResult = isEquals(alternative.trim(), right, type);
            if (checkingResult) {
                commonResult = true;
            }
        }
        Assertions.assertThat(commonResult) //todo optimize
                .withFailMessage(() -> " left: " + alternativesString + ", " +
                        " right: " + right)
                .isEqualTo(true);
    }

    private boolean isEquals(String left, String right, String type) {
        log.info("isEquals");
        log.info("left: " + left);
        log.info("right: " + right);
        log.info("type: " + type);
        if (Objects.equals(type, "number")) {
            return bigDecimalUtils.isEquals(left, right, type);
        } else if (Objects.equals(type, "date")) {
            return dateUtils.isEquals(left, right, type);
        } else if (Objects.equals(type, "string")) {
            return left.equals(right);
        } else if (Objects.equals(type, "boolean")) {
            return left.toLowerCase(Locale.ROOT).equals(right);
        } else {
            throw new AssertionError("Undefined type: " + type);
        }
    }

    public void checkDatabaseResponse(List<HashMap<String, Object>> response, String columnName, String left, int row) {
        log.info("checkResult");
        log.info("left: " + left);
        String right = response.get(row - 1).get(columnName).toString();
        log.info("right: " + right);
        log.info("row: " + row);
        try {
            Assertions.assertThat(left).isEqualTo(right);
        } catch (AssertionFailedError e) {
            bigDecimalUtils.assertEquals(left, right, "number");
        }
    }
}
