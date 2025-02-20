package GrandmasFoodProduct.GrandmasFoodProduct.application.mapper;

import GrandmasFoodProduct.GrandmasFoodProduct.application.dto.ProductResponse;
import GrandmasFoodProduct.GrandmasFoodProduct.domain.models.Product;
import org.mapstruct.Mapper;


import java.util.List;


@Mapper(componentModel = "spring")
public interface ProductResponseMapper {


    ProductResponse toResponse(Product product);

    default List <ProductResponse> toResponseList(List<Product> products) {
        return products.stream().map(this::toResponse).toList();
    }
}
