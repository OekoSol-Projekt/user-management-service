package at.oekosol.usermanagementservice.repository;


import at.oekosol.usermanagementservice.model.Role;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * Repository for performing CRUD operations on Role entities.
 */
public interface RoleRepository extends ReactiveCrudRepository<Role, Long> {
    Mono<Role> findByName(String name);
}
