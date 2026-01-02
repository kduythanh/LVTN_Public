import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { useHocSinhTHCS, useTrangThaiCapNhatHoSo } from "@/hooks";
import { PageTitle, Spinner, NoData } from "@/components/common";
import {
  ImportHocSinhTHCS,
  ExportHocSinhTHCS,
  ExportTKHocSinhTHCS,
  DeleteAllHocSinhTHCS,
  DeleteHocSinhTHCS,
  ImportInitHocSinhTHCS,
} from "@/components/modal";

export function ButtonRow({ duocPhepCapNhat }) {
  const { fetchHocSinh, soDinhDanh, tenDinhDanh } = useHocSinhTHCS();
  return (
    <div className="text-center my-1">
      {duocPhepCapNhat && (
        <Link to="/thcs/hocsinh/init" className="btn btn-primary mx-1 mt-1">
          Khởi tạo hồ sơ
        </Link>
      )}
      {duocPhepCapNhat && (
        <ImportInitHocSinhTHCS onImportSuccess={fetchHocSinh} />
      )}
      {duocPhepCapNhat && (
        <Link to="/thcs/hocsinh/add" className="btn btn-primary mx-1 mt-1">
          Thêm hồ sơ đầy đủ
        </Link>
      )}
      {duocPhepCapNhat && <ImportHocSinhTHCS onImportSuccess={fetchHocSinh} />}
      <ExportHocSinhTHCS soDinhDanh={soDinhDanh} />
      <ExportTKHocSinhTHCS soDinhDanh={soDinhDanh} />
      {duocPhepCapNhat && (
        <DeleteAllHocSinhTHCS
          soDinhDanh={soDinhDanh}
          tenDinhDanh={tenDinhDanh}
          onDeleted={fetchHocSinh}
        />
      )}
    </div>
  );
}

export function Content({ listHS, loading, onSelect, duocPhepCapNhat }) {
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
                <th>Nguyện vọng cao nhất</th>
                <th>Thao tác</th>
              </tr>
            </thead>
            <tbody>
              {listHS.map((hs) => {
                const nvMax =
                  hs.nguyenVong.length > 0 ? hs.nguyenVong[0] : null;
                return (
                  <tr key={hs.thongTinHS.maHS}>
                    <td>{hs.thongTinHS.maHS}</td>
                    <td>{hs.thongTinHS.hoVaChuLotHS}</td>
                    <td>{hs.thongTinHS.tenHS}</td>
                    <td className="text-center">
                      {hs.thongTinHS.gioiTinh === true ? "Nữ" : "Nam"}
                    </td>
                    <td className="text-center">{hs.thongTinHS.ngaySinh}</td>
                    <td>{hs.thongTinHS.noiSinh}</td>
                    {nvMax != null ? (
                      <td>
                        <strong>
                          NV{nvMax.thuTu}
                          {nvMax.nv2B == 1 && "b"}
                          {nvMax.lopTiengPhap == 1 && "-Pháp"}
                        </strong>
                        : {nvMax.tenTHPT}
                        {nvMax.thuTu === 1 && nvMax.tenLopChuyen
                          ? ` (${nvMax.tenLopChuyen})`
                          : ""}
                      </td>
                    ) : (
                      <td className="fst-italic">(không có)</td>
                    )}
                    <td className="text-center">
                      <Link
                        to={`/thcs/hocsinh/detail/${hs.thongTinHS.maHS}`}
                        className="btn btn-info mx-1"
                      >
                        Xem chi tiết
                      </Link>
                      {duocPhepCapNhat && (
                        <Link
                          to={`/thcs/hocsinh/update/${hs.thongTinHS.maHS}`}
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

export default function THCSHocSinhManagement() {
  const {
    listHS,
    loading: loadingHS,
    searchHocSinh,
    fetchHocSinh,
  } = useHocSinhTHCS();
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
    document.title = "Quản lý học sinh";
  }, []);
  return (
    <div className="container-fluid container-lg short-page">
      <PageTitle title={"QUẢN LÝ HỒ SƠ HỌC SINH DỰ THI"} />
      <ButtonRow duocPhepCapNhat={duocPhepCapNhat} />
      <form
        className="d-flex justify-content-center align-items-center my-2"
        onSubmit={handleSubmit}
      >
        <input
          type="text"
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
        duocPhepCapNhat={duocPhepCapNhat}
      />
      <DeleteHocSinhTHCS
        selectedHS={selectedHS}
        onClose={() => setSelectedHS(null)}
      />
    </div>
  );
}
