import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";
import { useEffect } from "react";
import {
  Header,
  Footer,
  ScrollToTop,
  ScrollToTopButton,
} from "@/components/common";
import AppRoutes from "@/AppRoutes";

export default function App() {
  useEffect(() => {
    const updateHeaderHeight = () => {
      const header = document.querySelector("header");
      if (header) {
        document.documentElement.style.setProperty(
          "--header-height",
          `${header.offsetHeight}px`
        );
      }
    };
    updateHeaderHeight();
    window.addEventListener("resize", updateHeaderHeight);
    return () => window.removeEventListener("resize", updateHeaderHeight);
  }, []);

  return (
    <>
      <div>
        <header className="sticky-top">
          <Header />
        </header>
        <ScrollToTop />
        <main>
          <AppRoutes />
        </main>
        <footer>
          <Footer />
        </footer>
      </div>
      <ScrollToTopButton />
    </>
  );
}
