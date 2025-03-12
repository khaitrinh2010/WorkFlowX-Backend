package workflowx.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import workflowx.auth_service.entity.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByStudyGroup_IdOrderByTimestampAsc(Long groupId);
}
