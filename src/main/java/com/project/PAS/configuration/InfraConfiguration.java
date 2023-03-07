package com.project.PAS.configuration;

import com.project.PAS.core.repository.PasRepository;
import com.project.PAS.infrastructure.pas.DefaultPasRepository;
import com.project.PAS.infrastructure.pas.PasJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfraConfiguration {
    @Bean
    PasRepository pasRepository(PasJpaRepository pasJpaRepository){
        return new DefaultPasRepository(pasJpaRepository);
    }
}
