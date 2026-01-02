import { useEffect, useState, useRef } from "react";
import ReCAPTCHA from "react-google-recaptcha";
import { commonApi } from "@/api";
import { PageTitle, Spinner } from "@/components";
import { useTrangThaiCongBoKetQua } from "@/hooks";

export function HoiDongThiDropdown({ value, onChange }) {
  const [listTHPT, setListTHPT] = useState([]);

  useEffect(() => {
    const fetchTHPT = async () => {
      try {
        const res = await commonApi.getTHPT();
        setListTHPT(res.data);
      } catch (error) {
        console.error("Lỗi khi lấy danh sách THPT:", error);
      }
    };
    fetchTHPT();
  }, []);

  return (
    <select
      name="hoiDongThi"
      id="hoiDongThi"
      value={value}
      onChange={(e) => onChange(e.target.value)}
      className="form-select border-black overflow-y-auto focus-ring focus-ring-success"
      required
    >
      <option key="" value="">
        Chọn hội đồng thi
      </option>
      {listTHPT.map((thpt) => (
        <option key={thpt.maTHPT} value={thpt.maTHPT}>
          {thpt.tenTHPT}
        </option>
      ))}
    </select>
  );
}

export function LoaiTraCuuDropdown({ value, onChange }) {
  return (
    <select
      name="loaiTC"
      id="loaiTC"
      value={value}
      onChange={(e) => onChange(e.target.value)}
      className="form-select border-black overflow-y-auto focus-ring focus-ring-success"
      required
    >
      <option key="" value="">
        Chọn loại tra cứu
      </option>
      <option key="1" value="1">
        Theo số báo danh
      </option>
      <option key="2" value="2">
        Theo mã học sinh
      </option>
      <option key="3" value="3">
        Theo họ và tên
      </option>
    </select>
  );
}

export function NoiDungTraCuuTextBox({ value, onChange }) {
  return (
    <input
      className="form-control border-black focus-ring focus-ring-success"
      type="text"
      id="noiDungTC"
      name="noiDungTC"
      value={value}
      placeholder="Nhập thông tin tra cứu..."
      onChange={(e) => onChange(e.target.value)}
      required
    />
  );
}

export function SearchForm() {
  const [hoiDongThi, setHoiDongThi] = useState("");
  const [loaiTC, setLoaiTC] = useState("");
  const [noiDungTC, setNoiDungTC] = useState("");
  const [captchaToken, setCaptchaToken] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [isError, setIsError] = useState(false);

  const captchaRef = useRef(null);
  const [listKQ, setListKQ] = useState([]);
  const [TSDetail, setTSDetail] = useState("");
  const {
    loading: loadingTT,
    duocPhepCongBo,
    reloadTrangThai,
  } = useTrangThaiCongBoKetQua();

  const handleCaptcha = (value) => setCaptchaToken(value);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setListKQ([]);
    setTSDetail("");
    setMessage("");
    if (!hoiDongThi || !loaiTC || !noiDungTC) {
      setMessage("Vui lòng chọn và nhập đầy đủ thông tin trước khi tìm kiếm!");
      setIsError(true);
      return;
    }
    if (!captchaToken) {
      setMessage("Vui lòng xác minh reCAPTCHA trước khi submit!");
      setIsError(true);
      return;
    }
    setLoading(true);
    setIsError(false);
    try {
      if (captchaToken) {
        const res = await commonApi.getKetQuaThi(
          hoiDongThi,
          loaiTC,
          noiDungTC,
          captchaToken
        );
        await new Promise((resolve) => setTimeout(resolve, 500));
        const results = res.data;
        setListKQ(results);
        if (results.length === 0) {
          setMessage(
            "Không tìm thấy thí sinh nào phù hợp với thông tin đã nhập."
          );
          setIsError(true);
        }
      }
    } catch (error) {
      console.error("Tìm kiếm thất bại: ", error);
      const errorMsg =
        error.response?.data?.message ||
        "Tìm kiếm thất bại, vui lòng kiểm tra thông tin và thử lại sau!";
      setMessage(errorMsg);
      setListKQ([]);
      setIsError(true);
    } finally {
      setLoading(false);
      captchaRef.current?.reset();
      setCaptchaToken("");
    }
  };

  const handleFindDetail = async (soBaoDanh) => {
    setIsError(false);
    const selected = listKQ.find((item) => item.soBaoDanh == soBaoDanh);
    if (selected) {
      setTSDetail(selected);
    } else {
      setTSDetail("");
      setMessage("Không tìm thấy thí sinh trong danh sách.");
      setIsError(true);
    }
  };

  useEffect(() => {
    reloadTrangThai();
  }, [reloadTrangThai]);

  useEffect(() => {
    if (listKQ.length === 0) {
      setTSDetail("");
    } else if (listKQ.length === 1) {
      setTSDetail(listKQ[0]);
    } else {
      setTSDetail("");
    }
  }, [listKQ]);

  return (
    <div className="full-width-background-nolimit d-flex">
      <img
        src="/images/process-4.jpg"
        alt="Background"
        className="background-image"
      />
      <div className="overlay"></div>
      <div className="container-fluid position-relative d-flex flex-column z-2">
        <div
          className="mx-auto my-2 rounded w-100"
          style={{
            backgroundColor: "rgba(255, 255, 255, 0.5)",
            maxWidth: "1320px",
          }}
        >
          <div className="m-2 p-2 bg-white rounded">
            <form onSubmit={handleSubmit}>
              <PageTitle
                title={
                  "TRA CỨU KẾT QUẢ TUYỂN SINH LỚP 10 THPT NĂM HỌC 2026-2027"
                }
              />
              <hr />
              {loadingTT ? (
                <Spinner />
              ) : !duocPhepCongBo ? (
                <>
                  <div className="alert alert-danger text-center mt-2 mx-auto">
                    Chưa đến thời gian công bố kết quả tuyển sinh, vui lòng quay
                    lại sau!
                  </div>
                </>
              ) : (
                <>
                  <div className="container-fluid">
                    <div className="row">
                      <div className="d-flex col-12 col-md-3 justify-content-start justify-content-md-end text-nowrap align-items-center my-1">
                        <label
                          htmlFor="hoiDongThi"
                          className="form-label fw-bold m-0"
                        >
                          Hội đồng thi:
                        </label>
                      </div>
                      <div className="col-12 col-md-9 my-1">
                        <HoiDongThiDropdown
                          value={hoiDongThi}
                          onChange={setHoiDongThi}
                        />
                      </div>
                    </div>
                    <div className="row">
                      <div className="d-flex col-12 col-md-3 justify-content-start justify-content-md-end text-nowrap align-items-center my-1">
                        <label
                          htmlFor="loaiTC"
                          className="form-label fw-bold m-0"
                        >
                          Tra cứu theo:
                        </label>
                      </div>
                      <div className="col-12 col-md-9 my-1">
                        <LoaiTraCuuDropdown
                          value={loaiTC}
                          onChange={setLoaiTC}
                        />
                      </div>
                    </div>
                    <div className="row">
                      <div className="d-flex col-12 col-md-3 justify-content-start justify-content-md-end text-nowrap align-items-center my-1">
                        <label
                          htmlFor="noiDungTC"
                          className="form-label fw-bold m-0"
                        >
                          Nội dung tra cứu:
                        </label>
                      </div>
                      <div className="col-12 col-md-9 my-1">
                        <NoiDungTraCuuTextBox
                          value={noiDungTC}
                          onChange={setNoiDungTC}
                        />
                      </div>
                    </div>
                    <div className="row">
                      <div className="d-flex justify-content-center align-items-center my-1">
                        <ReCAPTCHA
                          ref={captchaRef}
                          sitekey={import.meta.env.VITE_RECAPTCHA_SITE_KEY}
                          onChange={handleCaptcha}
                        />
                      </div>
                    </div>
                    {!loading && isError && (
                      <div className={"alert alert-danger text-center"}>
                        {message}
                      </div>
                    )}
                  </div>
                  <hr />
                  <div className="text-center fw-bold text-black my-2">
                    <button className="btn btn-success" type="submit">
                      Tìm kiếm
                    </button>
                  </div>
                </>
              )}
            </form>
            {loading && <Spinner />}
            {!loading && listKQ.length > 1 && TSDetail == "" && (
              <>
                <h5 className="text-center fw-bold my-2">
                  Tìm thấy {listKQ.length} thí sinh thỏa mãn. Vui lòng chọn thí
                  sinh để xem chi tiết:
                </h5>
                <div
                  className="d-flex flex-column flex-fill overflow-hidden my-2"
                  style={{ maxHeight: "75vh" }}
                >
                  <div className="table-responsive overflow-auto flex-fill mx-auto">
                    <table className="table table-hover align-middle nowrap w-auto m-0">
                      <thead className="text-center sticky-top z-1">
                        <tr className="table-success">
                          <th>Mã học sinh</th>
                          <th>SBD</th>
                          <th colSpan={2}>Họ và tên thí sinh</th>
                          <th>Xem chi tiết</th>
                        </tr>
                      </thead>
                      <tbody>
                        {listKQ.map((item) => (
                          <tr key={item.soBaoDanh}>
                            <td>{item.maHS}</td>
                            <td>{item.soBaoDanh}</td>
                            <td>{item.hoVaChuLotHS}</td>
                            <td>{item.tenHS}</td>
                            <td className="text-center">
                              <button
                                className="btn btn-primary"
                                onClick={() => handleFindDetail(item.soBaoDanh)}
                              >
                                Xem chi tiết
                              </button>
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                </div>
              </>
            )}
            {!loading && TSDetail != "" && <ThiSinhDetail data={TSDetail} />}
          </div>
        </div>
      </div>
    </div>
  );
}

export function ThiSinhDetail({ data }) {
  const diemToan = data.diemToan ?? 0;
  const diemVan = data.diemVan ?? 0;
  const diemMonThu3 = data.diemMonThu3 ?? 0;
  const diemMonChuyen = data.diemMonChuyen ?? 0;
  const uuTien = data.diemCongUuTien ?? 0;
  const khuyenKhich = data.diemCongKhuyenKhich ?? 0;
  const khuyenKhichChuyen = data.diemCongKhuyenKhichChuyen ?? 0;

  const tongDiem = diemToan + diemVan + diemMonThu3 + uuTien + khuyenKhich;
  const tongDiemChuyen =
    diemMonChuyen !== null && diemMonChuyen !== undefined
      ? diemToan + diemVan + diemMonThu3 + 2 * diemMonChuyen + khuyenKhichChuyen
      : null;
  const dau = data.nguyenVongDau;
  const thptDau = data.truongTHPTDau;

  return (
    <div>
      <h5 className="fs-2 text-center fw-bold my-2">
        KẾT QUẢ THI TUYỂN SINH LỚP 10 THPT - NĂM HỌC 2026-2027
      </h5>
      <div
        className="table-responsive mx-auto my-2"
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
                  {data.hoVaChuLotHS} {data.tenHS}
                </strong>{" "}
                (Trường {data.tenTHCS})
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
                {dau != null ? (
                  <>
                    <div className="alert text-center border-success m-0 p-2">
                      <div className="fs-3 text-success">
                        Chúc mừng bạn đã trúng tuyển
                      </div>
                      <div className="fs-4 fw-bold text-success">
                        Nguyện vọng {dau} - Trường {thptDau}
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
    </div>
  );
}

export default function SearchResult() {
  useEffect(() => {
    document.title = "Tra cứu kết quả";
  }, []);
  return (
    <>
      <SearchForm />
    </>
  );
}
