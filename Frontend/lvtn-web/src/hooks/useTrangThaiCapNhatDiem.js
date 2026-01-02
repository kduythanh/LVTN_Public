import { useState, useEffect, useCallback, useMemo } from "react";
import { commonApi } from "@/api";

export default function useTrangThaiCapNhatDiem() {
  const [choPhepCapNhatDiem, setChoPhepCapNhatDiem] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchTrangThai = useCallback(async () => {
    try {
      setLoading(true);
      const res = await commonApi.getTrangThaiByMaTT(4);
      const data = res.data || {};
      const giaTriBoolean = data.giaTriBoolean ?? false;
      setChoPhepCapNhatDiem(giaTriBoolean);
    } catch (err) {
      console.error("Lỗi khi lấy trạng thái cho phép cập nhật điểm:", err);
      setChoPhepCapNhatDiem(false);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchTrangThai();
  }, [fetchTrangThai]);

  const duocPhepCapNhatDiem = useMemo(() => {
    if (loading || choPhepCapNhatDiem === null) return false;
    return choPhepCapNhatDiem;
  }, [loading, choPhepCapNhatDiem]);

  return {
    choPhepCapNhatDiem,
    duocPhepCapNhatDiem,
    loading,
    reloadTrangThai: fetchTrangThai,
  };
}
