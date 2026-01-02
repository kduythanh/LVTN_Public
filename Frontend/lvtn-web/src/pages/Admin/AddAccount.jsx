import { useEffect, useState } from "react";
import { commonApi, adminApi } from "@/api";
import { PageTitle } from "@/components/common";

export function FormDetail() {
  const [errorList, setErrorList] = useState([]);
  const [submitting, setSubmitting] = useState(false);
  const [success, setSuccess] = useState(false);
  const [message, setMessage] = useState("");

  const [listTruong, setListTruong] = useState([]);
  const [tenTK, setTenTK] = useState("");
  const [doiTuong, setDoiTuong] = useState("");
  const [maTruong, setMaTruong] = useState("");
  const handleReset = () => {
    setListTruong([]);
    setTenTK("");
    setDoiTuong("");
    setMaTruong("");
    setErrorList([]);
    setSubmitting(false);
    setSuccess(false);
    setMessage(false);
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    let errors = [];
    if (tenTK === "") errors.push("Vui lòng nhập tên tài khoản!");
    if (doiTuong === "") errors.push("Vui lòng chọn đối tượng tài khoản!");
    if (maTruong === "") errors.push("Vui lòng chọn trường (THCS/THPT)!");
    setErrorList(errors);
    if (errors.length > 0) return;
    setSubmitting(true);
    setSuccess(false);
    setMessage("");
    const formData = new FormData();
    formData.append("tenTK", tenTK);
    formData.append("maLoaiTK", doiTuong);
    formData.append("soDinhDanh", maTruong);
    try {
      const res = await adminApi.addAccount(formData);
      setSubmitting(false);
      setSuccess(true);
      setMessage(res.message);
    } catch (error) {
      console.error("Lỗi khi thêm tài khoản:", error);
      setSubmitting(false);
      setSuccess(false);
      setMessage(error.response?.data?.message || error.message);
    }
  };
  const fetchTHCS = async () => {
    try {
      setListTruong([]);
      const res = await commonApi.getTHCS();
      setListTruong(res.data.filter((item) => item.maTHCS !== "0"));
    } catch (error) {
      console.error("Lỗi khi lấy danh sách THCS:", error);
    }
  };
  const fetchTHPT = async () => {
    try {
      setListTruong([]);
      const res = await commonApi.getTHPT();
      setListTruong(res.data);
    } catch (error) {
      console.error("Lỗi khi lấy danh sách THPT:", error);
    }
  };
  useEffect(() => {
    setMaTruong("");
    if (doiTuong == "2") {
      fetchTHCS();
    } else if (doiTuong == "3") {
      fetchTHPT();
    } else {
      setListTruong([]);
    }
  }, [doiTuong]);
  return (
    <>
      <form onSubmit={handleSubmit} className="my-2">
        <div className="mb-3 row">
          <label htmlFor="tenTK" className="col-sm-3 col-form-label fw-bold">
            Tên tài khoản<span className="text-danger">*</span>
          </label>
          <div className="col-sm-9">
            <input
              type="text"
              className="form-control"
              id="tenTK"
              name="tenTK"
              value={tenTK}
              onChange={(e) => setTenTK(e.target.value)}
              required
            />
          </div>
        </div>
        <div className="mb-3 row">
          <label htmlFor="doiTuong" className="col-sm-3 col-form-label fw-bold">
            Đối tượng<span className="text-danger">*</span>
          </label>
          <div className="col-sm-9">
            <select
              className="form-select"
              id="doiTuong"
              name="doiTuong"
              value={doiTuong}
              onChange={(e) => setDoiTuong(e.target.value)}
              required
            >
              <option key="" value="">
                -- Chọn đối tượng --
              </option>
              <option key="2" value="2">
                THCS
              </option>
              <option key="3" value="3">
                THPT
              </option>
            </select>
          </div>
        </div>
        <div className="mb-3 row">
          <label htmlFor="truong" className="col-sm-3 col-form-label fw-bold">
            Chọn trường<span className="text-danger">*</span>
          </label>
          <div className="col-sm-9">
            <select
              className="form-select"
              id="truong"
              name="truong"
              value={maTruong}
              onChange={(e) => setMaTruong(e.target.value)}
              required
              disabled={doiTuong === ""}
            >
              <option key="" value="">
                -- Chọn trường --
              </option>
              {doiTuong === "2" &&
                listTruong
                  .filter((t) => t.maTHCS) // lọc bỏ trường null/undefined
                  .map((t) => (
                    <option
                      key={`THCS_${t.maTHCS}`}
                      value={t.maTHCS}
                      title={t.tenTHCS}
                    >
                      {t.tenTHCS}
                    </option>
                  ))}

              {doiTuong === "3" &&
                listTruong
                  .filter((t) => t.maTHPT) // lọc bỏ trường null/undefined
                  .map((t) => (
                    <option
                      key={`THPT_${t.maTHPT}`}
                      value={t.maTHPT}
                      title={t.tenTHPT}
                    >
                      {t.tenTHPT}
                    </option>
                  ))}
            </select>
          </div>
        </div>
        <div className="mb-3 row">
          <div className="alert alert-primary">
            Lưu ý: Mật khẩu sẽ được tạo tự động khi tài khoản đã được tạo thành
            công, Quản trị viên vui lòng truy cập CSDL hoặc tải danh sách tài
            khoản để xem mật khẩu mặc định!
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
            Thêm tài khoản
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
export default function AdminAddAccount() {
  useEffect(() => {
    document.title = "Thêm tài khoản mới";
  }, []);
  return (
    <div className="container long-page">
      <PageTitle
        title={"THÊM TÀI KHOẢN MỚI"}
        subtitle={"(Tài khoản cán bộ phụ trách THCS, THPT)"}
      />
      <FormDetail />
    </div>
  );
}
