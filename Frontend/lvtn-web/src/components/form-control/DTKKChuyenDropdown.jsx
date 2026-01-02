import { useState, useEffect } from "react";
import { commonApi } from "@/api";

export default function DTKKChuyenDropdown({ id, value, onChange, disabled }) {
  const [listDTKKChuyen, setListDTKKChuyen] = useState([]);

  useEffect(() => {
    const fetchDTKKChuyen = async () => {
      try {
        const res = await commonApi.getDTKKChuyen();
        setListDTKKChuyen(res.data);
      } catch (error) {
        console.error("Lỗi khi lấy danh sách DTKK chuyên:", error);
      }
    };
    fetchDTKKChuyen();
  }, []);

  return (
    <select
      className="form-select focus-ring focus-ring-success"
      id={id}
      name={id}
      value={value}
      onChange={(e) => onChange(e.target.value)}
      required
      disabled={disabled}
    >
      <option key="" value="">
        -- Chọn đối tượng --
      </option>
      {listDTKKChuyen.map((dtkkc) => (
        <option
          key={dtkkc.maDTKKChuyen}
          value={dtkkc.maDTKKChuyen}
          title={`${dtkkc.tenDTKKChuyen} (Điểm cộng: ${dtkkc.diemCong})`}
        >
          {dtkkc.tenDTKKChuyen.length > 95
            ? `${dtkkc.tenDTKKChuyen.slice(0, 95)}... (Điểm cộng: ${dtkkc.diemCong})`
            : `${dtkkc.tenDTKKChuyen} (Điểm cộng: ${dtkkc.diemCong})`}
        </option>
      ))}
    </select>
  );
}
