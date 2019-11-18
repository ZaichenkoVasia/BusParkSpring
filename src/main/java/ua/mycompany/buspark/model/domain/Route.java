package ua.mycompany.buspark.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ua.mycompany.buspark.model.domain.enums.Status;

import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
public class Route {

    private Integer id;

    @NotEmpty
    @Pattern(regexp = "([A-Z])([a-z]{2,20})|([А-Я]([a-я]{2,20}))")
    private String arrival;

    @NotEmpty
    @Pattern(regexp = "([A-Z])([a-z]{2,20})|([А-Я]([a-я]{2,20}))")
    private String departure;

    @Min(10)
    @Max(999999)
    @NotNull
    private Integer distance;

    private Bus bus;

    @NotEmpty
    private Status status;
}
