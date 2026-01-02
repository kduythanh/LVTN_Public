export default function GioiTinhDropdown({ id, value, onChange }) {
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
        -- Chọn giới tính --
      </option>
      <option key="false" value="false" title="Nam">
        Nam
      </option>
      <option key="true" value="true" title="Nữ">
        Nữ
      </option>
    </select>
  );
}
