export default function NgoaiNguDropdown({ id, value, onChange }) {
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
        -- Lựa chọn ngoại ngữ --
      </option>
      <option key="Tiếng Anh" value="Tiếng Anh" title="Tiếng Anh">
        Tiếng Anh
      </option>
      <option key="Tiếng Pháp" value="Tiếng Pháp" title="Tiếng Pháp">
        Tiếng Pháp
      </option>
    </select>
  );
}
