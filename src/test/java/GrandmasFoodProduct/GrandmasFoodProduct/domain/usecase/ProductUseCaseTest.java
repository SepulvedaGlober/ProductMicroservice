package GrandmasFoodProduct.GrandmasFoodProduct.domain.usecase;


import GrandmasFoodProduct.GrandmasFoodProduct.domain.exceptions.*;
import GrandmasFoodProduct.GrandmasFoodProduct.domain.models.Category;
import GrandmasFoodProduct.GrandmasFoodProduct.domain.models.Product;
import GrandmasFoodProduct.GrandmasFoodProduct.domain.spi.IProductPersistencePort;
import GrandmasFoodProduct.GrandmasFoodProduct.infrastructure.exception.NoDataFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductUseCaseTest {


    @Mock
    private IProductPersistencePort productPersistencePort;

    @InjectMocks
    private ProductUseCase productUseCase;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product(
                "CHEESE BURGER BIG COMBO",
                Category.HAMBURGUERS_AND_HOTDOGS,
                "Burger double 8 Oz meat, cheese, french fries and Big soda",
                21008.41f,
                true
        );
    }

    // ✅ Crear Producto (Éxito)
    @Test
    void createProduct_Success() {
        when(productPersistencePort.getProductByFantasyName(testProduct.getFantasyName()))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() -> productUseCase.createProduct(testProduct));

        verify(productPersistencePort, times(1)).createProduct(testProduct);
    }

    @Test
    void createProduct_ShouldThrowException_WhenDuplicateFantasyName() {
        when(productPersistencePort.getProductByFantasyName(testProduct.getFantasyName()))
                .thenReturn(Optional.of(testProduct));

        assertThrows(DuplicateFantasyNameException.class, () -> productUseCase.createProduct(testProduct));

        verify(productPersistencePort, never()).createProduct(any(Product.class));
    }

    @Test
    void getProductByUuid_Success() {
        String uuid = "18773eee-f787-4b20-8dbb-de96f1a9a02e";
        when(productPersistencePort.getProductByUuid(uuid)).thenReturn(Optional.of(testProduct));

        Optional<Product> result = productUseCase.getProductByUuid(uuid);

        assertTrue(result.isPresent());
        assertEquals(testProduct.getFantasyName(), result.get().getFantasyName());
    }

    @Test
    void getProductByUuid_ShouldThrowException_WhenProductNotFound() {
        String uuid = "invalid-uuid";
        when(productPersistencePort.getProductByUuid(uuid)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productUseCase.getProductByUuid(uuid));
    }

    @Test
    void updateProduct_Success() {
        Product updatedProduct = new Product(
                "NEW BURGER",
                Category.HAMBURGUERS_AND_HOTDOGS,
                "Updated Description",
                19999.99f,
                false
        );

        when(productPersistencePort.getProductByUuid(anyString()))
                .thenReturn(Optional.of(testProduct));
        when(productPersistencePort.getProductByFantasyName(updatedProduct.getFantasyName()))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() -> productUseCase.updateProduct(updatedProduct));

        verify(productPersistencePort, times(1)).updateProduct(updatedProduct);
    }

    @Test
    void updateProduct_ShouldThrowException_WhenProductNotFound() {
        when(productPersistencePort.getProductByUuid(testProduct.getUuid()))
                .thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productUseCase.updateProduct(testProduct));
    }

    @Test
    void updateProduct_ShouldThrowException_WhenDuplicateFantasyName() {
        Product anotherProduct = new Product(
                "NEW BURGER",
                Category.HAMBURGUERS_AND_HOTDOGS,
                "Updated Description",
                19999.99f,
                false
        );

        when(productPersistencePort.getProductByUuid(anyString()))
                .thenReturn(Optional.of(testProduct));
        when(productPersistencePort.getProductByFantasyName(anotherProduct.getFantasyName()))
                .thenReturn(Optional.of(anotherProduct));

        assertThrows(DuplicateFantasyNameException.class, () -> productUseCase.updateProduct(anotherProduct));
    }

    @Test
    void deleteProduct_Success() {
        when(productPersistencePort.getProductByUuid(testProduct.getUuid()))
                .thenReturn(Optional.of(testProduct));

        assertDoesNotThrow(() -> productUseCase.deleteProduct(testProduct.getUuid()));

        verify(productPersistencePort, times(1)).deleteProduct(testProduct);
    }

    @Test
    void deleteProduct_ShouldThrowException_WhenProductNotFound() {
        when(productPersistencePort.getProductByUuid(testProduct.getUuid()))
                .thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productUseCase.deleteProduct(testProduct.getUuid()));
    }

    @Test
    void searchProductsByFantasyName_Success() {
        when(productPersistencePort.searchProductsByFantasyName("BURGER"))
                .thenReturn(List.of(testProduct));

        List<Product> products = productUseCase.searchProductsByFantasyName("BURGER");

        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
    }

    @Test
    void searchProductsByFantasyName_ShouldThrowException_WhenNoProductsFound() {
        when(productPersistencePort.searchProductsByFantasyName("BURGER"))
                .thenReturn(List.of());

        assertThrows(NoDataFoundException.class, () -> productUseCase.searchProductsByFantasyName("BURGER"));
    }

    @Test
    void searchProductsByFantasyName_ShouldThrowException_WhenKeywordIsEmpty() {
        assertThrows(InvalidProductDataNameException.class, () -> productUseCase.searchProductsByFantasyName(""));
    }

    @Test
    void createProduct_ShouldThrowException_WhenNameExceedsMaxLength() {
        testProduct.setFantasyName("A".repeat(256));

        assertThrows(InvalidProductDataNameException.class, () -> productUseCase.createProduct(testProduct));
        verify(productPersistencePort, never()).createProduct(any(Product.class));
    }

    @Test
    void createProduct_ShouldThrowException_WhenDescriptionExceedsMaxLength() {
        testProduct.setDescription("A".repeat(512));

        assertThrows(InvalidProductDataDescriptionException.class, () -> productUseCase.createProduct(testProduct));
        verify(productPersistencePort, never()).createProduct(any(Product.class));
    }

    @Test
    void createProduct_ShouldThrowException_WhenPriceIsNegative() {
        testProduct.setPrice(-1.0f);

        assertThrows(InvalidProductDataPriceException.class, () -> productUseCase.createProduct(testProduct));
        verify(productPersistencePort, never()).createProduct(any(Product.class));
    }

    @Test
    void createProduct_ShouldThrowException_WhenPriceHasInvalidDecimals() {
        testProduct.setPrice(10.999f);

        assertThrows(InvalidProductDataPriceException.class, () -> productUseCase.createProduct(testProduct));
        verify(productPersistencePort, never()).createProduct(any(Product.class));
    }

    @Test
    void createProduct_ShouldUppercaseFantasyName() {
        testProduct.setFantasyName("burger");
        when(productPersistencePort.getProductByFantasyName("BURGER")).thenReturn(Optional.empty());

        productUseCase.createProduct(testProduct);

        assertEquals("BURGER", testProduct.getFantasyName());
        verify(productPersistencePort).createProduct(testProduct);
    }

    @Test
    void getAllProducts_Success() {
        List<Product> expectedProducts = List.of(testProduct);
        when(productPersistencePort.getAllProducts()).thenReturn(expectedProducts);

        List<Product> result = productUseCase.getAllProducts();

        assertEquals(expectedProducts, result);
        verify(productPersistencePort).getAllProducts();
    }

    @Test
    void updateProduct_ShouldKeepSameFantasyName() {
        Product existingProduct = new Product();
        existingProduct.setUuid(testProduct.getUuid());
        existingProduct.setFantasyName(testProduct.getFantasyName());

        when(productPersistencePort.getProductByUuid(testProduct.getUuid()))
                .thenReturn(Optional.of(existingProduct));

        assertDoesNotThrow(() -> productUseCase.updateProduct(testProduct));
        verify(productPersistencePort).updateProduct(testProduct);
    }

    @Test
    void searchProductsByFantasyName_ShouldThrowException_WhenKeywordIsNull() {
        assertThrows(InvalidProductDataNameException.class,
                () -> productUseCase.searchProductsByFantasyName(null));
        verify(productPersistencePort, never()).searchProductsByFantasyName(any());
    }

    @Test
    void isValidPrice_ShouldReturnTrue_WhenPriceHasNoDecimals() {
        testProduct.setPrice(100f);
        assertDoesNotThrow(() -> productUseCase.createProduct(testProduct));
    }

    @Test
    void isValidPrice_ShouldReturnTrue_WhenPriceHasTwoDecimals() {
        testProduct.setPrice(100.50f);
        assertDoesNotThrow(() -> productUseCase.createProduct(testProduct));
    }



}