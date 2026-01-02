import { useState, useRef, useEffect } from "react";
import * as bootstrap from "bootstrap";
import { sgddtApi } from "@/api";
import { useTrangThaiCapSBD } from "@/hooks";
import { Spinner } from "@/components/common";

export default function UpdateSBDThiSinhSGDDT({ onUpdated }) {
  const [isUpdating, setIsUpdating] = useState(false);
  const [isError, setIsError] = useState(false);
  const [message, setMessage] = useState("");
  const [shouldReload, setShouldReload] = useState(false);
  const shouldReloadRef = useRef(false);
  const modalRef = useRef(null);
  const bsModalRef = useRef(null);
  const { loading, reloadTrangThai, duocPhepCapSBD } = useTrangThaiCapSBD();
  useEffect(() => {
    shouldReloadRef.current = shouldReload;
  }, [shouldReload]);
  // Khởi tạo Bootstrap modal
  useEffect(() => {
    if (!modalRef.current) return;
    if (modalRef.current) {
      bsModalRef.current = new bootstrap.Modal(modalRef.current, {
        backdrop: "static", // không tắt modal khi click ngoài
        keyboard: false, // không tắt bằng ESC
        focus: true,
      });
    }
    const modalEl = modalRef.current;

    const handleHidden = async () => {
      setMessage("");
      setIsUpdating(false);
      if (onUpdated && shouldReloadRef.current) {
        await onUpdated();
        shouldReloadRef.current = false;
        setShouldReload(false);
      }
    };

    modalEl.addEventListener("hidden.bs.modal", handleHidden);
    return () => {
      modalEl.removeEventListener("hidden.bs.modal", handleHidden);
    };
  }, [onUpdated]);

  const handleUpdate = async () => {
    setIsError(false);
    setShouldReload(false);
    setIsUpdating(true);
    setMessage("Đang cập nhật số báo danh và phòng thi...");

    try {
      await sgddtApi.updateSBDAndPhongThi();
      await new Promise((resolve) => setTimeout(resolve, 500));
      setMessage("Cập nhật số báo danh và phòng thi thành công!");
      setShouldReload(true);
    } catch (error) {
      console.error(error);
      setIsError(true);
      const backendMsg = error?.response?.data?.message;
      const defaultMsg =
        "Có lỗi khi cập nhật số báo danh và phòng thi cho thí sinh!";
      setMessage(backendMsg || defaultMsg);
    } finally {
      setIsUpdating(false);
    }
  };

  const openModal = async () => {
    setIsUpdating(true);
    await reloadTrangThai();
    bsModalRef.current?.show();
    handleUpdate();
  };

  const closeModal = () => bsModalRef.current?.hide();

  return (
    <>
      <button
        className="btn btn-primary mx-1 mt-1"
        onClick={openModal}
        disabled={isUpdating}
      >
        Cập nhật SBD, phòng thi thí sinh
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
          <div className="modal-content text-center p-4">
            <h5 className="mb-3">
              Cập nhật số báo danh và phòng thi cho thí sinh
            </h5>
            {loading ? (
              <Spinner />
            ) : !duocPhepCapSBD ? (
              <>
                <div className="alert alert-danger text-center mt-2 mx-auto">
                  {message}
                </div>
                <button className="btn btn-secondary mt-2" onClick={closeModal}>
                  Đóng
                </button>
              </>
            ) : (
              <>
                {isUpdating ? (
                  <div className="text-center my-3">
                    <div
                      className="spinner-border text-success"
                      role="status"
                      style={{ width: "3rem", height: "3rem" }}
                    ></div>
                    <p className="mt-2">{message}</p>
                    <div className="fst-italic">
                      Quá trình này có thể mất một khoảng thời gian.
                    </div>
                  </div>
                ) : (
                  <>
                    <div
                      className={`alert my-2 ${
                        isError ? "alert-danger" : "alert-success"
                      }`}
                    >
                      {message}
                    </div>
                    {!isError && (
                      <div className="alert alert-info mt-2">
                        Sau khi cập nhật xong, nếu không còn nhu cầu cập nhật
                        nữa, cần tắt ngay trạng thái{" "}
                        <strong>
                          Cho phép cập nhật số báo danh, phòng thi
                        </strong>{" "}
                        để tránh mất dữ liệu khi nhập điểm sau này.
                      </div>
                    )}
                    <button
                      className="btn btn-secondary mt-2"
                      onClick={closeModal}
                    >
                      {isError ? "Đóng" : "Đóng và tải lại"}
                    </button>
                  </>
                )}
              </>
            )}
          </div>
        </div>
      </div>
    </>
  );
}
