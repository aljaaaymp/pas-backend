package com.project.PAS.infrastructure.pas.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PAS_VEHICLE")
public class PasVehicleEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "COLOR")
    private String color;
    @Column(name = "TYPE")
    private String type;
}
