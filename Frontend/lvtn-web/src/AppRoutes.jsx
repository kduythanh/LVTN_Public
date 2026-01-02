import { Routes, Route } from "react-router-dom";
import {
  ProtectedRoute,
  ThiSinhSGDDTProvider,
  HocSinhTHCSProvider,
  HocSinhTHPTProvider,
  StateSGDDTProvider,
  AccountAdminProvider,
  ThiSinhTHPTProvider,
} from "@/contexts";
import {
  Homepage,
  SearchResult,
  AdmissionInfo,
  Information,
  Login,
  NotFound,
  Forbidden,
} from "@/pages";
import {
  AdminHomepage,
  AdminAccountManagement,
  AdminChangePassword,
  AdminAddAccount,
} from "@/pages/Admin";
import {
  HDTSHomepage,
  HDTSThiSinhManagement,
  HDTSChangePassword,
  HDTSUpdateScore,
  HDTSStateManagement,
} from "@/pages/SGDDT";
import {
  THCSHomepage,
  THCSHocSinhManagement,
  THCSChangePassword,
  THCSHocSinhDetail,
  THCSAddHocSinh,
  THCSUpdateHocSinh,
  THCSInitHocSinh,
} from "@/pages/THCS";
import {
  THPTHomepage,
  THPTChangePassword,
  THPTHocSinhManagement,
  THPTThiSinhManagement,
  THPTHocSinhDetail,
  THPTAddHocSinh,
  THPTUpdateHocSinh,
} from "@/pages/THPT";
import {
  HocSinhHomepage,
  HocSinhDetail,
  HocSinhChangePassword,
  UpdateHocSinh,
  HocSinhAdmissionResult,
} from "@/pages/HocSinh";

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Homepage />} />
      <Route path="/search-result" element={<SearchResult />} />
      <Route path="/admission-info" element={<AdmissionInfo />} />
      <Route path="/information" element={<Information />} />
      <Route path="/login" element={<Login />} />

      {/* Các route của admin */}
      <Route
        path="/admin"
        element={
          <ProtectedRoute allowedRoles={[0]}>
            <AdminHomepage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/admin/account/overview"
        element={
          <ProtectedRoute allowedRoles={[0]}>
            <AccountAdminProvider>
              <AdminAccountManagement />
            </AccountAdminProvider>
          </ProtectedRoute>
        }
      />
      <Route
        path="/admin/change-password"
        element={
          <ProtectedRoute allowedRoles={[0]}>
            <AdminChangePassword />
          </ProtectedRoute>
        }
      />
      <Route
        path="/admin/add-account"
        element={
          <ProtectedRoute allowedRoles={[0]}>
            <AdminAddAccount />
          </ProtectedRoute>
        }
      />

      {/* Các route của HĐTS-SGDĐT */}
      <Route
        path="/sgddt"
        element={
          <ProtectedRoute allowedRoles={[1]}>
            <HDTSHomepage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/sgddt/change-password"
        element={
          <ProtectedRoute allowedRoles={[1]}>
            <HDTSChangePassword />
          </ProtectedRoute>
        }
      />
      <Route
        path="/sgddt/thisinh/overview"
        element={
          <ProtectedRoute allowedRoles={[1]}>
            <ThiSinhSGDDTProvider>
              <HDTSThiSinhManagement />
            </ThiSinhSGDDTProvider>
          </ProtectedRoute>
        }
      />
      <Route
        path="/sgddt/thisinh/:soBaoDanh/update-score"
        element={
          <ProtectedRoute allowedRoles={[1]}>
            <HDTSUpdateScore />
          </ProtectedRoute>
        }
      />
      <Route
        path="/sgddt/trang-thai"
        element={
          <ProtectedRoute allowedRoles={[1]}>
            <StateSGDDTProvider>
              <HDTSStateManagement />
            </StateSGDDTProvider>
          </ProtectedRoute>
        }
      />

      {/* Các route của thcs */}
      <Route
        path="/thcs"
        element={
          <ProtectedRoute allowedRoles={[2]}>
            <THCSHomepage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/thcs/hocsinh/overview"
        element={
          <ProtectedRoute allowedRoles={[2]}>
            <HocSinhTHCSProvider>
              <THCSHocSinhManagement />
            </HocSinhTHCSProvider>
          </ProtectedRoute>
        }
      />
      <Route
        path="/thcs/change-password"
        element={
          <ProtectedRoute allowedRoles={[2]}>
            <THCSChangePassword />
          </ProtectedRoute>
        }
      />
      <Route
        path="/thcs/hocsinh/detail/:maHS"
        element={
          <ProtectedRoute allowedRoles={[2]}>
            <THCSHocSinhDetail />
          </ProtectedRoute>
        }
      />
      <Route
        path="/thcs/hocsinh/init"
        element={
          <ProtectedRoute allowedRoles={[2]}>
            <THCSInitHocSinh />
          </ProtectedRoute>
        }
      />
      <Route
        path="/thcs/hocsinh/add"
        element={
          <ProtectedRoute allowedRoles={[2]}>
            <THCSAddHocSinh />
          </ProtectedRoute>
        }
      />
      <Route
        path="/thcs/hocsinh/update/:maHS"
        element={
          <ProtectedRoute allowedRoles={[2]}>
            <THCSUpdateHocSinh />
          </ProtectedRoute>
        }
      />

      {/* Các route của thpt */}
      <Route
        path="/thpt"
        element={
          <ProtectedRoute allowedRoles={[3]}>
            <THPTHomepage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/thpt/change-password"
        element={
          <ProtectedRoute allowedRoles={[3]}>
            <THPTChangePassword />
          </ProtectedRoute>
        }
      />
      <Route
        path="/thpt/hocsinh/overview"
        element={
          <ProtectedRoute allowedRoles={[3]}>
            <HocSinhTHPTProvider>
              <THPTHocSinhManagement />
            </HocSinhTHPTProvider>
          </ProtectedRoute>
        }
      />
      <Route
        path="/thpt/hocsinh/detail/:maHS"
        element={
          <ProtectedRoute allowedRoles={[3]}>
            <THPTHocSinhDetail />
          </ProtectedRoute>
        }
      />
      <Route
        path="/thpt/hocsinh/add"
        element={
          <ProtectedRoute allowedRoles={[3]}>
            <THPTAddHocSinh />
          </ProtectedRoute>
        }
      />
      <Route
        path="/thpt/hocsinh/update/:maHS"
        element={
          <ProtectedRoute allowedRoles={[3]}>
            <THPTUpdateHocSinh />
          </ProtectedRoute>
        }
      />
      <Route
        path="/thpt/thisinh/overview"
        element={
          <ProtectedRoute allowedRoles={[3]}>
            <ThiSinhTHPTProvider>
              <THPTThiSinhManagement />
            </ThiSinhTHPTProvider>
          </ProtectedRoute>
        }
      />

      {/* Các route của hocsinh */}
      <Route
        path="/hocsinh"
        element={
          <ProtectedRoute allowedRoles={[4]}>
            <HocSinhHomepage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/hocsinh/detail"
        element={
          <ProtectedRoute allowedRoles={[4]}>
            <HocSinhDetail />
          </ProtectedRoute>
        }
      />
      <Route
        path="/hocsinh/change-password"
        element={
          <ProtectedRoute allowedRoles={[4]}>
            <HocSinhChangePassword />
          </ProtectedRoute>
        }
      />
      <Route
        path="/hocsinh/update"
        element={
          <ProtectedRoute allowedRoles={[4]}>
            <UpdateHocSinh />
          </ProtectedRoute>
        }
      />
      <Route
        path="/hocsinh/result"
        element={
          <ProtectedRoute allowedRoles={[4]}>
            <HocSinhAdmissionResult />
          </ProtectedRoute>
        }
      />

      <Route path="/403" element={<Forbidden />} />
      <Route path="*" element={<NotFound />} />
    </Routes>
  );
}
