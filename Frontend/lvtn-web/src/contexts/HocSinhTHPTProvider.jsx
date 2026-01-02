import { useState, useCallback, useEffect } from "react";
import { useAuth } from "@/hooks";
import { thptApi } from "@/api";
import { HocSinhTHPTContext } from "@/contexts";

export default function HocSinhTHPTProvider({ children }) {
  const { user } = useAuth();
  const [listHS, setListHS] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchHocSinh = useCallback(async () => {
    if (!user || !user.soDinhDanh) {
      setListHS([]);
      setLoading(false);
      return;
    }
    try {
      setLoading(true);
      const res = await thptApi.findHocSinhNgoaiTinh(user.soDinhDanh);
      await new Promise((resolve) => setTimeout(resolve, 250));
      setListHS(res.data || []);
    } catch (error) {
      console.error("Lỗi khi lấy danh sách học sinh:", error);
    } finally {
      setLoading(false);
    }
  }, [user]);

  const searchHocSinh = async (kw) => {
    const trimmed = kw.trim();
    if (trimmed === "") {
      await fetchHocSinh();
      return;
    }
    setLoading(true);
    try {
      const res = await thptApi.searchHocSinhNgoaiTinh(
        user.soDinhDanh,
        trimmed
      );
      await new Promise((resolve) => setTimeout(resolve, 250));
      setListHS(res.data || []);
    } catch (err) {
      console.error("Lỗi khi tìm kiếm học sinh:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchHocSinh();
  }, [fetchHocSinh]);

  const value = {
    listHS,
    loading,
    fetchHocSinh,
    searchHocSinh,
    soDinhDanh: user?.soDinhDanh,
    tenDinhDanh: user?.tenDinhDanh,
    thptChuyen: user?.thptChuyen,
  };

  return (
    <HocSinhTHPTContext.Provider value={value}>
      {children}
    </HocSinhTHPTContext.Provider>
  );
}
