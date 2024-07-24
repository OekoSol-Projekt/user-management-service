package at.oekosol.usermanagementservice.repository;

import at.oekosol.usermanagementservice.model.Organization;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrganizationRepository extends ReactiveCrudRepository<Organization, Long>{
}
