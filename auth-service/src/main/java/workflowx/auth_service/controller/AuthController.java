package workflowx.auth_service.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import workflowx.auth_service.dto.JwtResponseDto;
import workflowx.auth_service.dto.UserLoginDto;
import workflowx.auth_service.dto.UserRegisterDto;
import workflowx.auth_service.service.AuthService;

import java.security.Principal;
import java.security.Security;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Welcome to Lam mtfk Khai service");
    }

    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validateToken(
            @RequestParam("email") String email,
            @RequestParam("token") String token) {

        boolean isValid = authService.validateToken(token, email);
        return ResponseEntity.ok(isValid);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDto request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto request) {
        String token = authService.login(request);
        return ResponseEntity.ok(new JwtResponseDto(token));
    }
    //NOTE
    //In a Spring application, when a user authenticates (e.g., via OAuth2, basic authentication, or
    // other mechanisms),
    // Spring Security populates the Principal object with the user's authentication details.
    //In the case of OAuth2, the Principal typically contains the authenticated user's details fetched
    // from the OAuth2 provider (e.g., Google, GitHub, etc.).

}
