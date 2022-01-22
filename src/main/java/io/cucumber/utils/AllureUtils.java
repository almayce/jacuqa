package io.cucumber.utils;

import io.qameta.allure.Attachment;
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
}
