package com.tanle.employee.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EmployeeConfig {
    @Value("${spring.application.address-base-url}")
    private String ADDRESS_BASE_URL;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
