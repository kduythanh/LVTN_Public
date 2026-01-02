import axiosClient from "../axiosClient";

const thcsApi = {
  findHocSinh: (maTHCS) => {
    return axiosClient.get(`/thcs/${maTHCS}/hocsinh`);
  },
  searchHocSinh: (maTHCS, keyword) => {
    return axiosClient.get(`/thcs/${maTHCS}/hocsinh/search`, {
      params: { keyword: keyword },
    });
  },
  exportHocSinh: (maTHCS, axiosConfig) => {
    return axiosClient.get(`/thcs/${maTHCS}/hocsinh/export`, {
      responseType: "blob",
      ...axiosConfig,
    });
  },
  exportTKHocSinh: (maTHCS, axiosConfig) => {
    return axiosClient.get(`/thcs/${maTHCS}/hocsinh/taikhoan/export`, {
      responseType: "blob",
      ...axiosConfig,
    });
  },
  findInfoHocSinh: (maHS) => {
    return axiosClient.get(`/thcs/hocsinh/${maHS}`);
  },
  initHocSinh: (maTHCS, formData) => {
    return axiosClient.post(`/thcs/${maTHCS}/hocsinh/init`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  },
  addHocSinh: (maTHCS, formData) => {
    return axiosClient.post(`/thcs/${maTHCS}/hocsinh`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  },
  importInitHocSinh: (maTHCS, excelFile, onUploadProgress) => {
    const formData = new FormData();
    formData.append("excelFile", excelFile);
    return axiosClient.post(`/thcs/${maTHCS}/hocsinh/init/import`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
      onUploadProgress: (event) => {
        if (onUploadProgress) onUploadProgress(event);
      },
    });
  },
  importHocSinh: (maTHCS, excelFile, zipFile, onUploadProgress) => {
    const formData = new FormData();
    formData.append("excelFile", excelFile);
    formData.append("zipFile", zipFile);
    return axiosClient.post(`/thcs/${maTHCS}/hocsinh/import`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
      onUploadProgress: (event) => {
        if (onUploadProgress) onUploadProgress(event);
      },
    });
  },
  updateHocSinh: (maTHCS, maHS, formData) => {
    return axiosClient.patch(`/thcs/${maTHCS}/hocsinh/${maHS}`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  },
  deleteHocSinh: (maTHCS, maHS) => {
    return axiosClient.delete(`/thcs/${maTHCS}/hocsinh/${maHS}`);
  },
  deleteAllHocSinh: (maTHCS) => {
    return axiosClient.delete(`/thcs/${maTHCS}/hocsinh`);
  },
};

export default thcsApi;
