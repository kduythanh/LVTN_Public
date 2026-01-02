import { useState, useEffect, useCallback, useMemo } from "react";
import { commonApi } from "@/api";

export default function useTrangThaiCongBoKetQua() {
  const [khoaCongBo, setKhoaCongBo] = useState(false);
  const [thoiGianCongBo, setThoiGianCongBo] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchTrangThai = useCallback(async () => {
    try {
      setLoading(true);
      const res1 = await commonApi.getTrangThaiByMaTT(6);
      const data1 = res1.data || {};
      const res2 = await commonApi.getTrangThaiByMaTT(7);
      const data2 = res2.data || {};
      const khoaCongBo = data1.giaTriBoolean ?? true;
      const rawTimestamp = Array.isArray(data2.giaTriTimestamp)
        ? data2.giaTriTimestamp
        : null;

      let tgCongBo = null;
      if (Array.isArray(rawTimestamp) && rawTimestamp.length >= 3) {
        const [year, month, day, hour = 0, minute = 0, second = 0] =
          rawTimestamp;
        tgCongBo = new Date(year, month - 1, day, hour, minute, second);
      }

      setKhoaCongBo(khoaCongBo);
      setThoiGianCongBo(tgCongBo);
    } catch (err) {
      console.error("Lỗi khi lấy trạng thái cập nhật hồ sơ:", err);
      setKhoaCongBo(false);
      setThoiGianCongBo(null);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchTrangThai();
  }, [fetchTrangThai]);

  const duocPhepCongBo = useMemo(() => {
    if (loading) return false;
    if (khoaCongBo) return false;
    if (thoiGianCongBo && new Date() < thoiGianCongBo) return false;
    return true;
  }, [loading, khoaCongBo, thoiGianCongBo]);

  return {
    khoaCongBo,
    thoiGianCongBo,
    loading,
    duocPhepCongBo,
    reloadTrangThai: fetchTrangThai,
  };
}
