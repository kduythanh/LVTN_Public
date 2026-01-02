import axios from "axios";

const axiosClient = axios.create({
  baseURL: "/api", // URL gốc backend
});

// Thêm interceptors để tự động gắn token nếu có
axiosClient.interceptors.request.use(
  (config) => {
    const authString = localStorage.getItem("auth");
    let tokenValue = null;
    if (authString) {
      try {
        // 2. Parse chuỗi JSON thành Object
        const authObject = JSON.parse(authString);

        // 3. Trích xuất giá trị token thực sự
        if (authObject && authObject.token) {
          tokenValue = authObject.token;
          console.log("Token trích xuất thành công!");
        }
      } catch (e) {
        console.error("Lỗi parse JSON auth từ Local Storage:", e);
      }
    }
    if (tokenValue) {
      config.headers.Authorization = `Bearer ${tokenValue}`;
      console.log("Sending request with header:", config.headers.Authorization);
    } else {
      console.warn("Không tìm thấy token để attach vào header!");
    }
    return config;
  },
  (error) => Promise.reject(error)
);

axiosClient.interceptors.response.use(
  (response) => response.data, // tự động lấy response.data
  (error) => {
    // xử lý lỗi chung
    console.error(error);
    throw error;
  }
);

export default axiosClient;
