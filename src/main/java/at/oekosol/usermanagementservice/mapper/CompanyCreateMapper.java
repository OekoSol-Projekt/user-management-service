package at.oekosol.usermanagementservice.mapper;

import at.oekosol.usermanagementservice.dtos.CompanyCreateDTO;
import at.oekosol.usermanagementservice.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CompanyCreateMapper {
    CompanyCreateMapper INSTANCE = Mappers.getMapper(CompanyCreateMapper.class);

    Company toEntity(CompanyCreateDTO companyCreateDTO);
}
