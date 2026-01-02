import { useState, useEffect, useRef, useCallback } from "react";
import { commonApi } from "@/api";

export default function useImageUpload() {
  const defaultImageLink = "/images/default-avatar.jpg";
  const [anhDaiDien, setAnhDaiDien] = useState(null);
  const [preview, setPreview] = useState(defaultImageLink);
  const fileInputRef = useRef(null);

  const handleImageChange = useCallback((e) => {
    const file = e.target.files[0];
    if (file) {
      setAnhDaiDien(file);
      setPreview(file ? URL.createObjectURL(file) : "");
    }
  }, []);

  const resetImage = useCallback(() => {
    setAnhDaiDien(null);
    setPreview(defaultImageLink);
    if (fileInputRef.current) fileInputRef.current.value = "";
  }, [defaultImageLink]);

  const setInitialImage = useCallback(
    async (maHS) => {
      try {
        const res = await commonApi.getAnhHocSinh(maHS);
        if (res) {
          const imageUrl = URL.createObjectURL(res);
          setPreview(imageUrl);
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

  return {
    anhDaiDien,
    preview,
    fileInputRef,
    handleImageChange,
    setAnhDaiDien,
    setPreview,
    resetImage,
    setInitialImage,
  };
}
