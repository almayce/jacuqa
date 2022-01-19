package io.cucumber.utils;

import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.junit.ComparisonFailure;
import org.opentest4j.AssertionFailedError;

import java.util.HashMap;
import java.util.List;

public class ListOfMapsUtils {

    Logger log = Logger.getLogger(this.getClass().getSimpleName());

    private BigDecimalUtils bigDecimalUtils = new BigDecimalUtils();

    public void checkResult(List<HashMap<String, Object>> response, String columnName, String expected, int row) {

        String actual = response.get(row - 1).get(columnName).toString();
        log.info("column: " + columnName + ", expected: " + expected + ", actual: " + actual + ", row: " + row);
        try {
            Assertions.assertThat(actual).isEqualTo(expected);
        } catch (AssertionFailedError e) {
            bigDecimalUtils.assertEquals(columnName, actual, columnName, expected);
        }
    }
}
