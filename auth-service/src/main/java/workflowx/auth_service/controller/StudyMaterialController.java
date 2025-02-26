package workflowx.auth_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import workflowx.auth_service.entity.StudyMaterial;
import workflowx.auth_service.service.StudyMaterialService;

import java.util.List;

@RestController
@RequestMapping("/api/study-materials")
public class StudyMaterialController {

    private final StudyMaterialService studyMaterialService;

    public StudyMaterialController(StudyMaterialService studyMaterialService) {
        this.studyMaterialService = studyMaterialService;
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadStudyMaterial(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file) {
        try {
            StudyMaterial studyMaterial = studyMaterialService.uploadStudyMaterial(title, description, file);
            return ResponseEntity.ok(studyMaterial);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading study material");
        }
    }

    @GetMapping()
    public ResponseEntity<?> getSummary() {
        try {
            List<StudyMaterial> result = studyMaterialService.getAllStudyMaterials();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching summary");
        }
    }

    @GetMapping("/summary/{id}")
    public ResponseEntity<?> getSummary(@PathVariable Long id) {
        try {
            String result = studyMaterialService.getSummary(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching summary");
        }
    }
}
