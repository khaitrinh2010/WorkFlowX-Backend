package workflowx.auth_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workflowx.auth_service.dto.UserDto;
import workflowx.auth_service.entity.User;
import workflowx.auth_service.mapper.UserMapper;
import workflowx.auth_service.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    @Override
    public void saveUser(UserDto userDto) {
        userRepository.save(userMapper.toEntity(userDto));
    }

    @Override
    @Transactional
    public UserDto getUser(Long id) {
        return userMapper.toDto(userRepository.findById(id).get());
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(UserDto userDto) {
        userRepository.save(userMapper.toEntity(userDto));
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> resultList = new ArrayList<>();
        for (User user : users) {
            resultList.add(userMapper.toDto(user));
        }
        return resultList;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userMapper.toDto(userRepository.findByEmail(email));
    }
}
