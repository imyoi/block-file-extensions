package api.flow.blockfileextensions.repository;

import api.flow.blockfileextensions.domain.FileExtensionRequest;
import api.flow.blockfileextensions.entity.FileExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileExtensionRepository extends JpaRepository<FileExtension, Long> {

    List<FileExtension> findByType(String type);
    Boolean existsByName(String name);

    FileExtension findByName(String name);
    Integer countByType(String type);
}
