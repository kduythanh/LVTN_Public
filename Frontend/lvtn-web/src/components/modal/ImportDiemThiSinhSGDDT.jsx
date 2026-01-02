import { useState, useRef, useEffect } from "react";
import * as bootstrap from "bootstrap";
import { sgddtApi } from "@/api";
import { useTrangThaiCapNhatDiem } from "@/hooks";
import { Spinner } from "@/components/common";

export default function ImportDiemThiSinhSGDDT({ onImportSuccess }) {
  const [file, setFile] = useState(null);
  const fileInputRef = useRef(null);
  const { loading, reloadTrangThai, duocPhepCapNhatDiem } =
    useTrangThaiCapNhatDiem();

  const [isUploading, setIsUploading] = useState(false);
  const [uploadProgress, setUploadProgress] = useState(0);
  const [isProcessing, setIsProcessing] = useState(false);
  const [message, setMessage] = useState("");
  const [isError, setIsError] = useState(false);
  const [errorList, setErrorList] = useState([]);
  const [shouldReload, setShouldReload] = useState(false);

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
      setMessage("");
      setIsError(false);
      setErrorList([]);
      setIsProcessing(false);
      setIsUploading(false);
      setUploadProgress(0);
      setFile(null);
      if (fileInputRef.current) fileInputRef.current.value = "";
      if (shouldReload && onImportSuccess) {
        await onImportSuccess();
        setShouldReload(false);
      }
    };

    modalEl.addEventListener("hidden.bs.modal", handleHidden);
    return () => modalEl.removeEventListener("hidden.bs.modal", handleHidden);
  }, [shouldReload, onImportSuccess]);

  const openModal = async () => {
    await reloadTrangThai();
    bsModalRef.current?.show();
  };

  const closeModal = () => bsModalRef.current?.hide();

  const handleImport = async () => {
    if (!file) {
      setIsError(true);
      setMessage("Vui lòng lựa chọn file Excel trước khi import!");
      return;
    }
    setMessage("Đang tải file lên...");
    setIsUploading(true);
    setIsError(false);
    setErrorList([]);
    setUploadProgress(0);
    try {
      const res = await sgddtApi.importKetQuaThi(file, (event) => {
        if (event.total) {
          const percent = Math.round((event.loaded * 100) / event.total);
          setUploadProgress(percent);
        }
      });
      await new Promise((resolve) => setTimeout(resolve, 1000));
      setIsUploading(false);
      setIsProcessing(true);
      setMessage("Đang xử lý dữ liệu phía máy chủ...");
      await new Promise((resolve) => setTimeout(resolve, 1000));

      setIsProcessing(false);
      setMessage(res.message);
      setErrorList(res.data.errorList || []);
      if (res.data.successList.length > 0) setShouldReload(true);
    } catch (err) {
      console.error(err);
      const backendMsg = err?.response?.data?.message;
      const defaultMsg = "Có lỗi khi cập nhật điểm cho thí sinh!";
      setMessage(backendMsg || defaultMsg);
    } finally {
      setIsUploading(false);
      setIsProcessing(false);
      setFile(null);
      if (fileInputRef.current) fileInputRef.current.value = "";
    }
  };

  return (
    <>
      <button className="btn btn-warning mx-1 mt-1" onClick={openModal}>
        Import điểm thí sinh
      </button>

      <div
        className="modal fade"
        tabIndex="-1"
        ref={modalRef}
        aria-hidden="true"
      >
        <div
          className="modal-dialog modal-dialog-centered"
          style={{ width: "60%", minWidth: "400px" }}
        >
          <div className="modal-content">
            <div className="modal-header justify-content-center">
              <h5 className="modal-title">Import điểm thí sinh từ Excel</h5>
            </div>

            <div className="modal-body">
              {loading ? (
                <Spinner />
              ) : !duocPhepCapNhatDiem ? (
                <>
                  <div className="alert alert-danger text-center mt-2 mx-auto">
                    Hệ thống hiện không cho phép cập nhật điểm thí sinh! Vui
                    lòng bật chức năng ở mục Quản lý trạng thái để thực hiện
                    chức năng này!
                  </div>
                </>
              ) : (
                <>
                  <div className="alert alert-info">
                    Quý Thầy/Cô vui lòng tải bảng điểm thí sinh về trước (ở nút{" "}
                    <strong>Xuất bảng điểm ra Excel</strong>), đây là mẫu bảng
                    điểm chuẩn dùng để nhập điểm cho thí sinh. Sau đó quý
                    Thầy/Cô cập nhật điểm trên bảng điểm đó và import lại tại
                    đây là có thể cập nhật điểm cho thí sinh thành công.
                  </div>
                  <div className="my-2">
                    <label className="form-label fw-bold">
                      File Excel (.xls, .xlsx)
                      <span className="text-danger">*</span>
                    </label>
                    <input
                      type="file"
                      accept=".xlsx, .xls"
                      ref={fileInputRef}
                      className="form-control"
                      onChange={(e) => setFile(e.target.files[0])}
                      disabled={isUploading || isProcessing}
                    />
                  </div>
                  {/* Progress bar (cho quá trình upload) */}
                  {isUploading && (
                    <div className="text-center my-3">
                      <div className="progress" style={{ height: "25px" }}>
                        <div
                          className="progress-bar bg-success"
                          role="progressbar"
                          style={{ width: `${uploadProgress}%` }}
                        >
                          {uploadProgress}%
                        </div>
                      </div>
                      <p className="mt-2">Đang tải file lên máy chủ...</p>
                    </div>
                  )}

                  {/* Spinner (cho quá trình xử lý phía backend) */}
                  {isProcessing && (
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
                    ((!isUploading && !isProcessing) || isError ? (
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

                  {/* Danh sách lỗi */}
                  {errorList.length > 0 && (
                    <div className="alert alert-danger">
                      <div>
                        <span>
                          {" "}
                          <strong className="text-center">
                            Lỗi ({errorList.length}):{" "}
                          </strong>
                          {/* Nút tải lỗi ra file .txt */}
                          <button
                            className="btn btn-outline-danger btn-sm"
                            onClick={() => {
                              const blob = new Blob([errorList.join("\n")], {
                                type: "text/plain;charset=utf-8",
                              });
                              const url = URL.createObjectURL(blob);
                              const a = document.createElement("a");
                              a.href = url;
                              a.download = "ImportDiemErrors.txt";
                              document.body.appendChild(a);
                              a.click();
                              document.body.removeChild(a);
                              URL.revokeObjectURL(url);
                            }}
                          >
                            <i className="bi bi-file-earmark-text"></i> Tải danh
                            sách lỗi
                          </button>
                        </span>
                      </div>
                      <div
                        style={{
                          maxHeight: "150px",
                          overflowY: "auto",
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
                  )}
                </>
              )}
            </div>

            <div className="modal-footer justify-content-center">
              {!isUploading && !isProcessing && (
                <>
                  <button className="btn btn-secondary" onClick={closeModal}>
                    Đóng
                  </button>
                  {duocPhepCapNhatDiem && (
                    <button className="btn btn-success" onClick={handleImport}>
                      Thực hiện import
                    </button>
                  )}
                </>
              )}
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
