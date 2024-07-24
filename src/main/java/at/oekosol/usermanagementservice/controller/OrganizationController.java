package at.oekosol.usermanagementservice.controller;

import at.oekosol.usermanagementservice.dtos.CompanyCreateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/organizations")
@Slf4j
@RequiredArgsConstructor
public class OrganizationController {


    @PostMapping("/createCompany")
    public Mono<ResponseEntity<String>> create(@RequestBody CompanyCreateDTO companyCreateDTO) {
        log.info("Create company: {}", companyCreateDTO.name());
        return null;
    }

}
