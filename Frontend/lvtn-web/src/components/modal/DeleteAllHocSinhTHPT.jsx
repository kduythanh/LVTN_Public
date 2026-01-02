import { useState, useRef, useEffect } from "react";
import * as bootstrap from "bootstrap";
import { thptApi } from "@/api";
import { DeleteSpinner, Spinner } from "@/components/common";
import { useTrangThaiCapNhatHoSo } from "@/hooks";

export default function DeleteAllHocSinhTHPT({
  soDinhDanh,
  tenDinhDanh,
  onDeleted,
}) {
  const modalRef = useRef(null);
  const bsModalRef = useRef(null);
  const { duocPhepCapNhat, loading, reloadTrangThai } =
    useTrangThaiCapNhatHoSo();

  const [isProcessing, setIsProcessing] = useState(false);
  const [message, setMessage] = useState("");
  const [isError, setIsError] = useState(false);
  const [shouldReload, setShouldReload] = useState(false);
  const shouldReloadRef = useRef(false);

  useEffect(() => {
    shouldReloadRef.current = shouldReload;
  }, [shouldReload]);

  useEffect(() => {
    if (modalRef.current) {
      bsModalRef.current = new bootstrap.Modal(modalRef.current, {
        backdrop: "static", // không tắt modal khi click ngoài
        keyboard: false, // không tắt bằng ESC
        focus: true,
      });
    }
    const modalEl = modalRef.current;
    const handleShown = () => {
      setMessage("");
      setIsError(false);
      setIsProcessing(false);
      setShouldReload(false);
    };
    const handleHidden = async () => {
      setMessage("");
      setIsError(false);
      setIsProcessing(false);
      if (shouldReloadRef.current) {
        if (onDeleted) await onDeleted();
        shouldReloadRef.current = false;
        setShouldReload(false);
      }
    };
    modalEl.addEventListener("shown.bs.modal", handleShown);
    modalEl.addEventListener("hidden.bs.modal", handleHidden);
    return () => {
      modalEl.removeEventListener("shown.bs.modal", handleShown);
      modalEl.removeEventListener("hidden.bs.modal", handleHidden);
    };
  }, [onDeleted]);

  const openModal = async () => {
    await reloadTrangThai();
    bsModalRef.current?.show();
  };

  const closeModal = () => bsModalRef.current?.hide();

  const handleDelete = async () => {
    setIsProcessing(true);
    setMessage("");
    setIsError(false);
    try {
      await thptApi.deleteAllHocSinhNgoaiTinh(soDinhDanh);
      await new Promise((resolve) => setTimeout(resolve, 1000));
      setMessage(`Xóa toàn bộ học sinh của trường ${tenDinhDanh} thành công!`);
      setShouldReload(true);
    } catch (error) {
      console.error(error);
      setIsError(true);
      setMessage(
        `Lỗi khi xóa học sinh: ${
          error.response?.data?.message || error.message
        }`
      );
    } finally {
      setIsProcessing(false);
    }
  };

  return (
    <>
      <button className="btn btn-danger mx-1 mt-1" onClick={openModal}>
        Xóa toàn bộ hồ sơ
      </button>

      <div
        className="modal fade"
        tabIndex="-1"
        ref={modalRef}
        aria-hidden="true"
      >
        <div
          className="modal-dialog modal-dialog-centered"
          style={{ width: "50vw", minWidth: "600px" }}
        >
          <div className="modal-content">
            <div className="modal-header bg-danger text-white justify-content-center">
              <h5 className="modal-title">
                Xóa toàn bộ học sinh (ngoài TP Cần Thơ) của trường
              </h5>
            </div>

            <div className="modal-body text-center">
              {loading ? (
                <Spinner />
              ) : !duocPhepCapNhat ? (
                <div className="alert alert-danger text-center mt-2 mx-auto">
                  Bạn không được phép xóa hồ sơ học sinh do đã hết hạn đăng ký
                  hoặc hệ thống không cho phép!
                </div>
              ) : (
                <>
                  {!message && (
                    <>
                      <div className="text-wrap">
                        Bạn có chắc chắn muốn xóa tất cả học sinh (ngoài TP Cần
                        Thơ) của trường <strong>{tenDinhDanh}</strong> không?
                      </div>
                    </>
                  )}
                  {isProcessing && <DeleteSpinner />}
                  {message && (
                    <div
                      className={`alert ${
                        isError ? "alert-danger" : "alert-success"
                      } mt-3`}
                      role="alert"
                    >
                      {message}
                    </div>
                  )}
                </>
              )}
            </div>

            <div className="modal-footer justify-content-center">
              {!isProcessing && (!message || isError) && (
                <>
                  <button className="btn btn-secondary" onClick={closeModal}>
                    {duocPhepCapNhat ? "Hủy" : "Đóng"}
                  </button>
                  {duocPhepCapNhat && (
                    <button className="btn btn-danger" onClick={handleDelete}>
                      Xóa
                    </button>
                  )}
                </>
              )}
              {!isProcessing && message && !isError && (
                <button className="btn btn-success" onClick={closeModal}>
                  Đóng và tải lại dữ liệu
                </button>
              )}
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
