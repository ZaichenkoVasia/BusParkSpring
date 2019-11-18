package ua.mycompany.buspark.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ua.mycompany.buspark.model.domain.enums.Status;

import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
public class Bus {
    private final Integer id;

    @NotEmpty
    @Pattern(regexp = "([A-Z])([a-z]{2,20})|([А-Я]([a-я]{2,20}))")
    private String model;

    @Min(1)
    @Max(99)
    @NotNull
    private Integer seats;

    private User user;

    @NotEmpty
    private Status status;
}
