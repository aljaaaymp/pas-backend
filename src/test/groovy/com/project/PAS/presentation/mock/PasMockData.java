package com.project.PAS.presentation.mock;

import com.project.PAS.core.dto.pas.PasDTO;
import com.project.PAS.core.dto.pas.PasRequestDTO;
import com.project.PAS.core.dto.pas.PasResponseDTO;
import com.project.PAS.infrastructure.pas.entity.PasCustomerEntity;
import com.project.PAS.infrastructure.pas.entity.PasEntity;
import com.project.PAS.infrastructure.pas.entity.PasPolicyEntity;
import com.project.PAS.infrastructure.pas.entity.PasVehicleEntity;

import java.time.LocalDate;

public class PasMockData {
    static PasResponseDTO generatePasResponseDTO(){
        return PasResponseDTO.builder()
                .values(generatePasDTO())
                .build();
    }
    static PasRequestDTO generatePasRequestDTO(){
        return PasRequestDTO.builder()
                .values(generatePasDTO())
                .build();
    }
    static PasDTO generatePasDTO(){
        return PasDTO.builder()
                .accountNumber(1000L)
                .firstName("John")
                .lastname("Doe")
                .age(25L)
                .policyNumber(100000L)
                .color("black")
                .type("type")
                .effectiveDate(LocalDate.now())
                .expirationDate(LocalDate.now())
                .build();
    }
    static PasEntity generatePasEntity(){
        return PasEntity.builder()
                .accountNumber(1000L)
                .pasCustomerEntity(generatePasCustomerEntity())
                .pasPolicyEntity(generatePasPolicyEntity())
                .pasVehicleEntity(generatePasVehicleEntity())
                .build();
    }
    static PasVehicleEntity generatePasVehicleEntity(){
        return PasVehicleEntity.builder()
                .color("black")
                .type("type")
                .build();
    }
    static PasPolicyEntity generatePasPolicyEntity(){
        return PasPolicyEntity.builder()
                .policyNumber(100000L)
                .effectiveDate(LocalDate.now())
                .expirationDate(LocalDate.now())
                .build();
    }
    static PasCustomerEntity generatePasCustomerEntity(){
        return PasCustomerEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .age(25L)
                .build();
    }
}
