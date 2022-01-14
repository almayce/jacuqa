package io.cucumber.utils;

import io.cucumber.storage.GlobalStorage;
import jep.Interpreter;
import jep.JepException;
import jep.SharedInterpreter;
import org.apache.log4j.Logger;
import org.junit.Assert;

public class PythonUtils {

    private Logger log = Logger.getLogger(this.getClass().getSimpleName());
    private Interpreter interpreter = new SharedInterpreter();

    public void setAllPlaceholders() {
        GlobalStorage.getStringStorage().forEach((s, s2) -> interpreter.set(s, s2));
    }

    public void exec(String script) {
        log.info("script: " + script);
        try {
            interpreter.exec(script);
        } catch (JepException e) {
            Assert.fail(e.getMessage());
        } finally {
            interpreter.close();
        }
    }

    public void exec(String outputVariableName, String script) {
        log.info("script: " + script);
        try {
            interpreter.exec(script);
            String output = interpreter.getValue(outputVariableName).toString();
            GlobalStorage.getStringStorage().put(outputVariableName, output);
        } catch (JepException e) {
            Assert.fail(e.getMessage());
        } finally {
            interpreter.close();
        }
    }
}
