package at.oekosol.usermanagementservice.model;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("companies")
public class Company extends Organization {

    @NotNull
    private String name;
    private String address;
    @Transient
    List<User> users;
}
