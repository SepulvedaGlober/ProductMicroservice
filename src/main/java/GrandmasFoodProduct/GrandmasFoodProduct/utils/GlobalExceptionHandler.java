package GrandmasFoodProduct.GrandmasFoodProduct.utils;


import GrandmasFoodProduct.GrandmasFoodProduct.domain.exceptions.*;
import GrandmasFoodProduct.GrandmasFoodProduct.infrastructure.exception.NoDataFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.NO_UUID_PRODUCT_FOUND.getCode(),
                ErrorMessages.NO_UUID_PRODUCT_FOUND_EXCEPTION,
                ex.getClass().getSimpleName());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateFantasyNameException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateFantasyNameException(DuplicateFantasyNameException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.DUPLICATE_FANTASY_NAME.getCode(),
                ErrorMessages.DUPLICATE_FANTASY_NAME_EXCEPTION,
                ex.getClass().getSimpleName()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoDataFoundException(NoDataFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.NO_DATA_FOUND.getCode(),
                ErrorMessages.NO_DATA_FOUND_EXCEPTION,
                ex.getClass().getSimpleName()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidProductDataNameException.class)
    public ResponseEntity<ErrorResponse> handleInvalidProductDataNameException(InvalidProductDataNameException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.DUPLICATE_FANTASY_NAME.getCode(),
                ErrorMessages.DUPLICATE_FANTASY_NAME_EXCEPTION,
                ex.getClass().getSimpleName()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidProductDataDescriptionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidProductDataDescriptionException(InvalidProductDataDescriptionException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.INVALID_PRODUCT_DATA_DESCRIPTION.getCode(),
                ErrorMessages.INVALID_PRODUCT_DATA_DESCRIPTION_EXCEPTION,
                ex.getClass().getSimpleName()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidProductDataPriceException.class)
    public ResponseEntity<ErrorResponse> handleInvalidProductDataPriceException(InvalidProductDataPriceException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.INVALID_PRODUCT_DATA_PRICE.getCode(),
                ErrorMessages.INVALID_PRODUCT_DATA_PRICE_EXCEPTION,
                ex.getClass().getSimpleName()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                ErrorMessages.INTERNAL_SERVER_ERROR_EXCEPTION,
                ex.getClass().getSimpleName());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
