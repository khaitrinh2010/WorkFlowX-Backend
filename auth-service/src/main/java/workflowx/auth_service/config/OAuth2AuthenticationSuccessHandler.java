package workflowx.auth_service.config;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import workflowx.auth_service.config.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import workflowx.auth_service.dto.UserDto;
import workflowx.auth_service.entity.User;
import workflowx.auth_service.service.UserService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtService;
    private final UserService userService; // ✅ Injected properly

    @Autowired // ✅ Ensure dependency injection
    public OAuth2AuthenticationSuccessHandler(JwtUtil jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        UserDto user = userService.getUserByEmail(email);
        String jwtToken = jwtService.generateToken(email);
        String encodedJwtToken = URLEncoder.encode(jwtToken, StandardCharsets.UTF_8);
        String encodedUserId = URLEncoder.encode(user.getId().toString(), StandardCharsets.UTF_8);
        String redirectUrl = "http://localhost:3000/auth/callback?token=" + encodedJwtToken + "&userId=" + encodedUserId;
        response.sendRedirect(redirectUrl);
    }
}
