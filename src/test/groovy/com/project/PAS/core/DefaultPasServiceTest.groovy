package com.project.PAS.core

import com.project.PAS.core.dto.pas.PasRequestDTO
import com.project.PAS.core.repository.PasRepository
import com.project.PAS.presentation.mock.PasMockData
import org.springframework.data.jpa.domain.Specification

import java.time.LocalDate

class DefaultPasServiceTest extends Specification{
    PasService pasService
    PasRepository pasRepository

    def setup(){
        pasRepository.Mock(PasRepository)
        pasService = new com.project.PAS.core.impl.DefaultPasService(pasRepository);
    }

    def "Should be able to getPasByAccountNumber given AccountNumber"(){
        when:
        def result = pasService.getPasByAccountNumber(1000L)
        then:
        1 * pasRepository.getPas(_) >> { args ->
            with(args[0] as Long){
                assert it == 1000L
            }
            PasMockData.generatePasResponseDTO()
        }
        result.values.accountNumber == 1000L
        result.values.policyNumber == 100000L
        result.values.firstName == "John"
        result.values.lastname == "Doe"
        result.values.age == 25L
        result.values.color == "black"
        result.values.type == "type"
        result.values.effectiveDate == LocalDate.now()
        result.values.expirationDate == LocalDate.now()

    }
    def "should be able to savePas() given PasRequestDTO"(){
        when:
        pasService.savePas(PasMockData.generatePasRequestDTO())
        then:
        1 * pasRepository.savePas(_) >> { args ->
            with(args[0] as PasRequestDTO){
                pasDTOChecker(it)
            }
        }
    }
    private void pasDTOChecker(PasRequestDTO it){
        it.values.accountNumber == 1000L
        it.values.policyNumber == 100000L
        it.values.firstName == "John"
        it.values.lastname == "Doe"
        it.values.age == 25L
        it.values.type == "type"
        it.values.color == "black"
        it.values.effectiveDate == LocalDate.now()
        it.values.expirationDate == LocalDate.now()

    }
}
