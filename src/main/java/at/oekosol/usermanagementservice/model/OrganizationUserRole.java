package at.oekosol.usermanagementservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("organization_user_roles")
@Data
public class OrganizationUserRole {

    @Id
    private Long id;
    private Long organizationId;
    private Long userId;
    private Long roleId;
}
