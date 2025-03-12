package workflowx.auth_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workflowx.auth_service.entity.*;
import workflowx.auth_service.repository.StudyGroupRepository;
import workflowx.auth_service.repository.UserRepository;
import workflowx.auth_service.repository.GroupMemberRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Set<GroupMember> members = new HashSet<>();
        GroupMember member = new GroupMember();
        member.setGroup(group);
        member.setUser(createdBy);
        member.setRole(Role.ADMIN);
        createdBy.getGroupMemberships().add(member);
        members.add(member);
        group.setMembers(members);
        List<ChatMessage> messages = new ArrayList<>();
        group.setMessages(messages);
        studyGroupRepository.save(group);
        groupMemberRepository.save(member);
        return group;
    }



    public List<StudyGroup> getAllGroups() {
        return studyGroupRepository.findAll();
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

    public List<StudyGroup> getGroupsByUserId(Long userId) {
        List<StudyGroup> result = new ArrayList<>();
        List<StudyGroup> allGroups = studyGroupRepository.findAll();
        for (StudyGroup group : allGroups) {
            Set<GroupMember> members = group.getMembers();
            for (GroupMember member : members) {
                if (member.getUser().getId().equals(userId)) {
                    result.add(group);
                    break;
                }
            }
        }
        return result;
    }
}

