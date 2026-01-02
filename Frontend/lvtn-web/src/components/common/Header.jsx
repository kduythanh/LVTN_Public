import { Link } from "react-router-dom";
import { useAuth } from "@/hooks";
import { LogoutButton } from "@/components/modal";
export function Greeting({ content }) {
  return <div className="nav-link text-end fw-bold">{content}</div>;
}

export function ChangePasswordButton({ link }) {
  return (
    <Link to={link} className="nav-link text-center py-0 flex-fill">
      Đổi mật khẩu
    </Link>
  );
}

export function NavMenu() {
  const { user } = useAuth();
  if (user === null) return <NavMenuNoLoggedIn />;
  else {
    if (user.maLoaiTK === 0) return <NavMenuAdmin user={user} />;
    else if (user.maLoaiTK === 1) return <NavMenuSGDDT user={user} />;
    else if (user.maLoaiTK === 2) return <NavMenuTHCS user={user} />;
    else if (user.maLoaiTK === 3) return <NavMenuTHPT user={user} />;
    else if (user.maLoaiTK === 4) return <NavMenuHocSinh user={user} />;
    else return <NavMenuNoLoggedIn />;
  }
}

export function LogoSection() {
  return (
    <div className="col-sm-12 col-xl d-flex px-0">
      <div className="align-content-center">
        <img src="/images/SGDDT.png" alt="logo" style={{ height: "70px" }} />
      </div>
      <div className="ps-2 align-content-center">
        <a className="nav-link" href="#">
          <div className="mb-0 fs-6">
            SỞ GIÁO DỤC VÀ ĐÀO TẠO THÀNH PHỐ CẦN THƠ
          </div>
          <div className="mb-0 fw-bold fs-5">
            HỆ THỐNG TUYỂN SINH LỚP 10 THPT TRỰC TUYẾN
          </div>
        </a>
      </div>
    </div>
  );
}

export function NavItem({ link, label, icon }) {
  return (
    <div
      className="nav-item align-content-center flex-fill"
      style={{ minHeight: "70px" }}
    >
      <Link to={link} className="nav-link text-center fs-5">
        {icon && <i className={icon}></i>} {label}
      </Link>
    </div>
  );
}

export function NavMenuNoLoggedIn() {
  return (
    <div
      className="col-sm-12 col-xl d-flex justify-content-between px-0"
      style={{ minHeight: "70px" }}
    >
      <NavItem link="/" icon="bi bi-house-door-fill" />
      <NavItem link="/search-result" label="Tra cứu kết quả" />
      <NavItem link="/admission-info" label="Chỉ tiêu tuyển sinh" />
      <NavItem link="/information" label="Văn bản về kỳ thi" />
      <NavItem link="/login" label="Đăng nhập" />
    </div>
  );
}

export function NavMenuAdmin({ user }) {
  return (
    <div
      className="col-sm-12 col-xl d-flex justify-content-between px-0"
      style={{ minHeight: "70px" }}
    >
      <NavItem link="/admin" icon="bi bi-house-door-fill" />
      <NavItem link="/admin/account/overview" label="Quản lý tài khoản" />
      <div
        className="d-flex flex-column justify-content-center px-1"
        style={{ minWidth: "250px" }}
      >
        <Greeting content={`Xin chào, ${user?.tenDinhDanh || "Admin"}`} />
        <div className="d-flex flex-row justify-content-between fs-5">
          <ChangePasswordButton link={"/admin/change-password"} />
          <span>|</span>
          <LogoutButton />
        </div>
      </div>
    </div>
  );
}

export function NavMenuSGDDT({ user }) {
  return (
    <div
      className="col-sm-12 col-xl d-flex justify-content-between px-0"
      style={{ minHeight: "70px" }}
    >
      <NavItem link="/sgddt" icon="bi bi-house-door-fill" />
      <NavItem link="/sgddt/thisinh/overview" label="Quản lý thí sinh" />
      <NavItem link="/sgddt/trang-thai" label="Quản lý trạng thái" />
      <div className="d-flex flex-column justify-content-center px-1">
        <Greeting
          content={`Xin chào, ${user?.tenDinhDanh || "Sở GD&ĐT TP Cần Thơ"}`}
        />
        <div className="d-flex flex-row justify-content-between fs-5">
          <ChangePasswordButton link={"/sgddt/change-password"} />
          <span>|</span>
          <LogoutButton />
        </div>
      </div>
    </div>
  );
}

export function NavMenuTHCS({ user }) {
  return (
    <div
      className="col-sm-12 col-xl d-flex justify-content-between px-0"
      style={{ minHeight: "70px" }}
    >
      <NavItem link="/thcs" icon="bi bi-house-door-fill" />
      <NavItem link="/thcs/hocsinh/overview" label="Quản lý học sinh" />
      <div className="d-flex flex-column justify-content-center px-1">
        <Greeting content={`Xin chào, ${user?.tenDinhDanh || "Trường THCS"}`} />
        <div className="d-flex flex-row justify-content-end fs-5">
          <ChangePasswordButton link={"/thcs/change-password"} />
          <span>|</span>
          <LogoutButton />
        </div>
      </div>
    </div>
  );
}

export function NavMenuTHPT({ user }) {
  return (
    <div
      className="col-sm-12 col-xl d-flex justify-content-between px-0"
      style={{ minHeight: "70px" }}
    >
      <NavItem link="/thpt" icon="bi bi-house-door-fill" />
      {user.tsNgoaiTPCT == true ? (
        <NavItem link="/thpt/hocsinh/overview" label="Quản lý hồ sơ" />
      ) : (
        ""
      )}
      <NavItem link="/thpt/thisinh/overview" label="Quản lý thí sinh" />
      <div className="d-flex flex-column justify-content-center px-1">
        <Greeting content={`Xin chào, ${user?.tenDinhDanh || "Trường THPT"}`} />
        <div className="d-flex flex-row justify-content-between fs-5">
          <ChangePasswordButton link={"/thpt/change-password"} />
          <span>|</span>
          <LogoutButton />
        </div>
      </div>
    </div>
  );
}

export function NavMenuHocSinh({ user }) {
  return (
    <div
      className="col-sm-12 col-xl d-flex justify-content-between px-0"
      style={{ minHeight: "50px" }}
    >
      <NavItem link="/hocsinh" icon="bi bi-house-door-fill" />
      <NavItem link="/hocsinh/detail" label="Thông tin học sinh" />
      <NavItem link="/hocsinh/result" label="Kết quả" />
      <div className="d-flex flex-column justify-content-center px-1">
        <Greeting
          content={`Xin chào, ${user?.tenDinhDanh || "Học sinh"} (${user?.soDinhDanh || ""})`}
        />
        <div className="d-flex flex-row justify-content-between fs-5">
          <ChangePasswordButton link={"/hocsinh/change-password"} />
          <span>|</span>
          <LogoutButton />
        </div>
      </div>
    </div>
  );
}

export default function Header() {
  return (
    <div className="container-fluid" style={{ zIndex: "1000" }}>
      <div className="row bg-success text-light">
        <LogoSection />
        <NavMenu />
      </div>
    </div>
  );
}
