package googleapi.drive.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GoogleFile {
    private String fileName;
    private String mimeType;
    private String description;
}
