package io.cucumber.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.model.Status;
import io.restassured.response.Response;

public class AllureUtils {

    @Attachment(fileExtension = "txt", type = "text/plain", value = "{name}")
    public static String attachText(String name, String content) {
        return content;
    }

    @Attachment(fileExtension = "json", type = "text/json", value = "RestJsonResponse")
    public static String attachResponse(Response strResponse) {
        return strResponse.asString();
    }

    public static void allureStep(String name, String message, Throwable throwable) {
        if (throwable != null)
            Allure.step(name + ": " + throwable.getMessage(), Status.FAILED);
        else
            Allure.step(name + ": " + message, Status.PASSED);
    }
}
