import axiosClient from "../axiosClient";

const sgddtApi = {
  getTrangThai: () => {
    return axiosClient.get("/sgddt/trangthai");
  },
  getTrangThaiByMaTT: (maTT) => {
    return axiosClient.get(`/sgddt/trangthai/${maTT}`);
  },
  updateTrangThai: (maTT, kieuDuLieu, newVal) => {
    return axiosClient.patch(`/sgddt/trangthai/${maTT}`, null, {
      params: { kieuDuLieu, newVal },
    });
  },
  getThiSinh: () => {
    return axiosClient.get("/sgddt/thisinh");
  },
  findThiSinh: (soBaoDanh) => {
    return axiosClient.get(`/sgddt/thisinh/${soBaoDanh}`);
  },
  searchThiSinh: (keyword) => {
    return axiosClient.get("/sgddt/thisinh/search", {
      params: { keyword: keyword },
    });
  },
  updateSBDAndPhongThi: () => {
    return axiosClient.post("/sgddt/thisinh/capnhat");
  },
  xetTuyen: () => {
    return axiosClient.patch("/sgddt/xettuyen");
  },
  updateKetQuaThi: (soBaoDanh, ketQuaThi) => {
    return axiosClient.patch(`/sgddt/thisinh/kqthi/${soBaoDanh}`, ketQuaThi, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  },
  importKetQuaThi: (file, onUploadProgress) => {
    const formData = new FormData();
    formData.append("file", file);
    return axiosClient.patch("/sgddt/thisinh/kqthi/import", formData, {
      headers: { "Content-Type": "multipart/form-data" },
      onUploadProgress: (event) => {
        if (onUploadProgress) onUploadProgress(event);
      },
    });
  },
  exportKetQuaThi: (axiosConfig) => {
    return axiosClient.get("/sgddt/thisinh/kqthi/export", {
      responseType: "blob",
      ...axiosConfig,
    });
  },
  exportKetQuaTuyenSinh: (axiosConfig) => {
    return axiosClient.get("/sgddt/kqts/export", {
      responseType: "blob",
      ...axiosConfig,
    });
  },
};

export default sgddtApi;
