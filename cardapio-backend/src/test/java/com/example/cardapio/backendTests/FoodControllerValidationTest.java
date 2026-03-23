package com.example.cardapio.backendTests;

import com.example.cardapio.controller.FoodController;
import com.example.cardapio.service.FoodService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FoodController.class)
class FoodControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FoodService foodService;

    @ParameterizedTest
    @CsvSource({
            "Pizza, https://img.com/pizza.jpg, 30.0, OK",
            "'', https://img.com/test.jpg, 30.0, BAD_REQUEST",
            "Burger, https://img.com/burger.jpg, -10.0, BAD_REQUEST",
            "'DROP TABLE', https://hack.com, 50.0, OK"
    })
    void shouldValidateFoodInputs(String title, String url, float price, String expectedStatus) throws Exception {

        String json = """
    {
      "title": "%s",
      "url": "%s",
      "price": %s
    }
    """.formatted(title, url, price);

        mockMvc.perform(post("/food")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(
                        expectedStatus.equals("OK") ?
                                status().isOk() :
                                status().isBadRequest()
                );
    }

}

