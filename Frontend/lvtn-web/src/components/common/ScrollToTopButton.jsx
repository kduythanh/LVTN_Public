import { useEffect, useState } from "react";

export default function ScrollToTopButton() {
  const [visible, setVisible] = useState(false);

  // Theo dõi vị trí cuộn trang
  useEffect(() => {
    const handleScroll = () => {
      if (window.scrollY > 50) setVisible(true);
      else setVisible(false);
    };
    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  // Khi bấm nút
  const scrollToTop = () => {
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  return (
    <button
      onClick={scrollToTop}
      className="btn btn-success shadow"
      style={{
        position: "fixed",
        bottom: "30px",
        right: "30px",
        borderRadius: "10%",
        width: "50px",
        height: "50px",
        fontSize: "20px",
        opacity: visible ? 0.8 : 0,
        visibility: visible ? "visible" : "hidden",
        transition: "opacity 0.4s, visibility 0.4s",
        zIndex: "12000",
      }}
      title="Lên đầu trang"
    >
      <i className="bi bi-arrow-up"></i>
    </button>
  );
}
