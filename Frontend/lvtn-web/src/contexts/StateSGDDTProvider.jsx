import { useState, useEffect, useCallback } from "react";
import { sgddtApi } from "@/api";
import { StateSGDDTContext } from "@/contexts";

export default function StateSGDDTProvider({ children }) {
  const [listTrangThai, setListTrangThai] = useState([]);
  const [loading, setLoading] = useState(false);

  const fetchTrangThai = useCallback(async () => {
    try {
      setLoading(true);
      const res = await sgddtApi.getTrangThai();
      await new Promise((resolve) => setTimeout(resolve, 250));
      setListTrangThai(res.data);
    } catch (error) {
      console.error("Lỗi khi lấy trạng thái:", error);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchTrangThai();
  }, [fetchTrangThai]);

  const value = { loading, listTrangThai, fetchTrangThai };

  return (
    <StateSGDDTContext.Provider value={value}>
      {children}
    </StateSGDDTContext.Provider>
  );
}
