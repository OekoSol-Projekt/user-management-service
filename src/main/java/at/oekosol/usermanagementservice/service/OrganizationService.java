package at.oekosol.usermanagementservice.service;

import at.oekosol.sharedlibrary.events.UserInvitationEvent;
import at.oekosol.usermanagementservice.dtos.CompanyCreateDTO;
import at.oekosol.usermanagementservice.dtos.OrganizationCreateUserRoleMapDTO;
import at.oekosol.usermanagementservice.dtos.UserCreateDTO;
import at.oekosol.usermanagementservice.mapper.CompanyCreateMapper;
import at.oekosol.usermanagementservice.mapper.CompanyCreateMapperImpl;
import at.oekosol.usermanagementservice.model.*;
import at.oekosol.usermanagementservice.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final PersonalOrgRepository personalOrgRepository;
    private final CompanyCreateMapperImpl companyCreateMapperImpl;
    private PendingInviteRepository pendingInviteRepository;
    private final OrganizationUserRoleRepository organizationUserRoleRepository;
    private KafkaTemplate<String, Object> kafkaTemplate;

    public Mono<OrganizationUserRole> assignRoleToUser(Long organizationId, Long userId, Long roleId) {
        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        organizationUserRole.setOrganizationId(organizationId);
        organizationUserRole.setUserId(userId);
        organizationUserRole.setRoleId(roleId);

        return organizationUserRoleRepository.save(organizationUserRole);
    }

    public Flux<Role> getUserRolesInOrganization(Long organizationId, Long userId) {
        return organizationUserRoleRepository.findByOrganizationId(organizationId)
                .filter(our -> our.getUserId().equals(userId))
                .flatMap(our -> roleRepository.findById(our.getRoleId()));
    }

    public Flux<User> getUsersWithRoleInOrganization(Long organizationId, Long roleId) {
        return organizationUserRoleRepository.findByOrganizationId(organizationId)
                .filter(our -> our.getRoleId().equals(roleId))
                .flatMap(our -> userRepository.findById(our.getUserId()));
    }

    /**
     * Creates a new company and publishes user invitations.
     *
     * @param companyCreateDTO the DTO containing company creation details.
     * @return the created Company.
     */
    @Transactional
    public Mono<Company> createCompanyComplx(CompanyCreateDTO companyCreateDTO) {


        // Create an Organization entry first
        Organization organization = new Organization();
        organization.setType("company");

        return organizationRepository.save(organization)
                .doOnNext(savedOrganization -> log.info("Created Organization with ID: {}", savedOrganization.getId()))
                .flatMap(savedOrganization -> {

                    Company company = CompanyCreateMapper.INSTANCE.toEntity(companyCreateDTO);
                    company.setOrganizationId(savedOrganization.getId());

                    return companyRepository.save(company)
                            .doOnNext(savedCompany -> log.info("Created Company with ID: {}", savedCompany.getOrganizationId()))
                            .flatMap(savedCompany -> {
                                List<String> existingUsers = new ArrayList<>();
                                List<OrganizationCreateUserRoleMapDTO> newUsers = new ArrayList<>();

                                return Flux.fromIterable(companyCreateDTO.users())
                                        .flatMap(userRoleMap -> userRepository.existsByEmail(userRoleMap.email())
                                                .doOnNext(exists -> {
                                                    if (exists) {
                                                        existingUsers.add(userRoleMap.email());
                                                    } else {
                                                        newUsers.add(userRoleMap);
                                                    }
                                                }))
                                        .then()
                                        .flatMap(unused -> {
                                            UserInvitationEvent event = new UserInvitationEvent(
                                                    savedCompany.getOrganizationId(),
                                                    existingUsers,
                                                    newUsers.stream().map(OrganizationCreateUserRoleMapDTO::email).collect(Collectors.toList())
                                            );
                                            kafkaTemplate.send("UserInvitationTopic", event);
                                            log.info("Published UserInvitationEvent for company ID: {}", savedCompany.getOrganizationId());

                                            List<PendingInvite> pendingInvites = newUsers.stream().map(userRoleMap ->
                                                    new PendingInvite(savedCompany.getOrganizationId(), userRoleMap.email(), userRoleMap.role().id())
                                            ).collect(Collectors.toList());

                                            return pendingInviteRepository.saveAll(pendingInvites).then(Mono.just(savedCompany));
                                        });
                            });
                });
    }

    @Transactional
    public Mono<Company> createCompany(CompanyCreateDTO companyCreateDTO) {
        // Create an Organization entry first
        Organization organization = new Organization();
        organization.setType("company");

        return organizationRepository.save(organization)
                .doOnNext(savedOrganization -> log.info("Created Organization with ID: {}", savedOrganization.getId()))
                .flatMap(savedOrganization -> {

                    Company company = new Company();
                    company.setName(companyCreateDTO.name());
                    company.setAddress(companyCreateDTO.address());
                    company.setOrganizationId(4L);

                    return companyRepository.save(company);
                });
    }

    public Mono<Role> createRole(Role role) {
        return roleRepository.save(role);
    }
}
