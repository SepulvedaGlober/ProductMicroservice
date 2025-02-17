package GrandmasFoodProduct.GrandmasFoodProduct.application.dto;

import jakarta.validation.constraints.*;
import GrandmasFoodProduct.GrandmasFoodProduct.domain.models.Category;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    @NonNull
    private String fantasyName;
    @NonNull
    private Category category;
    @NonNull
    private String description;
    @Positive(message = "Price must be greater than zero")
    private float price;
    private boolean available;
}
