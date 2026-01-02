import { useContext } from "react";
import { AccountAdminContext } from "@/contexts";

export default function useAccountAdmin() {
  const context = useContext(AccountAdminContext);
  if (!context) {
    throw new Error(
      "useAccountAdmin phải được sử dụng bên trong <AccountAdminProvider>"
    );
  }
  return context;
}
