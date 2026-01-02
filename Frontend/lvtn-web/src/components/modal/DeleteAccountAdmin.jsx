import { useEffect, useRef, useState } from "react";
import * as bootstrap from "bootstrap";
import { adminApi } from "@/api";
import { useAccountAdmin } from "@/hooks";
import { DeleteSpinner } from "@/components/common";

export default function DeleteAccountAdmin({ selectedTK, onClose }) {
  const modalRef = useRef(null);
  const bsModalRef = useRef(null);

  const { fetchAccounts, renderLoaiTK } = useAccountAdmin();
  const [isProcessing, setIsProcessing] = useState(false);
  const [message, setMessage] = useState("");
  const [isError, setIsError] = useState(false);
  const [shouldReload, setShouldReload] = useState(false);

  // Khởi tạo modal
  useEffect(() => {
    if (modalRef.current) {
      bsModalRef.current = new bootstrap.Modal(modalRef.current, {
        backdrop: "static",
        keyboard: false,
        focus: true,
      });
    }
  }, []);

  // Xử lý nhiệm vụ sau khi đóng modal
  useEffect(() => {
    const modalEl = modalRef.current;
    if (!modalEl) return;

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
      if (shouldReload) {
        await fetchAccounts();
        setShouldReload(false);
      }
      if (onClose) onClose();
    };

    modalEl.addEventListener("shown.bs.modal", handleShown);
    modalEl.addEventListener("hidden.bs.modal", handleHidden);
    return () => {
      modalEl.removeEventListener("shown.bs.modal", handleShown);
      modalEl.removeEventListener("hidden.bs.modal", handleHidden);
    };
  }, [onClose, shouldReload, fetchAccounts]);

  // Hiện/ẩn modal khi có thay đổi về tài khoản
  useEffect(() => {
    if (selectedTK && bsModalRef.current) openModal();
    else if (!selectedTK && bsModalRef.current) closeModal();
  }, [selectedTK]);

  const openModal = () => bsModalRef.current?.show();

  const closeModal = () => bsModalRef.current?.hide();

  const handleDelete = async () => {
    if (!selectedTK) return;

    setIsProcessing(true);
    setMessage("");
    setIsError(false);

    try {
      await adminApi.deleteAccount(selectedTK.tenTK);
      await new Promise((resolve) => setTimeout(resolve, 1000));
      setMessage(`Xóa tài khoản ${selectedTK.tenTK} thành công!`);
      setShouldReload(true);
    } catch (error) {
      console.error(error);
      setIsError(true);
      setMessage(
        `Lỗi khi xóa tài khoản: ${
          error.response?.data?.message || error.message
        }`
      );
    } finally {
      setIsProcessing(false);
    }
  };

  return (
    <div
      className="modal fade"
      ref={modalRef}
      tabIndex="-1"
      aria-labelledby="DeleteAccountAdminLabel"
      aria-hidden="true"
    >
      <div
        className="modal-dialog modal-dialog-centered"
        style={{ width: "100%", maxWidth: "600px" }}
      >
        <div className="modal-content">
          <div className="modal-header bg-danger text-white justify-content-center">
            <h5 className="modal-title" id="DeleteAccountAdminLabel">
              Xác nhận xóa tài khoản
            </h5>
          </div>

          <div className="modal-body text-center">
            {selectedTK && (
              <div className="text-wrap my-2">
                Tài khoản đã chọn: <strong>{selectedTK.tenTK}</strong> (
                {renderLoaiTK(selectedTK.maLoaiTK)})
              </div>
            )}
            {!message && !isProcessing && (
              <div className="my-2">
                Bạn có chắc chắn muốn xóa tài khoản này không?
              </div>
            )}
            {isProcessing && <DeleteSpinner />}
            {message && (
              <div
                className={`alert ${
                  isError ? "alert-danger" : "alert-success"
                } my-2`}
                role="alert"
              >
                {message}
              </div>
            )}
          </div>

          <div className="modal-footer justify-content-center">
            {!isProcessing && (!message || isError) && (
              <>
                <button className="btn btn-secondary" onClick={closeModal}>
                  Hủy
                </button>
                <button className="btn btn-danger" onClick={handleDelete}>
                  Xóa
                </button>
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
  );
}
