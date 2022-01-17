package io.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.utils.FileUtils;
import io.cucumber.utils.PythonUtils;
import io.cucumber.utils.StringUtils;

import java.io.File;

public class PythonStepDefinitions {

    private StringUtils stringUtils = new StringUtils();
    private StringBuilder preScriptStringBuilder = new StringBuilder();

    @Given("py-prescript: {string}")
    public void py_prescript(String name) {
        File preScript = new File("/" + System.getProperty("user.dir") + "/src/main/py/" + name);
        String content = new FileUtils().readFile(preScript).trim();
        preScriptStringBuilder
                .append("\n")
                .append(content)
                .append("\n");
    }

    @Then("execute py-script:")
    public void execute_py_script(String docString) {
        String preScript = stringUtils.setPlaceholders(preScriptStringBuilder.toString());
        PythonUtils pythonUtils = new PythonUtils();
        pythonUtils.setAllPlaceholders();
        pythonUtils.exec(preScript + "\n" + docString);
    }

    @Then("execute py-script then define placeholder {string}:")
    public void execute_py_script_then_define_placeholder(String outputVariableName, String docString) {
        String preScript = stringUtils.setPlaceholders(preScriptStringBuilder.toString());
        PythonUtils pythonUtils = new PythonUtils();
        pythonUtils.setAllPlaceholders();
        pythonUtils.exec(outputVariableName, preScript + "\n" + docString);
    }
}
