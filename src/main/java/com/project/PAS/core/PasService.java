package com.project.PAS.core;

import com.project.PAS.core.dto.pas.PasRequestDTO;
import com.project.PAS.core.dto.pas.PasResponseDTO;

public interface PasService {
    PasResponseDTO getPasByAccountNumber(Long accountNumber);
    void savePas(PasRequestDTO pasRequestDTO);
}
