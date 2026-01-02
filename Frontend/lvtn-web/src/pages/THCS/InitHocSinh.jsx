import { useState, useEffect } from "react";
import { useAuth, useTrangThaiCapNhatHoSo } from "@/hooks";
import { thcsApi } from "@/api";
import { PageTitle, Spinner } from "@/components/common";
import { GioiTinhButtonGroup, TextInput } from "@/components/form-control";

export function FormDetail() {
  const { user } = useAuth();
  const { duocPhepCapNhat, loading, reloadTrangThai } =
    useTrangThaiCapNhatHoSo();
  const [errorList, setErrorList] = useState([]);
  const [submitting, setSubmitting] = useState(false);
  const [success, setSuccess] = useState(false);
  const [message, setMessage] = useState("");

  const [MSHS, setMSHS] = useState("");
  const [hoTenHS, setHoTenHS] = useState("");
  const [gioiTinh, setGioiTinh] = useState("");
  const [ngaySinh, setNgaySinh] = useState("");
  const [noiSinh, setNoiSinh] = useState("");
  const tenTHCS = user.tenDinhDanh;

  const handleReset = (e) => {
    e.preventDefault();
    // Xóa các thông tin về thông báo
    setErrorList([]);
    setSubmitting(false);
    setSuccess(false);
    setMessage("");

    setMSHS("");
    setHoTenHS("");
    setGioiTinh("");
    setNgaySinh("");
    setNoiSinh("");
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    // Kiểm tra các trường
    let errors = [];
    if (gioiTinh === "") errors.push("Vui lòng chọn giới tính!");
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

      const formData = new FormData();
      formData.append("maHS", MSHS);
      formData.append("hoVaChuLotHS", hoVaChuLotHS);
      formData.append("tenHS", tenHS);
      formData.append("gioiTinh", gioiTinh);
      formData.append("ngaySinh", ngaySinh);
      formData.append("noiSinh", noiSinh);
      formData.append("maTHCS", user.soDinhDanh);

      const res = await thcsApi.initHocSinh(user.soDinhDanh, formData);
      setSubmitting(false);
      setSuccess(true);
      setMessage(res.data.message);
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

  if (loading) return <Spinner />;
  if (!duocPhepCapNhat)
    return (
      <div className="alert alert-danger text-center mt-2 mx-auto">
        Bạn không được phép khởi tạo hồ sơ học sinh do đã hết hạn đăng ký hoặc
        hệ thống không cho phép!
      </div>
    );
  return (
    <>
      <form onSubmit={handleSubmit} className="my-2">
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
            <label htmlFor="tenTHCS">Học sinh trường THCS</label>
          </div>
          <div className="col-sm-9">
            <TextInput id="tenTHCS" value={tenTHCS} disabled={true} />
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

export default function THCSInitHocSinh() {
  useEffect(() => {
    document.title = "Khởi tạo hồ sơ học sinh";
  }, []);
  return (
    <div className="container-fluid container-lg long-page">
      <PageTitle title={"KHỞI TẠO HỒ SƠ HỌC SINH"} />
      <FormDetail />
    </div>
  );
}
