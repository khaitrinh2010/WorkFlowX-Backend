package workflowx.auth_service.dto;

public class JwtResponseDto {
    private String token;

    public JwtResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
