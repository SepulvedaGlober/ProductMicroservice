package GrandmasFoodProduct.GrandmasFoodProduct.domain.api;

import GrandmasFoodProduct.GrandmasFoodProduct.domain.models.Product;

import java.util.List;
import java.util.Optional;

public interface IProductServicePort {
    void createProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(String uuid);
    List<Product> getAllProducts();
    Optional <Product> getProductByUuid(String uuid);
    List<Product> searchProductsByFantasyName(String keyword);

}
