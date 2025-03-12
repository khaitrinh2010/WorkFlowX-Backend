package workflowx.auth_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Users")
@JsonIgnoreProperties("groupMemberships")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // Bcrypt-hashed

    private String role; // e.g., ADMIN, COLLABORATOR

    private String provider; // LOCAL, GOOGLE

    private String providerId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<GroupMember> groupMemberships = new ArrayList<>();

    @Override
    public String toString() {
        return "User{id=" + id + "}"; // Avoid referencing groupMemberships
    }
}
