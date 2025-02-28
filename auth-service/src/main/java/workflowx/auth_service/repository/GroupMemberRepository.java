package workflowx.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import workflowx.auth_service.entity.GroupMember;
import workflowx.auth_service.entity.StudyGroup;
import workflowx.auth_service.entity.User;

import java.util.Optional;

@Service
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    GroupMember findByGroupIdAndUserId(Long userId, Long groupId);

    void deleteByGroupId(Long groupId);

    boolean existsByGroupAndUser(StudyGroup group, User user);

}
