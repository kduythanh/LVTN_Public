import { useState, useRef, useEffect } from "react";
import * as bootstrap from "bootstrap";
import { adminApi } from "@/api";

export default function ImportAccountAdmin() {
  const [tenTK, setTenTK] = useState("");
  const [isUpdating, setIsUpdating] = useState(false);
  const [message, setMessage] = useState("");
  const [isError, setIsError] = useState(false);

  const modalRef = useRef(null);
  const bsModalRef = useRef(null);

  // Khởi tạo Bootstrap modal
  useEffect(() => {
    if (modalRef.current) {
      bsModalRef.current = new bootstrap.Modal(modalRef.current, {
        backdrop: "static", // không tắt modal khi click ngoài
        keyboard: false, // không tắt bằng ESC
        focus: true,
      });
    }
  }, []);

  useEffect(() => {
    const modalEl = modalRef.current;
    if (!modalEl) return;

    const handleHidden = async () => {
      setTenTK("");
      setMessage("");
      setIsError(false);
      setIsUpdating(false);
    };

    modalEl.addEventListener("hidden.bs.modal", handleHidden);
    return () => modalEl.removeEventListener("hidden.bs.modal", handleHidden);
  }, []);

  const openModal = () => bsModalRef.current?.show();

  const closeModal = () => bsModalRef.current?.hide();

  const handleImport = async () => {
    if (tenTK == "" || tenTK == null) {
      setIsError(true);
      setMessage("Vui lòng nhập tên tài khoản cần đặt lại mật khẩu!");
      return;
    }
    setIsError(false);
    setIsUpdating(true);
    setMessage("Đang xử lý...");
    try {
      const res = await adminApi.resetPassword(tenTK);
      await new Promise((resolve) => setTimeout(resolve, 1000));
      setIsUpdating(false);
      setMessage(res.message);
    } catch (err) {
      setIsError(true);
      console.error(err);
      setIsUpdating(false);
      setMessage(
        "Đặt lại mật khẩu không thành công: " +
          (err.response?.data?.message || err.message)
      );
    }
  };

  return (
    <>
      <button className="btn btn-warning mx-1 mt-1" onClick={openModal}>
        Cấp lại mật khẩu cho tài khoản
      </button>

      <div
        className="modal fade"
        tabIndex="-1"
        ref={modalRef}
        aria-hidden="true"
      >
        <div
          className="modal-dialog modal-dialog-centered"
          style={{ width: "50vw", minWidth: "300px" }}
        >
          <div className="modal-content">
            <div className="modal-header justify-content-center">
              <h5 className="modal-title">Cấp lại mật khẩu cho tài khoản</h5>
            </div>

            <div className="modal-body">
              <div className="alert alert-warning">
                Đây là thao tác NGUY HIỂM, ảnh hưởng đến truy cập của tài khoản!
                Vui lòng chỉ thực hiện khi thật sự có yêu cầu từ chủ tài khoản.
              </div>
              <div className="my-2">
                <label className="form-label fw-bold">
                  Tên tài khoản<span className="text-danger">*</span>
                </label>
                <input
                  type="text"
                  className="form-control focus-ring focus-ring-success"
                  value={tenTK}
                  onChange={(e) => setTenTK(e.target.value)}
                  disabled={isUpdating}
                />
              </div>
              {/* Spinner */}
              {isUpdating && (
                <div className="text-center my-3">
                  <div
                    className="spinner-border text-success"
                    role="status"
                    style={{ width: "3rem", height: "3rem" }}
                  ></div>
                  <p className="mt-2">{message}</p>
                </div>
              )}

              {message && !isUpdating && (
                <div
                  className={`alert my-2 ${
                    isError ? "alert-danger" : "alert-info"
                  }`}
                >
                  {message}
                </div>
              )}
            </div>

            <div className="modal-footer justify-content-center">
              {!isUpdating && (
                <>
                  <button
                    className="btn btn-secondary"
                    onClick={closeModal}
                    style={{ width: "100px" }}
                  >
                    Đóng
                  </button>
                  <button className="btn btn-warning" onClick={handleImport}>
                    Cấp lại mật khẩu
                  </button>
                </>
              )}
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
