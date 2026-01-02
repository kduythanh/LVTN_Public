package com.example.lvtn.controller;

import com.example.lvtn.annotation.CheckCongBoKetQua;
import com.example.lvtn.common.ApiRes;
import com.example.lvtn.dto.*;
import com.example.lvtn.model.*;
import com.example.lvtn.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/api/common")
public class CommonController {
    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    private final TaiKhoanService tkService;
    private final THPTService thptService;
    private final THCSService thcsService;
    private final DanTocService dtService;
    private final DTUTService dtutService;
    private final DTKKService dtkkService;
    private final DTKKChuyenService dtkkchuyenService;
    private final ThiSinhService tsService;
    private final CaptchaService captchaService;
    private final LopChuyenTHPTService lcthptService;
    private final HocSinhService hsService;
    private final TrangThaiCSDLService ttcsdlService;

    public CommonController(TaiKhoanService tkService, THPTService thptService, THCSService thcsService,
                            DanTocService dtService, DTUTService dtutService, DTKKService dtkkService,
                            DTKKChuyenService dtkkchuyenService, ThiSinhService tsService,
                            CaptchaService captchaService, LopChuyenTHPTService lcthptService, HocSinhService hsService, TrangThaiCSDLService ttcsdlService) {
        this.tkService = tkService;
        this.thptService = thptService;
        this.thcsService = thcsService;
        this.dtService = dtService;
        this.dtutService = dtutService;
        this.dtkkService = dtkkService;
        this.dtkkchuyenService = dtkkchuyenService;
        this.tsService = tsService;
        this.captchaService = captchaService;
        this.lcthptService = lcthptService;
        this.hsService = hsService;
        this.ttcsdlService = ttcsdlService;
    }

    // Đăng nhập, phân quyền tương ứng (ĐÃ HOÀN THÀNH)
    @Operation(
            summary = "Đăng nhập",
            description = "Người dùng đăng nhập vào hệ thống",
            tags = { "common" })
    @PostMapping(value = "/login", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiRes<TaiKhoanToken>> login(@ModelAttribute TaiKhoanDangNhap tkDangNhap) {
        logger.info("Login attempt with username: {}", tkDangNhap.getTenTK());
        logger.info("Captcha token received: {}", tkDangNhap.getCaptchaToken());
        if (!captchaService.verifyCaptcha(tkDangNhap.getCaptchaToken())) {
            logger.warn("Captcha verification failed for user: {}", tkDangNhap.getTenTK());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiRes<>(400, "Captcha không hợp lệ!", null));
        }
        logger.info("Captcha verification succeeded for user: {}", tkDangNhap.getTenTK());
        TaiKhoanToken response = tkService.login(tkDangNhap);
        return ResponseEntity.ok(new ApiRes<>(200, "Đăng nhập tài khoản " + tkDangNhap.getTenTK() + " thành công!", response));
    }

    // Đổi mật khẩu (ĐÃ HOÀN THÀNH)
    @Operation(
            summary = "Đổi mật khẩu",
            description = "Người dùng đổi mật khẩu tài khoản hiện tại",
            tags = { "common" })
    @PostMapping(value = "/password/{tenTK}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiRes<Void>> changePassword(@PathVariable @Schema(description = "Tên tài khoản") String tenTK, @ModelAttribute ChangePasswordDTO changePasswordDTO) {
        tkService.changePassword(tenTK, changePasswordDTO);
        return ResponseEntity.ok(new ApiRes<>(200, "Đổi mật khẩu tài khoản " + tenTK + " thành công!", null));
    }

    // Đăng xuất (ĐÃ HOÀN THÀNH)
    @Operation(
            summary = "Đăng xuất",
            description = "Người dùng đăng xuất tài khoản hiện tại",
            tags = { "common" })
    @PostMapping(value = "/logout")
    public ResponseEntity<ApiRes<Void>> logout() {
        return ResponseEntity.ok(new ApiRes<>(200, "Đăng xuất tài khoản thành công!", null));
    }

    // Lấy danh sách các dân tộc (sử dụng cho dropdown) (ĐÃ HOÀN THÀNH)
    @Operation(
            summary = "Lấy danh sách các dân tộc",
            description = "Lấy danh sách các dân tộc",
            tags = { "common" })
    @GetMapping("/dantoc")
    public ResponseEntity<ApiRes<List<DanToc>>> findAllDanToc() {
        List<DanToc> result = dtService.findAllDanToc();
        return ResponseEntity.ok(new ApiRes<>(200, "Tìm kiếm danh sách các dân tộc thành công!", result));
    }

    // Lấy danh sách các đối tượng ưu tiên (sử dụng cho dropdown) (ĐÃ HOÀN THÀNH)
    @Operation(
            summary = "Lấy danh sách các đối tượng ưu tiên",
            description = "Lấy danh sách các đối tượng ưu tiên",
            tags = { "common" })
    @GetMapping("/dtut")
    public ResponseEntity<ApiRes<List<DTUT>>> findAllDTUT() {
        List<DTUT> result = dtutService.findAllDTUT();
        return ResponseEntity.ok(new ApiRes<>(200, "Tìm kiếm danh sách các đối tượng ưu tiên thành công!", result));
    }

    // Lấy danh sách các đối tượng khuyến khích (sử dụng cho dropdown) (ĐÃ HOÀN THÀNH)
    @Operation(
            summary = "Lấy danh sách các đối tượng khuyến khích",
            description = "Lấy danh sách các đối tượng khuyến khích",
            tags = { "common" })
    @GetMapping("/dtkk")
    public ResponseEntity<ApiRes<List<DTKK>>> findAllDTKK() {
        List<DTKK> result = dtkkService.findAllDTKK();
        return ResponseEntity.ok(new ApiRes<>(200, "Tìm kiếm danh sách các đối tượng khuyến khích thành công!", result));
    }

    // Lấy danh sách các đối tượng khuyến khích (chuyên) (sử dụng cho dropdown) (ĐÃ HOÀN THÀNH)
    @Operation(
            summary = "Lấy danh sách các đối tượng khuyến khích (chuyên)",
            description = "Lấy danh sách các đối tượng khuyến khích (chuyên)",
            tags = { "common" })
    @GetMapping("/dtkk-chuyen")
    public ResponseEntity<ApiRes<List<DTKKChuyen>>> findAllDTKKChuyen() {
        List<DTKKChuyen>  result = dtkkchuyenService.findAllDTKKChuyen();
        return ResponseEntity.ok(new ApiRes<>(200, "Tìm kiếm danh sách các đối tượng khuyến khích (chuyên) thành công!", result));
    }

    // Lấy danh sách các trường THCS (sử dụng cho dropdown) (ĐÃ HOÀN THÀNH)
    @Operation(
            summary = "Lấy danh sách các trường THCS",
            description = "Lấy danh sách các trường THCS trên địa bàn",
            tags = { "common" })
    @GetMapping("/thcs")
    public ResponseEntity<ApiRes<List<THCS>>> findAllTHCS() {
        List<THCS> result = thcsService.findAllTHCS();
        return ResponseEntity.ok(new ApiRes<>(200, "Tìm kiếm danh sách các trường THCS thành công!", result));
    }

    // Lấy danh sách các trường THPT (sử dụng cho dropdown) (ĐÃ HOÀN THÀNH)
    @Operation(
            summary = "Lấy danh sách các trường THPT",
            description = "Lấy danh sách các trường THPT trên địa bàn",
            tags = { "common" })
    @GetMapping("/thpt")
    public ResponseEntity<ApiRes<List<THPT>>> findAllTHPT() {
        List<THPT> result = thptService.findAllTHPT();
        return ResponseEntity.ok(new ApiRes<>(200, "Tìm kiếm danh sách các trường THPT thành công!", result));
    }

    // Lấy danh sách các lớp chuyên có trong trường THPT chuyên tương ứng (sử dụng cho dropdown)
    @Operation(
            summary = "Lấy danh sách các lớp chuyên có trong trường THPT chuyên",
            description = "Lấy danh sách các lớp chuyên có trong trường THPT chuyên tương ứng",
            tags = { "common" })
    @GetMapping("/lopchuyen/{maTHPT}")
    public ResponseEntity<ApiRes<List<LopChuyenTHPTDTO>>> findAllLopChuyenByMaTHPT(@PathVariable("maTHPT") String maTHPT) {
        List<LopChuyenTHPTDTO> result = lcthptService.getAllLopChuyenTHPTByMaTHPT(maTHPT);
        return ResponseEntity.ok(new ApiRes<>(200, "Tìm kiếm lớp chuyên trong trường THPT mang mã số " + maTHPT + " thành công!", result));
    }

    // Tra cứu kết quả thi (khi người dùng tra cứu ở chức năng Tra cứu kết quả)
    @Operation(
            summary = "Tra cứu kết quả thi",
            description = "Tra cứu kết quả thi theo hội đồng thi và từ khóa",
            tags = { "common" })
    @GetMapping("/ketquathi")
    @CheckCongBoKetQua
    public ResponseEntity<ApiRes<List<KetQuaTuyenSinhDTO>>> findKQThiByKeyword(@RequestParam String maTHPT, @RequestParam Integer type, @RequestParam String keyword, @RequestHeader("X-Captcha-Token") String captchaToken) {
        logger.info("Captcha token received for searching result: {}", captchaToken);
        if (!captchaService.verifyCaptcha(captchaToken)) {
            logger.warn("Captcha verification failed for searching result");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiRes<>(400, "Captcha không hợp lệ!", null));
        }
        List<KetQuaTuyenSinhDTO> ts = tsService.getKetQuaTuyenSinh(maTHPT, type, keyword);
        return ResponseEntity.ok(new ApiRes<>(200, "Tra cứu kết quả thi thành công!", ts));
    }

    // Tra cứu kết quả thi theo SBD (xem chi tiết)
    @Operation(
            summary = "Tra cứu kết quả thi đầy đủ theo SBD",
            description = "Tra cứu kết quả thi đầy đủ theo SBD",
            tags = { "common" })
    @GetMapping("/ketquathi/{soBaoDanh}")
    @CheckCongBoKetQua
    public ResponseEntity<ApiRes<KetQuaTuyenSinhDTO>> findKQThiBySBD(@PathVariable String soBaoDanh) {
        KetQuaTuyenSinhDTO kq = tsService.findKetQuaTuyenSinhBySBD(soBaoDanh);
        return ResponseEntity.ok(new ApiRes<>(200, "Tra cứu kết quả thi thành công!", kq));
    }

    // Lấy danh sách các trường THPT và chỉ tiêu
    @Operation(
            summary = "Lấy danh sách các trường THPT và chỉ tiêu",
            description = "Lấy danh sách các trường THPT trên địa bàn và chỉ tiêu",
            tags = { "common" })
    @GetMapping("/thpt/chitieu")
    public ResponseEntity<ApiRes<List<ChiTieuTHPTDTO>>> findDSTHPTAndChiTieu() {
        List<ChiTieuTHPTDTO> result = thptService.findDSTHPTAndChiTieu();
        return ResponseEntity.ok(new ApiRes<>(200, "Tìm kiếm danh sách các trường THPT thành công!", result));
    }

    // Lấy danh sách các lớp chuyên của các trường THPT chuyên và chỉ tiêu
    @Operation(
            summary = "Lấy danh sách các lớp chuyên của các trường THPT chuyên và chỉ tiêu",
            description = "Lấy danh sách các lớp chuyên của các trường THPT chuyên trên địa bàn và chỉ tiêu",
            tags = { "common" })
    @GetMapping("/thpt/chitieu-chuyen")
    public ResponseEntity<ApiRes<List<ChiTieuTHPTChuyenDTO>>> findDSTHPTChuyenAndChiTieu() {
        List<ChiTieuTHPTChuyenDTO> result = thptService.findDSTHPTChuyenAndChiTieu();
        return ResponseEntity.ok(new ApiRes<>(200, "Tìm kiếm danh sách các trường THPT thành công!", result));
    }

    @Operation(
            summary = "Lấy hình ảnh học sinh",
            description = "Lấy hình ảnh học sinh",
            tags = { "common" })
    @GetMapping("/anh/{maHS}")
    public ResponseEntity<Resource> getAnhHocSinh(@PathVariable String maHS) {
        try {
            Resource fileResource = hsService.loadAnhHocSinh(maHS);
            String contentType = Files.probeContentType(fileResource.getFile().toPath());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(fileResource);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            summary = "Lấy trạng thái theo mã trạng thái",
            description = "Lấy trạng thái theo mã trạng thái",
            tags = { "common" })
    @GetMapping("/trangthai/{maTT}")
    public ResponseEntity<ApiRes<TrangThaiCSDL>> getTrangThaiByMaTT(@PathVariable Integer maTT) {
        TrangThaiCSDL tt = ttcsdlService.findById(maTT);
        return ResponseEntity.ok(new ApiRes<>(200, "Tìm kiếm trạng thái có mã " + maTT + " thành công!", tt));
    }

    @Operation(summary = "Cập nhật trạng thái hệ thống", description = "Cập nhật trạng thái hệ thống", tags = { "common" })
    @PatchMapping("/trangthai/{maTT}")
    public ResponseEntity<ApiRes<Void>> updateTrangThai(@PathVariable Integer maTT, @RequestParam String kieuDuLieu, @RequestParam String newVal) {
        ttcsdlService.updateTrangThai(maTT, kieuDuLieu, newVal);
        return ResponseEntity.ok(new ApiRes<>(200, "Cập nhật trạng thái thành công!", null));
    }
}
