import { useCallback } from "react";

export default function useFileDownload() {
  const downloadFile = useCallback(
    async (apiFunc, filename, mimeType, onProgress) => {
      const startTime = Date.now();
      try {
        let detectedMimeType = mimeType;
        if (!detectedMimeType && filename) {
          const ext = filename.split(".").pop().toLowerCase();
          switch (ext) {
            case "xlsx":
              detectedMimeType =
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
              break;
            case "xls":
              detectedMimeType = "application/vnd.ms-excel";
              break;
            case "docx":
              detectedMimeType =
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
              break;
            case "doc":
              detectedMimeType = "application/msword";
              break;
            case "pdf":
              detectedMimeType = "application/pdf";
              break;
            case "zip":
              detectedMimeType = "application/zip";
              break;
            case "csv":
              detectedMimeType = "text/csv";
              break;
            default:
              detectedMimeType = "application/octet-stream";
          }
        }
        // Gọi API qua callback để lấy axios config
        const res = await apiFunc({
          responseType: "blob",
          onDownloadProgress: (progressEvent) => {
            if (onProgress && progressEvent.total) {
              const percent = Math.round(
                (progressEvent.loaded * 100) / progressEvent.total
              );
              onProgress(percent);
            }
          },
        });

        const blob = new Blob([res], {
          type: detectedMimeType,
        });
        const elapsed = Date.now() - startTime;
        if (elapsed < 1000) {
          await new Promise((resolve) => setTimeout(resolve, 1000 - elapsed));
        }
        const url = window.URL.createObjectURL(blob);
        return { success: true, blob, url, filename };
      } catch (error) {
        console.error("File download failed:", error);
        return { success: false, error };
      }
    },
    []
  );

  return { downloadFile };
}
