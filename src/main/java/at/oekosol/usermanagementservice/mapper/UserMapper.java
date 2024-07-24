package at.oekosol.usermanagementservice.mapper;

import at.oekosol.usermanagementservice.dtos.UserDTO;
import at.oekosol.usermanagementservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDto(User user);
    User toEntity(UserDTO userDTO);

}
