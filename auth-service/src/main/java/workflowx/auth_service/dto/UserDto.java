package workflowx.auth_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String username;
    private String role;
    private String provider;
    private String providerId;
}
