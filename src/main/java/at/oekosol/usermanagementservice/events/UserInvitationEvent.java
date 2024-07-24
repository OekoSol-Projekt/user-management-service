package at.oekosol.usermanagementservice.events;

import lombok.Data;

import java.util.List;

@Data
public class UserInvitationEvent {
    private Long companyId;
    private List<String> existingUsers;
    private List<String> newUsers;
}
