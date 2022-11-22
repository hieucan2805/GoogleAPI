package googleapi.drive.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FileTypes {
    SPREADSHEET("application/vnd.google-apps.spreadsheet"),
    DOCUMENT("application/vnd.google-apps.document"),
    RAR("application/x-rar"),
    FOLDER("application/vnd.google-apps.folder"),
    PDF("application/pdf"),
    HTML("text/html"),
    IMG_JPEG("image/jpeg"),
    IMG_PNG("image/png"),
    VIDEO_MP4("video/mp4");

    private final String mimeType;
}
