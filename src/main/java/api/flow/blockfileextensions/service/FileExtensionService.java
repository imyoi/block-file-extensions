package api.flow.blockfileextensions.service;

import api.flow.blockfileextensions.domain.FileExtensionRequest;
import api.flow.blockfileextensions.domain.FileExtensionResponse;
import api.flow.blockfileextensions.entity.FileExtension;
import api.flow.blockfileextensions.exception.CustomException;
import api.flow.blockfileextensions.exception.ERROR;
import api.flow.blockfileextensions.repository.FileExtensionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class FileExtensionService {

    private final static Integer MAXIMUN_COUNT = 200;
    private final FileExtensionRepository fileExtensionRepository;


    public FileExtensionService(FileExtensionRepository fileExtensionRepository) {
        this.fileExtensionRepository = fileExtensionRepository;
    }

    public List<FileExtensionResponse> getFileExtensionList(String type) {
        List<FileExtension> extensionList = fileExtensionRepository.findByType(type);
        return extensionList.stream().map(FileExtensionResponse::of).collect(Collectors.toList());
    }

    public void setDefaultExtension(FileExtensionRequest request){
        if (request.getId() == null) throw new CustomException(ERROR.NO_INPUT_ID);
        if (isExistById(request.getId())) {
            FileExtension fileExtension = fileExtensionRepository.findById(request.getId()).get();
            fileExtension.updateExtension(request.getCheckYn());
        } else fileExtensionRepository.save(new FileExtension(request));
    }

    public void setCustomExtension(FileExtensionRequest request){
        if (fileExtensionRepository.countByType("C") >= MAXIMUN_COUNT){
            throw new CustomException(ERROR.INPUT_REQUEST_EXCEED);
        }
        if (fileExtensionRepository.existsByName(request.getName())) {
            throw new CustomException(ERROR.ALREADY_EXIST_EXTENSION);
        }

        fileExtensionRepository.save(new FileExtension(request));
    }

    private boolean isExistById(Long id) {
        return fileExtensionRepository.findById(id).isPresent();
    }

    public ResponseEntity<?> removeCustomExtension(Long id){
        fileExtensionRepository.deleteById(id);
        return ResponseEntity.ok(id);
    }
}