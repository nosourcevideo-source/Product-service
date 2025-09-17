package com.product_service.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;

    @NotBlank(message = "name should not be empty")
    private String name;

    @Positive(message = "price must be positive value")
    private Double price;

    @NotBlank(message = "description should not be empty")
    private String description;

    @Positive(message = "quantity must be positive value")
    private Integer quantity;
}
