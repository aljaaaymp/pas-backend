package com.project.PAS.configuration;

import com.project.PAS.core.repository.PasRepository;
import com.project.PAS.core.PasService;
import com.project.PAS.core.impl.DefaultPasService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {
    @Bean
    PasService pasService(PasRepository pasRepository){
        return new DefaultPasService(pasRepository);
    }
}
