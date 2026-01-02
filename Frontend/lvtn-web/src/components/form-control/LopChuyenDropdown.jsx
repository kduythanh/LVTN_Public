import { useEffect, useState } from "react";
import { commonApi } from "@/api";

export default function LopChuyenDropdown({ maTHPT, id, value, onChange }) {
  const [listLopChuyen, setListLopChuyen] = useState([]);

  useEffect(() => {
    const fetchLopChuyen = async () => {
      if (!maTHPT) {
        setListLopChuyen([]);
        return;
      }
      try {
        setListLopChuyen([]);
        const res = await commonApi.getLopChuyen(maTHPT);
        setListLopChuyen(res.data);
      } catch (error) {
        console.error("Lỗi khi lấy danh sách lớp chuyên:", error);
      }
    };
    fetchLopChuyen();
  }, [maTHPT]);

  return (
    <select
      className="form-select focus-ring focus-ring-success"
      id={id}
      name={id}
      value={value}
      onChange={(e) => onChange(e.target.value)}
      disabled={!maTHPT}
    >
      <option key="" value="">
        -- Chọn lớp chuyên --
      </option>
      {listLopChuyen.map((lc) => (
        <option
          key={lc.maLopChuyen}
          value={lc.maLopChuyen}
          title={`${lc.tenLopChuyen} (Môn thi: ${lc.monChuyen})`}
        >
          {lc.tenLopChuyen} (Môn thi: {lc.monChuyen})
        </option>
      ))}
    </select>
  );
}
