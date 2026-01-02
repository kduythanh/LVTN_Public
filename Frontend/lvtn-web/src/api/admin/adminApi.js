import axiosClient from "../axiosClient";

const adminApi = {
  getAccountList: () => {
    return axiosClient.get("/admin/account");
  },
  findAccount: (keyword) => {
    return axiosClient.get("/admin/account/search", {
      params: { keyword: keyword },
    });
  },
  exportAccount: (axiosConfig) => {
    return axiosClient.get("/admin/account/export", {
      responseType: "blob",
      ...axiosConfig,
    });
  },
  exportGVAccount: (axiosConfig) => {
    return axiosClient.get("/admin/account/canbo/export", {
      responseType: "blob",
      ...axiosConfig,
    });
  },
  addAccount: (account) => {
    return axiosClient.post("/admin/account", account, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  },
  importAccount: (file, onUploadProgress) => {
    const formData = new FormData();
    formData.append("file", file);
    return axiosClient.post("/admin/account/import", formData, {
      headers: { "Content-Type": "multipart/form-data" },
      onUploadProgress: (event) => {
        if (onUploadProgress) onUploadProgress(event);
      },
    });
  },
  deleteAccount: (tenTK) => {
    return axiosClient.delete(`/admin/account/${tenTK}`);
  },
  resetPassword: (tenTK) => {
    return axiosClient.patch(`/admin/account/reset/${tenTK}`);
  },
};

export default adminApi;
