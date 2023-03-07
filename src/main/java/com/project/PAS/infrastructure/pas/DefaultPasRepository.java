package com.project.PAS.infrastructure.pas;

import com.project.PAS.core.dto.pas.PasDTO;
import com.project.PAS.core.dto.pas.PasRequestDTO;
import com.project.PAS.core.dto.pas.PasResponseDTO;
import com.project.PAS.core.repository.PasRepository;
import com.project.PAS.infrastructure.pas.entity.PasCustomerEntity;
import com.project.PAS.infrastructure.pas.entity.PasEntity;
import com.project.PAS.infrastructure.pas.entity.PasPolicyEntity;
import com.project.PAS.infrastructure.pas.entity.PasVehicleEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class DefaultPasRepository implements PasRepository {
    private final PasJpaRepository pasJpaRepository;

    public DefaultPasRepository(PasJpaRepository pasJpaRepository) {
        this.pasJpaRepository = pasJpaRepository;
    }

    @Override
    public PasResponseDTO getPas(Long accountNumber) {

        Optional<PasEntity> pasEntity = pasJpaRepository.findByAccountNumber(accountNumber);
        PasResponseDTO pasResponseDTO = new PasResponseDTO();
        if (pasEntity.isEmpty()) {
            log.info("No account number " + accountNumber + " found.");
            return pasResponseDTO;
        }
        PasDTO pasDTO = buildPasDTO(pasEntity.get());
        setPasResponseDTO(pasResponseDTO, pasDTO);
        return pasResponseDTO;
    }
    @Override
    public void savePas(PasRequestDTO pasRequestDTO) {
        PasEntity initialPasEntity = buildInitialPasEntity(pasRequestDTO);
        PasCustomerEntity pasCustomerEntity = toPasCustomerEntity(pasRequestDTO);
        PasPolicyEntity pasPolicyEntity = toPasPolicyEntity(pasRequestDTO);
        PasVehicleEntity pasVehicleEntity = toPasVehicleEntity(pasRequestDTO);

        PasEntity pasEntity = validateAndBuildPasEntity(initialPasEntity,
                pasRequestDTO,
                pasCustomerEntity,
                pasPolicyEntity,
                pasVehicleEntity);
        pasJpaRepository.save(pasEntity);
    }

    private PasEntity validateAndBuildPasEntity(PasEntity initialPasEntity,
                                                PasRequestDTO pasRequestDTO,
                                                PasCustomerEntity pasCustomerEntity,
                                                PasPolicyEntity pasPolicyEntity,
                                                PasVehicleEntity pasVehicleEntity) {
        checkAndSetIdsForExistingPasEntities(initialPasEntity,
                pasRequestDTO,
                pasCustomerEntity,
                pasPolicyEntity,
                pasVehicleEntity);
        initialPasEntity.setPasCustomerEntity(pasCustomerEntity);
        initialPasEntity.setPasPolicyEntity(pasPolicyEntity);
        initialPasEntity.setPasVehicleEntity(pasVehicleEntity);
        return initialPasEntity;
    }

    private void checkAndSetIdsForExistingPasEntities(PasEntity pasEntity,
                                                      PasRequestDTO pasRequestDTO,
                                                      PasCustomerEntity pasCustomerEntity,
                                                      PasPolicyEntity pasPolicyEntity,
                                                      PasVehicleEntity pasVehicleEntity) {
        Optional<PasEntity> existingPas = pasJpaRepository.findByAccountNumber(pasRequestDTO.getValues().getAccountNumber());
        if(existingPas.isPresent()){
            pasEntity.setId(existingPas.get().getId());
            pasCustomerEntity.setId(existingPas.get().getPasCustomerEntity().getId());
            pasPolicyEntity.setId(existingPas.get().getPasPolicyEntity().getId());
            pasVehicleEntity.setId(existingPas.get().getPasVehicleEntity().getId());
        }
    }

    private PasVehicleEntity toPasVehicleEntity(PasRequestDTO pasRequestDTO) {
        PasDTO pasDTO = pasRequestDTO.getValues();
        return PasVehicleEntity.builder()
                .type(pasDTO.getType())
                .color(pasDTO.getColor())
                .build();
    }

    private PasPolicyEntity toPasPolicyEntity(PasRequestDTO pasRequestDTO) {
        PasDTO pasDTO = pasRequestDTO.getValues();
        return PasPolicyEntity.builder()
                .policyNumber(pasDTO.getPolicyNumber())
                .effectiveDate(pasDTO.getEffectiveDate())
                .expirationDate(pasDTO.getExpirationDate())
                .build();
    }

    private PasCustomerEntity toPasCustomerEntity(PasRequestDTO pasRequestDTO) {
        PasDTO pasDTO = pasRequestDTO.getValues();
        return PasCustomerEntity.builder()
                .age(pasDTO.getAge())
                .firstName(pasDTO.getFirstName())
                .lastName(pasDTO.getLastname())
                .build();
    }

    private PasEntity buildInitialPasEntity(PasRequestDTO pasRequestDTO) {
        return PasEntity.builder()
                .accountNumber(pasRequestDTO.getValues().getAccountNumber())
                .build();
    }

    private void setPasResponseDTO(PasResponseDTO pasResponseDTO, PasDTO pasDTO) {
        pasResponseDTO.setValues(pasDTO);
    }

    private PasDTO buildPasDTO(PasEntity pasEntity) {
        PasDTO pasDTO = new PasDTO();
        setPasCustomerEntity(pasEntity, pasDTO);
        setPasPolicyEntity(pasEntity, pasDTO);
        setPasVehicleEntity(pasEntity, pasDTO);
        pasDTO.setAccountNumber(pasEntity.getAccountNumber());
        return pasDTO;
    }

    private void setPasVehicleEntity(PasEntity pasEntity, PasDTO pasDTO) {
        PasVehicleEntity pasVehicleEntity = pasEntity.getPasVehicleEntity();
        pasDTO.setColor(pasVehicleEntity.getColor());
        pasDTO.setType(pasVehicleEntity.getType());
    }

    private void setPasPolicyEntity(PasEntity pasEntity, PasDTO pasDTO) {
        PasPolicyEntity pasPolicyEntity = pasEntity.getPasPolicyEntity();
        pasDTO.setPolicyNumber(pasPolicyEntity.getPolicyNumber());
        pasDTO.setEffectiveDate(pasPolicyEntity.getEffectiveDate());
        pasDTO.setExpirationDate(pasPolicyEntity.getExpirationDate());
    }

    private void setPasCustomerEntity(PasEntity pasEntity, PasDTO pasDTO) {
        PasCustomerEntity pasCustomerEntity = pasEntity.getPasCustomerEntity();
        pasDTO.setFirstName(pasCustomerEntity.getFirstName());
        pasDTO.setLastname(pasCustomerEntity.getLastName());
        pasDTO.setAge(pasCustomerEntity.getAge());
    }


}
