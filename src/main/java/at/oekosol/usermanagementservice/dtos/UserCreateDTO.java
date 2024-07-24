package at.oekosol.usermanagementservice.dtos;

public record UserCreateDTO(String email, String name, String surname, String password) {
}
