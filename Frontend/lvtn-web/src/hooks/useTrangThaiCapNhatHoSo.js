import { useState, useEffect, useCallback, useMemo } from "react";
import { commonApi } from "@/api";

export default function useTrangThaiCapNhatHoSo() {
  const [choPhepCapNhat, setChoPhepCapNhat] = useState(null);
  const [thoiHanDangKy, setThoiHanDangKy] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchTrangThai = useCallback(async () => {
    try {
      setLoading(true);
      const res1 = await commonApi.getTrangThaiByMaTT(1);
      const data1 = res1.data || {};
      const res2 = await commonApi.getTrangThaiByMaTT(2);
      const data2 = res2.data || {};
      const choPhep = data1.giaTriBoolean ?? true;
      const rawTimestamp = Array.isArray(data2.giaTriTimestamp)
        ? data2.giaTriTimestamp
        : null;
      let thoiHanDK = null;
      if (Array.isArray(rawTimestamp) && rawTimestamp.length >= 3) {
        const [year, month, day, hour = 0, minute = 0, second = 0] =
          rawTimestamp;
        thoiHanDK = new Date(year, month - 1, day, hour, minute, second);
      }
      setChoPhepCapNhat(choPhep);
      setThoiHanDangKy(thoiHanDK);
      const hetHan = thoiHanDK && new Date() > thoiHanDK;
      if (hetHan && choPhep) {
        try {
          await commonApi.updateTrangThai(1, "BOOLEAN", "false");
          setChoPhepCapNhat(false);
        } catch (updateErr) {
          console.error("Lỗi khi đồng bộ trạng thái cập nhật:", updateErr);
        }
      }
      setChoPhepCapNhat(choPhep);
      setThoiHanDangKy(thoiHanDK);
    } catch (err) {
      console.error("Lỗi khi lấy trạng thái cập nhật hồ sơ:", err);
      setChoPhepCapNhat(false);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchTrangThai();
  }, [fetchTrangThai]);

  const duocPhepCapNhat = useMemo(() => {
    if (loading || choPhepCapNhat === null) return false;
    if (thoiHanDangKy) {
      const deadline = new Date(thoiHanDangKy);
      if (new Date() > deadline) return false;
    }
    return choPhepCapNhat;
  }, [choPhepCapNhat, thoiHanDangKy, loading]);

  return {
    choPhepCapNhat,
    thoiHanDangKy,
    loading,
    duocPhepCapNhat,
    reloadTrangThai: fetchTrangThai,
  };
}
