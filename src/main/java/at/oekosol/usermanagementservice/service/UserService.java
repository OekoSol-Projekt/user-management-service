package at.oekosol.usermanagementservice.service;

import at.oekosol.usermanagementservice.model.Role;
import at.oekosol.usermanagementservice.model.User;
import at.oekosol.usermanagementservice.repository.RoleRepository;
import at.oekosol.usermanagementservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")))
                .map(user -> org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRoles().stream().map(Role::getName).toArray(String[]::new))
                        .build());
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
     * Saves a new user.
     *
     * @param user the user to save
     * @return the saved user
     */
    public Mono<User> saveUser(User user) {
        log.info("Saving new user with username: {}", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user)
                .flatMap(savedUser -> {
                    if (savedUser.getRoles() != null) {
                        return Mono.when(
                                savedUser.getRoles().stream().map(role -> roleRepository.findByName(role.getName())
                                        .switchIfEmpty(roleRepository.save(role))
                                        .map(savedRole -> {
                                            savedUser.getRoles().add(savedRole);
                                            return savedRole;
                                        })).collect(Collectors.toList())
                        ).thenReturn(savedUser);
                    } else {
                        return Mono.just(savedUser);
                    }
                });
    }

}
