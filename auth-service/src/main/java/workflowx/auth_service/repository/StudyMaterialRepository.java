package workflowx.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workflowx.auth_service.entity.StudyMaterial;

@Repository
public interface StudyMaterialRepository extends JpaRepository<StudyMaterial, Long> {
    StudyMaterial findByTitle(String title);
}
