package com.lucasdonsilva.shortener.integration.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HealthConfigurationIT extends IntegrationTestIT {

    @Test
    @DisplayName("Call /health successfully")
    public void healthSuccess() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.components.mongo.status").value("UP"))
                .andExpect(jsonPath("$.components.healthConfiguration.details.reference").value("mongodb://user:password@localhost:5656/base?options=value"));
    }
}