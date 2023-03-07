package com.project.PAS.presentation;

import com.project.PAS.core.dto.pas.PasRequestDTO;
import com.project.PAS.core.dto.pas.PasResponseDTO;
import com.project.PAS.core.PasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pas")
@Slf4j
public class PasController {
    PasService pasService;

    public PasController(PasService pasService) {
        this.pasService = pasService;
    }
    @GetMapping(path = "/account/{accountNumber}")
    public PasResponseDTO getPasByAccountNumber(@PathVariable Long accountNumber){
        log.info("Retrieving Pas with account number: " + accountNumber);
        PasResponseDTO val = pasService.getPasByAccountNumber(accountNumber);
        log.info("[END] - Successfully retrieved Pas with account number: " + accountNumber);
        return val;
    }
    public ResponseEntity savePas(@RequestBody PasRequestDTO pasRequestDTO){
        pasService.savePas(pasRequestDTO);
        return ResponseEntity.ok("Pas saved");
    }
}
