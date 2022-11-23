package googleapi;

import lombok.Getter;

@Getter
public enum StatusCode {
    CODE_200(200, ""),
    CODE_201(201, ""),
    CODE_204(204, ""),
    CODE_400(400, "Missing required field: name"),
    CODE_400_BLANK_SUMMARY(400, "You must specify a summary of the issue."),
    CODE_400_NULL_SUMMARY(400, "Operation value must be a string"),
    CODE_401(401, "Invalid access token"),
    CODE_404(404, "Invalid access token");

    public final int code;
    public final String message;

    StatusCode(int code, String error_message) {
        this.code = code;
        this.message = error_message;
    }
}
