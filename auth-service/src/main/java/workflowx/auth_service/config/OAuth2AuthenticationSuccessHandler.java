package workflowx.auth_service.config;
import workflowx.auth_service.config.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtService;

    public OAuth2AuthenticationSuccessHandler(JwtUtil jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        String jwtToken = jwtService.generateToken(email);
        String encodedToken = URLEncoder.encode(jwtToken, StandardCharsets.UTF_8);
        String redirectUrl = "http://localhost:8083/oauth-success?token=" + encodedToken;
        response.sendRedirect(redirectUrl);
    }
}
