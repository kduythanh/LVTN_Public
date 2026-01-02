import { NoData, PageTitle, Spinner } from "@/components/common";
import { useAuth, useTrangThaiCongBoKetQua } from "@/hooks";
import { useEffect, useState } from "react";
import { hocsinhApi } from "@/api";

export function Content() {
  const { user } = useAuth();
  const {
    loading: loadingTT,
    duocPhepCongBo,
    reloadTrangThai,
  } = useTrangThaiCongBoKetQua();
  const maHS = user.soDinhDanh;
  const [ketQua, setKetQua] = useState(null);
  const [loading, setLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState("");
  const [diemToan, setDiemToan] = useState(null);
  const [diemVan, setDiemVan] = useState(null);
  const [diemMonThu3, setDiemMonThu3] = useState(null);
  const [diemMonChuyen, setDiemMonChuyen] = useState(null);
  const [uuTien, setUuTien] = useState(0);
  const [khuyenKhich, setKhuyenKhich] = useState(0);
  const [khuyenKhichChuyen, setKhuyenKhichChuyen] = useState(0);
  const [tongDiem, setTongDiem] = useState(0);
  const [tongDiemChuyen, setTongDiemChuyen] = useState(null);
  const [nvDau, setNVDau] = useState(null);
  const [thptDau, setTHPTDau] = useState(null);

  useEffect(() => {
    reloadTrangThai();
  }, [reloadTrangThai]);
  useEffect(() => {
    const fetchKQTS = async () => {
      setLoading(true);
      setErrorMessage("");
      try {
        const res = await hocsinhApi.findKetQuaTuyenSinh(maHS);
        const kq = res.data;
        setKetQua(kq);
        setDiemToan(kq.diemToan || 0);
        setDiemVan(kq.diemVan || 0);
        setDiemMonThu3(kq.diemMonThu3 || 0);
        setDiemMonChuyen(kq.diemMonChuyen || 0);
        setUuTien(kq.diemCongUuTien || 0);
        setKhuyenKhich(kq.diemCongKhuyenKhich || 0);
        setKhuyenKhichChuyen(kq.diemCongKhuyenKhichChuyen || 0);
        setTongDiem(
          kq.diemToan +
            kq.diemVan +
            kq.diemMonThu3 +
            kq.diemCongUuTien +
            kq.diemCongKhuyenKhich || 0
        );
        if (kq.diemMonChuyen != null && kq.diemMonChuyen != undefined) {
          setTongDiemChuyen(
            kq.diemToan +
              kq.diemVan +
              kq.diemMonThu3 +
              2 * kq.diemMonChuyen +
              kq.diemCongKhuyenKhichChuyen || 0
          );
        }
        setNVDau(kq.nguyenVongDau);
        setTHPTDau(kq.truongTHPTDau);
      } catch (error) {
        console.error("Lỗi khi lấy kết quả tuyển sinh:", error);
        const message =
          error?.response?.data?.message ||
          error.message ||
          "Không thể lấy kết quả tuyển sinh!";
        setErrorMessage(message);
      } finally {
        setLoading(false);
      }
    };
    fetchKQTS();
  }, [maHS]);
  if (loadingTT) return <Spinner />;
  if (!duocPhepCongBo)
    return (
      <div className="alert alert-danger text-center mt-2 mx-auto">
        {errorMessage}
      </div>
    );
  if (loading) return <Spinner />;
  if (ketQua === null)
    return <NoData content="Không tồn tại kết quả tuyển sinh của học sinh!" />;
  return (
    <>
      <div
        className="table-responsive mx-auto my-1"
        style={{ width: "fit-content", maxWidth: "100%" }}
      >
        <table
          className="table align-middle nowrap w-auto m-0"
          style={{ backgroundColor: "transparent" }}
        >
          <tbody>
            <tr>
              <td colSpan={2} className="text-center fs-5">
                Thí sinh:{" "}
                <strong>
                  {ketQua.hoVaChuLotHS} {ketQua.tenHS}
                </strong>{" "}
                (Trường {ketQua.tenTHCS})
              </td>
            </tr>
            <tr>
              <td>Điểm Toán:</td>
              <td>{diemToan.toFixed(2)}</td>
            </tr>
            <tr>
              <td>Điểm Ngữ văn:</td>
              <td>{diemVan.toFixed(2)}</td>
            </tr>
            <tr>
              <td>Điểm môn thứ 3:</td>
              <td>{diemMonThu3.toFixed(2)}</td>
            </tr>
            <tr>
              <td>Điểm cộng Ưu tiên:</td>
              <td>{uuTien.toFixed(2)}</td>
            </tr>
            <tr>
              <td>Điểm cộng Khuyến khích:</td>
              <td>{khuyenKhich.toFixed(2)}</td>
            </tr>
            <tr>
              <td className="fw-bold">Tổng điểm (thang điểm 30):</td>
              <td className="fw-bold">{tongDiem.toFixed(2)}</td>
            </tr>

            {tongDiemChuyen !== null && (
              <>
                <tr>
                  <td>Điểm môn chuyên:</td>
                  <td>{diemMonChuyen.toFixed(2)}</td>
                </tr>
                <tr>
                  <td>Điểm cộng Khuyến khích (thi chuyên):</td>
                  <td>{khuyenKhichChuyen.toFixed(2)}</td>
                </tr>
                <tr>
                  <td className="fw-bold">Tổng điểm chuyên (thang điểm 50):</td>
                  <td className="fw-bold">{tongDiemChuyen.toFixed(2)}</td>
                </tr>
              </>
            )}
            <tr>
              <td colSpan={2} className="border-white px-0">
                {nvDau != null ? (
                  <>
                    <div className="alert text-center border-success m-0 p-2">
                      <div className="fs-3 text-success">
                        Chúc mừng bạn đã trúng tuyển
                      </div>
                      <div className="fs-4 fw-bold text-success">
                        Nguyện vọng {nvDau} - Trường {thptDau}
                      </div>
                    </div>
                  </>
                ) : (
                  <>
                    <div className="alert alert-danger text-center m-0 p-2">
                      <div>
                        Rất tiếc, bạn đã không trúng tuyển nguyện vọng nào.
                      </div>
                      <div>
                        Nếu bạn thấy có sai sót về điểm bài thi, vui lòng liên
                        hệ hội đồng thi để được hướng dẫn phúc khảo bài thi.
                      </div>
                    </div>
                  </>
                )}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </>
  );
}

export default function HocSinhAdmissionResult() {
  useEffect(() => {
    document.title = "Kết quả tuyển sinh";
  }, []);
  return (
    <div className="container-fluid container-lg long-page">
      <PageTitle title={"KẾT QUẢ TUYỂN SINH LỚP 10 THPT NĂM HỌC 2026-2027"} />
      <Content />
    </div>
  );
}
