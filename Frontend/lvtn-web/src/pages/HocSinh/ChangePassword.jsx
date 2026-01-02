import { useEffect, useState } from "react";
import { useAuth } from "@/hooks";
import { commonApi } from "@/api";
import { PageTitle } from "@/components";

export default function HocSinhChangePassword() {
  const { user } = useAuth();
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmNewPassword, setConfirmNewPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [isError, setIsError] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage("");
    setIsError(false);
    try {
      const res = await commonApi.changePassword(
        user.tenTK,
        currentPassword,
        newPassword,
        confirmNewPassword
      );
      await new Promise((resolve) => setTimeout(resolve, 500));
      setMessage(res.message);
      setIsError(false);
    } catch (error) {
      console.error("Change password failed:", error);
      setMessage("Đổi mật khẩu thất bại: " + error.response?.data?.message);
      setIsError(true);
    } finally {
      setLoading(false);
    }
  };
  useEffect(() => {
    document.title = "Đổi mật khẩu";
  }, []);
  return (
    <div className="full-width-background d-flex">
      <img
        src="/images/background-pano-hocsinh.jpg"
        alt="Background"
        className="background-image"
      />
      <div className="overlay"></div>
      <div className="container-fluid position-relative d-flex flex-column z-2">
        <div
          className="mx-auto my-2 rounded w-100"
          style={{
            backgroundColor: "rgba(255, 255, 255, 0.5)",
            maxWidth: "500px",
            width: "100%",
          }}
        >
          <div className="m-2 p-2 bg-white rounded">
            <form onSubmit={handleSubmit}>
              <PageTitle title={"ĐỔI MẬT KHẨU"} />
              <hr />
              <div className="px-2">
                <div className="my-2">
                  <label className="form-label m-0" htmlFor="currentPassword">
                    Mật khẩu hiện tại
                  </label>
                </div>
                <div className="my-2">
                  <input
                    className="form-control focus-ring focus-ring-success"
                    type="password"
                    id="currentPassword"
                    name="currentPassword"
                    value={currentPassword}
                    onChange={(e) => setCurrentPassword(e.target.value)}
                    placeholder="Nhập mật khẩu hiện tại..."
                    required
                    disabled={loading}
                  />
                </div>
                <div className="my-2">
                  <label className="form-label m-0" htmlFor="newPassword">
                    Mật khẩu mới
                  </label>
                </div>
                <div className="my-2">
                  <input
                    className="form-control focus-ring focus-ring-success"
                    type="password"
                    id="newPassword"
                    name="newPassword"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    placeholder="Nhập mật khẩu mới..."
                    required
                    disabled={loading}
                  />
                </div>
                <div className="my-2">
                  <label
                    className="form-label m-0"
                    htmlFor="confirmNewPassword"
                  >
                    Xác nhận mật khẩu mới
                  </label>
                </div>
                <div className="my-2">
                  <input
                    className="form-control focus-ring focus-ring-success"
                    type="password"
                    id="confirmNewPassword"
                    name="confirmNewPassword"
                    value={confirmNewPassword}
                    onChange={(e) => setConfirmNewPassword(e.target.value)}
                    placeholder="Nhập lại mật khẩu mới..."
                    required
                    disabled={loading}
                  />
                </div>
              </div>
              <hr />
              <div className="text-center my-2">
                {!loading && (
                  <button className="btn btn-success" type="submit">
                    Lưu
                  </button>
                )}
              </div>
              {loading && (
                <div className="text-center text-success my-2">
                  <div
                    className="spinner-border text-success"
                    role="status"
                    style={{ width: "3rem", height: "3rem" }}
                  ></div>
                  <p className="my-2">Đang xử lý...</p>
                </div>
              )}
              {message && (
                <div
                  className={`alert my-2 ${
                    isError ? "alert-danger" : "alert-success"
                  }`}
                >
                  {message}
                </div>
              )}
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
