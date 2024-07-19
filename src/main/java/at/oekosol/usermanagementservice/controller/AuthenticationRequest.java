package at.oekosol.usermanagementservice.controller;

import lombok.Data;

/**
 * Represents an authentication request.
 */
@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
