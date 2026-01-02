import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth, useHocSinhTHPT, useTrangThaiCapNhatHoSo } from "@/hooks";
import {
  ImportHocSinhTHPT,
  ExportHocSinhTHPT,
  ExportTKHocSinhTHPT,
  DeleteAllHocSinhTHPT,
  DeleteHocSinhTHPT,
} from "@/components/modal";
import { PageTitle, Spinner, NoData } from "@/components/common";

export function ButtonRow({ duocPhepCapNhat }) {
  const { fetchHocSinh, soDinhDanh, tenDinhDanh } = useHocSinhTHPT();
  return (
    <div className="text-center my-1">
      {duocPhepCapNhat && (
        <Link to="/thpt/hocsinh/add" className="btn btn-primary mx-1 mt-1">
          Thêm hồ sơ mới
        </Link>
      )}
      {duocPhepCapNhat && <ImportHocSinhTHPT onImportSuccess={fetchHocSinh} />}
      <ExportHocSinhTHPT soDinhDanh={soDinhDanh} />
      <ExportTKHocSinhTHPT soDinhDanh={soDinhDanh} />
      {duocPhepCapNhat && (
        <DeleteAllHocSinhTHPT
          soDinhDanh={soDinhDanh}
          tenDinhDanh={tenDinhDanh}
          onDeleted={fetchHocSinh}
        />
      )}
    </div>
  );
}
export function Content({
  listHS,
  loading,
  onSelect,
  thptChuyen,
  duocPhepCapNhat,
}) {
  if (loading) return <Spinner />;

  if (listHS.length === 0)
    return (
      <>
        <NoData content="Không có học sinh nào trong danh sách!" />
      </>
    );

  return (
    <>
      <div className="d-flex flex-column flex-fill overflow-hidden my-2">
        <div className="table-responsive overflow-auto flex-fill">
          <table className="table table-hover align-middle nowrap w-100">
            <thead className="text-center sticky-top z-1">
              <tr className="table-success">
                <th>MSHS</th>
                <th colSpan="2">Họ và tên học sinh</th>
                <th>Giới tính</th>
                <th>Ngày sinh</th>
                <th>Nơi sinh</th>
                <th>Trường THCS</th>
                {thptChuyen === true ? <th>Lớp chuyên</th> : ""}
                <th>Thao tác</th>
              </tr>
            </thead>
            <tbody id="student-tbody">
              {listHS.map((hs, index) => {
                let lopChuyen = "";
                // Lấy dữ liệu về lớp chuyên
                hs.nguyenVong?.forEach((nv) => {
                  if (nv.thuTu == 1) {
                    lopChuyen = nv.tenLopChuyen;
                  }
                });
                return (
                  <tr key={index}>
                    <td>{hs.thongTinHS.maHS}</td>
                    <td>{hs.thongTinHS.hoVaChuLotHS} </td>
                    <td>{hs.thongTinHS.tenHS}</td>
                    <td className="text-center">
                      {hs.thongTinHS.gioiTinh === true ? "Nữ" : "Nam"}
                    </td>
                    <td className="text-center">{hs.thongTinHS.ngaySinh}</td>
                    <td>{hs.thongTinHS.noiSinh}</td>
                    <td>
                      {hs.thongTinHS.tenTHCSNgoaiTPCT}
                      <br />({hs.thongTinHS.tenXaNgoaiTPCT},{" "}
                      {hs.thongTinHS.tenTinhNgoaiTPCT})
                    </td>
                    {thptChuyen === true ? <td>{lopChuyen}</td> : ""}
                    <td className="text-center">
                      <Link
                        to={`/thpt/hocsinh/detail/${hs.thongTinHS.maHS}`}
                        className="btn btn-info mx-1"
                      >
                        Xem chi tiết
                      </Link>
                      {duocPhepCapNhat && (
                        <Link
                          to={`/thpt/hocsinh/update/${hs.thongTinHS.maHS}`}
                          className="btn btn-warning mx-1"
                        >
                          Cập nhật
                        </Link>
                      )}
                      {duocPhepCapNhat && (
                        <button
                          className="btn btn-danger mx-1"
                          onClick={() => onSelect(hs)}
                        >
                          Xóa
                        </button>
                      )}
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      </div>
    </>
  );
}
export default function THPTHocSinhManagement() {
  const { user } = useAuth();
  const navigate = useNavigate();
  const {
    listHS,
    loading: loadingHS,
    thptChuyen,
    searchHocSinh,
    fetchHocSinh,
  } = useHocSinhTHPT();
  const { duocPhepCapNhat, loading: loadingTrangThai } =
    useTrangThaiCapNhatHoSo();
  const loading = loadingHS || loadingTrangThai;
  const [selectedHS, setSelectedHS] = useState(null);
  const [input, setInput] = useState("");
  const handleSubmit = async (e) => {
    e.preventDefault();
    await searchHocSinh(input);
  };
  useEffect(() => {
    if (user.tsNgoaiTPCT !== true) {
      navigate("/thpt");
    }
  }, [navigate, user]);
  useEffect(() => {
    document.title = "Quản lý học sinh";
  }, []);
  return (
    <div className="container-fluid container-lg short-page">
      <PageTitle title={"QUẢN LÝ HỒ SƠ HỌC SINH NGOÀI TP CẦN THƠ DỰ THI"} />
      <ButtonRow duocPhepCapNhat={duocPhepCapNhat} />
      <form
        className="d-flex justify-content-center align-items-center my-2"
        onSubmit={handleSubmit}
      >
        <input
          type="text"
          id="searchHSTHPT"
          className="form-control focus-ring focus-ring-success w-50"
          placeholder="Nhập MSHS hoặc họ và tên để tìm kiếm..."
          value={input}
          onChange={(e) => setInput(e.target.value)}
          disabled={loading}
        />
        <button
          type="submit"
          className="btn btn-success mx-2"
          disabled={loading}
        >
          Tìm kiếm
        </button>
        <button
          type="button"
          className="btn btn-secondary"
          onClick={() => {
            setInput("");
            fetchHocSinh();
          }}
        >
          Làm mới
        </button>
      </form>
      <Content
        listHS={listHS}
        loading={loading}
        onSelect={setSelectedHS}
        thptChuyen={thptChuyen}
        duocPhepCapNhat={duocPhepCapNhat}
      />
      <DeleteHocSinhTHPT
        selectedHS={selectedHS}
        onClose={() => setSelectedHS(null)}
      />
    </div>
  );
}
