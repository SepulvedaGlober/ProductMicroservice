package GrandmasFoodProduct.GrandmasFoodProduct.utils;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NO_UUID_PRODUCT_FOUND("E1001"),
    DUPLICATE_FANTASY_NAME("E1002"),
    NO_PRODUCT_FOUND("E1003"),
    NO_PRODUCT_UUID_FOUND("E1004"),
    NO_KEYWORD("E1005"),
    INVALID_PRODUCT_DATA_NAME("E1006"),
    INVALID_PRODUCT_DATA_DESCRIPTION("E1007"),
    NO_DATA_FOUND("E1008"),
    INVALID_PRODUCT_DATA_PRICE("E1009"),
    INTERNAL_SERVER_ERROR("E5000");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }
}
