import { useState, useCallback, useEffect } from "react";
import { adminApi } from "@/api";
import { AccountAdminContext } from "@/contexts";

export default function AccountAdminProvider({ children }) {
  const [listTK, setListTK] = useState([]);
  const [loading, setLoading] = useState(false);

  const fetchAccounts = useCallback(async () => {
    setLoading(true);
    try {
      const res = await adminApi.getAccountList();
      await new Promise((resolve) => setTimeout(resolve, 250));
      setListTK(res.data);
    } catch (error) {
      console.error("Lỗi khi lấy danh sách tài khoản:", error);
    } finally {
      setLoading(false);
    }
  }, []);

  const searchAccounts = async (kw) => {
    const trimmed = kw.trim();
    if (trimmed === "") {
      await fetchAccounts();
      return;
    }
    setLoading(true);
    try {
      const res = await adminApi.findAccount(trimmed);
      await new Promise((resolve) => setTimeout(resolve, 250));
      setListTK(res.data || []);
    } catch (err) {
      console.error("Lỗi khi tìm kiếm học sinh:", err);
    } finally {
      setLoading(false);
    }
  };

  const renderLoaiTK = (maLoaiTK) => {
    switch (maLoaiTK) {
      case 0:
        return "Quản trị viên";
      case 1:
        return "Hội đồng tuyển sinh";
      case 2:
        return "Giáo viên THCS";
      case 3:
        return "Giáo viên THPT";
      default:
        return "Học sinh";
    }
  };

  useEffect(() => {
    fetchAccounts();
  }, [fetchAccounts]);

  const value = {
    listTK,
    loading,
    fetchAccounts,
    searchAccounts,
    renderLoaiTK,
  };

  return (
    <AccountAdminContext.Provider value={value}>
      {children}
    </AccountAdminContext.Provider>
  );
}
