import { useState, useEffect } from "react";
import { commonApi } from "@/api";

export default function DTKKDropdown({ id, value, onChange }) {
  const [listDTKK, setListDTKK] = useState([]);

  useEffect(() => {
    const fetchDTKK = async () => {
      try {
        const res = await commonApi.getDTKK();
        setListDTKK(res.data);
      } catch (error) {
        console.error("Lỗi khi lấy danh sách DTKK:", error);
      }
    };
    fetchDTKK();
  }, []);

  return (
    <select
      className="form-select focus-ring focus-ring-success"
      id={id}
      value={value}
      name={id}
      onChange={(e) => onChange(e.target.value)}
      required
    >
      <option key="" value="">
        -- Chọn đối tượng --
      </option>
      {listDTKK.map((dtkk) => (
        <option
          key={dtkk.maDTKK}
          value={dtkk.maDTKK}
          title={`${dtkk.tenDTKK} (Điểm cộng: ${dtkk.diemCong})`}
        >
          {dtkk.tenDTKK.length > 95
            ? `${dtkk.tenDTKK.slice(0, 95)}... (Điểm cộng: ${dtkk.diemCong})`
            : `${dtkk.tenDTKK} (Điểm cộng: ${dtkk.diemCong})`}
        </option>
      ))}
    </select>
  );
}
