package io.cucumber.utils;

import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    Logger log = Logger.getLogger(this.getClass().getSimpleName());

    public void assertEquals(String left, String right, String type) {
        log.info("assertEquals");
        log.info("left: " + left);
        log.info("right: " + right);
        log.info("type: " + type);
        try {
            String leftDateString = left.split(";")[0].trim();
            String leftPattern = left.split(";")[1].trim();
            String rightDateString = right.split(";")[0].trim();
            String rightPattern = right.split(";")[1].trim();
            Date leftDate = new SimpleDateFormat(leftPattern).parse(leftDateString);
            Date rightDate = new SimpleDateFormat(rightPattern).parse(rightDateString);
            Assertions
                    .assertThat(leftDate.compareTo(rightDate) == (0))
                    .withFailMessage(() -> "left: " + leftDate + ", " +
                            "right: " + rightDate + ", type: " + type)
                    .isEqualTo(true);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new AssertionError("Define date patterns to compare: " + left + " and " + right);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public boolean isEquals(String left, String right, String type) {
        log.info("isEquals");
        log.info("left: " + left);
        log.info("right: " + right);
        log.info("type: " + type);
        try {
            String leftDateString = left.split(";")[0].trim();
            String leftPattern = left.split(";")[1].trim();
            String rightDateString = right.split(";")[0].trim();
            String rightPattern = right.split(";")[1].trim();
            Date leftDate = new SimpleDateFormat(leftPattern).parse(leftDateString);
            Date rightDate = new SimpleDateFormat(rightPattern).parse(rightDateString);
            return leftDate.compareTo(rightDate) == (0);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new AssertionError("Define date patterns to compare: " + left + " and " + right);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
