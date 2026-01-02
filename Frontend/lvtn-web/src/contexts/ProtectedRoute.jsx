import { Navigate } from "react-router-dom";
import { useAuth } from "@/hooks";
import { Spinner } from "@/components";

export default function ProtectedRoute({ children, allowedRoles }) {
  const { user, loading } = useAuth();

  if (loading) return <Spinner />;

  if (!user) return <Navigate to="/" replace />;

  if (!allowedRoles.includes(user.maLoaiTK))
    return <Navigate to="/403" replace />;

  return children;
}
