package at.oekosol.usermanagementservice.service;

import at.oekosol.usermanagementservice.dtos.UserRegistrationRequestDTO;
import at.oekosol.usermanagementservice.exception.RoleNotFoundException;
import at.oekosol.usermanagementservice.model.User;
import at.oekosol.usermanagementservice.repository.RoleRepository;
import at.oekosol.usermanagementservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return null;
    }

    //    private final UserRoleRepository userRoleRepository;
//
//    @Override
//    public Mono<UserDetails> findByUsername(String username) {
//        return userRepository.findByUsername(username).switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found"))).flatMap(user -> userRoleRepository.findByUserId(user.getId()).flatMap(userRole -> roleRepository.findById(userRole.getRoleId())).map(role -> new SimpleGrantedAuthority(role.getName())).collectList().map(authorities -> org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPassword()).authorities(authorities).build()));
//    }
//
//    /**
//     * Finds a user by username.
//     *
//     * @param username the username
//     * @return the user
//     */
//    public Mono<User> findUserByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }
//
//    /**
//     * Registers a new user.
//     *
//     * @param userRegistrationRequestDto the registration request
//     * @return the registered user
//     */
////    public Mono<Object> registerUser(UserRegistrationRequestDTO userRegistrationRequestDto) {
////        return userRepository.findByUsername(userRegistrationRequestDto.username())
////                .flatMap(existingUser -> Mono.error(new UsernameNotFoundException("User already exists"))).switchIfEmpty(Mono.defer(() -> {
////                    User newUser = new User();
////                    newUser.setUsername(userRegistrationRequestDto.username());
////                    newUser.setPassword(passwordEncoder.encode(userRegistrationRequestDto.password()));
////                    return userRepository.save(newUser).flatMap(user -> {
////                        Mono<Void> rolesMono = Mono.when(userRegistrationRequestDto.roles().stream()
////                                .map(roleName -> roleRepository.findByName(roleName).switchIfEmpty(Mono.error(new RoleNotFoundException("Role not found: " + roleName))).flatMap(role -> {
////                                    UserRole userRole = new UserRole();
////                                    userRole.setUserId(user.getId());
////                                    userRole.setRoleId(role.getId());
////                                    return userRoleRepository.save(userRole);
////                                })).collect(Collectors.toList()));
////                        return rolesMono.then(Mono.just(user));
////                    });
////                }));
////    }
}
