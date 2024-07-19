package at.oekosol.usermanagementservice.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Web filter for JWT authentication.
 */
@Component
public class JwtAuthenticationWebFilter implements WebFilter {

    private final JwtUtil jwtUtil;
    private final ReactiveAuthenticationManager authenticationManager;
    private final ServerSecurityContextRepository securityContextRepository = NoOpServerSecurityContextRepository.getInstance();

    public JwtAuthenticationWebFilter(JwtUtil jwtUtil, ReactiveAuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String jwtToken = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
        }

        if (jwtToken != null) {
            String username = jwtUtil.extractUsername(jwtToken);
            return authenticationManager.authenticate(new JwtAuthenticationToken(jwtToken))
                    .flatMap(authentication -> securityContextRepository.save(exchange, new SecurityContextImpl(authentication))
                            .then(chain.filter(exchange)))
                    .onErrorResume(e -> {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    });
        }

        return chain.filter(exchange);
    }
}