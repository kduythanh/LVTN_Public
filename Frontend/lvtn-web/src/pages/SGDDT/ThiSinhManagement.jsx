import { Link } from "react-router-dom";
import { useThiSinhSGDDT, useTrangThaiCapNhatDiem } from "@/hooks";
import {
  ExportDiemThiSinhSGDDT,
  ImportDiemThiSinhSGDDT,
  UpdateSBDThiSinhSGDDT,
  PageTitle,
  Spinner,
  NoData,
  XetTuyenSGDDT,
  ExportKQTSSGDDT,
} from "@/components";
import { useEffect, useState } from "react";

export function ButtonRow({ fetchThiSinh }) {
  return (
    <div className="text-center my-1">
      <UpdateSBDThiSinhSGDDT onUpdated={fetchThiSinh} />
      <ImportDiemThiSinhSGDDT onImportSuccess={fetchThiSinh} />
      <ExportDiemThiSinhSGDDT />
      <XetTuyenSGDDT />
      <ExportKQTSSGDDT />
    </div>
  );
}
export function Content({ listTS, loading, duocPhepCapNhatDiem }) {
  if (loading) return <Spinner />;

  if (listTS.length === 0)
    return <NoData content="Không có thí sinh nào trong danh sách!" />;
  return (
    <>
      <div className="d-flex flex-column flex-fill overflow-hidden my-2">
        <div className="table-responsive overflow-auto flex-fill">
          <table className="table table-hover align-middle nowrap w-100">
            <thead className="text-center sticky-top z-1 align-middle">
              <tr className="table-success">
                <th>Số báo danh</th>
                <th colSpan={2}>Họ và tên học sinh</th>
                <th>Điểm Toán</th>
                <th>Điểm Ngữ văn</th>
                <th>Điểm môn thứ 3</th>
                <th>Điểm môn chuyên</th>
                <th>Thao tác</th>
              </tr>
            </thead>
            <tbody>
              {listTS.map((ts) => (
                <tr key={ts.maHS}>
                  <td>{ts.soBaoDanh}</td>
                  <td>{ts.hoVaChuLotHS}</td>
                  <td>{ts.tenHS}</td>
                  <td className="text-end">
                    {ts.diemToan != null ? Number(ts.diemToan).toFixed(2) : "-"}
                  </td>
                  <td className="text-end">
                    {ts.diemVan != null ? Number(ts.diemVan).toFixed(2) : "-"}
                  </td>
                  <td className="text-end">
                    {ts.diemMonThu3 != null
                      ? Number(ts.diemMonThu3).toFixed(2)
                      : "-"}
                  </td>
                  <td className="text-end">
                    {ts.diemMonChuyen != null
                      ? Number(ts.diemMonChuyen).toFixed(2)
                      : "-"}
                  </td>
                  <td className="text-center">
                    {ts.soBaoDanh && duocPhepCapNhatDiem && (
                      <Link
                        to={`/sgddt/thisinh/${ts.soBaoDanh}/update-score`}
                        className="btn btn-warning mx-1"
                        aria-disabled={ts.soBaoDanh ? "false" : "true"}
                      >
                        Cập nhật điểm
                      </Link>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </>
  );
}

export default function HDTSThiSinhManagement() {
  const {
    listTS,
    loading: loadingTS,
    fetchThiSinh,
    searchThiSinh,
  } = useThiSinhSGDDT();
  const {
    loading: loadingTT,
    reloadTrangThai,
    duocPhepCapNhatDiem,
  } = useTrangThaiCapNhatDiem();
  const loading = loadingTS || loadingTT;
  const [input, setInput] = useState("");
  const handleSubmit = async (e) => {
    e.preventDefault();
    await searchThiSinh(input);
  };
  useEffect(() => {
    document.title = "Quản lý kết quả thi thí sinh";
    reloadTrangThai();
  }, [reloadTrangThai]);
  return (
    <div className="container-fluid container-lg short-page">
      <PageTitle title="QUẢN LÝ KẾT QUẢ THI THÍ SINH" />
      <ButtonRow fetchThiSinh={fetchThiSinh} />
      <form
        className="d-flex justify-content-center align-items-center my-2"
        onSubmit={handleSubmit}
      >
        <input
          type="text"
          id="searchHSTHPT"
          className="form-control focus-ring focus-ring-success w-50"
          placeholder="Nhập MSHS, số báo danh hoặc họ và tên để tìm kiếm..."
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
            fetchThiSinh();
          }}
        >
          Làm mới
        </button>
      </form>
      <Content
        listTS={listTS}
        loading={loading}
        duocPhepCapNhatDiem={duocPhepCapNhatDiem}
      />
    </div>
  );
}
