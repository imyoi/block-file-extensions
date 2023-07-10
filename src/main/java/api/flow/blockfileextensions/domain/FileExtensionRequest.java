package api.flow.blockfileextensions.domain;

import lombok.Builder;
import lombok.Data;

@Data
public class FileExtensionRequest {

    private Long id;
    private String name;
    private String type;
    private String checkYn;
}