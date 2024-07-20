package at.oekosol.usermanagementservice.dtos;

import java.util.Set;

public record RegistrationRequestDto(String username, String password, Set<String> roles) {
}

