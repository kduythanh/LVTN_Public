import { useEffect } from "react";

export default function HDTSHomepage() {
  useEffect(() => {
    document.title = "Trang chủ - SGD&ĐT";
  }, []);
  return (
    <div className="full-width-background d-flex">
      <img
        src="/images/sgddt-pano.jpg"
        alt="Background"
        className="position-absolute top-0 start-0 w-100 h-100 z-0 object-fit-cover"
      />
      <div className="overlay position-absolute top-0 start-0 w-100 h-100 z-1"></div>
      <div className="container-fluid position-relative d-flex flex-column justify-content-center my-auto text-light z-2 h-100">
        <div className="display-5 fw-bold text-center content">
          TRANG DÀNH RIÊNG CHO CÁN BỘ HỘI ĐỒNG TUYỂN SINH
        </div>
        <div className="display-5 fw-bold text-center content">
          SỞ GIÁO DỤC VÀ ĐÀO TẠO TP CẦN THƠ
        </div>
      </div>
    </div>
  );
}
