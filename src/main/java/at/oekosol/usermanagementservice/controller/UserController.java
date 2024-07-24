package at.oekosol.usermanagementservice.controller;

import at.oekosol.usermanagementservice.config.JwtUtil;
import at.oekosol.usermanagementservice.dtos.UserAuthenticationRequestDTO;
import at.oekosol.usermanagementservice.dtos.UserRegistrationRequestDTO;
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
     * @param userRegistrationRequestDto the registration request
     * @return the response entity
     */
    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestBody UserRegistrationRequestDTO userRegistrationRequestDto) {
        log.info("Registering user: {}", userRegistrationRequestDto.username());
        return null;
        //return userService.registerUser(userRegistrationRequestDto).map(user -> ResponseEntity.ok("User registered successfully")).onErrorResume(e -> Mono.just(ResponseEntity.status(400).body(e.getMessage())));
    }

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param userAuthenticationRequestDto the authentication request
     * @return the JWT token
     */
    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody UserAuthenticationRequestDTO userAuthenticationRequestDto) {
        log.info("Authenticating user: {}", userAuthenticationRequestDto.getUsername());
        return userService.findByUsername(userAuthenticationRequestDto.getUsername()).flatMap(userDetails -> {
            if (passwordEncoder.matches(userAuthenticationRequestDto.getPassword(), userDetails.getPassword())) {
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
        return null;
        //return ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication).flatMap(authentication -> userService.findUserByUsername(authentication.getName()).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.status(404).build()));
    }

    @GetMapping("/test")
    public Mono<ResponseEntity<String>> test() {
        return Mono.just(ResponseEntity.ok("Test successful"));
    }
}
