package GrandmasFoodProduct.GrandmasFoodProduct.infrastructure.rest;

import GrandmasFoodProduct.GrandmasFoodProduct.application.dto.ProductRequest;
import GrandmasFoodProduct.GrandmasFoodProduct.application.dto.ProductResponse;
import GrandmasFoodProduct.GrandmasFoodProduct.application.handler.IProductHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final IProductHandler productHandler;


    //CREATE PRODUCT
    @Operation(summary = "Create a new product", description = "Endpoint for creating a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created", content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflict: A product with the same name already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        try{
            ProductResponse productResponse = productHandler.createProduct(productRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);

        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //SEARCH PRODUCT BY UUID
    @Operation(summary = "Search product", description = "Search for a product by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found", content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product not encontrado"),
            @ApiResponse(responseCode = "400", description = "Invalid UUID format"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<ProductResponse> getProductByUuid(@PathVariable String uuid) {
        Optional<ProductResponse> productResponse = productHandler.getProductByUuid(uuid);
        return productResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    //UPDATE PRODUCT BY UUID
    @Operation(summary = "Update an existing product", description = "Updates a product in the database using its UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product updated correctly", content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflict : A product with the same name already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{uuid}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String uuid, @RequestBody ProductRequest productRequest) {
        try{
            productHandler.updateProduct(uuid, productRequest);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //DELETE PRODUCT
    @Operation(summary = "Delete product", description = "Deletes a product from the database using its UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted correctly"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid UUID format"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String uuid) {
        productHandler.deleteProduct(uuid);
        return ResponseEntity.noContent().build();
    }

    //GET ALL PRODUCTS
    @Operation(summary = "List all products", description = "Endpoint to list all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all products", content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "204", description = "No products found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productHandler.getAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    //SEARCH PRODUCTS BY FANTASY NAME
    @Operation(summary = "Search products by fantasy name", description = "Search for products that contain the keyword in their fantasy name (case-insensitive).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found", content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Keyword is empty"),
            @ApiResponse(responseCode = "404", description = "No products found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam(name = "q") String keyword) {
        List<ProductResponse> products = productHandler.searchProductsByFantasyName(keyword);
        return ResponseEntity.ok(products);
    }
}
