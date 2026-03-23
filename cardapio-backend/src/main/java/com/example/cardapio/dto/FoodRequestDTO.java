package com.example.cardapio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record FoodRequestDTO(@NotBlank
                             @Pattern(
                                     regexp = "^[a-zA-Z0-9\\s]+$",
                                     message = "Title contains invalid characters"
                             )
                             String title,
                             @NotBlank
                             String url,
                             @Positive
                             float price) {
}
