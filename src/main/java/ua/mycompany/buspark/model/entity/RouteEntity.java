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
@Table(name = "routes")
public class RouteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Integer id;

    @Column(name = "route_arrival", nullable = false, length = 20)
    private String arrival;

    @Column(name = "route_departure", nullable = false, length = 20)
    private String departure;

    @Column(name = "route_distance", nullable = false, length = 6)
    private Integer distance;

    @OneToOne
    @JoinColumn(name = "bus_id")
    private BusEntity bus;

    @Column(name = "route_status", nullable = false, length = 5)
    private Status status;
}
