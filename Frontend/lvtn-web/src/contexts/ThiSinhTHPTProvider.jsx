import { useState, useCallback, useEffect } from "react";
import { useAuth } from "@/hooks";
import { thptApi } from "@/api";
import { ThiSinhTHPTContext } from "@/contexts";

export default function ThiSinhTHPTProvider({ children }) {
  const { user } = useAuth();
  const [listTS, setListTS] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchThiSinh = useCallback(async () => {
    if (!user || !user.soDinhDanh) {
      setListTS([]);
      setLoading(false);
      return;
    }
    try {
      setLoading(true);
      const res = await thptApi.findThiSinh(user.soDinhDanh);
      await new Promise((resolve) => setTimeout(resolve, 250));
      setListTS(res.data || []);
    } catch (error) {
      console.error("Lỗi khi lấy danh sách thí sinh:", error);
    } finally {
      setLoading(false);
    }
  }, [user]);

  const searchThiSinh = async (kw) => {
    const trimmed = kw.trim();
    if (trimmed === "") {
      await fetchThiSinh();
      return;
    }
    setLoading(true);
    try {
      const res = await thptApi.searchThiSinh(user.soDinhDanh, trimmed);
      await new Promise((resolve) => setTimeout(resolve, 250));
      setListTS(res.data || []);
    } catch (err) {
      console.error("Lỗi khi tìm kiếm học sinh:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchThiSinh();
  }, [fetchThiSinh]);

  const value = {
    listTS,
    loading,
    fetchThiSinh,
    searchThiSinh,
    soDinhDanh: user?.soDinhDanh,
    tenDinhDanh: user?.tenDinhDanh,
    thptChuyen: user?.thptChuyen,
  };

  return (
    <ThiSinhTHPTContext.Provider value={value}>
      {children}
    </ThiSinhTHPTContext.Provider>
  );
}
