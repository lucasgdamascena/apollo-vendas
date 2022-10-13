package com.damascena.apollovendas.configuration;

import com.damascena.apollovendas.services.DataBaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestProfile {

    private DataBaseService dataBaseService;

    public TestProfile(DataBaseService dataBaseService) {
        this.dataBaseService = dataBaseService;
    }

    @Bean
    public boolean inicializar() {
        try {
            dataBaseService.InstanciarBaseDeTeste();
            return true;
        } catch (ParseException parseException) {
            return false;
        }
    }
}