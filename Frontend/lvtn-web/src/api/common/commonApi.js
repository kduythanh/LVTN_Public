import axiosClient from "../axiosClient";

const commonApi = {
  login: (username, password, captchaToken) => {
    const formData = new FormData();
    formData.append("tenTK", username);
    formData.append("matKhau", password);
    formData.append("captchaToken", captchaToken);
    return axiosClient.post("/common/login", formData, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  },
  logout: () => {
    return axiosClient.post("/common/logout");
  },
  changePassword: (tenTK, matKhauCu, matKhauMoi, XNMatKhauMoi) => {
    const formData = new FormData();
    formData.append("oldPassword", matKhauCu);
    formData.append("newPassword", matKhauMoi);
    formData.append("confirmNewPassword", XNMatKhauMoi);
    return axiosClient.post(`/common/password/${tenTK}`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  },
  getTHCS: () => {
    return axiosClient.get("/common/thcs");
  },
  getDTUT: () => {
    return axiosClient.get("/common/dtut");
  },
  getDTKK: () => {
    return axiosClient.get("/common/dtkk");
  },
  getDTKKChuyen: () => {
    return axiosClient.get("/common/dtkk-chuyen");
  },
  getDanToc: () => {
    return axiosClient.get("/common/dantoc");
  },
  getTHPT: () => {
    return axiosClient.get("/common/thpt");
  },
  getLopChuyen: (maTHPT) => {
    return axiosClient.get(`/common/lopchuyen/${maTHPT}`);
  },
  getChiTieuTHPT: () => {
    return axiosClient.get("/common/thpt/chitieu");
  },
  getChiTieuTHPTChuyen: () => {
    return axiosClient.get("/common/thpt/chitieu-chuyen");
  },
  getKetQuaThi: (hoiDongThi, loaiTC, noiDungTC, captchaToken) => {
    return axiosClient.get("/common/ketquathi", {
      params: { maTHPT: hoiDongThi, type: loaiTC, keyword: noiDungTC },
      headers: {
        "X-Captcha-Token": captchaToken,
      },
    });
  },
  getKQTBySBD: (soBaoDanh) => {
    return axiosClient.get(`/common/ketquathi/${soBaoDanh}`);
  },
  getAnhHocSinh: (maHS) => {
    return axiosClient.get(`/common/anh/${maHS}`, {
      responseType: "blob",
    });
  },
  getTrangThaiByMaTT: (maTT) => {
    return axiosClient.get(`/common/trangthai/${maTT}`);
  },
  updateTrangThai: (maTT, kieuDuLieu, newVal) => {
    return axiosClient.patch(`/common/trangthai/${maTT}`, null, {
      params: { kieuDuLieu, newVal },
    });
  },
};

export default commonApi;
