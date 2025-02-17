package GrandmasFoodProduct.GrandmasFoodProduct.application.mapper;

import GrandmasFoodProduct.GrandmasFoodProduct.application.dto.ProductResponse;
import GrandmasFoodProduct.GrandmasFoodProduct.domain.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper {


    ProductResponse toResponse(Product product);

    default List <ProductResponse> toResponseList(List<Product> products) {
        return products.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
