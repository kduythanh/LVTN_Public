import { useState, useEffect } from "react";
import { commonApi } from "@/api";

export default function DTUTDropdown({ id, value, onChange }) {
  const [listDTUT, setListDTUT] = useState([]);

  useEffect(() => {
    const fetchDTUT = async () => {
      try {
        const res = await commonApi.getDTUT();
        setListDTUT(res.data);
      } catch (error) {
        console.error("Lỗi khi lấy danh sách DTUT:", error);
      }
    };
    fetchDTUT();
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
      {listDTUT.map((dtut) => (
        <option
          key={dtut.maDTUT}
          value={dtut.maDTUT}
          title={`${dtut.tenDTUT} (Điểm cộng: ${dtut.diemCong})`}
        >
          {dtut.tenDTUT.length > 95
            ? `${dtut.tenDTUT.slice(0, 95)}... (Điểm cộng: ${dtut.diemCong})`
            : `${dtut.tenDTUT} (Điểm cộng: ${dtut.diemCong})`}
        </option>
      ))}
    </select>
  );
}
