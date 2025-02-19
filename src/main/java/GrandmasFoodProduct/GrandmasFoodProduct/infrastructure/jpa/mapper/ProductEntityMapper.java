package GrandmasFoodProduct.GrandmasFoodProduct.infrastructure.jpa.mapper;

import GrandmasFoodProduct.GrandmasFoodProduct.domain.models.Product;
import GrandmasFoodProduct.GrandmasFoodProduct.infrastructure.jpa.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {
    ProductEntityMapper INSTANCE = Mappers.getMapper(ProductEntityMapper.class);


    ProductEntity toEntity(Product product);


    Product toProduct(ProductEntity productEntity);


    List<Product> toProductList(List<ProductEntity> productEntities);
}
