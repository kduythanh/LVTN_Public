import { useState, useEffect } from "react";
import { commonApi } from "@/api";
import { AuthContext } from "@/contexts";

export default function AuthProvider({ children }) {
  const [token, setToken] = useState(null);
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  const login = async (username, password, captchaToken) => {
    const res = await commonApi.login(username, password, captchaToken);
    const data = res.data;
    let user = data.user;
    if (String(user.maLoaiTK) === "3") {
      try {
        const resTHPT = await commonApi.getTHPT();
        const listTHPT = resTHPT.data;
        const infoTHPT = listTHPT.find(
          (item) => item.maTHPT === user.soDinhDanh
        );

        if (infoTHPT) {
          user = {
            ...user,
            thptChuyen: infoTHPT.thptChuyen,
            tsNgoaiTPCT: infoTHPT.tsNgoaiTPCT,
          };
        } else {
          console.warn(
            `Không tìm thấy thông tin trường THPT với mã ${user.soDinhDanh}`
          );
        }
      } catch (err) {
        console.error("Không thể lấy thông tin trường THPT:", err);
      }
    }
    return { token: data.token, user };
  };

  const finalizeLogin = (token, user) => {
    setToken(token);
    setUser(user);
    localStorage.setItem("auth", JSON.stringify({ token, user }));
  };

  const logout = async () => {
    commonApi.logout().catch((err) => {
      console.warn("Không thể gọi API logout:", err);
    });
    setToken(null);
    setUser(null);
    localStorage.removeItem("auth");
  };

  useEffect(() => {
    const saved = localStorage.getItem("auth");
    if (saved) {
      const parsed = JSON.parse(saved);
      setToken(parsed.token);
      setUser(parsed.user);
    }
    setLoading(false);
  }, []);

  const value = {
    token,
    user,
    loading,
    login,
    finalizeLogin,
    logout,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
