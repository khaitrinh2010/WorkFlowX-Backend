package workflowx.auth_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Users")
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
}
