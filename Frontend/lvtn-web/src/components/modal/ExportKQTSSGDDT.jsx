import { useState, useRef, useEffect } from "react";
import * as bootstrap from "bootstrap";
import { useFileDownload } from "@/hooks";
import { sgddtApi } from "@/api";

export default function ExportDiemThiSinhSGDDT() {
  const { downloadFile } = useFileDownload();
  const [progress, setProgress] = useState(0);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const modalRef = useRef(null);
  const bsModalRef = useRef(null);
  // Khởi tạo Bootstrap modal
  useEffect(() => {
    const handleExport = async () => {
      setLoading(true);
      setProgress(0);
      setMessage("");

      const result = await downloadFile(
        (config) => sgddtApi.exportKetQuaTuyenSinh(config),
        "KetQuaTuyenSinh_SGDDT.xlsx",
        null,
        (percent) => setProgress(percent)
      );

      if (result.success) {
        setMessage("Xuất file thành công!");
        const link = document.createElement("a");
        link.href = result.url;
        link.download = result.filename;
        link.click();
        window.URL.revokeObjectURL(result.url);
      } else {
        setMessage("Xuất file thất bại!");
      }

      setLoading(false);
    };

    if (modalRef.current) {
      bsModalRef.current = new bootstrap.Modal(modalRef.current, {
        backdrop: "static", // không tắt modal khi click ngoài
        keyboard: false, // không tắt bằng ESC
        focus: true,
      });
    }
    const modalEl = modalRef.current;

    const handleHidden = () => {
      setProgress(0);
      setMessage("");
      setLoading(false);
    };

    modalEl.addEventListener("shown.bs.modal", handleExport);
    modalEl.addEventListener("hidden.bs.modal", handleHidden);
    return () => {
      modalEl.removeEventListener("shown.bs.modal", handleExport);
      modalEl.removeEventListener("hidden.bs.modal", handleHidden);
    };
  }, [downloadFile]);

  const openModal = () => {
    setLoading(true);
    bsModalRef.current?.show();
  };

  const closeModal = () => bsModalRef.current?.hide();

  return (
    <>
      <button
        className="btn btn-success mx-1 mt-1"
        onClick={openModal}
        disabled={loading}
      >
        Xuất bảng kết quả tuyển sinh
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
              {loading ? "Đang xuất dữ liệu..." : "Kết quả xuất dữ liệu"}
            </h5>
            <div className="progress mb-3" style={{ height: "25px" }}>
              <div
                className="progress-bar bg-success"
                role="progressbar"
                style={{ width: `${progress}%` }}
              >
                {progress}%
              </div>
            </div>

            {message && (
              <div className="text-success fw-bold mt-2">{message}</div>
            )}
            {!loading && (
              <button className="btn btn-secondary mt-3" onClick={closeModal}>
                Đóng
              </button>
            )}
          </div>
        </div>
      </div>
    </>
  );
}
