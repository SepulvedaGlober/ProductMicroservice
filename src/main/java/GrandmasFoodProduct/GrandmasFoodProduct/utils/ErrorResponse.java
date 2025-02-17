package GrandmasFoodProduct.GrandmasFoodProduct.utils;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    private String code;
    private LocalDateTime timestamp;
    private String description;
    private String exception;

    public ErrorResponse(String code, String description, String exception) {
        this.code = code;
        this.timestamp = LocalDateTime.now();
        this.description = description;
        this.exception = exception;
    }

    public ErrorResponse(String code, String description) {
        this.code = code;
        this.timestamp = LocalDateTime.now();
        this.description = description;
    }
}
