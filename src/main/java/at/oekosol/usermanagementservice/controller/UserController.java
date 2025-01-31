package at.oekosol.usermanagementservice.controller;

import at.oekosol.usermanagementservice.config.JwtUtil;
import at.oekosol.usermanagementservice.dtos.AuthenticationRequestDto;
import at.oekosol.usermanagementservice.dtos.RegistrationRequestDto;
import at.oekosol.usermanagementservice.model.User;
import at.oekosol.usermanagementservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new user.
     *
     * @param registrationRequestDto the registration request
     * @return the response entity
     */
    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestBody RegistrationRequestDto registrationRequestDto) {
        log.info("Registering user: {}", registrationRequestDto.username());
        return userService.registerUser(registrationRequestDto).map(user -> ResponseEntity.ok("User registered successfully")).onErrorResume(e -> Mono.just(ResponseEntity.status(400).body(e.getMessage())));
    }

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param authenticationRequestDto the authentication request
     * @return the JWT token
     */
    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        log.info("Authenticating user: {}", authenticationRequestDto.getUsername());
        return userService.findByUsername(authenticationRequestDto.getUsername()).flatMap(userDetails -> {
            if (passwordEncoder.matches(authenticationRequestDto.getPassword(), userDetails.getPassword())) {
                return Mono.just(ResponseEntity.ok(jwtUtil.generateToken(userDetails)));
            } else {
                return Mono.just(ResponseEntity.status(401).build());
            }
        });
    }

    /**
     * Gets the authenticated user.
     *
     * @return the authenticated user
     */
    @GetMapping("/me")
    public Mono<ResponseEntity<User>> getAuthenticatedUser() {
        return ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication).flatMap(authentication -> userService.findUserByUsername(authentication.getName()).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.status(404).build()));
    }

    @GetMapping("/test")
    public Mono<ResponseEntity<String>> test() {
        return Mono.just(ResponseEntity.ok("Test successful"));
    }
}
