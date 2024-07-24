package at.oekosol.usermanagementservice.controller;

import at.oekosol.usermanagementservice.dtos.CompanyCreateDTO;
import at.oekosol.usermanagementservice.model.Company;
import at.oekosol.usermanagementservice.model.Role;
import at.oekosol.usermanagementservice.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing organizations.
 */
@RestController
@RequestMapping("/organizations")
@Slf4j
@RequiredArgsConstructor
public class OrganizationController {


    private final OrganizationService organizationService;

    /**
     * Creates a new company.
     *
     * @param companyCreateDTO the DTO containing company creation details.
     * @return the created Company.
     */
    @PostMapping("/createCompany")
    public Mono<ResponseEntity<Company>> createCompany(@RequestBody CompanyCreateDTO companyCreateDTO) {
        log.debug("REST request to create Company: {}", companyCreateDTO);
        return organizationService.createCompany(companyCreateDTO)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> {
                    log.error("Error creating company: {}", e.getMessage());
                    return Mono.just(ResponseEntity.badRequest().build());
                });
    }

    @PostMapping("/createRole")
    public Mono<ResponseEntity<Role>> createRole(@RequestBody Role role) {
        log.debug("REST request to create Role: {}", role);
        return organizationService.createRole(role)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> {
                    log.error("Error creating role: {}", e.getMessage());
                    return Mono.just(ResponseEntity.badRequest().build());
                });
    }


}
