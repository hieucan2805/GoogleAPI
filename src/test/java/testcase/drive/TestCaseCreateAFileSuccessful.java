package testcase.drive;

import googleapi.StatusCode;
import googleapi.drive.DriveAPI;
import googleapi.drive.data.FileTypes;
import googleapi.drive.file.GoogleFile;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testcase.BaseTest;
import utils.CommonMethods;
import utils.DateUtils;
import utils.Log;

public class TestCaseCreateAFileSuccessful extends BaseTest {


    String date = DateUtils.getCurrentDate();
    String fileName = "Test_file_" + date;
    String type = FileTypes.DOCUMENT.getMimeType();
    String description = "This is description created on " + date;
    GoogleFile googleFile = new GoogleFile(fileName,type,description);

    DriveAPI driveAPI = new DriveAPI(googleFile);
    StatusCode statusCode200 = StatusCode.CODE_200;
    SoftAssert softAssert = new SoftAssert();

    @Test(description = "User is able to create a new file successful on Google Drive")
    public void VerifyUserIsAbleToCreateANewIssueSuccessful() {
        Log.step("Create a new file with valid data");
        Response createFileResponse = driveAPI.createFile();
        CommonMethods createFileResponseBody = new CommonMethods(createFileResponse.getBody().asString());

        Log.step("Get file " + createFileResponseBody.getStringValue("id") + " details");
        Response getFileResponse = driveAPI.getFileWithID(createFileResponseBody.getStringValue("id"));
        CommonMethods getFileResponseBody = new CommonMethods(getFileResponse.getBody().asString());

        Log.info("Verify point: Verify the response code of created new issue request is: " + statusCode200.getCode());
        softAssert.assertEquals(createFileResponse.statusCode(), statusCode200.getCode());
        Log.info("Verify point: Verify the response code of get issue request is: " + statusCode200.getCode());
        softAssert.assertEquals(getFileResponse.statusCode(), statusCode200.getCode());
        Log.info("Verify point: Verify the new file id is: " + createFileResponseBody.getStringValue("id"));
        softAssert.assertEquals(getFileResponseBody.getStringValue("id"), createFileResponseBody.getStringValue("id"));
        Log.info("Verify point: Verify the new file summary is: " + fileName);
        softAssert.assertEquals(getFileResponseBody.getStringValue("name"), fileName);
        Log.info("Verify point: Verify the new file type is: " + fileName);
        softAssert.assertEquals(getFileResponseBody.getStringValue("mimeType"), type);

        softAssert.assertAll();

        Log.step("AFTER-TEST: Delete created issue after test");
        driveAPI.deleteFileWithID(createFileResponseBody.getStringValue("id"));
    }
}
