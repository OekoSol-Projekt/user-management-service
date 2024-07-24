package at.oekosol.usermanagementservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Mono;

@Table("personal_org")
public class PersonalOrg{

    @Id
    private Long organizationId;
    private Long ownerId;

    @Transient
    private Mono<User> owner;
}
