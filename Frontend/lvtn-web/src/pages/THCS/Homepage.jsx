import { useEffect } from "react";

export default function THCSHomepage() {
  useEffect(() => {
    document.title = "Trang chủ - THCS";
  }, []);
  return (
    <div className="full-width-background d-flex">
      <img
        src="/images/background-pano-thcs.jpg"
        alt="Background"
        className="position-absolute top-0 start-0 w-100 h-100 z-0 object-fit-cover"
      />
      <div className="overlay position-absolute top-0 start-0 w-100 h-100 z-1"></div>
      <div className="container-fluid position-relative d-flex flex-column justify-content-center my-auto text-light z-2 h-100">
        <div className="display-5 fw-bold text-center content">
          TRANG DÀNH RIÊNG CHO CÁN BỘ PHỤ TRÁCH TRƯỜNG THCS
        </div>
      </div>
    </div>
  );
}
