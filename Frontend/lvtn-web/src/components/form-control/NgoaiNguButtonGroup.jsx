import React from "react";
export default function NgoaiNguButtonGroup({ id, value, onChange }) {
  const options = ["Tiếng Anh", "Tiếng Pháp"];
  const handleToggle = (opt) => {
    if (value === opt) {
      onChange("");
    } else {
      onChange(opt);
    }
  };
  return (
    <div className="btn-group" role="group" aria-label={`Ngoại ngữ - ${id}`}>
      {options.map((opt) => (
        <React.Fragment key={opt}>
          <input
            type="checkbox"
            className="btn-check"
            name={id}
            id={`${id}-${opt}`}
            value={opt}
            checked={value === opt}
            onChange={() => handleToggle(opt)}
            autoComplete="off"
          />
          <label
            className={`btn btn-outline-success ${value === opt ? "text-white" : "text-black"}`}
            htmlFor={`${id}-${opt}`}
            style={{ width: "120px" }}
          >
            {opt}
          </label>
        </React.Fragment>
      ))}
    </div>
  );
}
