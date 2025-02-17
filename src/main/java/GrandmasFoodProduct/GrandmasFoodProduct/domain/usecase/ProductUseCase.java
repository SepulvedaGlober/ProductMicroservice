package GrandmasFoodProduct.GrandmasFoodProduct.domain.usecase;

import GrandmasFoodProduct.GrandmasFoodProduct.domain.api.IProductServicePort;
import GrandmasFoodProduct.GrandmasFoodProduct.domain.exceptions.*;
import GrandmasFoodProduct.GrandmasFoodProduct.domain.models.Product;
import GrandmasFoodProduct.GrandmasFoodProduct.domain.spi.IProductPersistencePort;
import GrandmasFoodProduct.GrandmasFoodProduct.infrastructure.exception.NoDataFoundException;

import java.util.List;
import java.util.Optional;

import static GrandmasFoodProduct.GrandmasFoodProduct.utils.ErrorMessages.*;
import static GrandmasFoodProduct.GrandmasFoodProduct.utils.Constants.*;


public class ProductUseCase implements IProductServicePort {

    private final IProductPersistencePort productPersistencePort;

    public ProductUseCase(IProductPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }



    @Override
    public void createProduct(Product product) {
        String upperCase = product.getFantasyName().toUpperCase();
        product.setFantasyName(upperCase);
        Optional<Product> existingProduct = productPersistencePort.getProductByFantasyName(product.getFantasyName());

        if (existingProduct.isPresent()) {
            throw new DuplicateFantasyNameException(DUPLICATE_FANTASY_NAME_EXCEPTION);
        }
        if(product.getFantasyName().length() > MAX_PRODUCT_NAME_LENGTH){
            throw new InvalidProductDataNameException(INVALID_PRODUCT_DATA_NAME_EXCEPTION);
        }
        if(product.getDescription().length() > MAX_PRODUCT_DESCRIPTION_LENGTH){
            throw new InvalidProductDataDescriptionException(INVALID_PRODUCT_DATA_DESCRIPTION_EXCEPTION);
        }
        if(product.getPrice() < 0 || !isValidPrice(product.getPrice())){
            throw new InvalidProductDataPriceException(INVALID_PRODUCT_DATA_PRICE_EXCEPTION);
        }

         productPersistencePort.createProduct(product);

    }

    @Override
    public void updateProduct(Product product) {
        String uuid = product.getUuid();
        Product existingProduct = productPersistencePort.getProductByUuid(uuid)
                .orElseThrow(() -> new ProductNotFoundException(
                        NO_PRODUCT_UUID_FOUND_EXCEPTION));

        if(!existingProduct.getFantasyName().equalsIgnoreCase(product.getFantasyName())){
            Optional<Product> sameName = productPersistencePort.getProductByFantasyName(product.getFantasyName());
            if(sameName.isPresent()){
                throw new DuplicateFantasyNameException(DUPLICATE_FANTASY_NAME_EXCEPTION);
            }
        }
        product.setUuid(existingProduct.getUuid());
        productPersistencePort.updateProduct(product);

    }

    @Override
    public void deleteProduct(String uuid) {
        Product product = productPersistencePort.getProductByUuid(uuid)
                .orElseThrow(() -> new ProductNotFoundException(
                        NO_PRODUCT_UUID_FOUND_EXCEPTION
                ));

        productPersistencePort.deleteProduct(product);
    }


    @Override
    public List<Product> getAllProducts() {
        return productPersistencePort.getAllProducts();
    }

    @Override
    public Optional<Product> getProductByUuid(String uuid) {
        if(productPersistencePort.getProductByUuid(uuid).isEmpty()){
            throw new ProductNotFoundException(NO_PRODUCT_UUID_FOUND_EXCEPTION);
        }
        return productPersistencePort.getProductByUuid(uuid);
    }

    @Override
    public List<Product> searchProductsByFantasyName(String keyword) {
        if(keyword == null || keyword.isEmpty()){
            throw new InvalidProductDataNameException(NO_KEYWORD);
        }
        List<Product> products = productPersistencePort.searchProductsByFantasyName(keyword);
        if(products.isEmpty()){
            throw new NoDataFoundException(NO_DATA_FOUND_EXCEPTION);
        }
        return productPersistencePort.searchProductsByFantasyName(keyword);
    }

    private boolean isValidPrice(float price) {
        String priceString = String.valueOf(price);
        int decimalIndex = priceString.indexOf('.');
        if (decimalIndex == -1) {
            return true;
        }
        int decimalPlaces = priceString.length() - decimalIndex - 1;
        return decimalPlaces <= 2;
    }

}
