package workflowx.auth_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import workflowx.auth_service.entity.User;
import workflowx.auth_service.repository.UserRepository;

import java.util.Collections;
import java.util.Map;

@Service
public class OAuth2UserService implements org.springframework.security.oauth2.client.userinfo.OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Autowired
    private UserRepository userRepository;

    /**
     * Fetch user details from OAuth2 provider and save it to the database
     * @param userRequest, the OAuth2 user request
     * @return OAuth2User
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //OAuth2UserRequest is passed to the loadUser method by Spring Security when handling an OAuth2 login
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); //the name of the ID field that OAuth2 provider uses to uniquely identify the user
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        User user = userRepository.findByEmail(email);

        if (user == null) {
            registerNewUser(email, attributes);
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                userNameAttributeName
        );
    }

    private void registerNewUser(String email, Map<String, Object> attributes) {
        User user = new User();
        user.setEmail(email);
        user.setUsername((String) attributes.get("name"));
        user.setPassword("");
        user.setRole("ROLE_USER");
        user.setProvider("GOOGLE");
        user.setProviderId((String) attributes.get("sub"));
        userRepository.save(user);
    }

}
