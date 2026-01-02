import { useContext } from "react";
import { ThiSinhTHPTContext } from "@/contexts";

export default function useThiSinhTHPT() {
  const context = useContext(ThiSinhTHPTContext);
  if (!context) {
    throw new Error(
      "useThiSinhTHPT phải được sử dụng bên trong <ThiSinhTHPTProvider>"
    );
  }
  return context;
}
