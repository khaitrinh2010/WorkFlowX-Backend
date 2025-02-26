package workflowx.auth_service.mapper;

import org.mapstruct.Mapper;
import workflowx.auth_service.dto.UserDto;
import workflowx.auth_service.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);

    UserDto toDto(User user);
}
