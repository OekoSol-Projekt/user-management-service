package at.oekosol.usermanagementservice.mapper;

import at.oekosol.usermanagementservice.dtos.OrganizationUserRoleDTO;
import at.oekosol.usermanagementservice.model.OrganizationUserRole;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrganizationUserRoleMapper {
    OrganizationUserRoleMapper INSTANCE = Mappers.getMapper(OrganizationUserRoleMapper.class);

    OrganizationUserRoleDTO toDto(OrganizationUserRole organizationUserRole);
    OrganizationUserRole toEntity(OrganizationUserRoleDTO organizationUserRoleDTO);
}
