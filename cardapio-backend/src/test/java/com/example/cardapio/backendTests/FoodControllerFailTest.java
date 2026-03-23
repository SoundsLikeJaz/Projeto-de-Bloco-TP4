package com.example.cardapio.backendTests;

import com.example.cardapio.controller.FoodController;
import com.example.cardapio.service.FoodService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FoodController.class)
class FoodControllerFailTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FoodService foodService;

    @Test
    void shouldNotCallServiceWhenValidationFails() throws Exception {

        String invalidJson = """
        {
          "title": "",
          "url": "",
          "price": -10
        }
        """;

        mockMvc.perform(post("/food")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());

        verify(foodService, never()).saveFood(any());
    }

    @Test
    void shouldHandleServiceFailureGracefully() throws Exception {

        when(foodService.findAll())
                .thenThrow(new RuntimeException("Database down"));

        mockMvc.perform(get("/food"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(
                        containsString("unexpected error")
                ));
    }

    @Test
    void shouldHandleTimeoutScenario() throws Exception {

        when(foodService.findAll())
            .thenThrow(new RuntimeException("Timeout simulated"));

        mockMvc.perform(get("/food"))
                .andExpect(status().isInternalServerError());
    }

}

