package workflowx.auth_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2Controller {

    @GetMapping("/oauth-success")
    public String googleLogin(@RequestParam("token") String token) {
        return "Welcome to Google! Your JWT token is: " + token;
    }
}
