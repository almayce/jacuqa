package io.cucumber.utils;

import io.cucumber.storage.GlobalStorage;
import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    Logger log = Logger.getLogger(this.getClass().getSimpleName());

    public String setPlaceholders(String string) {
        String preparedString = string;
        Pattern re = Pattern.compile("(\\$\\{})|(\\$\\{)(.+?)(})", Pattern.MULTILINE | Pattern.COMMENTS);
        Matcher matchSentence = re.matcher(preparedString);
        while (matchSentence.find()) {
            String placeholder = matchSentence.group();
            String replacement = GlobalStorage.getStringStorage().get(placeholder.substring(2, placeholder.length() - 1));
            log.info("setPlaceholders.replacement: " + replacement);
            try {
                preparedString = preparedString.replace(placeholder, replacement);
            } catch (NullPointerException e) {
                Assertions.fail("Define value for the placeholder " + placeholder);
            }
        }
        return preparedString;
    }

    public Boolean isAlternativeExists(String string) {
        String preparedString = string;
        Pattern re = Pattern.compile("(OR\\{})|(OR\\{)(.+?)(})", Pattern.MULTILINE | Pattern.COMMENTS);
        Matcher matchSentence = re.matcher(preparedString);
        return matchSentence.matches();
    }

    public String extractByRegex(String regex, String source) {
        String preparedSource = setPlaceholders(source);
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(preparedSource);
        while (m.find()) {
            log.info(m.group());
            return m.group();
        }
        throw new NullPointerException("Source: " + preparedSource + ", RegEx: " + regex);
    }
}
