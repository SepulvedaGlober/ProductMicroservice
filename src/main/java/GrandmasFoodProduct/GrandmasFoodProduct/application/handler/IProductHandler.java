package GrandmasFoodProduct.GrandmasFoodProduct.application.handler;

import GrandmasFoodProduct.GrandmasFoodProduct.application.dto.ProductRequest;
import GrandmasFoodProduct.GrandmasFoodProduct.application.dto.ProductResponse;
import GrandmasFoodProduct.GrandmasFoodProduct.domain.models.Product;

import java.util.List;
import java.util.Optional;

public interface IProductHandler {
    ProductResponse createProduct(ProductRequest productRequest);
    void updateProduct(String uuid, ProductRequest productRequest);
    void deleteProduct(String id);
    Optional<ProductResponse> getProductByUuid(String uuid);
    List<ProductResponse> getAllProducts();
    List<ProductResponse> searchProductsByFantasyName(String keyword);
}
