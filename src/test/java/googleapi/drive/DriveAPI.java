package googleapi.drive;

import googleapi.drive.file.GoogleFile;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private GoogleFile googleFile;
    private String fileName;
    private String mimeType;
    private String description;
    private String filePayLoad = "{\r\n  \"name\": \"%s\",\r\n  \"mimeType\": \"%s\",\r\n  \"description\":\"%s\"\r\n}";

    public DriveAPI(GoogleFile googleFile) {
        this.googleFile = googleFile;
        this.fileName = googleFile.getFileName();
        this.mimeType = googleFile.getMimeType();
        this.description = googleFile.getDescription();
    }

    public synchronized Response createFile() {
        issueEndpoint = GoogleDriveBaseURL + fileEndPoint;
        Log.info("path: " + issueEndpoint);
        String payload = String.format(filePayLoad, this.fileName, this.mimeType, this.description);

        return post(issueEndpoint, Constants.ACCESS_TOKEN, payload);
    }

    public synchronized Response getFileWithID(String issueIdOrName) {
        issueEndpoint = GoogleDriveBaseURL + fileEndPoint + "/" + issueIdOrName;
        Log.info("path: " + issueEndpoint);

        return get(issueEndpoint, Constants.ACCESS_TOKEN);
    }

    public synchronized Response deleteFileWithID(String issueIdOrName) {
        issueEndpoint = GoogleDriveBaseURL + fileEndPoint + "/" + issueIdOrName;
        Log.info("path: " + issueEndpoint);

        return delete(issueEndpoint, Constants.ACCESS_TOKEN);
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
