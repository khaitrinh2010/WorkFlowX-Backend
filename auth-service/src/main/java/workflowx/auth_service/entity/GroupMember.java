package workflowx.auth_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import workflowx.auth_service.entity.StudyGroup;
import workflowx.auth_service.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "group_members")
@Data
@EqualsAndHashCode(exclude = {"user", "group"})
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "study_group_id", nullable = false)
    @JsonIgnore
    private StudyGroup group;

    @Enumerated(EnumType.STRING)
    private Role role = Role.STUDENT; // STUDENT or ADMIN

    private LocalDateTime joinedAt = LocalDateTime.now();
}
