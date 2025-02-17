package GrandmasFoodProduct.GrandmasFoodProduct.infrastructure.rest;

import GrandmasFoodProduct.GrandmasFoodProduct.application.dto.ProductRequest;
import GrandmasFoodProduct.GrandmasFoodProduct.application.dto.ProductResponse;
import GrandmasFoodProduct.GrandmasFoodProduct.application.handler.IProductHandler;
import GrandmasFoodProduct.GrandmasFoodProduct.domain.models.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductRestControllerTest {
    private MockMvc mockMvc;

    @Mock
    private IProductHandler productHandler;

    @InjectMocks
    private ProductRestController productRestController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ProductRequest productRequest;
    private ProductResponse productResponse;
    private String uuid;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productRestController).build();

        uuid = UUID.randomUUID().toString();

        productRequest = new ProductRequest();
        productRequest.setFantasyName("CHEESE BURGER BIG COMBO");
        productRequest.setCategory(Category.HAMBURGUERS_AND_HOTDOGS);
        productRequest.setDescription("Burger double 8 oz meat, cheese, french fries and Big soda");
        productRequest.setPrice(21008.41f);
        productRequest.setAvailable(true);

        productResponse = new ProductResponse();
        productResponse.setUuid(uuid);
        productResponse.setFantasyName("CHEESE BURGER BIG COMBO");
        productResponse.setCategory(Category.HAMBURGUERS_AND_HOTDOGS);
        productResponse.setDescription("Burger double 8 oz meat, cheese, french fries and Big soda");
        productResponse.setPrice(21008.41f);
        productResponse.setAvailable(true);
    }

    @Test
    void createProduct_Success() throws Exception {
        when(productHandler.createProduct(any(ProductRequest.class))).thenReturn(productResponse);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid").value(uuid))
                .andExpect(jsonPath("$.fantasyName").value("CHEESE BURGER BIG COMBO"));

        verify(productHandler, times(1)).createProduct(any(ProductRequest.class));
    }

    @Test
    void getProductByUuid_Success() throws Exception {
        when(productHandler.getProductByUuid(anyString())).thenReturn(Optional.of(productResponse));

        mockMvc.perform(get("/products/{uuid}", uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(uuid))
                .andExpect(jsonPath("$.fantasyName").value("CHEESE BURGER BIG COMBO"));

        verify(productHandler, times(1)).getProductByUuid(uuid);
    }

    @Test
    void getProductByUuid_NotFound() throws Exception {
        when(productHandler.getProductByUuid(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/products/{uuid}", uuid))
                .andExpect(status().isNotFound());

        verify(productHandler, times(1)).getProductByUuid(uuid);
    }

    @Test
    void updateProduct_Success() throws Exception {
        doNothing().when(productHandler).updateProduct(anyString(), any(ProductRequest.class));

        mockMvc.perform(put("/products/{uuid}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isNoContent());

        verify(productHandler, times(1)).updateProduct(anyString(), any(ProductRequest.class));
    }

    @Test
    void deleteProduct_Success() throws Exception {
        doNothing().when(productHandler).deleteProduct(anyString());

        mockMvc.perform(delete("/products/{uuid}", uuid))
                .andExpect(status().isNoContent());

        verify(productHandler, times(1)).deleteProduct(uuid);
    }

    @Test
    void getAllProducts_Success() throws Exception {
        when(productHandler.getAllProducts()).thenReturn(Collections.singletonList(productResponse));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].uuid").value(uuid));

        verify(productHandler, times(1)).getAllProducts();
    }

    @Test
    void getAllProducts_NoContent() throws Exception {
        when(productHandler.getAllProducts()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/products"))
                .andExpect(status().isNoContent());

        verify(productHandler, times(1)).getAllProducts();
    }

    @Test
    void searchProducts_Success() throws Exception {
        when(productHandler.searchProductsByFantasyName(anyString()))
                .thenReturn(Collections.singletonList(productResponse));

        mockMvc.perform(get("/products/search?q=BURGER"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].uuid").value(uuid));

        verify(productHandler, times(1)).searchProductsByFantasyName(anyString());
    }

    @Test
    void searchProducts_NotFound() throws Exception {
        when(productHandler.searchProductsByFantasyName(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/products/search?q=BURGER"))
                .andExpect(status().isOk());

        verify(productHandler, times(1)).searchProductsByFantasyName(anyString());
    }
}