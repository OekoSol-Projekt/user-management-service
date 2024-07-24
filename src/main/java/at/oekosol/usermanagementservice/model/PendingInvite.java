package at.oekosol.usermanagementservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "pending_invites")
public class PendingInvite {

    @Id
    private Long id;

    private Long organizationId;
    private String email;
    private Long roleId;

    public PendingInvite(Long organizationId, String email, Long roleId) {
        this.organizationId = organizationId;
        this.email = email;
        this.roleId = roleId;
    }
}
