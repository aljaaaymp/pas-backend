package com.project.PAS.presentation

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.project.PAS.core.PasService
import com.project.PAS.core.dto.pas.PasResponseDTO
import com.project.PAS.presentation.mock.PasMockData
import org.springframework.data.jpa.domain.Specification
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.util.NestedServletException

import java.time.LocalDate

class PasControllerTest extends Specification {
    MockMvc mockMvc
    PasController pasController
    PasService pasService
    ObjectMapper objectMapper

    def setup() {
        pasService = Mock(PasService)
        pasController = new PasController(pasService)
        mockMvc = MockMvcBuilders.standaloneSetup(pasController).build()
        objectMapper = new ObjectMapper()
        objectMapper.registerModule(new JavaTimeModule())
    }

    def "POST /pas/account returns 200 status"() {
        when:
        def response = mockMvc.perform(
                MockMvcRequestBuilders.post("/pas/account")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(PasMockData.generatePasRequestDTO()))
        ).andReturn()
        then:
        response.getResponse().getStatus() == 200
    }

    def "GET /pas/account/{accountNumber} return 500 status when getting un-existing account"() {
        when:
        mockMvc.perform(MockMvcRequestBuilders.get("/pas/account")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andReturn()

        then:
        1 * pasService.getPasByAccountNumber(_ as Long) >> {
            throw new ServiceConfigurationError("Account number does not exist")
        }
        thrown(NestedServletException)
    }
    def "GET /pas/account/{accountNumber} returns 200"(){
        when:
        def response = mockMvc.perform(
                MockMvcRequestBuilders.get("pas/account/1000")
                .contentType("application/json")
        ).andReturn()
        then:
        1 * pasService.getPasByAccountNumber(_ as Long) >> { args ->
            with(args[0] as Long){
                1000L
            }
            PasMockData.generatePasResponseDTO()
        }
        response.getResponse().getStatus() == 200
        def result = objectMapper.readValue(response.getResponse().contentAsString, PasResponseDTO)
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
}
