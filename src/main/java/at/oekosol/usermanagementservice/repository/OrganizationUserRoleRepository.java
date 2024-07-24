package at.oekosol.usermanagementservice.repository;

import at.oekosol.usermanagementservice.model.OrganizationUserRole;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface OrganizationUserRoleRepository extends ReactiveCrudRepository<OrganizationUserRole, Long>{
    Flux<OrganizationUserRole> findByOrganizationId(Long organizationId);
    Flux<OrganizationUserRole> findByUserId(Long userId);
    Flux<OrganizationUserRole> findByRoleId(Long roleId);
}
