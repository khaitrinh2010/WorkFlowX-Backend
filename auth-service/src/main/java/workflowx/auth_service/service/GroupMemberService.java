package workflowx.auth_service.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workflowx.auth_service.entity.GroupMember;
import workflowx.auth_service.entity.Role;
import workflowx.auth_service.entity.StudyGroup;
import workflowx.auth_service.entity.User;
import workflowx.auth_service.repository.GroupMemberRepository;
import workflowx.auth_service.repository.StudyGroupRepository;
import workflowx.auth_service.repository.UserRepository;

@Service
public class GroupMemberService {

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private StudyGroupRepository studyGroupRepository;

    @Autowired
    private UserRepository userRepository;

    // Join a study group
    public void joinGroup(Long groupId, Long userId) {
        StudyGroup group = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean alreadyMember = groupMemberRepository.existsByGroupAndUser(group, user);
        if (alreadyMember) {
            throw new RuntimeException("User is already in the group");
        }

        GroupMember member = new GroupMember();
        member.setGroup(group);
        member.setUser(user);
        member.setRole(Role.STUDENT);
        groupMemberRepository.save(member);
    }

    // Leave a study group
    public void leaveGroup(Long groupId, Long userId) {
        GroupMember member = groupMemberRepository.findByGroupIdAndUserId(groupId, userId);


        groupMemberRepository.delete(member);
    }

    public void updateUserRole(Long groupId, Long userId, Role newRole, Long adminId) {
        GroupMember adminMember = groupMemberRepository.findByGroupIdAndUserId(groupId, adminId);

        if (adminMember.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only admins can change roles");
        }

        GroupMember member = groupMemberRepository.findByGroupIdAndUserId(groupId, userId);

        member.setRole(newRole);
        groupMemberRepository.save(member);
    }
}

