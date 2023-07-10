package api.flow.blockfileextensions.entity;

import api.flow.blockfileextensions.common.BaseTime;
import api.flow.blockfileextensions.domain.FileExtensionRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "file_extensions")
public class FileExtension extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String checkYn;

    public FileExtension(FileExtensionRequest request) {
        if (request.getId() != null) {
            this.id = request.getId();
        }
        this.name = request.getName().toLowerCase();
        this.type = request.getType();
        this.checkYn = request.getCheckYn();
    }

    public void updateExtension(String checkYn) {
        this.checkYn = checkYn;
    }
}