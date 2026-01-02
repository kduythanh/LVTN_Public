import { useContext } from "react";
import { AuthContext } from "@/contexts";

export default function useAuth() {
  const context = useContext(AuthContext);
  if (!context)
    throw new Error("useAuth phải được sử dụng bên trong <AuthProvider>");
  return context;
}
