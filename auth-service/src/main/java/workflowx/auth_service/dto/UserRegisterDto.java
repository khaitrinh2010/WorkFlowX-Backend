package workflowx.auth_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRegisterDto {
    public String email;
    public String username;
    public String password;
}
