package io.cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.utils.StringUtils;
import org.assertj.core.api.Assertions;

public class AssertIonStepDefinitions {

    private StringUtils stringUtils = new StringUtils();

    @And("assert that {string} is null")
    public void assert_string_is_null(String value) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).isNull();
    }

    @And("assert that {string} is empty")
    public void assert_string_is_empty(String value) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).isEmpty();
    }

    @And("assert that {string} is blank")
    public void assert_string_is_blank(String value) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).isBlank();
    }

    @And("assert that {string} is not null")
    public void assert_string_is_not_null(String value) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).isNotNull();
    }

    @And("assert that {string} is not empty")
    public void assert_string_is_not_empty(String value) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).isNotEmpty();
    }

    @And("assert that {string} is not blank")
    public void assert_string_is_not_blank(String value) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).isNotBlank();
    }

    @And("assert that {string} is base64")
    public void assert_string_is_base64(String value) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).isBase64();
    }

    //

    @And("assert that {string} is lower case")
    public void assert_string_is_lower_case(String value) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).isLowerCase();
    }

    @And("assert that {string} is upper case")
    public void assert_string_is_upper_case(String value) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).isUpperCase();
    }

    //

    @And("assert that {string} is equal to {string}")
    public void assert_string_is_equal_to_string(String actual, String expected) {
        Assertions.assertThat(stringUtils.setPlaceholders(actual)).isEqualTo(stringUtils.setPlaceholders(expected));
    }

    @And("assert that {string} is equal to {int}")
    public void assert_string_is_equal_to_int(String value, int expected) {
        int actual = Integer.parseInt(stringUtils.setPlaceholders(value));
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @And("assert that {string} is equal to {float}")
    public void assert_string_is_equal_to_float(String value, float expected) {
        float actual = Float.parseFloat(stringUtils.setPlaceholders(value));
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    //

    @And("assert that {string} is greater than {string}")
    public void assert_string_is_greater_than_string(String value, String other) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).isGreaterThan(stringUtils.setPlaceholders(other));
    }

    @And("assert that {string} is greater than {int}")
    public void assert_string_is_greater_than_int(String value, int other) {
        int actual = Integer.parseInt(stringUtils.setPlaceholders(value));
        Assertions.assertThat(actual).isGreaterThan(other);
    }

    @And("assert that {string} is greater than {float}")
    public void assert_string_is_greater_than_float(String value, float other) {
        float actual = Float.parseFloat(stringUtils.setPlaceholders(value));
        Assertions.assertThat(actual).isGreaterThan(other);
    }

    //

    @And("assert that {string} is greater than or equal to {string}")
    public void assert_string_is_greater_than_or_equal_to_string(String value, String other) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).isGreaterThanOrEqualTo(stringUtils.setPlaceholders(other));
    }

    @And("assert that {string} is greater than or equal to {int}")
    public void assert_string_is_greater_than_or_equal_to_int(String value, int other) {
        int actual = Integer.parseInt(stringUtils.setPlaceholders(value));
        Assertions.assertThat(actual).isGreaterThanOrEqualTo(other);
    }

    @And("assert that {string} is greater than or equal to {float}")
    public void assert_string_is_greater_than_or_equal_to_float(String value, float other) {
        float actual = Float.parseFloat(stringUtils.setPlaceholders(value));
        Assertions.assertThat(actual).isGreaterThanOrEqualTo(other);
    }

    //

    @And("assert that {string} is less than {string}")
    public void assert_string_is_less_than_string(String value, String other) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).isLessThan(stringUtils.setPlaceholders(other));
    }

    @And("assert that {string} is less than {int}")
    public void assert_string_is_less_than_int(String value, int other) {
        int actual = Integer.parseInt(stringUtils.setPlaceholders(value));
        Assertions.assertThat(actual).isLessThan(other);
    }

    @And("assert that {string} is less than {float}")
    public void assert_string_is_less_than_float(String value, float other) {
        float actual = Float.parseFloat(stringUtils.setPlaceholders(value));
        Assertions.assertThat(actual).isLessThan(other);
    }

    //

    @And("assert that {string} is less than or equal to {string}")
    public void assert_string_is_less_than_or_equal_to_string(String value, String other) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).isLessThanOrEqualTo(stringUtils.setPlaceholders(other));
    }

    @And("assert that {string} is less than or equal to {int}")
    public void assert_string_is_less_than_or_equal_to_int(String value, int other) {
        int actual = Integer.parseInt(stringUtils.setPlaceholders(value));
        Assertions.assertThat(actual).isLessThanOrEqualTo(other);
    }

    @And("assert that {string} is less than or equal to {float}")
    public void assert_string_is_less_than_or_equal_to_float(String value, float other) {
        float actual = Float.parseFloat(stringUtils.setPlaceholders(value));
        Assertions.assertThat(actual).isLessThanOrEqualTo(other);
    }

    //

    @And("assert that {string} is between: {string} and {string}")
    public void assert_string_is_between(String value, String start, String end) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).isBetween(start, end);
    }

    @And("assert that {string} is between: {int} and {int}")
    public void assert_string_is_between(String value, int start, int end) {
        int actual = Integer.parseInt(stringUtils.setPlaceholders(value));
        Assertions.assertThat(actual).isBetween(start, end);
    }

    @And("assert that {string} is between: {float} and {float}")
    public void assert_string_is_between(String value, float start, float end) {
        float actual = Float.parseFloat(stringUtils.setPlaceholders(value));
        Assertions.assertThat(actual).isBetween(start, end);
    }

    //

    @And("assert that {string} is strictly between: {string} and {string}")
    public void assert_string_is_strictly_between_string_and_string(String value, String start, String end) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).isStrictlyBetween(start, end);
    }

    @And("assert that {string} is strictly between: {int} and {int}")
    public void assert_string_is_strictly_between_int_and_int(String value, int start, int end) {
        int actual = Integer.parseInt(stringUtils.setPlaceholders(value));
        Assertions.assertThat(actual).isStrictlyBetween(start, end);
    }

    @And("assert that {string} is strictly between: {float} and {float}")
    public void assert_string_is_strictly_between_float_and_float(String value, float start, float end) {
        float actual = Float.parseFloat(stringUtils.setPlaceholders(value));
        Assertions.assertThat(actual).isStrictlyBetween(start, end);
    }

    //

    @And("assert that {string} contains pattern {string}")
    public void assert_string_contains_pattern(String value, String pattern) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).containsPattern(stringUtils.setPlaceholders(pattern));
    }

    @And("assert that {string} does not contain pattern {string}")
    public void assert_string_does_not_contain_pattern(String value, String pattern) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).doesNotContainPattern(stringUtils.setPlaceholders(pattern));
    }

    //

    @And("assert that {string} has size {int}")
    public void assert_string_has_size(String value, int size) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).hasSize(size);
    }

    @And("assert that {string} has size between: {int} and {int}")
    public void assert_string_has_size_between(String value, int start, int end) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).hasSizeBetween(start, end);
    }

    @And("assert that {string} has size greater than {int}")
    public void assert_string_has_size_greater_then(String value, int size) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).hasSizeGreaterThan(size);
    }

    @And("assert that {string} has size less than {int}")
    public void assert_string_has_size_less_than(String value, int size) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).hasSizeLessThan(size);
    }

    @And("assert that {string} has size less than or equal to {int}")
    public void assert_string_has_size_less_than_or_equal_to(String value, int size) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).hasSizeLessThanOrEqualTo(size);
    }

    @And("assert that {string} has size greater than or equal to {int}")
    public void assert_string_has_size_greater_than_or_equal_to(String value, int size) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).hasSizeGreaterThanOrEqualTo(size);
    }

    //

    @And("assert that {string} starts with {string}")
    public void assert_string_starts_with(String value, String start) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).startsWith(stringUtils.setPlaceholders(start));
    }

    @And("assert that {string} does not start with {string}")
    public void assert_string_does_not_start_with(String value, String start) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).doesNotStartWith(stringUtils.setPlaceholders(start));
    }

    @And("assert that {string} ends with {string}")
    public void assert_string_ends_with(String value, String end) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).endsWith(stringUtils.setPlaceholders(end));
    }

    @And("assert that {string} does not end with {string}")
    public void assert_string_does_not_end_with(String value, String end) {
        Assertions.assertThat(stringUtils.setPlaceholders(value)).doesNotEndWith(stringUtils.setPlaceholders(end));
    }
}
