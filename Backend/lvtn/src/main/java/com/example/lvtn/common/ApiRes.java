package com.example.lvtn.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiRes<T> {

    @Schema(description = "Trạng thái của API Response (200, 400, 404, 500, ...)")
    private int status;

    @Schema(description = "Thông điệp trả về của API Response")
    private String message;

    @Schema(description = "Dữ liệu trả về của API Response (có thể có hoặc không có)")
    private T data;
}
