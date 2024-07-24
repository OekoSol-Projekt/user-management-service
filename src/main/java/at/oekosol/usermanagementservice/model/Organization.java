package at.oekosol.usermanagementservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("organizations")
@Data
public abstract class Organization {

    @Id
    private Long id;
    private String name;
    private String address;
    private User owner;
}
