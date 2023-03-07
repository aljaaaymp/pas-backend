package com.project.PAS.core.repository;

import com.project.PAS.core.dto.pas.PasRequestDTO;
import com.project.PAS.core.dto.pas.PasResponseDTO;

public interface PasRepository {
    PasResponseDTO getPas(Long accountNumber);
    void savePas(PasRequestDTO pasRequestDTO);
}
