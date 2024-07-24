package at.oekosol.usermanagementservice.mapper;

import at.oekosol.usermanagementservice.dtos.RoleDTO;
import at.oekosol.usermanagementservice.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDTO toDto(Role role);
    Role toEntity(RoleDTO roleDTO);
}
