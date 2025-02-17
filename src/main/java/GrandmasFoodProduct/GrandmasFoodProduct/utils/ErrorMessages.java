package GrandmasFoodProduct.GrandmasFoodProduct.utils;

public class ErrorMessages {
    public static final String NO_UUID_PRODUCT_FOUND_EXCEPTION = "No product found with the given UUID";
    public static final String DUPLICATE_FANTASY_NAME_EXCEPTION = "The product with this fantasy name already exists";
    public static final String NO_PRODUCT_FOUND_EXCEPTION = "No product found";
    public static final String NO_PRODUCT_UUID_FOUND_EXCEPTION = "No product with the given UUID found";
    public static final String NO_KEYWORD = "The keyword is empty";
    public static final String INVALID_PRODUCT_DATA_NAME_EXCEPTION = "Invalid product name too long";
    public static final String INVALID_PRODUCT_DATA_DESCRIPTION_EXCEPTION = "Invalid product description too long";
    public static final String NO_DATA_FOUND_EXCEPTION = "No products found";
    public static final String INVALID_PRODUCT_DATA_PRICE_EXCEPTION = "Invalid product price";
    public static final String INTERNAL_SERVER_ERROR_EXCEPTION = "Internal server error";


    private ErrorMessages() {
        throw new IllegalStateException("Utility class");
    }
}
