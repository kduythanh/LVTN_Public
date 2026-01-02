import React from "react";
export default function RenLuyenButtonGroup({ id, value, onChange }) {
  const options = ["Tốt", "Khá", "Đạt"];
  const handleToggle = (opt) => {
    if (value === opt) {
      onChange("");
    } else {
      onChange(opt);
    }
  };
  return (
    <>
      <div className="form-label my-auto mx-2">
        <label htmlFor={`${id}-Tốt`}>Rèn luyện:</label>
      </div>
      <div
        className="btn-group mx-2"
        role="group"
        aria-label={`Rèn luyện lớp ${id}`}
      >
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
              style={{ width: "60px" }}
            >
              {opt}
            </label>
          </React.Fragment>
        ))}
      </div>
    </>
  );
}
