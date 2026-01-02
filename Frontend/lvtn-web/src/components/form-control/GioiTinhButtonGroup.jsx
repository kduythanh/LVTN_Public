import React from "react";
export default function GioiTinhButtonGroup({ id, value, onChange }) {
  const options = [
    { label: "Nam", val: false },
    { label: "Nữ", val: true },
  ];
  const handleToggle = (optVal) => {
    if (value === optVal) {
      onChange("");
    } else {
      onChange(optVal);
    }
  };
  return (
    <div className="btn-group" role="group" aria-label={`Giới tính - ${id}`}>
      {options.map(({ label, val }) => (
        <React.Fragment key={label}>
          <input
            type="checkbox"
            className="btn-check"
            name={id}
            id={`${id}-${label}`}
            value={val}
            checked={value === val}
            onChange={() => handleToggle(val)}
            autoComplete="off"
          />
          <label
            className={`btn btn-outline-success ${value === val ? "text-white" : "text-black"}`}
            htmlFor={`${id}-${label}`}
            style={{ width: "60px" }}
          >
            {label}
          </label>
        </React.Fragment>
      ))}
    </div>
  );
}
