package workflowx.auth_service.service;

import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import workflowx.auth_service.entity.StudyMaterial;
import workflowx.auth_service.repository.StudyMaterialRepository;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyMaterialService {
    private final StudyMaterialRepository studyMaterialRepository;
    private final MinIOService minIOService;
    private final AIService aiService;

    public StudyMaterial uploadStudyMaterial(String title, String description, MultipartFile file) {
        try {
            String fileUrl = minIOService.uploadFile(file);
            String extractedText = "";
            if (file.getContentType() != null &&
                    file.getContentType().equalsIgnoreCase("application/pdf")) {
                    extractedText = extractTextFromPDF(file.getInputStream());
            }
            String summary = aiService.summarizeText(extractedText);
            String topics = aiService.extractTopics(extractedText);
            StudyMaterial studyMaterial = new StudyMaterial();
            studyMaterial.setTitle(title);
            studyMaterial.setDescription(description);
            studyMaterial.setFileUrl(fileUrl);
            studyMaterial.setExtractedText(extractedText);
            studyMaterial.setUploadedAt(LocalDateTime.now());
            studyMaterial.setSummary(summary);
            studyMaterial.setTopics(topics);
            return studyMaterialRepository.save(studyMaterial);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private String extractTextFromPDF(InputStream pdfStream) {
        try (PDDocument document = PDDocument.load(pdfStream)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getSummary(Long id) {
        StudyMaterial studyMaterial = studyMaterialRepository.findById(id).orElse(null);
        if (studyMaterial == null) {
            return null;
        }
        return studyMaterial.getSummary();
    }

    public List<StudyMaterial> getAllStudyMaterials() {
        return studyMaterialRepository.findAll();
    }

}
