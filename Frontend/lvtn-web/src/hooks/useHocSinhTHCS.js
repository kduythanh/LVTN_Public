import { useContext } from "react";
import { HocSinhTHCSContext } from "@/contexts";

export default function useHocSinhTHCS() {
  const context = useContext(HocSinhTHCSContext);
  if (!context) {
    throw new Error(
      "useHocSinhTHCS phải được sử dụng bên trong <HocSinhTHCSProvider>"
    );
  }
  return context;
}
