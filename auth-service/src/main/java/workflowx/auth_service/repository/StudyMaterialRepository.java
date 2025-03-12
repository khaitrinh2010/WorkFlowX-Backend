package workflowx.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workflowx.auth_service.entity.StudyMaterial;

import java.util.List;

@Repository
public interface StudyMaterialRepository extends JpaRepository<StudyMaterial, Long> {
    StudyMaterial findByTitle(String title);

    List<StudyMaterial> findByTeam_Id(Long teamId);
}
