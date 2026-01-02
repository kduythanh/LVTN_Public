import { useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";

export default function Forbidden() {
  const navigate = useNavigate();

  const handleGoBack = () => {
    if (window.history.length > 1) navigate(-1);
    else navigate("/");
  };

  useEffect(() => {
    document.title = "Trang không được phép truy cập";
  }, []);

  return (
    <div className="short-page centered-page bg-warning-subtle">
      <h1 className="display-1 text-warning mb-3 fw-bold">403</h1>
      <p className="lead fw-bold">Bạn không có quyền truy cập vào trang này!</p>
      <div className="d-flex gap-2">
        <button className="btn btn-secondary" onClick={handleGoBack}>
          <i className="bi bi-arrow-left"></i> Quay lại trang trước
        </button>
        <Link to="/" className="btn btn-primary">
          <i className="bi bi-house-door-fill"></i> Về trang chủ
        </Link>
      </div>
      <p className="text-muted mt-3 small fw-bold">
        Nếu bạn nghĩ đây là lỗi, hãy thử đăng xuất và đăng nhập lại!
      </p>
    </div>
  );
}
