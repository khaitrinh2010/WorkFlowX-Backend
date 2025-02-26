package workflowx.auth_service.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import workflowx.auth_service.config.JwtUtil;
import workflowx.auth_service.dto.UserLoginDto;
import workflowx.auth_service.dto.UserRegisterDto;
import workflowx.auth_service.entity.User;
import workflowx.auth_service.repository.UserRepository;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String login(UserLoginDto userLoginDto) {
        if (userRepository.findByEmail(userLoginDto.getUsername()) != null) {
            if (passwordEncoder.matches(userLoginDto.getPassword(), userRepository.findByEmail(userLoginDto.getUsername()).getPassword())) {
                return jwtUtil.generateToken(userLoginDto.getUsername());
            }
        }
        return null;
    }

    public boolean validateToken(String token, String email) {
        return jwtUtil.validateToken(token, email);
    }
    public String register(UserRegisterDto userRegisterDto) {
        String email = userRegisterDto.getEmail();
        if (userRepository.findByEmail(email) == null) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
            newUser.setUsername(userRegisterDto.getUsername());
            userRepository.save(newUser);
            return "New user created Successfully";
        }
        return null;
    }

}
