package at.oekosol.usermanagementservice.service;

import at.oekosol.usermanagementservice.dtos.RegistrationRequestDto;
import at.oekosol.usermanagementservice.model.Role;
import at.oekosol.usermanagementservice.model.User;
import at.oekosol.usermanagementservice.model.UserRole;
import at.oekosol.usermanagementservice.repository.RoleRepository;
import at.oekosol.usermanagementservice.repository.UserRepository;
import at.oekosol.usermanagementservice.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for user operations.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")))
                .flatMap(user -> userRoleRepository.findByUserId(user.getId())
                        .flatMap(userRole -> roleRepository.findById(userRole.getRoleId()))
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collectList()
                        .map(authorities -> org.springframework.security.core.userdetails.User
                                .withUsername(user.getUsername())
                                .password(user.getPassword())
                                .authorities(authorities)
                                .build()));
    }

    /**
     * Finds a user by username.
     *
     * @param username the username
     * @return the user
     */
    public Mono<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Registers a new user.
     *
     * @param registrationRequestDto the registration request
     * @return the registered user
     */
    public Mono<Object> registerUser(RegistrationRequestDto registrationRequestDto) {
        return userRepository.findByUsername(registrationRequestDto.username())
                .flatMap(existingUser -> Mono.error(new RuntimeException("User already exists")))
                .switchIfEmpty(Mono.defer(() -> {
                    User newUser = new User();
                    newUser.setUsername(registrationRequestDto.username());
                    newUser.setPassword(passwordEncoder.encode(registrationRequestDto.password()));
                    return userRepository.save(newUser)
                            .flatMap(user -> {
                                Mono<Void> rolesMono = Mono.when(
                                        registrationRequestDto.roles().stream()
                                                .map(roleName -> roleRepository.findByName(roleName)
                                                        .switchIfEmpty(Mono.error(new RuntimeException("Role not found: " + roleName)))
                                                        .flatMap(role -> {
                                                            UserRole userRole = new UserRole();
                                                            userRole.setUserId(user.getId());
                                                            userRole.setRoleId(role.getId());
                                                            return userRoleRepository.save(userRole);
                                                        }))
                                                .collect(Collectors.toList())
                                );
                                return rolesMono.then(Mono.just(user));
                            });
                }));
    }
}
