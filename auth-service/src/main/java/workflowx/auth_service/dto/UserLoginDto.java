package workflowx.auth_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserLoginDto {

    private String username;

    private String password;
}
