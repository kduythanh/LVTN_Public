import { useState, useEffect, useCallback, useMemo } from "react";
import { commonApi } from "@/api";

export default function useTrangThaiXetTuyen() {
  const [choPhepXetTuyen, setChoPhepXetTuyen] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchTrangThai = useCallback(async () => {
    try {
      setLoading(true);
      const res = await commonApi.getTrangThaiByMaTT(5);
      const data = res.data || {};
      const giaTriBoolean = data.giaTriBoolean ?? false;
      setChoPhepXetTuyen(giaTriBoolean);
    } catch (err) {
      console.error(
        "Lỗi khi lấy trạng thái cho phép chạy hệ thống xét tuyển:",
        err
      );
      setChoPhepXetTuyen(false);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchTrangThai();
  }, [fetchTrangThai]);

  const duocPhepXetTuyen = useMemo(() => {
    if (loading || choPhepXetTuyen === null) return false;
    return choPhepXetTuyen;
  }, [loading, choPhepXetTuyen]);

  return {
    choPhepXetTuyen,
    duocPhepXetTuyen,
    loading,
    reloadTrangThai: fetchTrangThai,
  };
}
