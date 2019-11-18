package ua.mycompany.buspark.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.mycompany.buspark.model.domain.enums.Status;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "buses")
public class BusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private Integer id;

    @Column(name = "bus_model", nullable = false, length = 20)
    private String model;

    @Column(name = "bus_seats", nullable = false, length = 3)
    private Integer seats;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "route_status", nullable = false, length = 5)
    private Status status;
}

