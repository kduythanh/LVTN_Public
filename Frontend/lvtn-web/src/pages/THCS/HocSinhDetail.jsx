import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { thcsApi } from "@/api";
import { useImagePreview } from "@/hooks";
import { NoData, PageTitle, Spinner } from "@/components/common";

export function TableDetail() {
  const { maHS } = useParams();
  const [hocSinh, setHocSinh] = useState(null);
  const [loading, setLoading] = useState(true);
  const { preview, loadImage } = useImagePreview();

  useEffect(() => {
    const fetchHSDetail = async () => {
      setLoading(true);
      try {
        const res = await thcsApi.findInfoHocSinh(maHS);
        const hs = res.data;
        setHocSinh(hs);
        if (maHS) loadImage(maHS);
      } catch (error) {
        console.error("Lỗi khi lấy danh sách học sinh:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchHSDetail();
  }, [maHS, loadImage]);

  if (loading) return <Spinner />;
  if (hocSinh === null)
    return <NoData content="Không tồn tại thông tin học sinh!" />;
  const nvArr = Array(5).fill(null);
  hocSinh.nguyenVong?.forEach((nv) => {
    if (nv.thuTu >= 1 && nv.thuTu <= 5) {
      nvArr[nv.thuTu - 1] = nv; // giữ nguyên object để dễ dùng
    }
  });
  return (
    <div className="table-responsive">
      <table className="table">
        <tbody>
          <tr style={{ height: "40px" }}>
            <td colSpan="2" className="bg-success text-white fw-bold">
              Phần 1: Thông tin cá nhân học sinh
            </td>
          </tr>
          <tr className="align-items-stretch">
            <td scope="row" className="col-3 fw-bold text-end">
              Ảnh học sinh
            </td>
            <td>
              <div
                style={{
                  width: "90px",
                  aspectRatio: "3 / 4",
                  overflow: "hidden",
                }}
              >
                <img
                  src={preview}
                  alt="Ảnh học sinh"
                  className="img-thumbnail object-fit-cover w-100 h-100"
                />
              </div>
            </td>
          </tr>
          <tr className="align-items-stretch">
            <td scope="row" className="col-3 fw-bold text-end">
              Mã số học sinh
            </td>
            <td>{hocSinh.thongTinHS.maHS}</td>
          </tr>
          <tr className="align-items-stretch">
            <td scope="row" className="col-3 fw-bold text-end">
              Họ và tên học sinh
            </td>
            <td>
              {hocSinh.thongTinHS.hoVaChuLotHS} {hocSinh.thongTinHS.tenHS}
            </td>
          </tr>
          <tr className="align-items-stretch">
            <td scope="row" className="col-3 fw-bold text-end">
              Giới tính
            </td>
            <td>{hocSinh.thongTinHS.gioiTinh === true ? "Nữ" : "Nam"}</td>
          </tr>
          <tr className="align-items-stretch">
            <td scope="row" className="col-3 fw-bold text-end">
              Ngày sinh
            </td>
            <td>{hocSinh.thongTinHS.ngaySinh}</td>
          </tr>
          <tr className="align-items-stretch">
            <td scope="row" className="col-3 fw-bold text-end">
              Nơi sinh
            </td>
            <td>{hocSinh.thongTinHS.noiSinh}</td>
          </tr>
          <tr className="align-items-stretch">
            <td scope="row" className="col-3 fw-bold text-end">
              Dân tộc
            </td>
            <td>{hocSinh.thongTinHS.tenDT}</td>
          </tr>
          <tr className="align-items-stretch">
            <td scope="row" className="col-3 fw-bold text-end">
              Địa chỉ thường trú
            </td>
            <td>{hocSinh.thongTinHS.diaChiThuongTru}</td>
          </tr>
          <tr className="align-items-stretch">
            <td scope="row" className="col-3 fw-bold text-end">
              Chỗ ở hiện nay
            </td>
            <td>{hocSinh.thongTinHS.choOHienNay}</td>
          </tr>
          <tr className="align-items-stretch">
            <td scope="row" className="col-3 fw-bold text-end">
              Học sinh trường THCS
            </td>
            <td>
              {hocSinh.thongTinHS.tenTHCS} ({hocSinh.thongTinHS.tenPhuongXaTHCS}
              )
            </td>
          </tr>
          <tr style={{ height: "40px" }}>
            <td colSpan="2" className="bg-success text-white fw-bold">
              Phần 2: Kết quả học tập cấp THCS
            </td>
          </tr>
          <tr className="align-items-stretch">
            <td scope="row" className="col-3 fw-bold text-end">
              Kết quả Học tập và Rèn luyện
            </td>
            <td>
              {hocSinh.kqHocTap.map((kq, index) => {
                return (
                  <div key={index}>
                    Lớp {kq.lop}: Học tập:{" "}
                    <span className="fw-bold">{kq.hocTap}</span>, Rèn luyện:{" "}
                    <span className="fw-bold">{kq.renLuyen}</span>
                  </div>
                );
              })}
              <div>
                Tổng điểm trung bình các môn lớp 9:{" "}
                <span className="fw-bold">
                  {hocSinh.thongTinHS.tongDiemTBLop9}
                </span>
              </div>
            </td>
          </tr>
          <tr className="align-items-stretch">
            <td scope="row" className="col-3 fw-bold text-end">
              Năm tốt nghiệp THCS
            </td>
            <td>{hocSinh.thongTinHS.namTotNghiepTHCS}</td>
          </tr>
          <tr style={{ height: "40px" }}>
            <td colSpan="2" className="bg-success text-white fw-bold">
              Phần 3: Thông tin đăng ký dự thi
            </td>
          </tr>
          <tr className="align-items-stretch">
            <td scope="row" className="col-3 fw-bold text-end">
              Đối tượng ưu tiên
            </td>
            <td>
              {hocSinh.thongTinHS.tenDTUT} (Điểm cộng:{" "}
              {hocSinh.thongTinHS.diemCongDTUT})
            </td>
          </tr>
          <tr className="align-items-stretch">
            <td scope="row" className="col-3 fw-bold text-end">
              Đối tượng khuyến khích
            </td>
            <td>
              {hocSinh.thongTinHS.tenDTKK} (Điểm cộng:{" "}
              {hocSinh.thongTinHS.diemCongDTKK})
            </td>
          </tr>
          <tr className="align-items-stretch">
            <td scope="row" className="col-3 fw-bold text-end">
              Đối tượng khuyến khích (thi chuyên)
            </td>
            <td>
              {hocSinh.thongTinHS.tenDTKKChuyen} (Điểm cộng:{" "}
              {hocSinh.thongTinHS.diemCongDTKKChuyen})
            </td>
          </tr>
          {nvArr.map((nv, index) => {
            return (
              <tr key={index} className="align-items-stretch">
                <td scope="row" className="col-3 fw-bold text-end">
                  Nguyện vọng {index + 1}
                </td>
                <td>
                  {nv ? (
                    <>
                      <div>
                        {nv.tenTHPT} ({nv.tenPhuongXaTHPT})
                        {nv.thuTu == 1 && nv.tenLopChuyen
                          ? " - " + nv.tenLopChuyen
                          : ""}
                        {nv.thuTu === 2 && (
                          <>
                            {nv.nv2B == 1 && (
                              <span className="fst-italic">
                                {" "}
                                (Nguyện vọng 2B)
                              </span>
                            )}
                            {nv.lopTiengPhap == 1 && (
                              <span className="fst-italic">
                                {" "}
                                (Lớp tiếng Pháp)
                              </span>
                            )}
                          </>
                        )}
                      </div>
                      {nv.thuTu == 1 && (
                        <div>
                          Điểm trung bình môn chuyên lớp 9:{" "}
                          <span className="fw-bold">
                            {hocSinh.thongTinHS.diemTBMonChuyen}
                          </span>
                        </div>
                      )}
                    </>
                  ) : (
                    <div className="fst-italic">(không có)</div>
                  )}
                </td>
              </tr>
            );
          })}
          <tr className="align-items-stretch">
            <td scope="row" className="col-3 fw-bold text-end">
              Ngoại ngữ đang học
            </td>
            <td>{hocSinh.thongTinHS.ngoaiNguDangHoc}</td>
          </tr>
          <tr className="align-items-stretch">
            <td scope="row" className="col-3 fw-bold text-end">
              Ngoại ngữ dự thi
            </td>
            <td>{hocSinh.thongTinHS.ngoaiNguDuThi}</td>
          </tr>
          <tr className="align-items-stretch">
            <td scope="row" className="col-3 fw-bold text-end">
              Số điện thoại
            </td>
            <td>{hocSinh.thongTinHS.soDienThoai}</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}

export default function THCSHocSinhDetail() {
  useEffect(() => {
    document.title = "Chi tiết hồ sơ học sinh";
  }, []);
  return (
    <div className="container-fluid container-lg long-page">
      <PageTitle title={"CHI TIẾT HỒ SƠ HỌC SINH"} />
      <TableDetail />
    </div>
  );
}
