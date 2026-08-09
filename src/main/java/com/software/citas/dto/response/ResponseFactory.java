package com.software.citas.dto.response;

import org.springframework.http.ResponseEntity;

public class ResponseFactory {

    private ResponseFactory() {
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Operación realizada correctamente",
                        data
                )
        );
    }
}