package io.cucumber.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Objects;
import java.util.Random;

public class RandomUtils {

    public String getRandomValue(String type, int length) {
        if (Objects.equals(type, "boolean")) {
            return randomBoolean();
        } else if (Objects.equals(type, "number")) {
            return randomNumber(length);
        } else {
            return randomString(length);
        }
    }

    public String randomBoolean() {
        return String.valueOf(new Random().nextBoolean());
    }

    public String randomNumber(int length) {
        long min = 1;
        long max = 9;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            long value = Double.valueOf((Math.random() * (max - min)) + min).longValue();
            stringBuilder.append(value);
        }
        return stringBuilder.toString();
    }

    public String randomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }
}
