import { useState, useEffect, useCallback, useMemo } from "react";
import { commonApi } from "@/api";

export default function useTrangThaiCapSBD() {
  const [choPhepCapSBD, setChoPhepCapSBD] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchTrangThai = useCallback(async () => {
    try {
      setLoading(true);
      const res = await commonApi.getTrangThaiByMaTT(3);
      const data = res.data || {};
      const giaTriBoolean = data.giaTriBoolean ?? false;
      setChoPhepCapSBD(giaTriBoolean);
    } catch (err) {
      console.error("Lỗi khi lấy trạng thái cấp SBD:", err);
      setChoPhepCapSBD(false);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchTrangThai();
  }, [fetchTrangThai]);

  const duocPhepCapSBD = useMemo(() => {
    if (loading || choPhepCapSBD === null) return false;
    return choPhepCapSBD;
  }, [loading, choPhepCapSBD]);

  return {
    choPhepCapSBD,
    duocPhepCapSBD,
    loading,
    reloadTrangThai: fetchTrangThai,
  };
}
