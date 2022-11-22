package googleapi.token;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import utils.Constants;
import utils.JacksonObjectUtils;
import utils.Log;
import io.restassured.http.ContentType;
import org.json.JSONObject;

import java.time.Instant;

import static googleapi.SpecBuilder.getAuthRequestSpec;
import static io.restassured.RestAssured.given;

public class GetGoogleAPIToken {
    private static Object refreshAccessToken() {
        // The payload definition using the Jackson library
        JsonNodeFactory jnf = JsonNodeFactory.instance;
        ObjectNode payload = jnf.objectNode();
        {
            payload.put("grant_type", "refresh_token");
            payload.put("client_id", Constants.CLIENT_ID);
            payload.put("client_secret", Constants.SECRET);
            payload.put("refresh_token", Constants.REFRESH_TOKEN);
        }

        JacksonObjectUtils.connectJacksonObjectMapperToUnirest();
        return payload;
    }

    private synchronized static void getAccessTokenResponse() {
        try {
            if (Constants.ACCESS_TOKEN == null || Instant.now().isAfter(Constants.EXPIRED_TIME)) {
                JSONObject access_token_response_body = new JSONObject(given(getAuthRequestSpec(Constants.TOKEN_URL))
                        .contentType(ContentType.JSON)
                        .body(refreshAccessToken())
                        .when().post()
                        .then()//.spec(getResponseSpec())
                        .extract().response().getBody().asString());

                Constants.ACCESS_TOKEN = access_token_response_body.getString("access_token");
                Constants.EXPIRED_TIME = Instant.now().plusSeconds(Integer.parseInt(access_token_response_body.getString("expires_in")) - 300);
            } else {
                System.out.println("Token is good to use");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ABORT!!! Failed to get token");
        }
    }

    public static void getAccessToken() {
        if (Constants.ACCESS_TOKEN == null) {
            getAccessTokenResponse();
            Log.info("Getting Access Token successful! \nACCESS TOKEN: " + Constants.ACCESS_TOKEN);
            Constants.AUTHORIZATION = String.format("Bearer %s", Constants.ACCESS_TOKEN);
        }
    }
}
