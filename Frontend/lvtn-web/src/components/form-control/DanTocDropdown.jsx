import { useEffect, useState } from "react";
import { commonApi } from "@/api";

export default function DanTocDropdown({ id, value, onChange }) {
  const [listDT, setListDT] = useState([]);

  useEffect(() => {
    const fetchDanToc = async () => {
      try {
        const res = await commonApi.getDanToc();
        setListDT(res.data);
      } catch (error) {
        console.error("Lỗi khi lấy danh sách dân tộc:", error);
      }
    };
    fetchDanToc();
  }, []);

  return (
    <select
      className="form-select focus-ring focus-ring-success"
      id={id}
      name={id}
      value={value}
      onChange={(e) => onChange(e.target.value)}
      required
    >
      <option key="" value="">
        -- Chọn dân tộc --
      </option>
      {listDT.map((dt) => (
        <option key={dt.maDT} value={dt.maDT}>
          {dt.tenDT}
        </option>
      ))}
    </select>
  );
}
