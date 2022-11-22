package googleapi;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class SpecBuilder {

    public static RequestSpecification getAuthRequestSpec(String URI) {
        return new RequestSpecBuilder()
                .setBaseUri(URI)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
//                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification getRequestSpec(String baseUrl) {
//        String issueEndpoint = String.format(Constants.BASE_URL, Constants.CLOUD_ID);
        String issueEndpoint = baseUrl;
        return new RequestSpecBuilder()
                .setBaseUri(issueEndpoint)
                .setBasePath("")
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification getResponseSpec() {
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification getResponseStatusSpec() {
        return new ResponseSpecBuilder()
                .log(LogDetail.STATUS)
                .build();
    }

    public static ResponseSpecification getResponseBodySpec() {
        return new ResponseSpecBuilder()
                .log(LogDetail.BODY)
                .build();
    }
}
