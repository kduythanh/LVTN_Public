import { useContext } from "react";
import { HocSinhTHPTContext } from "@/contexts";

export default function useHocSinhTHPT() {
  const context = useContext(HocSinhTHPTContext);
  if (!context) {
    throw new Error(
      "useHocSinhTHPT phải được sử dụng bên trong <HocSinhTHPTProvider>"
    );
  }
  return context;
}
