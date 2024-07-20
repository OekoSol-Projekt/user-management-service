package at.oekosol.usermanagementservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("user_roles")
public class UserRole {
    @Id
    private Long userId;
    private Long roleId;
}