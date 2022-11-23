package testcase;

import googleapi.token.TokenManager;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    @BeforeMethod
    public void setUp() {
        //Get the Authentication mode from OAuth2.0
        String authMode = "OAuth2";

        if (authMode.equals("OAuth2")) {
            //Sending the api request to get access token
            TokenManager.getAccessToken();
        } else {
            try {  //Custom Error message
                throw new Exception("Please provide API auth mode in config.properties file");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
