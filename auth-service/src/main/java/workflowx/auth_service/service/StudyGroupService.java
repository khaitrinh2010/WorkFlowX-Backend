package workflowx.auth_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workflowx.auth_service.entity.GroupMember;
import workflowx.auth_service.entity.Role;
import workflowx.auth_service.entity.StudyGroup;
import workflowx.auth_service.entity.User;
import workflowx.auth_service.repository.StudyGroupRepository;
import workflowx.auth_service.repository.UserRepository;
import workflowx.auth_service.repository.GroupMemberRepository;

@Service
public class StudyGroupService {

    @Autowired
    private StudyGroupRepository studyGroupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    public StudyGroup createGroup(String name, Long createdByUserId) {
        User createdBy = userRepository.findById(createdByUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StudyGroup group = new StudyGroup();
        group.setName(name);
        group.setCreatedBy(createdBy);
        studyGroupRepository.save(group);
        GroupMember member = new GroupMember();
        member.setGroup(group);
        member.setUser(createdBy);
        member.setRole(Role.ADMIN);
        groupMemberRepository.save(member);

        return group;
    }

    public StudyGroup getGroupById(Long groupId) {
        return studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }

    public void deleteGroup(Long groupId, Long userId) {
        StudyGroup group = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        GroupMember member = groupMemberRepository.findByGroupIdAndUserId(groupId, userId);

        if (member.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only admins can delete the group");
        }

        groupMemberRepository.deleteByGroupId(groupId);
        studyGroupRepository.delete(group);
    }
}

