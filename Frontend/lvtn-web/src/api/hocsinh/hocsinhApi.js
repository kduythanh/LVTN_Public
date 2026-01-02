import axiosClient from "../axiosClient";

const hocsinhApi = {
  findHocSinh: (maHS) => {
    return axiosClient.get(`/hocsinh/${maHS}`);
  },
  updateHocSinh: (maHS, formData) => {
    return axiosClient.patch(`/hocsinh/${maHS}`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  },
  findKetQuaTuyenSinh: (maHS) => {
    return axiosClient.get(`/hocsinh/ketquathi/${maHS}`);
  },
  exportWordHocSinh: (maHS, axiosConfig) => {
    return axiosClient.get(`/hocsinh/export-word/${maHS}`, {
      responseType: "blob",
      ...axiosConfig,
    });
  },
};

export default hocsinhApi;
