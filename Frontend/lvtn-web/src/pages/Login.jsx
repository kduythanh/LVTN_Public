import { useEffect, useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "@/hooks";
import ReCAPTCHA from "react-google-recaptcha";
import { PageTitle } from "@/components";

export function LoginContent() {
  const { login, finalizeLogin } = useAuth();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [captchaToken, setCaptchaToken] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [isError, setIsError] = useState(false);
  const [isSuccess, setIsSuccess] = useState(false);

  const navigate = useNavigate();
  const captchaRef = useRef(null);
  const handleCaptcha = (value) => setCaptchaToken(value); // lưu token

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!captchaToken) {
      setMessage("Vui lòng xác minh reCAPTCHA trước khi đăng nhập!");
      setIsError(true);
      return;
    }
    try {
      setLoading(true);
      setMessage("");
      setIsError(false);
      setIsSuccess(false);

      const { token, user } = await login(username, password, captchaToken);
      setMessage("Đăng nhập thành công! Đang chuyển hướng...");
      setIsError(false);
      setIsSuccess(true);

      setTimeout(() => {
        finalizeLogin(token, user);
        if (user.maLoaiTK === 0) navigate("/admin");
        else if (user.maLoaiTK === 1) navigate("/sgddt");
        else if (user.maLoaiTK === 2) navigate("/thcs");
        else if (user.maLoaiTK === 3) navigate("/thpt");
        else if (user.maLoaiTK === 4) navigate("/hocsinh");
        else navigate("/");
      }, 1000);
    } catch (error) {
      console.error("Login failed:", error);
      const errorMsg =
        error.response?.data?.message ||
        "Đăng nhập thất bại, vui lòng kiểm tra thông tin và thử lại.";
      setMessage(errorMsg);
      setIsError(true);
      setIsSuccess(false);
      captchaRef.current?.reset();
      setCaptchaToken("");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="full-width-background d-flex">
      <img
        src="/images/background-pano.jpg"
        alt="Background"
        className="background-image"
      />
      <div className="overlay"></div>
      <div className="container-fluid position-relative d-flex flex-column z-2">
        <div
          className="mx-auto my-2 rounded w-100"
          style={{
            backgroundColor: "rgba(255, 255, 255, 0.5)",
            maxWidth: "550px",
            width: "100%",
          }}
        >
          <div className="m-2 p-2 bg-white rounded">
            <form onSubmit={handleSubmit}>
              <PageTitle title={"ĐĂNG NHẬP"} />
              <hr />
              <div className="px-2">
                <div className="my-2">
                  <label className="form-label m-0" htmlFor="username">
                    Tên tài khoản
                  </label>
                </div>
                <div className="my-2">
                  <input
                    className="form-control focus-ring focus-ring-success"
                    type="text"
                    id="username"
                    name="username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    placeholder="Nhập tên tài khoản..."
                    required
                    readOnly={loading}
                  />
                </div>
                <div className="my-2">
                  <label className="form-label m-0" htmlFor="password">
                    Mật khẩu
                  </label>
                </div>
                <div className="my-2">
                  <input
                    className="form-control focus-ring focus-ring-success"
                    type="password"
                    id="password"
                    name="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Nhập mật khẩu..."
                    required
                    readOnly={loading}
                  />
                </div>
                <div className="my-2 d-flex justify-content-center">
                  <ReCAPTCHA
                    ref={captchaRef}
                    sitekey={import.meta.env.VITE_RECAPTCHA_SITE_KEY}
                    onChange={handleCaptcha}
                  />
                </div>
              </div>
              <div className="alert alert-info my-2">
                Nếu quên mật khẩu, vui lòng liên hệ quản trị viên để được hướng
                dẫn cấp lại mật khẩu cho tài khoản!
              </div>
              <hr />
              <div className="text-center my-2">
                {!loading && !isSuccess && (
                  <button className="btn btn-success" type="submit">
                    Đăng nhập
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

export default function Login() {
  useEffect(() => {
    document.title = "Đăng nhập";
  }, []);
  return (
    <>
      <LoginContent />
    </>
  );
}
