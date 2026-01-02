import { useContext } from "react";
import { ThiSinhSGDDTContext } from "@/contexts";

export default function useThiSinhSGDDT() {
  const context = useContext(ThiSinhSGDDTContext);
  if (!context) {
    throw new Error(
      "useThiSinhSGDDT phải được sử dụng bên trong <ThiSinhSGDDTProvider>"
    );
  }
  return context;
}
