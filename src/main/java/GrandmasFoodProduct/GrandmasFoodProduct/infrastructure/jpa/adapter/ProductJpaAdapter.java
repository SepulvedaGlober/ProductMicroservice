package GrandmasFoodProduct.GrandmasFoodProduct.infrastructure.jpa.adapter;

import GrandmasFoodProduct.GrandmasFoodProduct.domain.exceptions.DuplicateFantasyNameException;
import GrandmasFoodProduct.GrandmasFoodProduct.domain.models.Product;
import GrandmasFoodProduct.GrandmasFoodProduct.domain.spi.IProductPersistencePort;
import GrandmasFoodProduct.GrandmasFoodProduct.infrastructure.exception.NoDataFoundException;
import GrandmasFoodProduct.GrandmasFoodProduct.infrastructure.jpa.entity.ProductEntity;
import GrandmasFoodProduct.GrandmasFoodProduct.infrastructure.jpa.mapper.ProductEntityMapper;
import GrandmasFoodProduct.GrandmasFoodProduct.infrastructure.jpa.repository.IProductRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static GrandmasFoodProduct.GrandmasFoodProduct.utils.ErrorMessages.*;

@RequiredArgsConstructor
public class ProductJpaAdapter implements IProductPersistencePort {

    private final IProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;


    @Override
    public void createProduct(Product product) {
        if(productRepository.findByFantasyName(product.getFantasyName()).isPresent()){
            throw new DuplicateFantasyNameException(DUPLICATE_FANTASY_NAME_EXCEPTION);
        }
        ProductEntity productEntity = productEntityMapper.toEntity(product);
        productRepository.save(productEntity);
    }

    @Override
    public void updateProduct(Product product) {
        ProductEntity existingProduct = productRepository.findByUuid(product.getUuid())
                .orElseThrow(() -> new NoDataFoundException(NO_UUID_PRODUCT_FOUND_EXCEPTION));

       existingProduct.setFantasyName(product.getFantasyName());
       existingProduct.setCategory(product.getCategory());
       existingProduct.setDescription(product.getDescription());
       existingProduct.setPrice(product.getPrice());
       existingProduct.setAvailable(product.isAvailable());

       productRepository.save(existingProduct);

    }

    @Override
    public void deleteProduct(Product product) {
        ProductEntity existingProduct = productRepository.findByUuid(product.getUuid())
                .orElseThrow(() -> new NoDataFoundException(NO_UUID_PRODUCT_FOUND_EXCEPTION));
        productRepository.delete(existingProduct);
    }

    @Override
    public List<Product> getAllProducts() {
        List<ProductEntity> productEntities = productRepository.findAll();
        if(productEntities.isEmpty()){
            throw new NoDataFoundException(NO_DATA_FOUND_EXCEPTION);
        }
        return productEntityMapper.toProductList(productEntities);
    }

    @Override
    public Optional<Product> getProductByUuid(String uuid) {
        return productRepository.findByUuid(uuid)
                .map(productEntityMapper::toProduct);
    }

    @Override
    public Optional<Product> getProductByFantasyName(String fantasyName) {
        return productRepository.findByFantasyName(fantasyName)
                .map(productEntityMapper::toProduct);
    }

    @Override
    public List<Product> searchProductsByFantasyName(String keyword) {
        List<ProductEntity> productEntities = productRepository.findByFantasyNameContainingIgnoreCase(keyword);
        if(productEntities.isEmpty()){
            throw new NoDataFoundException(NO_DATA_FOUND_EXCEPTION);
        }
        return productEntities.stream()
                .map(productEntityMapper::toProduct)
                .toList();

    }
}
