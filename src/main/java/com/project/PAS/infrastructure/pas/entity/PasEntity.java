package com.project.PAS.infrastructure.pas.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PAS")
public class PasEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ACCOUNT_NUMBER")
    private Long accountNumber;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PAS_CUSTOMER_ID", referencedColumnName = "ID")
    private PasCustomerEntity pasCustomerEntity;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PAS_POLICY_ID", referencedColumnName = "ID")
    private PasPolicyEntity pasPolicyEntity;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PAS_VEHICLE_ID", referencedColumnName = "ID")
    private PasVehicleEntity pasVehicleEntity;
}
