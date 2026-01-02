import { useState, useCallback, useEffect } from "react";
import { sgddtApi } from "@/api";
import { ThiSinhSGDDTContext } from "@/contexts";

export default function ThiSinhSGDDTProvider({ children }) {
  const [listTS, setListTS] = useState([]);
  const [loading, setLoading] = useState(false);

  const fetchThiSinh = useCallback(async () => {
    try {
      setLoading(true);
      const res = await sgddtApi.getThiSinh();
      await new Promise((resolve) => setTimeout(resolve, 250));
      setListTS(res.data || []);
    } catch (error) {
      console.error("Lỗi khi lấy danh sách thí sinh:", error);
    } finally {
      setLoading(false);
    }
  }, []);

  const searchThiSinh = async (kw) => {
    const trimmed = kw.trim();
    if (trimmed === "") {
      await fetchThiSinh();
      return;
    }
    setLoading(true);
    try {
      const res = await sgddtApi.searchThiSinh(trimmed);
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

  const value = { listTS, loading, fetchThiSinh, searchThiSinh };

  return (
    <ThiSinhSGDDTContext.Provider value={value}>
      {children}
    </ThiSinhSGDDTContext.Provider>
  );
}
