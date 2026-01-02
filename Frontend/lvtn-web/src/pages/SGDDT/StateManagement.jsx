import { useEffect, useState } from "react";
import { PageTitle, Spinner, UpdateStateSGDDT, NoData } from "@/components";
import { useStateSGDDT } from "@/hooks";

export function Content({ onSelect }) {
  const { loading, listTrangThai } = useStateSGDDT();

  const formatTimestamp = (ts) => {
    if (!ts) return "";
    if (Array.isArray(ts)) {
      const [y, m, d, h, min, s = 0] = ts;
      return new Date(y, m - 1, d, h, min, s).toLocaleString("vi-VN");
    }
    return new Date(ts).toLocaleString("vi-VN");
  };

  if (loading) return <Spinner />;
  if (listTrangThai.length === 0)
    return <NoData content="Không có trạng thái nào trong danh sách!" />;

  return (
    <>
      <div className="d-flex flex-column flex-fill overflow-hidden my-2">
        <div className="table-responsive overflow-auto flex-fill">
          <table className="table table-hover align-middle nowrap">
            <thead className="text-center sticky-top z-1">
              <tr className="table-success">
                <th>Tên trạng thái</th>
                <th>Giá trị trạng thái</th>
                <th>Cập nhật lần cuối</th>
                <th>Thao tác</th>
              </tr>
            </thead>
            <tbody>
              {listTrangThai.map((tt) => (
                <tr key={tt.maTT}>
                  <td>{tt.tenTrangThai}</td>
                  <td className="fw-bold">
                    {tt.kieuDuLieu === "BOOLEAN" ? (
                      tt.giaTriBoolean ? (
                        <span className="text-success">Cho phép</span>
                      ) : (
                        <span className="text-danger">Không cho phép</span>
                      )
                    ) : tt.kieuDuLieu === "TIMESTAMP" ? (
                      formatTimestamp(tt.giaTriTimestamp)
                    ) : (
                      tt.giaTriChuoi
                    )}
                  </td>
                  <td>{formatTimestamp(tt.tgCapNhat)}</td>
                  <td className="text-center">
                    <button
                      className="btn btn-warning mx-1"
                      onClick={() =>
                        onSelect({
                          ...tt,
                          oldVal:
                            tt.giaTriBoolean ??
                            tt.giaTriTimestamp ??
                            tt.giaTriChuoi ??
                            null,
                        })
                      }
                    >
                      Cập nhật
                    </button>
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

export default function HDTSStateManagement() {
  const [selectedTT, setSelectedTT] = useState(null);

  useEffect(() => {
    document.title = "Quản lý trạng thái kỳ thi";
  }, []);
  return (
    <div className="container-fluid container-lg short-page">
      <PageTitle title={"QUẢN LÝ TRẠNG THÁI KỲ THI TUYỂN SINH LỚP 10 THPT"} />
      <Content onSelect={setSelectedTT} />
      <UpdateStateSGDDT
        selectedTT={selectedTT}
        onClose={() => setSelectedTT(null)}
      />
    </div>
  );
}
