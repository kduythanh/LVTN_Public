import { useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
export default function NotFound() {
  const navigate = useNavigate();

  const handleGoBack = () => {
    if (window.history.length > 1) navigate(-1);
    else navigate("/");
  };

  useEffect(() => {
    document.title = "Trang không tồn tại";
  }, []);

  return (
    <div className="short-page centered-page bg-danger-subtle">
      <h1 className="display-1 text-danger mb-3 fw-bold">404</h1>
      <p className="lead fw-bold">
        Trang bạn yêu cầu không tồn tại hoặc đã bị xóa!
      </p>
      <div className="d-flex gap-2">
        <button className="btn btn-secondary" onClick={handleGoBack}>
          <i className="bi bi-arrow-left"></i> Quay lại trang trước
        </button>
        <Link to="/" className="btn btn-primary">
          <i className="bi bi-house-door-fill"></i> Về trang chủ
        </Link>
      </div>
    </div>
  );
}
