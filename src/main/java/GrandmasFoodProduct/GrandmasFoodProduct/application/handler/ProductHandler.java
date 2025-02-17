package GrandmasFoodProduct.GrandmasFoodProduct.application.handler;

import GrandmasFoodProduct.GrandmasFoodProduct.application.dto.ProductRequest;
import GrandmasFoodProduct.GrandmasFoodProduct.application.dto.ProductResponse;
import GrandmasFoodProduct.GrandmasFoodProduct.application.mapper.ProductRequestMapper;
import GrandmasFoodProduct.GrandmasFoodProduct.application.mapper.ProductResponseMapper;
import GrandmasFoodProduct.GrandmasFoodProduct.domain.api.IProductServicePort;
import GrandmasFoodProduct.GrandmasFoodProduct.domain.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductHandler implements IProductHandler {

    private final IProductServicePort productServicePort;
    private final ProductRequestMapper productRequestMapper;
    private final ProductResponseMapper productResponseMapper;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productRequestMapper.toProduct(productRequest);
        productServicePort.createProduct(product);
        return productResponseMapper.toResponse(product);
    }

    @Override
    public void updateProduct(String uuid, ProductRequest productRequest) {
        Product product = productRequestMapper.toProduct(productRequest);
        product.setUuid(uuid);
        productServicePort.updateProduct(product);
    }

    @Override
    public void deleteProduct(String id) {
        productServicePort.deleteProduct(id);
    }

    @Override
    public Optional<ProductResponse> getProductByUuid(String uuid) {
        return productServicePort.getProductByUuid(uuid)
                .map(productResponseMapper::toResponse);
    }


    @Override
    public List<ProductResponse> getAllProducts() {
        return productServicePort.getAllProducts()
                .stream()
                .map(productResponseMapper::toResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> searchProductsByFantasyName(String keyword) {
        List <Product> products = productServicePort.searchProductsByFantasyName(keyword);
        return products.stream()
                .map(productResponseMapper::toResponse)
                .toList();

    }
}
