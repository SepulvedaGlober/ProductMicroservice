package GrandmasFoodProduct.GrandmasFoodProduct.domain.exceptions;

public class DuplicateUuidException extends RuntimeException {
    public DuplicateUuidException(String message) {
        super(message);
    }
}
