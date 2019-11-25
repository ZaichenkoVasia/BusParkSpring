package com.spring.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Data
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
@Builder
@Entity
@Table(name = "assignments")
public class AssignmentEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "tax")
    private double tax;

    @Column(name = "journey")
    private Integer journey;

    @Basic(optional = false)
    @Column(name = "status", columnDefinition = "integer default 0")
    private Integer canceled;

    @JoinColumn(name = "id_route", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RouteEntity route;

    @JoinColumn(name = "id_bus", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private BusEntity bus;
}

