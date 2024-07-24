package at.oekosol.usermanagementservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;;


@Table("organization")
@Data
public class Organization {

    @Id
    private Long id;
    private String type;
}
