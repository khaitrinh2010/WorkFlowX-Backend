package workflowx.auth_service.entity;

import jakarta.persistence.*;
import lombok.*;
import workflowx.auth_service.entity.StudyGroup;
import workflowx.auth_service.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "group_members")
@Data
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private StudyGroup group;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private Role role = Role.STUDENT; // STUDENT or ADMIN

    private LocalDateTime joinedAt = LocalDateTime.now();
}
