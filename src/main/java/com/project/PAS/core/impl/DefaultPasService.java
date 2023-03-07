package com.project.PAS.core.impl;

import com.project.PAS.core.dto.pas.PasRequestDTO;
import com.project.PAS.core.dto.pas.PasResponseDTO;
import com.project.PAS.core.repository.PasRepository;
import com.project.PAS.core.PasService;

public class DefaultPasService implements PasService  {
    PasRepository pasRepository;

    public DefaultPasService(PasRepository pasRepository) {
        this.pasRepository = pasRepository;
    }

    @Override
    public PasResponseDTO getPasByAccountNumber(Long accountNumber) {
        return pasRepository.getPas(accountNumber);
    }

    @Override
    public void savePas(PasRequestDTO pasRequestDTO) {
        pasRepository.savePas(pasRequestDTO);
    }
}
