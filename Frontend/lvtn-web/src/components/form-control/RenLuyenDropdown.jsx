export default function RenLuyenDropdown({ id, value, onChange }) {
  return (
    <select
      className="form-select d-inline-block w-auto focus-ring focus-ring-success"
      id={id}
      value={value}
      name={id}
      onChange={(e) => onChange(e.target.value)}
      required
    >
      <option key="" value="">
        -- Lựa chọn --
      </option>
      <option key="Tốt" value="Tốt">
        Tốt
      </option>
      <option key="Khá" value="Khá">
        Khá
      </option>
      <option key="Đạt" value="Đạt">
        Đạt
      </option>
    </select>
  );
}
