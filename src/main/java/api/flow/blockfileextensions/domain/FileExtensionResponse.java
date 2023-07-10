package api.flow.blockfileextensions.domain;

import api.flow.blockfileextensions.entity.FileExtension;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileExtensionResponse {

    private Long id;
    private String name;
    private String type;
    private String checkYn;

    public static FileExtensionResponse of(FileExtension fileExtension) {
        return FileExtensionResponse.builder()
                .id(fileExtension.getId())
                .name(fileExtension.getName())
                .type(fileExtension.getType())
                .checkYn(fileExtension.getCheckYn())
                .build();
    }
}
