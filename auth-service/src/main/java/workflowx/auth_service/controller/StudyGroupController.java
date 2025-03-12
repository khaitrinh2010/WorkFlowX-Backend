package workflowx.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import workflowx.auth_service.dto.StudyGroupDto;
import workflowx.auth_service.entity.Role;
import workflowx.auth_service.entity.StudyGroup;
import workflowx.auth_service.service.GroupMemberService;
import workflowx.auth_service.service.StudyGroupService;

import java.util.List;

@RestController
@RequestMapping("api/groups")
public class StudyGroupController {

    @Autowired
    private StudyGroupService studyGroupService;

    @Autowired
    private GroupMemberService groupMemberService;

    // Create a new study group
    @PostMapping("/create")
    public ResponseEntity<StudyGroup> createGroup(@RequestBody StudyGroupDto dto) {
        StudyGroup group = studyGroupService.createGroup(dto.getName(), dto.getCreatedBy());
        return ResponseEntity.ok(group);
    }

    // Join a study group
    @PostMapping("/{id}/join")
    public ResponseEntity<String> joinGroup(@PathVariable Long id, @RequestParam Long userId) {
        groupMemberService.joinGroup(id, userId);
        return ResponseEntity.ok("User added to group");
    }

    // Leave a study group
    @DeleteMapping("/{id}/leave")
    public ResponseEntity<String> leaveGroup(@PathVariable Long id, @RequestParam Long userId) {
        groupMemberService.leaveGroup(id, userId);
        return ResponseEntity.ok("User left the group");
    }

    // Get group details
    @GetMapping("/{id}")
    public ResponseEntity<StudyGroup> getGroup(@PathVariable Long id) {
        StudyGroup group = studyGroupService.getGroupById(id);
        return ResponseEntity.ok(group);
    }

    // Delete a study group (only admins)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable Long id, @RequestParam Long userId) {
        studyGroupService.deleteGroup(id, userId);
        return ResponseEntity.ok("Group deleted");
    }

    // Update a user's role (Admin Only)
    @PatchMapping("/{groupId}/update-role")
    public ResponseEntity<String> updateUserRole(
            @PathVariable Long groupId,
            @RequestParam Long userId,
            @RequestParam Role newRole,
            @RequestParam Long adminId) {
        groupMemberService.updateUserRole(groupId, userId, newRole, adminId);
        return ResponseEntity.ok("User role updated");
    }

    /**
     * Get all groups a user is a member of
     * @param userId, the user id
     * @return
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<StudyGroup>> getGroupsByUser(@PathVariable Long userId) {
        List<StudyGroup> groups = studyGroupService.getGroupsByUserId(userId);
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudyGroup>> getAllGroups() {
        List<StudyGroup> groups = studyGroupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }
}

