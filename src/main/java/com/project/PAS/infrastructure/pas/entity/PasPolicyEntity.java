package com.project.PAS.infrastructure.pas.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PAS_POLICY")
public class PasPolicyEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "POLICY_NUMBER")
    private Long policyNumber;
    @Column(name = "EFFECTIVE_DATE")
    private LocalDate effectiveDate;
    @Column(name = "EXPIRATION_DATE")
    private LocalDate expirationDate;
}
