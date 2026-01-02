import { useEffect, useState } from "react";
import { commonApi } from "@/api";
import { PageTitle, Spinner } from "@/components";

export function THPTCard({ thpt }) {
  return (
    <div className="card border-black d-flex flex-column h-100">
      <div className="card-header bg-success text-white fw-bold text-center mb-auto">
        {thpt.tenTHPT}
      </div>
      <div className="card-body flex-fill">
        <div className="card-text m-0 p-0">
          <strong>Địa chỉ:</strong> {thpt.diaChi}
        </div>
      </div>
      <div className="card-footer mt-auto bg-success-subtle">
        Số lượng đăng ký:{" "}
        <strong>
          {thpt.soLuongDangKy}/{thpt.chiTieu}
        </strong>{" "}
        chỉ tiêu
      </div>
    </div>
  );
}

export function ChuyenCard({ lc, bg, text }) {
  return (
    <div className="card border-black d-flex flex-column h-100">
      <div className={`card-header fw-bold text-center ${bg} ${text} `}>
        {lc.tenLopChuyen}
      </div>
      <div className={`card-footer ${bg}-subtle`}>
        Số lượng đăng ký:{" "}
        <strong>
          {lc.soLuongDangKy}/{lc.chiTieu}
        </strong>{" "}
        chỉ tiêu
      </div>
    </div>
  );
}

export function AdmissionContent({ listTHPT }) {
  return (
    <div className="container-fluid">
      <div className="row">
        {listTHPT.map((thpt, index) => (
          <div
            key={index}
            className="col-12 col-md-6 col-lg-4 my-3" // 1 cột trên mobile, 2 trên tablet, 3 trên desktop
          >
            <THPTCard thpt={thpt} />
          </div>
        ))}
      </div>
    </div>
  );
}

export function TableChuyen({ list, bg, text }) {
  return (
    <div className="container-fluid">
      <div className="row">
        {list.map((lc, index) => (
          <div
            key={index}
            className="col-6 col-md-4 col-lg-3 my-3" // 1 cột trên mobile, 2 trên tablet, 3 trên desktop
          >
            <ChuyenCard lc={lc} bg={bg} text={text} />
          </div>
        ))}
      </div>
    </div>
  );
}

export function AdmissionListChuyen({ listC1, listC2, listC3 }) {
  return (
    <>
      <h4>Trường THPT Chuyên Lý Tự Trọng (phường Cái Răng)</h4>
      <TableChuyen list={listC1} bg={"bg-primary"} text={"text-white"} />
      <h4>Trường THPT Chuyên Vị Thanh (phường Vị Thanh)</h4>
      <TableChuyen list={listC2} bg={"bg-info"} />
      <h4>Trường THPT Chuyên Nguyễn Thị Minh Khai (phường Sóc Trăng)</h4>
      <TableChuyen list={listC3} bg={"bg-warning"} />
    </>
  );
}
export function AdmissionDetail() {
  const [loading, setLoading] = useState(false);
  const [listTHPT, setListTHPT] = useState([]);
  const [listC1, setListC1] = useState([]);
  const [listC2, setListC2] = useState([]);
  const [listC3, setListC3] = useState([]);
  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const [resTHPT, resTHPTChuyen] = await Promise.all([
          commonApi.getChiTieuTHPT(),
          commonApi.getChiTieuTHPTChuyen(),
        ]);
        await new Promise((resolve) => setTimeout(resolve, 500));
        setListTHPT(resTHPT.data);
        const dataChuyen = resTHPTChuyen.data;
        setListC1(dataChuyen.filter((item) => item.maTHPT === "92000F01"));
        setListC2(dataChuyen.filter((item) => item.maTHPT === "93000F16"));
        setListC3(dataChuyen.filter((item) => item.maTHPT === "94000706"));
      } catch (error) {
        console.error("Lỗi khi tải dữ liệu: ", error);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, []);

  if (loading) return <Spinner />;
  return (
    <div className="container-fluid overflow-auto">
      <h3 className="text-danger">
        I. Chỉ tiêu của các trường THPT công lập trên địa bàn TP Cần Thơ
      </h3>
      <h5 className="fst-italic fw-normal">
        (Các trường THPT chuyên xem chi tiết ở phía dưới)
      </h5>
      <AdmissionContent listTHPT={listTHPT} />
      <h3 className="text-danger">
        II. Chỉ tiêu của các trường THPT chuyên trên địa bàn TP Cần Thơ
      </h3>
      <AdmissionListChuyen listC1={listC1} listC2={listC2} listC3={listC3} />
    </div>
  );
}

export default function AdmissionInfo() {
  useEffect(() => {
    document.title = "Chỉ tiêu tuyển sinh";
  }, []);

  return (
    <div className="container-fluid container-md long-page">
      <PageTitle title={"THÔNG TIN CHỈ TIÊU CÁC TRƯỜNG THPT"} />
      <AdmissionDetail />
    </div>
  );
}
