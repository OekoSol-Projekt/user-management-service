package at.oekosol.usermanagementservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Represents a role in the system.
 */
@Data
@Table("roles")
public class Role {

    @Id
    private Long id;
    private String role_name;
}

