import { useEffect } from "react";
import { Navigate } from "react-router-dom";
import { useAuth } from "@/hooks";
import { PageTitle, Spinner } from "@/components";

export function FullWidthBackgroundHomepage() {
  return (
    <div className="full-width-background d-flex">
      <img
        src="/images/background-pano.jpg"
        alt="Background"
        className="background-image"
      />
      <div className="overlay"></div>
      <div className="container-fluid position-relative d-flex flex-column justify-content-center my-auto text-light z-2 h-100">
        <div className="display-5 fw-bold text-center content">
          KỲ THI TUYỂN SINH LỚP 10 THPT
          <br />
          NĂM HỌC 2026-2027
        </div>
        <div className="display-6 text-center content">
          Dự kiến tổ chức đầu tháng 6/2026
        </div>
        {/* <div className="display-1 fw-bold text-center content mt-5">
          Còn <span id="days" className="text-warning"></span> ngày
        </div> */}
      </div>
    </div>
  );
}

export function CarouselItem({
  title,
  image,
  footer,
  bgcolor,
  textColor = "",
}) {
  return (
    <div className={`card ${bgcolor} d-flex flex-column h-100 w-100`}>
      <div className={`card-header text-center fw-bold fs-5 ${textColor}`}>
        {title}
      </div>
      <div
        className="card-body p-0 position-relative"
        style={{ height: "300px" }}
      >
        <img
          src={image}
          alt="image"
          className="position-absolute top-0 start-0 w-100 h-100 object-fit-cover"
        />
      </div>
      <div className={`card-footer text-center fw-bold ${textColor}`}>
        {footer}
      </div>
    </div>
  );
}

export function MocThoiGian() {
  return (
    <div className="container-fluid">
      <PageTitle title={"CÁC MỐC THỜI GIAN QUAN TRỌNG"} />
      <div className="row">
        <div className="col-12 col-md-6 col-lg-3 my-2 d-flex justify-content-center">
          <CarouselItem
            title="Tháng 4/2026"
            image="/images/process-1.jpg"
            footer="Nhận hồ sơ đăng ký"
            bgcolor="bg-primary"
            textColor="text-white"
          />
        </div>
        <div className="col-12 col-md-6 col-lg-3 my-2 d-flex justify-content-center">
          <CarouselItem
            title="Tháng 5/2026"
            image="/images/process-2.jpg"
            footer="Điều chỉnh nguyện vọng"
            bgcolor="bg-warning"
          />
        </div>
        <div className="col-12 col-md-6 col-lg-3 my-2 d-flex justify-content-center">
          <CarouselItem
            title="Đầu tháng 6/2026"
            image="/images/process-3.jpg"
            footer="Thi tuyển"
            bgcolor="bg-danger"
            textColor="text-white"
          />
        </div>
        <div className="col-12 col-md-6 col-lg-3 my-2 d-flex justify-content-center">
          <CarouselItem
            title="Giữa tháng 6/2026"
            image="/images/process-4.jpg"
            footer="Công bố kết quả"
            bgcolor="bg-success"
            textColor="text-white"
          />
        </div>
      </div>
    </div>
  );
}

export function LichThi() {
  return (
    <div className="container-fluid flex-column justify-content-between my-auto text-black">
      <PageTitle
        title={"LỊCH THI TUYỂN SINH LỚP 10 THPT NĂM HỌC 2026-2027"}
        info={"(dự kiến tổ chức vào đầu tháng 6/2026)"}
      />
      <div
        className="table-responsive mx-auto my-2"
        style={{ width: "fit-content", maxWidth: "100%" }}
      >
        <table className="table text-center table-bordered border-black m-0">
          <thead className="align-middle table-success border-black">
            <tr>
              <th rowSpan="2">Ngày thi</th>
              <th rowSpan="2">Buổi thi</th>
              <th rowSpan="2">Bài thi</th>
              <th colSpan="4">Thời gian</th>
            </tr>
            <tr>
              <th>Làm bài</th>
              <th>Phát đề thi</th>
              <th>Tính giờ làm bài</th>
              <th>Hết giờ làm bài</th>
            </tr>
          </thead>
          <tbody className="align-middle">
            <tr>
              <td className="fst-italic" rowSpan="2">
                Thông báo sau
              </td>
              <td>Sáng</td>
              <td className="text-start">Toán</td>
              <td>120 phút</td>
              <td>07 giờ 25 phút</td>
              <td>07 giờ 30 phút</td>
              <td>09 giờ 30 phút</td>
            </tr>
            <tr>
              <td>Chiều</td>
              <td className="text-start">Môn thi thứ ba (xác định sau)</td>
              <td>60 phút</td>
              <td>13 giờ 55 phút</td>
              <td>14 giờ 00 phút</td>
              <td>15 giờ 00 phút</td>
            </tr>
            <tr>
              <td className="fst-italic">Thông báo sau</td>
              <td>Sáng</td>
              <td className="text-start">Ngữ văn</td>
              <td>120 phút</td>
              <td>07 giờ 25 phút</td>
              <td>07 giờ 30 phút</td>
              <td>09 giờ 30 phút</td>
            </tr>
            <tr>
              <td className="fst-italic">Thông báo sau</td>
              <td>Sáng</td>
              <td className="text-start">Các môn chuyên</td>
              <td>150 phút</td>
              <td>07 giờ 25 phút</td>
              <td>07 giờ 30 phút</td>
              <td>10 giờ 00 phút</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
}
export default function Homepage() {
  const { user, loading } = useAuth();
  useEffect(() => {
    document.title = "Trang chủ";
  }, []);
  if (loading) return <Spinner />;

  if (user) {
    // Nếu đã đăng nhập, chuyển về role tương ứng
    switch (user.maLoaiTK) {
      case 0:
        return <Navigate to="/admin" replace />;
      case 1:
        return <Navigate to="/sgddt" replace />;
      case 2:
        return <Navigate to="/thcs" replace />;
      case 3:
        return <Navigate to="/thpt" replace />;
      case 4:
        return <Navigate to="/hocsinh" replace />;
      default:
        return <Navigate to="/" replace />;
    }
  }
  return (
    <div>
      <FullWidthBackgroundHomepage />
      <MocThoiGian />
      <hr />
      <LichThi />
    </div>
  );
}
