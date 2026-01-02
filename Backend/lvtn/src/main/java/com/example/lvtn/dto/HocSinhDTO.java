package com.example.lvtn.dto;

import com.example.lvtn.model.KQHocTap;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HocSinhDTO {

    @Schema(description = "Thông tin chung của học sinh")
    private HocSinhFullData thongTinHS;

    @Schema(description = "Kết quả học tập")
    private List<KQHocTap> kqHocTap;

    @Schema(description = "Danh sách nguyện vọng")
    private List<NguyenVongFullData> nguyenVong;
}
