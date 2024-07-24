package at.oekosol.usermanagementservice.repository;

import at.oekosol.usermanagementservice.model.PersonalOrg;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PersonalOrgRepository extends ReactiveCrudRepository<PersonalOrg, Long>{
}
