package at.oekosol.usermanagementservice.dtos;

import lombok.Data;

/**
 * Represents an authentication request.
 */
@Data
public class UserAuthenticationRequestDTO {
    private String username;
    private String password;
}
