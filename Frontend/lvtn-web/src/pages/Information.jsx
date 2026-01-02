import { useEffect, useState } from "react";
import { PageTitle } from "@/components";

export function VanBanCoLienQuan() {
  const [selectedSrc, setSelectedSrc] = useState(null);

  const documents = [
    {
      src: "/files/QDUB_40_0001.pdf",
      content:
        "Quyết định của UBND TP Cần Thơ phê duyệt Kế hoạch tuyển sinh vào lớp 10",
    },
    {
      src: "/files/HuongDanTuyenSinh.pdf",
      content: "Hướng dẫn tuyển sinh của Sở Giáo dục và Đào tạo TP Cần Thơ",
    },
    {
      src: "/files/QuyDinhTuyenSinh10_2024.pdf",
      content: "Quy định thi tuyển sinh vào lớp 10 THPT",
    },
    {
      src: "/files/ChiTieuTuyenSinh2024.pdf",
      content: "Chỉ tiêu tuyển sinh dự kiến của các trường THPT",
    },
    {
      src: "/files/PhieuDangKy2024.pdf",
      content: "Phiếu đăng ký tuyển sinh vào lớp 10",
    },
    {
      src: "/files/DonPhucKhao.pdf",
      content: "Đơn xin phúc khảo bài thi",
    },
  ];

  return (
    <>
      <div className="fs-5 mb-2">
        Bấm vào các văn bản dưới đây để xem chi tiết:
      </div>
      <div className="list-group border-1">
        {documents.map((doc, index) => (
          <button
            key={index}
            className={`list-group-item list-group-item-action ${
              selectedSrc === doc.src ? "bg-success text-white" : ""
            }`}
            onClick={() =>
              setSelectedSrc(selectedSrc === doc.src ? null : doc.src)
            }
          >
            {doc.content}
          </button>
        ))}
      </div>
      {/* Hiển thị phần xem PDF nếu có văn bản được chọn */}
      {selectedSrc && (
        <div className="pdf-preview my-2">
          <div className="d-flex fs-5 my-2 fw-bold">
            <div className="pe-2">Chi tiết văn bản:</div>
            <div className="d-flex align-items-center">
              <a
                href={selectedSrc}
                target="_blank"
                rel="noopener noreferrer"
                className="btn btn-outline-success btn-sm my-auto"
              >
                <i className="bi bi-box-arrow-up-right me-1"></i>
                Mở văn bản
              </a>
            </div>
          </div>
          <div className="alert alert-info">
            Nếu không thể thấy văn bản trên điện thoại, hãy bấm vào nút "Mở văn
            bản" phía trên để xem chi tiết.
          </div>
          <iframe
            src={selectedSrc}
            width="100%"
            style={{ minHeight: "75vh" }}
          ></iframe>
        </div>
      )}
    </>
  );
}

export default function Information() {
  useEffect(() => {
    document.title = "Các văn bản có liên quan";
  }, []);
  return (
    <div className="container-fluid container-md long-page">
      <PageTitle
        title={"CÁC VĂN BẢN CÓ LIÊN QUAN ĐẾN KỲ THI"}
        info={"(sẽ tiếp tục cập nhật theo thông báo của sở GD&ĐT)"}
      />
      <VanBanCoLienQuan />
    </div>
  );
}
