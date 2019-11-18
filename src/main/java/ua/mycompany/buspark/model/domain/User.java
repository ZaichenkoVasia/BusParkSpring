package ua.mycompany.buspark.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.mycompany.buspark.model.domain.enums.Role;
import ua.mycompany.buspark.model.domain.enums.Status;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
public class User {
    private final Integer id;

    @NotEmpty
    @Pattern(regexp = "([A-Z])([a-z]{2,20})|([А-Я]([a-я]{2,20}))")
    private final String name;

    @NotEmpty
    @Pattern(regexp = "([A-Z])([a-z]{2,20})|([А-Я]([a-я]{2,20}))")
    private final String surname;

    @Email
    @NotEmpty
    private final String email;

    @Pattern(regexp = "(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}")
    @NotEmpty
    private final String password;

    @NotEmpty
    private final Role role;

    @NotEmpty
    private Status status;

    public User(User user, String password) {
        this.id = null;
        this.name = user.name;
        this.surname = user.surname;
        this.email = user.email;
        this.password = password;
        this.role = user.role;
    }
}
