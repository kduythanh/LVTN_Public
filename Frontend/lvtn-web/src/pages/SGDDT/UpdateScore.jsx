import { useCallback, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { PageTitle, Spinner } from "@/components";
import { sgddtApi } from "@/api";
import { useTrangThaiCapNhatDiem } from "@/hooks";

export function FormDetail() {
  const { soBaoDanh } = useParams();
  const {
    loading: loadingTT,
    reloadTrangThai,
    duocPhepCapNhatDiem,
  } = useTrangThaiCapNhatDiem();
  const [initialTS, setInitialTS] = useState(null);
  const [loading, setLoading] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [success, setSuccess] = useState(false);
  const [message, setMessage] = useState("");
  const [thptChuyen, setTHPTChuyen] = useState(false);

  const [maHS, setMaHS] = useState("");
  const [hoTenHS, setHoTenHS] = useState("");
  const [diemToan, setDiemToan] = useState("");
  const [diemVan, setDiemVan] = useState("");
  const [diemMonThu3, setDiemMonThu3] = useState("");
  const [diemMonChuyen, setDiemMonChuyen] = useState("");

  const setTSData = useCallback(
    (ts) => {
      setMaHS(ts.maHS);
      setHoTenHS(`${ts.hoVaChuLotHS} ${ts.tenHS}`);
      setDiemToan(ts.diemToan);
      setDiemVan(ts.diemVan);
      setDiemMonThu3(ts.diemMonThu3);
      setTHPTChuyen(ts.thptChuyen);
      if (!thptChuyen) {
        setDiemMonChuyen("");
      } else {
        setDiemMonChuyen(ts.diemMonChuyen);
      }
    },
    [thptChuyen]
  );

  useEffect(() => {
    const fetchThiSinh = async () => {
      setLoading(true);
      try {
        const res = await sgddtApi.findThiSinh(soBaoDanh);
        let ts = res.data;
        if (ts != null) setInitialTS(ts);
        setTSData(ts);
      } catch (error) {
        console.error("Lỗi khi lấy thông tin học sinh:", error);
      } finally {
        setLoading(false);
      }
    };
    reloadTrangThai();
    fetchThiSinh();
  }, [soBaoDanh, setTSData, reloadTrangThai]);

  const handleReset = async (e) => {
    e.preventDefault();
    // Xóa các thông tin về thông báo
    setSubmitting(false);
    setSuccess(false);
    setMessage("");
    if (initialTS) setTSData(initialTS);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      setSubmitting(true);
      setSuccess(false);
      setMessage("");

      const ketQuaThi = {
        maHS: maHS,
        diemToan: diemToan == "" ? null : parseFloat(diemToan),
        diemVan: diemVan == "" ? null : parseFloat(diemVan),
        diemMonThu3: diemMonThu3 == "" ? null : parseFloat(diemMonThu3),
        diemMonChuyen: diemMonChuyen == "" ? null : parseFloat(diemMonChuyen),
      };
      const formData = new FormData();
      formData.append("ketQuaThi", JSON.stringify(ketQuaThi));

      const res = await sgddtApi.updateKetQuaThi(soBaoDanh, ketQuaThi);
      setSubmitting(false);
      setSuccess(true);
      setMessage(res.message);
    } catch (error) {
      console.error("Lỗi khi cập nhật điểm thí sinh:", error);
      setSubmitting(false);
      setSuccess(false);
      setMessage(error.response?.data?.message || error.message);
    }
  };
  if (loadingTT) return <Spinner />;
  if (!duocPhepCapNhatDiem)
    return (
      <div className="alert alert-danger text-center mt-2 mx-auto">
        Hệ thống hiện không cho phép cập nhật điểm thí sinh! Vui lòng bật chức
        năng ở mục Quản lý trạng thái để thực hiện chức năng này!
      </div>
    );
  if (loading) return <Spinner />;
  return (
    <>
      <form onSubmit={handleSubmit}>
        <div className="mb-3 row">
          <label htmlFor="SBD" className="col-sm-3 col-form-label fw-bold">
            Số báo danh
          </label>
          <div className="col-sm-9">
            <input
              type="text"
              className="form-control"
              id="SBD"
              name="SBD"
              value={soBaoDanh}
              disabled
            />
          </div>
        </div>
        <div className="mb-3 row">
          <label htmlFor="hoTenTS" className="col-sm-3 col-form-label fw-bold">
            Họ và tên thí sinh
          </label>
          <div className="col-sm-9">
            <input
              type="text"
              className="form-control"
              id="hoTenTS"
              name="hoTenTS"
              value={hoTenHS}
              disabled
            />
          </div>
        </div>
        <div className="mb-3 row">
          <label htmlFor="diemToan" className="col-sm-3 col-form-label fw-bold">
            Điểm Toán
          </label>
          <div className="col-sm-9">
            <input
              type="number"
              className="form-control"
              id="diemToan"
              name="diemToan"
              value={diemToan}
              onChange={(e) => setDiemToan(e.target.value)}
              min="0"
              max="10"
              step="0.01"
            />
          </div>
        </div>
        <div className="mb-3 row">
          <label htmlFor="diemVan" className="col-sm-3 col-form-label fw-bold">
            Điểm Ngữ văn
          </label>
          <div className="col-sm-9">
            <input
              type="number"
              className="form-control"
              id="diemVan"
              name="diemVan"
              value={diemVan}
              onChange={(e) => setDiemVan(e.target.value)}
              min="0"
              max="10"
              step="0.01"
            />
          </div>
        </div>
        <div className="mb-3 row">
          <label
            htmlFor="diemMonThu3"
            className="col-sm-3 col-form-label fw-bold"
          >
            Điểm môn thứ 3
          </label>
          <div className="col-sm-9">
            <input
              type="number"
              className="form-control"
              id="diemMonThu3"
              name="diemMonThu3"
              value={diemMonThu3}
              onChange={(e) => setDiemMonThu3(e.target.value)}
              min="0"
              max="10"
              step="0.01"
            />
          </div>
        </div>
        <div className="mb-3 row">
          <label
            htmlFor="diemMonChuyen"
            className="col-sm-3 col-form-label fw-bold"
          >
            Điểm môn chuyên
          </label>
          <div className="col-sm-9">
            <input
              type="number"
              className="form-control"
              id="diemMonChuyen"
              name="diemMonChuyen"
              value={diemMonChuyen}
              onChange={(e) => setDiemMonChuyen(e.target.value)}
              min="0"
              max="10"
              step="0.01"
              disabled={!thptChuyen}
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
            Cập nhật điểm
          </button>
        </div>
      </form>
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
export default function HDTSUpdateScore() {
  const { soBaoDanh } = useParams();
  const navigate = useNavigate();
  useEffect(() => {
    document.title = "Cập nhật điểm thí sinh";
  }, []);
  if (soBaoDanh === "" || soBaoDanh === null) {
    navigate("/sgddt");
    return;
  }
  return (
    <div className="container short-page">
      <PageTitle title={"CẬP NHẬT ĐIỂM THÍ SINH"} />
      <FormDetail />
    </div>
  );
}
