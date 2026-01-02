export default function TextInput({
  id,
  value,
  placeholder,
  onChange,
  required,
  disabled,
}) {
  return (
    <input
      type="text"
      className="form-control focus-ring focus-ring-success"
      id={id}
      name={id}
      value={value}
      placeholder={placeholder}
      onChange={(e) => onChange(e.target.value)}
      required={required}
      disabled={disabled}
    />
  );
}
