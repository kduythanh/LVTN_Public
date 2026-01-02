import { useEffect } from "react";
import { Link } from "react-router-dom";

export default function HocSinhHomepage() {
  useEffect(() => {
    document.title = "Trang chủ - Học sinh";
  }, []);
  return (
    <div className="full-width-background d-flex">
      <img
        src="/images/background-pano-hocsinh.jpg"
        alt="Background"
        className="position-absolute top-0 start-0 w-100 h-100 z-0 object-fit-cover"
      />
      <div className="overlay position-absolute top-0 start-0 w-100 h-100 z-1"></div>
      <div className="container-fluid position-relative d-flex flex-column justify-content-center my-auto text-light h-100 z-2">
        <div className="display-5 fw-bold text-center content">
          KỲ THI TUYỂN SINH LỚP 10 THPT
          <br />
          NĂM HỌC 2026-2027
        </div>
        <div className="my-3"></div>
        <div className="display-5 fw-bold text-center text-warning content">
          TRANG DÀNH RIÊNG CHO HỌC SINH
        </div>
        <div className="display-6 text-center content">
          Mời bạn vào chức năng "Xem thông tin học sinh" để xem chi tiết
        </div>
        <div className="display-6 text-center content">
          <Link to="/hocsinh/detail" className="fs-4 btn btn-success">
            Xem thông tin học sinh
          </Link>
        </div>
      </div>
    </div>
  );
}
