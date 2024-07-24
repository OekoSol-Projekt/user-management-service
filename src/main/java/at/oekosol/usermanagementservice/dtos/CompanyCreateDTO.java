package at.oekosol.usermanagementservice.dtos;

import java.util.List;

public record CompanyCreateDTO(String name, String address, List<UserDTO> users) {
}
