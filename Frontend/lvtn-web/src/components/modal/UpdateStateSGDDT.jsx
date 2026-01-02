import { useState, useRef, useEffect } from "react";
import * as bootstrap from "bootstrap";
import { sgddtApi } from "@/api";
import { useStateSGDDT } from "@/hooks";

export default function UpdateStateSGDDT({ selectedTT, onClose }) {
  const [newVal, setNewVal] = useState("");

  const { fetchTrangThai } = useStateSGDDT();

  const [isUpdating, setIsUpdating] = useState(false);
  const [message, setMessage] = useState("");
  const [isError, setIsError] = useState(false);
  const [shouldReload, setShouldReload] = useState(false);
  const shouldReloadRef = useRef(false);

  const modalRef = useRef(null);
  const bsModalRef = useRef(null);

  useEffect(() => {
    shouldReloadRef.current = shouldReload;
  }, [shouldReload]);

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

    const handleShown = () => {
      setMessage("");
      setIsError(false);
      setIsUpdating(false);
      setShouldReload(false);
    };

    const handleHidden = async () => {
      setMessage("");
      setIsError(false);
      setIsUpdating(false);
      setNewVal("");
      if (shouldReloadRef.current) {
        await fetchTrangThai();
        shouldReloadRef.current = false;
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
  }, [fetchTrangThai, selectedTT, onClose]);

  useEffect(() => {
    const openModal = () => {
      setNewVal(selectedTT?.oldVal ?? "");
      bsModalRef.current?.show();
    };
    if (selectedTT && bsModalRef.current) openModal();
    else if (!selectedTT && bsModalRef.current) closeModal();
  }, [selectedTT]);

  const closeModal = () => {
    // bỏ focus hiện tại trước khi ẩn modal
    if (document.activeElement) document.activeElement.blur();
    bsModalRef.current?.hide();
  };

  const handleUpdate = async () => {
    if (newVal === "" || newVal === null || newVal === undefined) {
      setIsError(true);
      setMessage("Vui lòng nhập giá trị hợp lệ trước khi cập nhật!");
      return;
    }
    let normalizedVal = newVal;
    switch (selectedTT.kieuDuLieu) {
      case "BOOLEAN":
        normalizedVal = Boolean(newVal);
        break;

      case "TIMESTAMP":
        normalizedVal = new Date(newVal).toISOString();
        break;

      case "STRING":
        normalizedVal = String(newVal);
        break;
    }
    setIsUpdating(true);
    setIsError(false);
    setMessage("Đang cập nhật...");
    try {
      const res = await sgddtApi.updateTrangThai(
        selectedTT.maTT,
        selectedTT.kieuDuLieu,
        normalizedVal
      );
      await new Promise((resolve) => setTimeout(resolve, 500));
      setIsUpdating(false);
      setMessage(res.message || "Cập nhật thành công!");
      setShouldReload(true);
    } catch (err) {
      console.error(err);
      setIsUpdating(false);
      setIsError(true);
      setMessage(
        "Có lỗi khi cập nhật: " + (err.response?.data?.message || err.message)
      );
    }
  };

  return (
    <>
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
              <h5 className="modal-title">Cập nhật trạng thái kỳ thi</h5>
            </div>

            <div className="modal-body">
              {selectedTT && (
                <>
                  <div className="fw-bold text-center my-2">
                    {selectedTT.tenTrangThai}
                  </div>
                  {selectedTT.kieuDuLieu === "BOOLEAN" && (
                    <select
                      className="form-select"
                      value={newVal ? "true" : "false"}
                      onChange={(e) => setNewVal(e.target.value === "true")}
                    >
                      <option value={true}>Cho phép</option>
                      <option value={false}>Không cho phép</option>
                    </select>
                  )}

                  {selectedTT.kieuDuLieu === "TIMESTAMP" && (
                    <input
                      type="datetime-local"
                      className="form-control"
                      value={
                        Array.isArray(newVal)
                          ? new Date(
                              newVal[0],
                              newVal[1] - 1,
                              newVal[2],
                              newVal[3] ?? 0,
                              newVal[4] ?? 0,
                              newVal[5] ?? 0
                            )
                              .toLocaleString("sv-SE", {
                                timeZone: "Asia/Ho_Chi_Minh",
                              })
                              .slice(0, 16)
                          : newVal
                            ? new Date(newVal)
                                .toLocaleString("sv-SE", {
                                  timeZone: "Asia/Ho_Chi_Minh",
                                })
                                .slice(0, 16)
                            : ""
                      }
                      onChange={(e) => setNewVal(e.target.value)}
                    />
                  )}

                  {selectedTT.kieuDuLieu === "STRING" && (
                    <input
                      type="text"
                      className="form-control"
                      defaultValue={newVal || ""}
                      onChange={(e) => setNewVal(e.target.value)}
                    />
                  )}

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

                  {message &&
                    (!isUpdating || isError ? (
                      <div
                        className={`alert my-2 ${
                          isError ? "alert-danger" : "alert-info"
                        }`}
                      >
                        {message}
                      </div>
                    ) : (
                      ""
                    ))}
                </>
              )}
            </div>

            <div className="modal-footer justify-content-center">
              {!isUpdating && (!message || isError) && (
                <>
                  <button
                    className="btn btn-secondary"
                    onClick={closeModal}
                    style={{ width: "100px" }}
                  >
                    Đóng
                  </button>
                  <button className="btn btn-warning" onClick={handleUpdate}>
                    Cập nhật
                  </button>
                </>
              )}
              {!isUpdating && message && !isError && (
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
