package com.software.citas.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Setter
@Getter
public class ApiResponse<T>{
    private boolean success;
    private String message;
    private T data;

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }


    public static final class ApiResponses {

        public static <T> ResponseEntity<ApiResponse<T>> ok(String message, T data) {
            return ResponseEntity.ok(new ApiResponse<>(true, message, data));
        }

        public static <T> ResponseEntity<ApiResponse<T>> created(String message, T data) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, message, data));
        }

        public static ResponseEntity<ApiResponse<Void>> deleted(String message) {
            return ResponseEntity.ok(
                    new ApiResponse<>(true, message, null)
            );
        }
    }
}

