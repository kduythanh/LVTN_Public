import { useEffect, useState } from "react";
import { useAuth, useThiSinhTHPT } from "@/hooks";
import { NoData, PageTitle, Spinner } from "@/components/common";
import { ExportKQTSTHPT, ExportThiSinhTHPT } from "@/components/modal";

export function ButtonRow() {
  const { user } = useAuth();
  return (
    <div className="text-center my-1">
      <ExportThiSinhTHPT soDinhDanh={user.soDinhDanh} />
      <ExportKQTSTHPT soDinhDanh={user.soDinhDanh} />
    </div>
  );
}
export function Content({ listTS, loading, thptChuyen }) {
  if (loading) return <Spinner />;
  if (listTS.length === 0)
    return <NoData content="Không có thí sinh nào trong danh sách!" />;

  return (
    <>
      <div className="d-flex flex-column flex-fill overflow-hidden my-2">
        <div className="table-responsive overflow-auto flex-fill">
          <table className="table table-hover align-middle nowrap w-100">
            <thead className="text-center sticky-top z-1">
              <tr className="table-success">
                <th>MSHS</th>
                <th>Số báo danh</th>
                <th>Phòng thi</th>
                {thptChuyen === true ? <th>Phòng thi chuyên</th> : ""}
                <th colSpan="2">Họ và tên học sinh</th>
                <th>Giới tính</th>
                <th>Ngày sinh</th>
                <th>Nơi sinh</th>
                <th>Học sinh trường THCS</th>
                <th>Ghi chú</th>
              </tr>
            </thead>
            <tbody id="student-tbody">
              {listTS.map((ts, index) => {
                return (
                  <tr key={index}>
                    <td>{ts.maHS}</td>
                    <td>{ts.soBaoDanh}</td>
                    <td>{ts.phongThi}</td>
                    {thptChuyen === true ? <td>{ts.phongThiChuyen}</td> : ""}
                    <td>{ts.hoVaChuLotHS}</td>
                    <td>{ts.tenHS}</td>
                    <td>{ts.gioiTinh === true ? "Nữ" : "Nam"}</td>
                    <td>{ts.ngaySinh}</td>
                    <td>{ts.noiSinh}</td>
                    <td>{ts.tenTHCS}</td>
                    <td>{ts.tenLopChuyen}</td>
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
export default function THPTThiSinhManagement() {
  const { listTS, loading, thptChuyen, searchThiSinh, fetchThiSinh } =
    useThiSinhTHPT();
  const [input, setInput] = useState("");
  const handleSubmit = async (e) => {
    e.preventDefault();
    await searchThiSinh(input);
  };
  useEffect(() => {
    document.title = "Quản lý thí sinh";
  }, []);
  return (
    <div className="container-fluid container-lg short-page">
      <PageTitle
        title={"DANH SÁCH THÍ SINH DỰ THI TẠI TRƯỜNG"}
        info={
          "(Số báo danh, phòng thi sẽ được sở GD&ĐT cấp sau khi kết thúc thời gian đăng ký)"
        }
      />
      <ButtonRow />
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
            fetchThiSinh();
          }}
        >
          Làm mới
        </button>
      </form>
      <Content listTS={listTS} loading={loading} thptChuyen={thptChuyen} />
    </div>
  );
}
