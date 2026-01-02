import { useState, useRef, useEffect } from "react";
import * as bootstrap from "bootstrap";
import { thptApi } from "@/api";
import { useAuth, useTrangThaiCapNhatHoSo } from "@/hooks";
import { Spinner } from "../common";

export default function ImportHocSinhTHPT({ onImportSuccess }) {
  const { user } = useAuth();
  const { duocPhepCapNhat, loading, reloadTrangThai } =
    useTrangThaiCapNhatHoSo();
  const [excelFile, setExcelFile] = useState(null);
  const [zipFile, setZipFile] = useState(null);
  const excelInputRef = useRef(null);
  const zipInputRef = useRef(null);

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
      setExcelFile(null);
      setZipFile(null);
      if (excelInputRef.current) excelInputRef.current.value = "";
      if (zipInputRef.current) zipInputRef.current.value = "";
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
    if (!excelFile) {
      setIsError(true);
      setMessage("Vui lòng lựa chọn file Excel trước khi import!");
      return;
    }
    setIsUploading(true);
    setMessage("Đang tải file lên...");
    setIsError(false);
    setErrorList([]);
    setUploadProgress(0);
    try {
      const res = await thptApi.importHocSinh(
        user.soDinhDanh,
        excelFile,
        zipFile,
        (event) => {
          if (event.total) {
            const percent = Math.round((event.loaded * 100) / event.total);
            setUploadProgress(percent);
          }
        }
      );
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
      setIsError(true);
      setMessage(
        "Có lỗi xảy ra khi tải lên: " +
          (err.response?.data?.message || err.message)
      );
    } finally {
      setIsUploading(false);
      setIsProcessing(false);
      setExcelFile(null);
      setZipFile(null);
      if (excelInputRef.current) excelInputRef.current.value = "";
      if (zipInputRef.current) zipInputRef.current.value = "";
    }
  };

  return (
    <>
      <button className="btn btn-warning mx-1 mt-1" onClick={openModal}>
        Import hồ sơ
      </button>

      <div
        className="modal fade"
        tabIndex="-1"
        ref={modalRef}
        aria-hidden="true"
      >
        <div className="modal-dialog modal-dialog-centered modal-dialog-scrollable">
          <div className="modal-content">
            <div className="modal-header justify-content-center">
              <h5 className="modal-title">Import học sinh từ Excel</h5>
            </div>

            <div className="modal-body">
              {loading ? (
                <Spinner />
              ) : !duocPhepCapNhat ? (
                <div className="alert alert-danger text-center mt-2 mx-auto">
                  Bạn không được phép thêm hồ sơ học sinh do đã hết hạn đăng ký
                  hoặc hệ thống không cho phép!
                </div>
              ) : (
                <>
                  <div className="alert alert-info m-0">
                    Quý Thầy/Cô vui lòng tải mẫu Excel bên dưới, sau đó cập nhật
                    thông tin trên file và import lại tại đây là có thể thêm,
                    cập nhật hồ sơ học sinh thành công.
                  </div>
                  <a
                    href="/templates/ImportHocSinh_Template.xlsx"
                    className="btn btn-primary text-white mx-auto my-2"
                    download
                  >
                    Tải file mẫu tại đây
                  </a>
                  <div className="mb-3">
                    <label className="form-label fw-bold">
                      File Excel (.xls, .xlsx)
                      <span className="text-danger">*</span>
                    </label>
                    <input
                      type="file"
                      accept=".xls, .xlsx"
                      ref={excelInputRef}
                      className="form-control"
                      onChange={(e) => setExcelFile(e.target.files[0])}
                      disabled={isUploading || isProcessing}
                    />
                  </div>

                  <div className="mb-3">
                    <label className="form-label fw-bold">
                      File ZIP ảnh học sinh (.zip)
                    </label>
                    <div className="text-start small text-muted">
                      <span className="text-danger fw-bold">Lưu ý:</span>
                      <br />
                      - File nén hình ảnh CHỈ CHẤP NHẬN định dạng .zip.
                      <br />
                      - Các hình ảnh trong file ZIP phải có tên TRÙNG KHỚP với
                      mã học sinh.
                      <br />
                      - Chỉ chấp nhận hình ảnh định dạng .jpg, .png hoặc .jpeg.
                      <br />
                      Ví dụ: Mã số HS là 001 thì hình ảnh tương ứng là 001.jpg,
                      001.png hoặc 001.jpeg.
                    </div>
                    <input
                      type="file"
                      accept=".zip"
                      ref={zipInputRef}
                      className="form-control"
                      onChange={(e) => setZipFile(e.target.files[0])}
                      disabled={isUploading || isProcessing}
                    />
                  </div>

                  {/* Progress bar (cho quá trình upload) */}
                  {isUploading && (
                    <div className="text-center my-2">
                      <div className="progress" style={{ height: "25px" }}>
                        <div
                          className="progress-bar bg-success"
                          role="progressbar"
                          style={{ width: `${uploadProgress}%` }}
                        >
                          {uploadProgress}%
                        </div>
                      </div>
                      <p className="my-2">Đang tải file lên máy chủ...</p>
                    </div>
                  )}

                  {/* Spinner (cho quá trình xử lý phía backend) */}
                  {isProcessing && (
                    <div className="text-center my-2">
                      <div
                        className="spinner-border text-success"
                        role="status"
                        style={{ width: "3rem", height: "3rem" }}
                      ></div>
                      <p className="my-2">{message}</p>
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
                    ) : null)}

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
                              a.download = "ImportHocSinhErrors.txt";
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
                  <button
                    className="btn btn-secondary"
                    onClick={closeModal}
                    style={{ width: "100px" }}
                  >
                    Đóng
                  </button>
                  {duocPhepCapNhat && (
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
