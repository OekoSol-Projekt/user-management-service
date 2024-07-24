package at.oekosol.usermanagementservice.repository;

import at.oekosol.usermanagementservice.model.Company;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CompanyRepository extends ReactiveCrudRepository<Company, Long> {
}
