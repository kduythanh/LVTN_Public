export default function Spinner() {
  return (
    <div className="d-flex flex-column flex-fill overflow-hidden justify-content-center align-items-center my-2">
      <div className="spinner-border text-success" role="status">
        <span className="visually-hidden">Đang tải...</span>
      </div>
      <div className="text-success mt-1">Đang tải dữ liệu...</div>
    </div>
  );
}
