package at.oekosol.usermanagementservice.dtos;

import lombok.Data;

/**
 * Represents an authentication request.
 */
@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
