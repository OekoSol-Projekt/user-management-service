package at.oekosol.usermanagementservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Represents a user in the system.
 */
@Data
@Table("users")
public class User {

    @Id
    private Long id;
    private String username;
    private String password;
}

