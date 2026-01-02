import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth, useTrangThaiCapNhatHoSo } from "@/hooks";
import { PageTitle, FormPartTitle, Spinner } from "@/components/common";
import {
  DanTocDropdown,
  DTUTDropdown,
  DTKKDropdown,
  DTKKChuyenDropdown,
  NguyenVongChuyenDropdown,
  LopChuyenDropdown,
  NguyenVongNgoaiTPCTDropdown,
  GioiTinhButtonGroup,
  HocTapButtonGroup,
  RenLuyenButtonGroup,
  NgoaiNguButtonGroup,
  ImageInput,
  TextInput,
} from "@/components/form-control";
import { useImageUpload } from "@/hooks";
import { thptApi } from "@/api";

export function FormDetail() {
  const { user } = useAuth();
  const { duocPhepCapNhat, loading, reloadTrangThai } =
    useTrangThaiCapNhatHoSo();
  const [errorList, setErrorList] = useState([]);
  const [submitting, setSubmitting] = useState(false);
  const [success, setSuccess] = useState(false);
  const [message, setMessage] = useState("");

  const { anhDaiDien, preview, fileInputRef, handleImageChange, resetImage } =
    useImageUpload();
  const [MSHS, setMSHS] = useState("");
  const [hoTenHS, setHoTenHS] = useState("");
  const [gioiTinh, setGioiTinh] = useState("");
  const [ngaySinh, setNgaySinh] = useState("");
  const [noiSinh, setNoiSinh] = useState("");
  const [danToc, setDanToc] = useState("");
  const [DCTT, setDCTT] = useState("");
  const [COHN, setCOHN] = useState("");
  const maTHCS = "0";
  const [tenTHCS, setTenTHCS] = useState("");
  const [loaiXa, setLoaiXa] = useState("");
  const [tenXa, setTenXa] = useState("");
  const [loaiTinh, setLoaiTinh] = useState("");
  const [tenTinh, setTenTinh] = useState("");

  const [HT6, setHT6] = useState("");
  const [RL6, setRL6] = useState("");
  const [HT7, setHT7] = useState("");
  const [RL7, setRL7] = useState("");
  const [HT8, setHT8] = useState("");
  const [RL8, setRL8] = useState("");
  const [HT9, setHT9] = useState("");
  const [RL9, setRL9] = useState("");
  const [TDTB9, setTDTB9] = useState("");
  const namTNTHCS = 2026;

  const [DTUT, setDTUT] = useState("");
  const [DTKK, setDTKK] = useState("");
  const [NV1, setNV1] = useState("");
  const [lopChuyen, setLopChuyen] = useState("");
  const [DTBChuyen9, setDTBChuyen9] = useState("");
  const [DTKKChuyen, setDTKKChuyen] = useState("");
  const [NV2, setNV2] = useState("");
  const [NNDH, setNNDH] = useState("");
  const [NNDT, setNNDT] = useState("");
  const [soDienThoai, setSoDienThoai] = useState("");

  const handleReset = (e) => {
    e.preventDefault();
    // Xóa các thông tin về thông báo
    setErrorList([]);
    setSubmitting(false);
    setSuccess(false);
    setMessage("");
    resetImage(); // Xóa ảnh đại diện

    setMSHS("");
    setHoTenHS("");
    setGioiTinh("");
    setDanToc("");
    setNgaySinh("");
    setNoiSinh("");
    setDCTT("");
    setCOHN("");
    setTenTHCS("");
    setLoaiXa("");
    setTenXa("");
    setLoaiTinh("");
    setTenTinh("");

    setHT6("");
    setRL6("");
    setHT7("");
    setRL7("");
    setHT8("");
    setRL8("");
    setHT9("");
    setRL9("");
    setTDTB9("");

    setDTUT("");
    setDTKK("");
    setNV1("");
    setLopChuyen("");
    setDTBChuyen9("");
    setDTKKChuyen("0");
    setNV2("");
    setNNDH("");
    setNNDT("");
    setSoDienThoai("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    // Kiểm tra các trường
    let errors = [];
    if (gioiTinh === "") errors.push("Vui lòng chọn giới tính!");
    if (!HT6) errors.push("Vui lòng chọn kết quả học tập lớp 6!");
    if (!RL6) errors.push("Vui lòng chọn kết quả rèn luyện lớp 6!");
    if (!HT7) errors.push("Vui lòng chọn kết quả học tập lớp 7!");
    if (!RL7) errors.push("Vui lòng chọn kết quả rèn luyện lớp 7!");
    if (!HT8) errors.push("Vui lòng chọn kết quả học tập lớp 8!");
    if (!RL8) errors.push("Vui lòng chọn kết quả rèn luyện lớp 8!");
    if (!HT9) errors.push("Vui lòng chọn kết quả học tập lớp 9!");
    if (!RL9) errors.push("Vui lòng chọn kết quả rèn luyện lớp 9!");
    if (!NNDH) errors.push("Vui lòng chọn ngoại ngữ đang học!");
    if (!NNDT) errors.push("Vui lòng chọn ngoại ngữ dự thi!");
    if (NV1 === "" && NV2 === "")
      errors.push("Vui lòng chọn ít nhất một trong hai nguyện vọng 1 hoặc 2!");
    if (
      NV1 !== "" &&
      (lopChuyen === "" || DTBChuyen9 === "" || DTKKChuyen === "")
    )
      errors.push("Vui lòng chọn đầy đủ thông tin nếu chọn NV1!");
    if (user.thptChuyen === true) {
      if (user.soDinhDanh !== NV1) {
        errors.push(
          "Học sinh đăng ký tại trường nào phải đặt nguyện vọng cao nhất vào trường đó!"
        );
      }
    } else {
      if (user.soDinhDanh !== NV2) {
        errors.push(
          "Học sinh đăng ký tại trường nào phải đặt nguyện vọng cao nhất vào trường đó!"
        );
      }
    }
    setErrorList(errors);
    if (errors.length > 0) return;
    try {
      setSubmitting(true);
      setSuccess(false);
      setMessage("");
      // Tách họ và tên
      const parts = hoTenHS.trim().split(/\s+/);
      const tenHS = parts.pop();
      const hoVaChuLotHS = parts.join(" ");

      let nguyenVong = [
        NV1 && {
          maHS: MSHS,
          thuTu: 1,
          maTHPT: NV1,
          maLopChuyen: lopChuyen,
          nv2B: false,
          lopTiengPhap: false,
        },
        NV2 && {
          maHS: MSHS,
          thuTu: 2,
          maTHPT: NV2,
          nv2B: false,
          lopTiengPhap: false,
        },
      ].filter(Boolean);

      const payload = {
        thongTinHS: {
          maHS: MSHS,
          hoVaChuLotHS,
          tenHS,
          gioiTinh,
          ngaySinh,
          noiSinh,
          maDT: danToc,
          diaChiThuongTru: DCTT,
          choOHienNay: COHN,
          maTHCS: maTHCS,
          namTotNghiepTHCS: namTNTHCS,
          maDTUT: DTUT,
          maDTKK: DTKK,
          maDTKKChuyen: DTKKChuyen,
          ngoaiNguDangHoc: NNDH,
          ngoaiNguDuThi: NNDT,
          soDienThoai,
          diemTBMonChuyen: DTBChuyen9,
          tongDiemTBLop9: TDTB9,
          tenTHCSNgoaiTPCT: tenTHCS ? "THCS " + tenTHCS.trim() : "",
          tenXaNgoaiTPCT: loaiXa && tenXa ? `${loaiXa} ${tenXa.trim()}` : "",
          tenTinhNgoaiTPCT:
            loaiTinh && tenTinh ? `${loaiTinh} ${tenTinh.trim()}` : "",
        },
        kqHocTap: [
          { maHS: MSHS, lop: 6, hocTap: HT6, renLuyen: RL6 },
          { maHS: MSHS, lop: 7, hocTap: HT7, renLuyen: RL7 },
          { maHS: MSHS, lop: 8, hocTap: HT8, renLuyen: RL8 },
          { maHS: MSHS, lop: 9, hocTap: HT9, renLuyen: RL9 },
        ],
        nguyenVong: nguyenVong,
      };

      const formData = new FormData();
      formData.append("data", JSON.stringify(payload)); // Dữ liệu JSON
      if (anhDaiDien) {
        formData.append("file", anhDaiDien); // File ảnh
      }

      const res = await thptApi.addHocSinh(user.soDinhDanh, formData);
      setSubmitting(false);
      setSuccess(true);
      setMessage(res.message);
    } catch (error) {
      console.error("Lỗi khi thêm học sinh:", error);
      setSubmitting(false);
      setSuccess(false);
      setMessage(error.response?.data?.message || error.message);
    }
  };
  useEffect(() => {
    reloadTrangThai();
  }, [reloadTrangThai]);
  useEffect(() => {
    if (NV1 == "") {
      setDTKKChuyen("0");
      setDTBChuyen9("");
      setLopChuyen("");
    }
  }, [NV1]);
  if (loading) return <Spinner />;
  if (!duocPhepCapNhat)
    return (
      <div className="alert alert-danger text-center mt-2 mx-auto">
        Bạn không được phép thêm hồ sơ học sinh do đã hết hạn đăng ký hoặc hệ
        thống không cho phép!
      </div>
    );
  return (
    <>
      <form onSubmit={handleSubmit} className="my-2">
        <FormPartTitle title="Phần 1: Thông tin cá nhân học sinh" />
        <div className="mb-3 row">
          <div className="col-sm-3 col-form-label fw-bold text-end">
            <label htmlFor="anhDaiDien">Ảnh học sinh</label>
          </div>
          <div className="col-sm-9">
            <ImageInput
              id="anhDaiDien"
              ref={fileInputRef}
              preview={preview}
              onChange={handleImageChange}
            />
          </div>
        </div>
        <div className="mb-3 row">
          <div className="col-sm-3 col-form-label fw-bold text-end">
            <label htmlFor="MSHS">
              Mã số học sinh<span className="text-danger">*</span>
            </label>
          </div>
          <div className="col-sm-9">
            <TextInput
              id="MSHS"
              value={MSHS}
              placeholder="Nhập mã số học sinh..."
              onChange={setMSHS}
              required={true}
            />
          </div>
        </div>
        <div className="mb-3 row">
          <div className="col-sm-3 col-form-label fw-bold text-end">
            <label htmlFor="hoTenHS">
              Họ và tên học sinh<span className="text-danger">*</span>
            </label>
          </div>
          <div className="col-sm-9">
            <TextInput
              id="hoTenHS"
              value={hoTenHS}
              placeholder="Nhập họ và tên học sinh..."
              onChange={setHoTenHS}
              required={true}
            />
          </div>
        </div>
        <div className="mb-3 row">
          <div className="col-sm-3 col-form-label fw-bold text-end">
            <label htmlFor="gioiTinh-Nam">
              Giới tính<span className="text-danger">*</span>
            </label>
          </div>
          <div className="col-sm-9">
            <GioiTinhButtonGroup
              id="gioiTinh"
              value={gioiTinh}
              onChange={setGioiTinh}
            />
          </div>
        </div>
        <div className="mb-3 row">
          <div className="col-sm-3 col-form-label fw-bold text-end">
            <label htmlFor="ngaySinh">
              Ngày sinh<span className="text-danger">*</span>
            </label>
          </div>
          <div className="col-sm-9">
            <input
              type="date"
              className="form-control focus-ring focus-ring-success"
              id="ngaySinh"
              name="ngaySinh"
              value={ngaySinh}
              onChange={(e) => setNgaySinh(e.target.value)}
              required
            />
          </div>
        </div>
        <div className="mb-3 row">
          <div className="col-sm-3 col-form-label fw-bold text-end">
            <label htmlFor="noiSinh">
              Nơi sinh<span className="text-danger">*</span>
            </label>
          </div>
          <div className="col-sm-9">
            <TextInput
              id="noiSinh"
              value={noiSinh}
              placeholder="Nhập nơi sinh (tỉnh/thành phố)..."
              onChange={setNoiSinh}
              required={true}
            />
          </div>
        </div>
        <div className="mb-3 row">
          <div className="col-sm-3 col-form-label fw-bold text-end">
            <label htmlFor="danToc">
              Dân tộc<span className="text-danger">*</span>
            </label>
          </div>
          <div className="col-sm-9">
            <DanTocDropdown id="danToc" value={danToc} onChange={setDanToc} />
          </div>
        </div>
        <div className="mb-3 row">
          <div className="col-sm-3 col-form-label fw-bold text-end">
            <label htmlFor="DCTT">Địa chỉ thường trú</label>
          </div>
          <div className="col-sm-9">
            <TextInput
              id="DCTT"
              value={DCTT}
              placeholder="Nhập địa chỉ thường trú..."
              onChange={setDCTT}
            />
          </div>
        </div>
        <div className="mb-3 row">
          <div className="col-sm-3 col-form-label fw-bold text-end">
            <label htmlFor="COHN">Chỗ ở hiện nay</label>
          </div>
          <div className="col-sm-9">
            <TextInput
              id="COHN"
              value={COHN}
              placeholder="Nhập địa chỉ chỗ ở hiện nay..."
              onChange={setCOHN}
            />
          </div>
        </div>
        <div className="mb-3 row">
          <div className="col-sm-3 col-form-label fw-bold text-end">
            <label htmlFor="tenTHCS">Học sinh trường THCS</label>
          </div>
          <div className="col-sm-9">
            <div className="input-group">
              <span className="input-group-text w-auto">THCS</span>
              <input
                type="text"
                id="tenTHCS"
                name="tenTHCS"
                className="form-control"
                placeholder="Tên trường THCS"
                value={tenTHCS}
                onChange={(e) => setTenTHCS(e.target.value)}
                required
              />
              <select
                className="form-select bg-body-tertiary w-auto"
                id="loaiXa"
                name="loaiXa"
                value={loaiXa}
                onChange={(e) => setLoaiXa(e.target.value)}
                required
              >
                <option value="">-- Chọn --</option>
                <option value="Phường">Phường</option>
                <option value="Xã">Xã</option>
                <option value="Đặc khu">Đặc khu</option>
              </select>
              <input
                type="text"
                id="tenXa"
                name="tenXa"
                className="form-control"
                placeholder="Tên xã/phường/đặc khu"
                value={tenXa}
                onChange={(e) => setTenXa(e.target.value)}
                required
              />
              <select
                className="form-select bg-body-tertiary w-auto"
                id="loaiTinh"
                name="loaiTinh"
                value={loaiTinh}
                onChange={(e) => setLoaiTinh(e.target.value)}
                required
              >
                <option value="">-- Chọn --</option>
                <option value="Tỉnh">Tỉnh</option>
                <option value="Thành phố">Thành phố</option>
              </select>
              <input
                type="text"
                id="tenTinh"
                name="tenTinh"
                className="form-control"
                placeholder="Tên tỉnh/thành phố"
                value={tenTinh}
                onChange={(e) => setTenTinh(e.target.value)}
                required
              />
            </div>
          </div>
        </div>
        <FormPartTitle title="Phần 2: Kết quả học tập cấp THCS" />
        <div className="mb-3 row">
          <div className="col-sm-3 col-form-label fw-bold text-end">
            Kết quả Học tập và Rèn luyện<span className="text-danger">*</span>
          </div>
          <div className="col-sm-9">
            <div className="d-flex flex-row align-items-center mb-3 flex-wrap">
              <div className="my-auto fw-bold me-2">Lớp 6: </div>
              <HocTapButtonGroup id="HT6" value={HT6} onChange={setHT6} />
              <RenLuyenButtonGroup id="RL6" value={RL6} onChange={setRL6} />
            </div>
            <div className="d-flex flex-row align-items-center mb-3 flex-wrap">
              <div className="my-auto fw-bold me-2">Lớp 7: </div>
              <HocTapButtonGroup id="HT7" value={HT7} onChange={setHT7} />
              <RenLuyenButtonGroup id="RL7" value={RL7} onChange={setRL7} />
            </div>
            <div className="d-flex flex-row align-items-center mb-3 flex-wrap">
              <div className="my-auto fw-bold me-2">Lớp 8: </div>
              <HocTapButtonGroup id="HT8" value={HT8} onChange={setHT8} />
              <RenLuyenButtonGroup id="RL8" value={RL8} onChange={setRL8} />
            </div>
            <div className="d-flex flex-row align-items-center mb-3 flex-wrap">
              <div className="my-auto fw-bold me-2">Lớp 9: </div>
              <HocTapButtonGroup id="HT9" value={HT9} onChange={setHT9} />
              <RenLuyenButtonGroup id="RL9" value={RL9} onChange={setRL9} />
            </div>
            <div className="row d-flex">
              <div className="col-auto">
                <label htmlFor="TDTB9" className="form-label me-2">
                  Tổng điểm trung bình các môn lớp 9:
                  <span className="text-danger">*</span>
                </label>
                <input
                  type="number"
                  className="form-control d-inline-block w-auto focus-ring focus-ring-success"
                  id="TDTB9"
                  name="TDTB9"
                  value={TDTB9}
                  placeholder="Nhập tổng ĐTB..."
                  onChange={(e) => setTDTB9(e.target.value)}
                  required
                />
              </div>
            </div>
          </div>
        </div>
        <div className="mb-3 row">
          <div className="col-sm-3 col-form-label fw-bold text-end">
            <label htmlFor="namTNTHCS">Năm tốt nghiệp THCS</label>
          </div>
          <div className="col-sm-9">
            <input
              type="number"
              className="form-control focus-ring focus-ring-success"
              id="namTNTHCS"
              name="namTNTHCS"
              value={namTNTHCS}
              disabled
            />
          </div>
        </div>
        <FormPartTitle title="Phần 3: Thông tin đăng ký dự thi" />
        <div className="mb-3 row">
          <div className="col-sm-3 col-form-label fw-bold text-end">
            <label htmlFor="dtut">
              Đối tượng ưu tiên<span className="text-danger">*</span>
            </label>
          </div>
          <div className="col-sm-9">
            <DTUTDropdown id="dtut" value={DTUT} onChange={setDTUT} />
          </div>
        </div>
        <div className="mb-3 row">
          <div className="col-sm-3 col-form-label fw-bold text-end">
            <label htmlFor="dtkk">
              Đối tượng khuyến khích<span className="text-danger">*</span>
            </label>
          </div>
          <div className="col-sm-9">
            <DTKKDropdown id="dtkk" value={DTKK} onChange={setDTKK} />
          </div>
        </div>
        <div className="mb-3 row">
          <div className="col-sm-3 col-form-label fw-bold text-end">
            Nguyện vọng 1
          </div>
          <div className="col-sm-9">
            <div className="row d-flex mb-3">
              <label htmlFor="NV1" className="form-label col-auto my-auto">
                Trường THPT chuyên:
              </label>
              <div className="col">
                <NguyenVongChuyenDropdown
                  id="NV1"
                  value={NV1}
                  onChange={setNV1}
                />
              </div>
            </div>
            <div className="row d-flex mb-3">
              <label
                htmlFor="lopChuyen"
                className="form-label col-auto my-auto"
              >
                Lớp chuyên:
              </label>
              <div className="col">
                <LopChuyenDropdown
                  maTHPT={NV1}
                  id="lopChuyen"
                  value={lopChuyen}
                  onChange={setLopChuyen}
                  disabled={NV1 === ""}
                />
              </div>
            </div>
            <div className="row d-flex">
              <div className="col-auto">
                <label htmlFor="DTBChuyen9" className="form-label me-2">
                  Điểm trung bình môn chuyên lớp 9:
                </label>
                <input
                  type="number"
                  className="form-control d-inline-block w-auto focus-ring focus-ring-success"
                  id="DTBChuyen9"
                  name="DTBChuyen9"
                  value={DTBChuyen9}
                  placeholder={NV1 !== "" ? "Nhập ĐTB..." : ""}
                  onChange={(e) => setDTBChuyen9(e.target.value)}
                  disabled={NV1 === ""}
                />
              </div>
            </div>
          </div>
        </div>
        <div className="mb-3 row">
          <div className="col-sm-3 col-form-label fw-bold text-end">
            <label htmlFor="dtkkChuyen">
              Đối tượng khuyến khích (thi chuyên)
            </label>
          </div>
          <div className="col-sm-9">
            <DTKKChuyenDropdown
              id="dtkkChuyen"
              value={DTKKChuyen}
              onChange={setDTKKChuyen}
              disabled={NV1 === ""}
            />
          </div>
        </div>
        <div className="mb-3 row">
          <div className="col-sm-3 col-form-label fw-bold text-end">
            <label htmlFor="NV2">Nguyện vọng 2</label>
          </div>
          <div className="col-sm-9">
            <NguyenVongNgoaiTPCTDropdown
              id="NV2"
              value={NV2}
              onChange={setNV2}
            />
          </div>
        </div>
        <div className="mb-3 row">
          <div className="col-sm-3 col-form-label fw-bold text-end">
            <label htmlFor="NNDH-Tiếng Anh">
              Ngoại ngữ đang học<span className="text-danger">*</span>
            </label>
          </div>
          <div className="col-sm-9">
            <NgoaiNguButtonGroup id="NNDH" value={NNDH} onChange={setNNDH} />
          </div>
        </div>
        <div className="mb-3 row">
          <div className="col-sm-3 col-form-label fw-bold text-end">
            <label htmlFor="NNDT-Tiếng Anh">
              Ngoại ngữ dự thi<span className="text-danger">*</span>
            </label>
          </div>
          <div className="col-sm-9">
            <NgoaiNguButtonGroup id="NNDT" value={NNDT} onChange={setNNDT} />
          </div>
        </div>
        <div className="mb-3 row">
          <div className="col-sm-3 col-form-label fw-bold text-end">
            <label htmlFor="soDienThoai">Số điện thoại</label>
          </div>

          <div className="col-sm-9">
            <input
              type="tel"
              className="form-control focus-ring focus-ring-success"
              id="soDienThoai"
              name="soDienThoai"
              placeholder="Nhập số điện thoại liên hệ..."
              value={soDienThoai}
              onChange={(e) => setSoDienThoai(e.target.value)}
            />
          </div>
        </div>
        <div className="d-flex justify-content-center">
          <button
            type="reset"
            className="btn btn-warning mx-2"
            onClick={handleReset}
          >
            Khôi phục
          </button>
          <button type="submit" className="btn btn-success mx-2">
            Thêm hồ sơ
          </button>
        </div>
      </form>
      {errorList.length > 0 && (
        <>
          <hr className="border-black" />
          <div className="alert alert-danger mt-2 w-50 mx-auto">
            <div className="text-center">
              <strong>Lỗi ({errorList.length}): </strong>
            </div>
            <div
              style={{
                fontSize: "0.9rem",
              }}
            >
              {errorList.map((err, i) => (
                <div className="text-start" key={i}>
                  - {err}
                </div>
              ))}
            </div>
          </div>
        </>
      )}
      {submitting && (
        <div className="text-center my-3">
          <div
            className="spinner-border text-success"
            role="status"
            style={{ width: "3rem", height: "3rem" }}
          ></div>
          <p className="mt-2">{message}</p>
        </div>
      )}
      {!submitting && message && (
        <div
          className={`alert ${success == true ? "alert-success" : "alert-danger"} text-center mt-2 mx-auto w-50`}
        >
          {message}
        </div>
      )}
    </>
  );
}

export default function THPTAddHocSinh() {
  const { user } = useAuth();
  const navigate = useNavigate();
  useEffect(() => {
    if (user.tsNgoaiTPCT !== true) navigate("/thpt");
  }, [navigate, user]);
  useEffect(() => {
    document.title = "Thêm hồ sơ học sinh mới";
  }, []);
  return (
    <div className="container-fluid container-lg long-page">
      <PageTitle
        title={"THÊM HỒ SƠ HỌC SINH MỚI"}
        subtitle={"(Học sinh ngoài địa bàn thành phố Cần Thơ)"}
      />
      <FormDetail />
    </div>
  );
}
