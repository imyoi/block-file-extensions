package api.flow.blockfileextensions.controller;

import api.flow.blockfileextensions.domain.FileExtensionRequest;
import api.flow.blockfileextensions.service.FileExtensionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class FileExtensionController {

    private final FileExtensionService fileExtensionService;

    public FileExtensionController(FileExtensionService fileExtensionService) {
        this.fileExtensionService = fileExtensionService;
    }

    @GetMapping("extension")
    public ResponseEntity<?> getFileExtensionList() {
        Map<String, Object> result = new HashMap<>();
        result.put("defaultExtensions", fileExtensionService.getFileExtensionList("D"));
        result.put("customExtensions", fileExtensionService.getFileExtensionList("C"));
        return ResponseEntity.ok(result);
    }

    @PostMapping("extension")
    public ResponseEntity<?> blockFileExtension(@RequestBody FileExtensionRequest fileExtensionRequest) {
        if ("C".equals(fileExtensionRequest.getType())) {
            fileExtensionService.setCustomExtension(fileExtensionRequest);
        } else {
            fileExtensionService.setDefaultExtension(fileExtensionRequest);
        }
        return ResponseEntity.ok(fileExtensionService.getFileExtensionList(fileExtensionRequest.getType()));
    }

    @DeleteMapping("extension/{id}")
    public ResponseEntity<?> removeCustomExtension(@PathVariable Long id) {
        return ResponseEntity.ok(fileExtensionService.removeCustomExtension(id));
    }
}
