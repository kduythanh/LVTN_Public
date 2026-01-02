import { useState, useEffect, useCallback } from "react";
import { commonApi } from "@/api";

export default function useImagePreview() {
  const defaultImageLink = "/images/default-avatar.jpg";
  const [preview, setPreview] = useState(defaultImageLink);

  const loadImage = useCallback(
    async (maHS) => {
      try {
        const res = await commonApi.getAnhHocSinh(maHS);
        if (res) {
          const url = URL.createObjectURL(res);
          setPreview(url);
        } else {
          setPreview(defaultImageLink);
        }
      } catch (error) {
        console.error("Không thể tải ảnh:", error);
        setPreview(defaultImageLink);
      }
    },
    [defaultImageLink]
  );

  useEffect(() => {
    return () => {
      if (preview && preview.startsWith("blob:")) {
        URL.revokeObjectURL(preview);
      }
    };
  }, [preview]);

  return { preview, loadImage };
}
