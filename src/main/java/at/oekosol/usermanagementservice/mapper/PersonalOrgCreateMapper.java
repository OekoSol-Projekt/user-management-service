package at.oekosol.usermanagementservice.mapper;

import at.oekosol.usermanagementservice.dtos.PersonalOrgCreateDTO;
import at.oekosol.usermanagementservice.model.PersonalOrg;
import at.oekosol.usermanagementservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonalOrgCreateMapper {
    PersonalOrgCreateMapper INSTANCE = Mappers.getMapper(PersonalOrgCreateMapper.class);

    PersonalOrg toEntity(PersonalOrgCreateDTO personalOrgCreateDTO);
}
