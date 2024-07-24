package at.oekosol.usermanagementservice.service;

import at.oekosol.usermanagementservice.dtos.CompanyCreateDTO;
import at.oekosol.usermanagementservice.mapper.CompanyCreateMapper;
import at.oekosol.usermanagementservice.model.Company;
import at.oekosol.usermanagementservice.model.OrganizationUserRole;
import at.oekosol.usermanagementservice.model.Role;
import at.oekosol.usermanagementservice.model.User;
import at.oekosol.usermanagementservice.repository.*;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final PersonalOrgRepository personalOrgRepository;
    private final OrganizationUserRoleRepository organizationUserRoleRepository;

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
}
