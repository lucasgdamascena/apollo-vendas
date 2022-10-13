package com.damascena.apollovendas.configuration;

import com.damascena.apollovendas.services.DataBaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevProfile {

    private DataBaseService dataBaseService;

    public DevProfile(DataBaseService dataBaseService) {
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