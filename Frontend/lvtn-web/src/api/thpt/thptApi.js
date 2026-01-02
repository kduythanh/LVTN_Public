import axiosClient from "../axiosClient";

const thptApi = {
  findHocSinhNgoaiTinh: (maTHPT) => {
    return axiosClient.get(`/thpt/${maTHPT}/hocsinh`);
  },
  searchHocSinhNgoaiTinh: (maTHPT, keyword) => {
    return axiosClient.get(`/thpt/${maTHPT}/hocsinh/search`, {
      params: { keyword: keyword },
    });
  },
  findThiSinh: (maTHPT) => {
    return axiosClient.get(`/thpt/${maTHPT}/thisinh`);
  },
  searchThiSinh: (maTHPT, keyword) => {
    return axiosClient.get(`/thpt/${maTHPT}/thisinh/search`, {
      params: { keyword: keyword },
    });
  },
  exportHocSinh: (maTHPT, axiosConfig) => {
    return axiosClient.get(`/thpt/${maTHPT}/hocsinh/export`, {
      responseType: "blob",
      ...axiosConfig,
    });
  },
  exportTKHocSinh: (maTHPT, axiosConfig) => {
    return axiosClient.get(`/thpt/${maTHPT}/hocsinh/taikhoan/export`, {
      responseType: "blob",
      ...axiosConfig,
    });
  },
  exportThiSinh: (maTHPT, axiosConfig) => {
    return axiosClient.get(`/thpt/${maTHPT}/thisinh/export`, {
      responseType: "blob",
      ...axiosConfig,
    });
  },
  exportKetQuaTuyenSinh: (maTHPT, axiosConfig) => {
    return axiosClient.get(`/thpt/${maTHPT}/kqts/export`, {
      responseType: "blob",
      ...axiosConfig,
    });
  },
  findInfoHocSinh: (maHS) => {
    return axiosClient.get(`/thpt/hocsinh/${maHS}`);
  },
  addHocSinh: (maTHPT, formData) => {
    return axiosClient.post(`/thpt/${maTHPT}/hocsinh`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  },
  importHocSinh: (maTHPT, excelFile, zipFile, onUploadProgress) => {
    const formData = new FormData();
    formData.append("excelFile", excelFile);
    formData.append("zipFile", zipFile);
    return axiosClient.post(`/thpt/${maTHPT}/hocsinh/import`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
      onUploadProgress: (event) => {
        if (onUploadProgress) onUploadProgress(event);
      },
    });
  },
  updateHocSinh: (maTHPT, maHS, formData) => {
    return axiosClient.patch(`/thpt/${maTHPT}/hocsinh/${maHS}`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  },
  deleteHocSinhNgoaiTinh: (maTHPT, maHS) => {
    return axiosClient.delete(`/thpt/${maTHPT}/hocsinh/${maHS}`);
  },
  deleteAllHocSinhNgoaiTinh: (maTHPT) => {
    return axiosClient.delete(`/thpt/${maTHPT}/hocsinh`);
  },
};

export default thptApi;
