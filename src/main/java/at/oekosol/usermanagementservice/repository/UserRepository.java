package at.oekosol.usermanagementservice.repository;


import at.oekosol.usermanagementservice.model.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

/**
 * Repository for performing CRUD operations on User entities.
 */
public interface UserRepository extends R2dbcRepository<User, Long> {
    Mono<User> findByUsername(String username);
}
