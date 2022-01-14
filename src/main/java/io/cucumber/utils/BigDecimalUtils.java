package io.cucumber.utils;

import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;

public class BigDecimalUtils {

    Logger log = Logger.getLogger(this.getClass().getSimpleName());

    public boolean isEquals(Object expected, Object actual) {
        BigDecimal actualDecimal = new BigDecimal(actual.toString());
        BigDecimal expectedDecimal;
        try {
            expectedDecimal = new BigDecimal(expected.toString());
        } catch (NumberFormatException e) {
            throw new AssertionError("Is: '" + expected + "' number?");
        }
        return actualDecimal.compareTo(expectedDecimal) == (0);
    }

    public void assertEquals(String leftKey, String leftValue, String rightKey, String rightValue) {
        log.info("assertEquality: " + leftKey + " expected: " + leftValue + ", " + rightKey + " actual: " + rightValue);
        BigDecimal leftDecimal = new BigDecimal(leftValue);
        BigDecimal rightDecimal = new BigDecimal(rightValue);
        Assertions.assertThat(leftDecimal.compareTo(rightDecimal) == (0))
                .withFailMessage(() -> leftKey + " expected: " + leftDecimal + ", " +
                        rightKey + " actual: " + rightDecimal)
                .isEqualTo(true);
    }
}
