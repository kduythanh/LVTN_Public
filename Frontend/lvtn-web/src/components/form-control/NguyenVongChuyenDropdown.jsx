import { useEffect, useState } from "react";
import { commonApi } from "@/api";

export default function NguyenVongChuyenDropdown({ id, value, onChange }) {
  const [listTHPT, setListTHPT] = useState([]);

  useEffect(() => {
    const fetchTHPT = async () => {
      try {
        const res = await commonApi.getTHPT();
        const filtered = res.data.filter((item) => item.thptChuyen === true);
        setListTHPT(filtered);
      } catch (error) {
        console.error("Lỗi khi lấy danh sách THPT:", error);
      }
    };
    fetchTHPT();
  }, []);

  return (
    <select
      className="form-select focus-ring focus-ring-success"
      id={id}
      name={id}
      value={value}
      onChange={(e) => onChange(e.target.value)}
    >
      <option key="" value="">
        -- Chọn trường THPT --
      </option>
      {listTHPT.map((thpt) => (
        <option key={thpt.maTHPT} value={thpt.maTHPT} title={thpt.tenTHPT}>
          {thpt.tenTHPT}
        </option>
      ))}
    </select>
  );
}
