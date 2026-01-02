import { useState, useCallback, useEffect } from "react";
import { useAuth } from "@/hooks";
import { thcsApi } from "@/api";
import { HocSinhTHCSContext } from "@/contexts";

export default function HocSinhTHCSProvider({ children }) {
  const { user } = useAuth();
  const [listHS, setListHS] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchHocSinh = useCallback(async () => {
    if (!user || !user.soDinhDanh) {
      setListHS([]);
      setLoading(false);
      return;
    }
    setLoading(true);
    try {
      const res = await thcsApi.findHocSinh(user.soDinhDanh);
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
      const res = await thcsApi.searchHocSinh(user.soDinhDanh, trimmed);
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
  };

  return (
    <HocSinhTHCSContext.Provider value={value}>
      {children}
    </HocSinhTHCSContext.Provider>
  );
}
