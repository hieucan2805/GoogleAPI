package googleapi.drive;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;
import utils.Constants;
import utils.Log;

import static googleapi.SpecBuilder.*;
import static io.restassured.RestAssured.given;

@Getter
@Setter
@NoArgsConstructor
public class DriveAPI {
    private static String GoogleDriveBaseURL = "https://www.googleapis.com/drive/v3";
    private String fileEndPoint = "/files";
    private String issueIdOrKey = "";
    private String authorization;
    private String issueEndpoint;

    private String filePayLoad = "{\r\n  \"mimeType\": \"application/vnd.google-apps.document\",\r\n  \"description\":\"This is api test\",\r\n  \"name\": \"testAPI\"\r\n}";

    public synchronized Response createIssue() {
        issueEndpoint = GoogleDriveBaseURL + fileEndPoint;
        Log.info("path: " + issueEndpoint);

        return post(issueEndpoint, Constants.ACCESS_TOKEN, filePayLoad);
    }



    private static Response post(String path, String token, Object object) {
        return given(getRequestSpec(GoogleDriveBaseURL))
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(object)
                .auth().oauth2(token)
                .when().post(path)
                .then().spec(getResponseStatusSpec()).spec(getResponseBodySpec())
                .extract()
                .response();
    }

    private static Response get(String path, String token) {
        return given(getRequestSpec(GoogleDriveBaseURL))
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when().get(path)
                .then().spec(getResponseStatusSpec()).spec(getResponseBodySpec())
                .extract()
                .response();
    }

    private static Response update(String path, String token, Object object) {
        return given(getRequestSpec(GoogleDriveBaseURL))
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(object)
                .when().put(path)
                .then()
                .spec(getResponseStatusSpec()).spec(getResponseBodySpec())
                .extract()
                .response();
    }

    private static Response delete(String path, String token) {
        return given(getRequestSpec(GoogleDriveBaseURL))
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when().delete(path)
                .then()
                .spec(getResponseStatusSpec()).spec(getResponseBodySpec())
                .extract()
                .response();
    }
}
