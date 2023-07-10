package api.flow.blockfileextensions.service;

import api.flow.blockfileextensions.domain.FileExtensionRequest;
import api.flow.blockfileextensions.entity.FileExtension;
import api.flow.blockfileextensions.exception.CustomException;
import api.flow.blockfileextensions.repository.FileExtensionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;

@SpringBootTest
@Transactional
class FileExtensionServiceTest {

    FileExtensionRequest fileExtensionRequest = new FileExtensionRequest();
    @Autowired
    FileExtensionService fileExtensionService;
    @Autowired
    FileExtensionRepository fileExtensionRepository;

    @Test
    void 확장자_리스트_불러오기() {
        ArrayList<String> extensions = new ArrayList<>();
        extensions.add("bat");
        extensions.add("cmd");
        extensions.add("com");

        for(int i = 0; i < extensions.size(); i++){
            fileExtensionRequest.setName(extensions.get(i));
            fileExtensionRequest.setType("D");
            fileExtensionService.setCustomExtension(fileExtensionRequest);
        }

        int count = fileExtensionRepository.countByType("D");
        Assertions.assertEquals(3, count);
    }

    @Test
    void 입력된_확장자_저장() {
        fileExtensionRequest.setName("bat");
        fileExtensionRequest.setType("C");

        fileExtensionService.setCustomExtension(fileExtensionRequest);

        boolean isExist = fileExtensionRepository.existsByName(fileExtensionRequest.getName());
        Assertions.assertEquals(true, isExist);
    }

    @Test
    void 고정_확장자_수정() {
        //저장
        fileExtensionRequest.setId(1L);
        fileExtensionRequest.setName("bat");
        fileExtensionRequest.setType("D");
        fileExtensionRequest.setCheckYn("N");
        fileExtensionService.setDefaultExtension(fileExtensionRequest);

        //수정
        FileExtensionRequest updateExtension = new FileExtensionRequest();
        updateExtension.setName("bat");
        updateExtension.setType("D");
        updateExtension.setCheckYn("Y");

        Long id = fileExtensionRepository.findByName("bat").getId();
        updateExtension.setId(id);
        fileExtensionService.setDefaultExtension(fileExtensionRequest);

        FileExtension selectExtension = fileExtensionRepository.findById(id).get();
        Assertions.assertEquals(fileExtensionRequest.getCheckYn(), selectExtension.getCheckYn()); //요청된체크값, 조회된체크값
    }

    @Test
    void 확장자_요청_초과_예외처리() {
        Assertions.assertThrows(CustomException.class, () -> {
            for(int i = 0; i < 201; i++){
                fileExtensionRequest.setName("bat" + i);
                fileExtensionRequest.setType("C");
                fileExtensionService.setCustomExtension(fileExtensionRequest);
            }
        }, "예외가 발생하지 않음");
    }

    @Test
    void 중복_데이터_예외처리() {
        fileExtensionRequest.setName("bat");
        fileExtensionRequest.setType("C");

        Assertions.assertThrows(CustomException.class, () -> {
            for(int i = 0; i < 2; i++){
                fileExtensionService.setCustomExtension(fileExtensionRequest);
            }
        }, "예외가 발생하지 않음");
    }

    @Test
    void 커스텀_확장자_삭제() {
        fileExtensionRequest.setName("bat");
        fileExtensionRequest.setType("C");
        fileExtensionService.setCustomExtension(fileExtensionRequest);

        Long id = fileExtensionRepository.findByName("bat").getId();
        fileExtensionService.removeCustomExtension(id);

        boolean isExist =  fileExtensionRepository.existsByName("bat");
        Assertions.assertEquals(false, isExist);
    }
}