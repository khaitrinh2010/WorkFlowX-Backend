package workflowx.auth_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "study_material")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Column(name = "file_url", columnDefinition = "VARCHAR(MAX)")
    private String fileUrl;

    @Lob
    @Column(name = "extracted_text", columnDefinition = "VARCHAR(MAX)")
    private String extractedText;

    @Lob
    @Column(name = "summary", columnDefinition = "VARCHAR(MAX)")
    private String summary;

    @Column(name = "topics", columnDefinition = "VARCHAR(MAX)")
    private String topics;


    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
}

