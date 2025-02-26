package workflowx.auth_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import workflowx.auth_service.dto.UserDto;
import workflowx.auth_service.repository.UserRepository;

import java.util.List;

public interface UserService {
    public void saveUser(UserDto userDto);
    public UserDto getUser(Long id);
    public void deleteUser(Long id);
    public void updateUser(UserDto userDto);
    public List<UserDto> getAllUsers();
    public UserDto getUserByEmail(String email);

}
