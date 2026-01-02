package com.example.lvtn.common;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 403 - Không có quyền truy cập
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiRes<Object>> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiRes<>(403, "Bạn không có quyền truy cập! " + ex.getMessage(), null));
    }

    /**
     * 404 - Không tìm thấy dữ liệu
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiRes<Object>> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiRes<>(404, ex.getMessage(), null));
    }

    /**
     * 400 - Dữ liệu không hợp lệ (validate body @Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiRes<Object>> handleValidation(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest()
                .body(new ApiRes<>(400, errors, null));
    }

    /**
     * 400 - Vi phạm constraint (validate param/query/path)
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiRes<Object>> handleConstraintViolation(ConstraintViolationException ex) {
        String errors = ex.getConstraintViolations()
                .stream()
                .map(err -> err.getPropertyPath() + ": " + err.getMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest()
                .body(new ApiRes<>(400, errors, null));
    }

    /**
     * 400 - Tham số không hợp lệ
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiRes<Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .body(new ApiRes<>(400, ex.getMessage(), null));
    }

    /**
     * 400 - Trạng thái không hợp lệ (dùng trong xử lý tiền điều kiện)
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiRes<Object>> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiRes<>(400, ex.getMessage(), null));
    }
    /**
     * 409 - Lỗi xung đột dữ liệu (trùng khóa, unique constraint)
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiRes<Object>> handleDataIntegrity(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiRes<>(409, "Dữ liệu bị xung đột hoặc vi phạm ràng buộc", null));
    }

    /**
     * 500 - Lỗi chung (RuntimeException)
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiRes<Object>> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiRes<>(500, ex.getMessage(), null));
    }

    /**
     * 500 - Lỗi không xác định
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiRes<Object>> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiRes<>(500, "Có lỗi xảy ra trong hệ thống", null));
    }
}
