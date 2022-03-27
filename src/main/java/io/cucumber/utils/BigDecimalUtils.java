package io.cucumber.utils;

import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;

public class BigDecimalUtils {

    Logger log = Logger.getLogger(this.getClass().getSimpleName());

    public boolean isEquals(String left, String right, String type) {
        log.info("assertEquals");
        log.info("left: " + left);
        log.info("right: " + right);
        log.info("type: " + type);
        BigDecimal leftDecimal;
        try {
            leftDecimal = new BigDecimal(left);
        } catch (NumberFormatException e) {
            throw new AssertionError("Is: '" + left + "' number?");
        }
        BigDecimal rightDecimal = new BigDecimal(right);
        return leftDecimal.compareTo(rightDecimal) == (0);
    }

    public void assertEquals(String left, String right, String type) {
        log.info("assertEquals");
        log.info("left: " + left);
        log.info("right: " + right);
        log.info("type: " + type);
        BigDecimal leftDecimal = new BigDecimal(left);
        BigDecimal rightDecimal = new BigDecimal(right);
        Assertions.assertThat(leftDecimal.compareTo(rightDecimal) == (0))
                .withFailMessage(() -> "left: " + leftDecimal + ", " +
                        "right: " + rightDecimal + ", type: " + type)
                .isEqualTo(true);
    }
}
