package at.oekosol.usermanagementservice.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("company")
@Data
public class Company {

    @Id
    private Long organizationId;

    @NotNull
    private String name;
    private String address;
    @Transient
    private List<User> users;
}
