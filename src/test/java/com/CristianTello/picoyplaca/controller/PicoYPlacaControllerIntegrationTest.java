package com.CristianTello.picoyplaca.controller;

import com.CristianTello.picoyplaca.PicoYPlacaMicroserviceApplication;
import com.CristianTello.picoyplaca.service.PicoYPlacaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = PicoYPlacaMicroserviceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PicoYPlacaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCheckPicoYPlacaEndpoint_whenRestricted() throws Exception {
        // Assuming the data initializer has set Monday with rules for license plates ending in 1,2
        mockMvc.perform(get("/api/check")
                        .param("licensePlate", "ABC-1231")
                        .param("date", "2025-02-10")  // a Monday
                        .param("time", "08:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isAllowed").value(false));
    }

    @Test
    public void testCheckPicoYPlacaEndpoint_whenAllowed() throws Exception {
        // Time outside restricted period
        mockMvc.perform(get("/api/check")
                        .param("licensePlate", "ABC-1231")
                        .param("date", "2025-02-10")  // a Monday
                        .param("time", "11:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isAllowed").value(true));
    }
}