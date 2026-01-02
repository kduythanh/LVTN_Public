import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import {
  ExportAccountAdmin,
  ExportGVAccountAdmin,
  ImportAccountAdmin,
  ResetPasswordAdmin,
} from "@/components/modal";
import { NoData, PageTitle, Spinner } from "@/components/common";
import { useAccountAdmin } from "@/hooks";
import { DeleteAccountAdmin } from "@/components";

export function ButtonRow({ onImportSuccess }) {
  return (
    <div className="text-center my-1">
      <Link to="/admin/add-account" className="btn btn-primary mx-1 mt-1">
        Thêm tài khoản mới
      </Link>
      <ImportAccountAdmin onImportSuccess={onImportSuccess} />
      <ExportAccountAdmin />
      <ExportGVAccountAdmin />
      <ResetPasswordAdmin />
    </div>
  );
}

export function Content({ onSelect, listTK, loading, renderLoaiTK }) {
  if (loading) return <Spinner />;

  if (!listTK || listTK.length === 0)
    return <NoData content="Không có tài khoản nào trong danh sách!" />;

  return (
    <>
      <div
        className="table-responsive mx-auto my-2"
        style={{ width: "fit-content", maxWidth: "100%" }}
      >
        <table className="table table-hover">
          <thead className="text-center sticky-top z-1">
            <tr className="table-success">
              <th>Tên tài khoản</th>
              <th>Tên định danh</th>
              <th>Mã định danh</th>
              <th>Đối tượng</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody className="align-middle">
            {listTK.map((acc) => (
              <tr key={acc.tenTK}>
                <td>{acc.tenTK}</td>
                <td>{acc.tenDinhDanh}</td>
                <td>{acc.soDinhDanh}</td>
                <td>{renderLoaiTK(acc.maLoaiTK)}</td>
                <td className="text-center">
                  {acc.maLoaiTK !== 0 &&
                    acc.maLoaiTK !== 1 &&
                    acc.maLoaiTK !== 4 && (
                      <button
                        className="btn btn-danger"
                        onClick={() => onSelect(acc)}
                      >
                        Xóa tài khoản
                      </button>
                    )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  );
}

export default function AdminAccountManagement() {
  const { listTK, loading, renderLoaiTK, searchAccounts, fetchAccounts } =
    useAccountAdmin();
  const [selectedTK, setSelectedTK] = useState(null);
  const [input, setInput] = useState("");
  const handleSubmit = async (e) => {
    e.preventDefault();
    await searchAccounts(input);
  };
  useEffect(() => {
    document.title = "Quản lý tài khoản";
  }, []);
  return (
    <div className="container-fluid container-lg short-page">
      <PageTitle title="QUẢN LÝ TÀI KHOẢN" />
      <ButtonRow onImportSuccess={fetchAccounts} />
      <form
        className="d-flex justify-content-center align-items-center my-2"
        onSubmit={handleSubmit}
      >
        <input
          type="text"
          id="searchHSTHPT"
          className="form-control focus-ring focus-ring-success w-50"
          placeholder="Nhập tên tài khoản, mã hoặc tên định danh..."
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
            fetchAccounts();
          }}
        >
          Làm mới
        </button>
      </form>
      <Content
        onSelect={setSelectedTK}
        listTK={listTK}
        loading={loading}
        renderLoaiTK={renderLoaiTK}
      />
      <DeleteAccountAdmin
        selectedTK={selectedTK}
        onClose={() => setSelectedTK(null)}
      />
    </div>
  );
}
