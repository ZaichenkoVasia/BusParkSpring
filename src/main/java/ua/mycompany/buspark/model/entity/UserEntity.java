package ua.mycompany.buspark.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.mycompany.buspark.model.domain.enums.Role;
import ua.mycompany.buspark.model.domain.enums.Status;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "user_name", nullable = false, length = 20)
    private String name;

    @Column(name = "user_surname", nullable = false, length = 20)
    private String surname;

    @Column(name = "user_email", nullable = false, unique = true, length = 20)
    private String email;

    @Column(name = "user_password", nullable = false, length = 20)
    private String password;

    @Column(name = "user_role", nullable = false, length = 7)
    private Role role;

    @Column(name = "user_status", nullable = false, length = 5)
    private Status status;
}
