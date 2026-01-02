import { useEffect, useRef, useState } from "react";
import * as bootstrap from "bootstrap";
import { thcsApi } from "@/api";
import { useHocSinhTHCS, useTrangThaiCapNhatHoSo } from "@/hooks";
import { DeleteSpinner, Spinner } from "@/components/common";

export default function DeleteHocSinhTHCS({ selectedHS, onClose }) {
  const modalRef = useRef(null);
  const bsModalRef = useRef(null);

  const { soDinhDanh, fetchHocSinh } = useHocSinhTHCS();
  const { duocPhepCapNhat, loading, reloadTrangThai } =
    useTrangThaiCapNhatHoSo();

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
        await fetchHocSinh();
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
  }, [onClose, shouldReload, fetchHocSinh]);

  // Hiện/ẩn modal khi có thay đổi về học sinh
  useEffect(() => {
    const openModal = async () => {
      await reloadTrangThai();
      bsModalRef.current?.show();
    };
    if (selectedHS && bsModalRef.current) openModal();
    else if (!selectedHS && bsModalRef.current) closeModal();
  }, [selectedHS, reloadTrangThai]);

  const closeModal = () => bsModalRef.current?.hide();

  const handleDelete = async () => {
    if (!selectedHS) return;

    setIsProcessing(true);
    setMessage("");
    setIsError(false);

    try {
      await thcsApi.deleteHocSinh(soDinhDanh, selectedHS.thongTinHS.maHS);
      await new Promise((resolve) => setTimeout(resolve, 1000));
      setMessage(
        `Xóa học sinh ${selectedHS.thongTinHS.hoVaChuLotHS} ${selectedHS.thongTinHS.tenHS} (${selectedHS.thongTinHS.maHS}) thành công!`
      );
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
    <div
      className="modal fade"
      ref={modalRef}
      tabIndex="-1"
      aria-labelledby="DeleteHocSinhTHCSLabel"
      aria-hidden="true"
    >
      <div
        className="modal-dialog modal-dialog-centered"
        style={{ width: "50vw", minWidth: "600px" }}
      >
        <div className="modal-content">
          <div className="modal-header bg-danger text-white justify-content-center">
            <h5 className="modal-title" id="DeleteHocSinhTHCSLabel">
              Xác nhận xóa học sinh
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
                {selectedHS && (
                  <div className="text-wrap my-2">
                    Học sinh đã chọn:{" "}
                    <strong>
                      {selectedHS.thongTinHS.hoVaChuLotHS}{" "}
                      {selectedHS.thongTinHS.tenHS}
                    </strong>{" "}
                    ({selectedHS.thongTinHS.maHS})
                  </div>
                )}
                {!message && !isProcessing && (
                  <div className="my-2">
                    Bạn có chắc chắn muốn xóa học sinh này không?
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
  );
}
