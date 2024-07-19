package at.oekosol.usermanagementservice.repository;


import at.oekosol.usermanagementservice.model.Role;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

/**
 * Repository for performing CRUD operations on Role entities.
 */
public interface RoleRepository extends R2dbcRepository<Role, Long> {
    Mono<Role> findByName(String name);
}
