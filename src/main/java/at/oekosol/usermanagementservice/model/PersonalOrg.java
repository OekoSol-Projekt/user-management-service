package at.oekosol.usermanagementservice.model;

import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Mono;

@Table("personal_orgs")
public class PersonalOrg extends Organization{
    private Long ownerId;

    @Transient
    private Mono<User> owner;
}
