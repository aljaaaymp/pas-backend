package com.project.PAS.infrastructure

import com.project.PAS.core.repository.PasRepository
import com.project.PAS.infrastructure.pas.DefaultPasRepository
import com.project.PAS.infrastructure.pas.PasJpaRepository
import com.project.PAS.infrastructure.pas.entity.PasEntity
import com.project.PAS.presentation.mock.PasMockData
import org.springframework.data.jpa.domain.Specification

import java.time.LocalDate

class PasRepositoryTest extends Specification{
    PasRepository pasRepository
    PasJpaRepository pasJpaRepository

    def setup(){
        pasJpaRepository = Mock(PasJpaRepository)
        pasRepository = new DefaultPasRepository(pasJpaRepository)
    }
    def "Should be able to getPas given account number"(){
        when:
        def result = pasRepository.getPas(1000L)
        then:
        1 * pasJpaRepository.findByAccountNumber(_) >> { args ->
            with(args[0] as Long){
                assert it == 1000L
            }
            Optional.of(PasMockData.generatePasEntity())
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
    def "should be able to savePas() given existing PasRequestDTO"(){
        given:
        pasJpaRepository.findByAccountNumber(_ as Long) >>
                Optional.of(PasMockData.generatePasEntity())
        when:
        pasRepository.savePas(PasMockData.generatePasRequestDTO())
        then:
        1 * pasJpaRepository.save(_) >> { args ->
            with(args[0] as PasEntity){
                pasEntityChecker(it)
            }
            PasMockData.generatePasEntity()
        }
    }
    private void pasEntityChecker(PasEntity it){
        assert it.accountNumber == 1000L
        assert it.pasCustomerEntity.firstName == "John"
        assert it.pasCustomerEntity.lastName == "Doe"
        assert it.pasCustomerEntity.age == 25L
        assert it.pasPolicyEntity.policyNumber == 100000L
        assert it.pasPolicyEntity.effectiveDate == LocalDate.now()
        assert it.pasPolicyEntity.expirationDate == LocalDate.now()
        assert it.pasVehicleEntity.color == "black"
        assert it.pasVehicleEntity.type == "type"

    }

}
