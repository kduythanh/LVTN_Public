import { useContext } from "react";
import { StateSGDDTContext } from "@/contexts";

export default function useStateSGDDT() {
  const context = useContext(StateSGDDTContext);
  if (!context) {
    throw new Error(
      "useStateSGDDT phải được sử dụng bên trong <StateSGDDTProvider>"
    );
  }
  return context;
}
