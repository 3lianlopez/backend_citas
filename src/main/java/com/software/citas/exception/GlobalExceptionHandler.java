package com.software.citas.exception;

import com.software.citas.dto.response.ApiResponse;
import com.software.citas.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse buildError(
            HttpStatus status,
            String message,
            HttpServletRequest request){

        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError(HttpStatus.NOT_FOUND,
                        ex.getMessage(),
                        request));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            BadRequestException ex,
            HttpServletRequest request){

        return ResponseEntity.badRequest()
                .body(buildError(HttpStatus.BAD_REQUEST,
                        ex.getMessage(),
                        request));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(
            ConflictException ex,
            HttpServletRequest request){

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(buildError(HttpStatus.CONFLICT,
                        ex.getMessage(),
                        request));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex,
            HttpServletRequest request){

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Ha ocurrido un error inesperado.",
                        request));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex) {

        String mensaje = String.format(
                "El método %s no está permitido. Métodos soportados: %s",
                ex.getMethod(),
                ex.getSupportedHttpMethods()
        );

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ApiResponse<>(
                        false,
                        mensaje,
                        null
                ));
    }
    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<ErrorResponse> handleInvalidOtp(
            InvalidOtpException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildError(
                        HttpStatus.BAD_REQUEST,
                        ex.getMessage(),
                        request
                ));
    }

    @ExceptionHandler(OtpLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleOtpLimitExceeded(
            OtpLimitExceededException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .body(buildError(
                        HttpStatus.TOO_MANY_REQUESTS,
                        ex.getMessage(),
                        request
                ));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalStateException(
            IllegalStateException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        new ApiResponse<>(
                                false,
                                exception.getMessage(),
                                null
                        )
                );
    }

}
