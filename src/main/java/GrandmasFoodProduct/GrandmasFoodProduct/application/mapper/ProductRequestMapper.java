package GrandmasFoodProduct.GrandmasFoodProduct.application.mapper;

import GrandmasFoodProduct.GrandmasFoodProduct.application.dto.ProductRequest;
import GrandmasFoodProduct.GrandmasFoodProduct.domain.models.Product;
import org.mapstruct.Mapper;



@Mapper(componentModel = "spring")
public interface ProductRequestMapper {

    Product toProduct(ProductRequest productRequest);

}
