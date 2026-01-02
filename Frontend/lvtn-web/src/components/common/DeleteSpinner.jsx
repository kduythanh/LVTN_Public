export default function DeleteSpinner() {
  return (
    <div className="my-2">
      <div className="spinner-border text-danger" role="status">
        <span className="visually-hidden">Đang xử lý...</span>
      </div>
      <div className="text-danger mt-1">Đang xóa dữ liệu...</div>
    </div>
  );
}
