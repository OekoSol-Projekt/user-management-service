package at.oekosol.usermanagementservice.repository;

import at.oekosol.usermanagementservice.model.PendingInvite;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface PendingInviteRepository extends ReactiveCrudRepository<PendingInvite, Long> {
    Mono<Void> deleteByEmail(String email);
}
