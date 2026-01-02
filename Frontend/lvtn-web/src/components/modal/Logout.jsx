import { useRef, useEffect, useState } from "react";
import * as bootstrap from "bootstrap";
import { useAuth } from "@/hooks";
import { useNavigate } from "react-router-dom";
import { createPortal } from "react-dom";

export default function LogoutButton() {
  const { logout } = useAuth();
  const navigate = useNavigate();
  const modalRef = useRef(null);
  const bsModalRef = useRef(null);
  const [shouldLogout, setShouldLogout] = useState(false);

  useEffect(() => {
    if (modalRef.current) {
      bsModalRef.current = new bootstrap.Modal(modalRef.current, {
        backdrop: "static",
        keyboard: false,
      });
    }
    const modalEl = modalRef.current;
    const handleHidden = async () => {
      if (shouldLogout) {
        logout();
        navigate("/");
        setShouldLogout(false); // reset lại sau khi xong
      }
    };
    modalEl.addEventListener("hidden.bs.modal", handleHidden);
    return () => modalEl.removeEventListener("hidden.bs.modal", handleHidden);
  }, [logout, navigate, shouldLogout]);

  const openConfirm = () => bsModalRef.current?.show();
  const closeConfirm = () => bsModalRef.current?.hide();

  const handleLogout = () => {
    setShouldLogout(true);
    closeConfirm();
  };

  return (
    <>
      <button
        className="nav-link text-center py-0 flex-fill"
        onClick={openConfirm}
      >
        Đăng xuất
      </button>

      {createPortal(
        <div
          className="modal fade"
          ref={modalRef}
          tabIndex="-1"
          aria-hidden="true"
        >
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content">
              <div className="modal-header justify-content-center">
                <h5 className="modal-title">Xác nhận đăng xuất</h5>
              </div>
              <div className="modal-body">
                <p>Bạn có chắc chắn muốn đăng xuất không?</p>
              </div>
              <div className="modal-footer justify-content-center">
                <button
                  className="btn btn-secondary"
                  onClick={closeConfirm}
                  style={{ minWidth: "100px" }}
                >
                  Hủy
                </button>
                <button
                  className="btn btn-danger"
                  onClick={handleLogout}
                  style={{ minWidth: "100px" }}
                >
                  Đăng xuất
                </button>
              </div>
            </div>
          </div>
        </div>,
        document.body // portal trực tiếp ra ngoài body
      )}
    </>
  );
}
