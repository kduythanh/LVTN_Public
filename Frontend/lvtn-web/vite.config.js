import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import path from "path";
import { fileURLToPath } from "url";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  base: "/",
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "src"),
    },
  },
  server: {
    port: 5173,
    proxy: {
      // Proxy tất cả request bắt đầu bằng /api sang backend Spring Boot
      "/api": {
        target: "http://localhost:8080",
        changeOrigin: true,
        secure: false,
        configure: (proxy) => {
          proxy.on("proxyReq", (proxyReq) => {
            // Log header trước khi gửi
            console.log(
              "ProxyReq headers:",
              proxyReq.getHeader("authorization")
            );
          });
          proxy.on("proxyRes", (proxyRes) => {
            // Log phản hồi từ backend
            console.log("ProxyRes status:", proxyRes.statusCode);
          });
        },
      },
      // Proxy các request tải file template
      "/templates": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
    },
  },
});
