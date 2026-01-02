export default function ImageInput({
  id,
  ref,
  onChange,
  preview,
  showRemove = false,
  removeChecked = false,
  onRemoveChange,
}) {
  return (
    <>
      <input
        type="file"
        className="form-control focus-ring focus-ring-success"
        id={id}
        name={id}
        accept="image/*"
        onChange={onChange}
        ref={ref}
      />
      {showRemove && (
        <div className="form-check mt-2">
          <input
            type="checkbox"
            className="form-check-input focus-ring focus-ring-danger border-danger"
            id={`${id}-remove`}
            checked={removeChecked}
            onChange={(e) => onRemoveChange?.(e.target.checked)}
            style={{
              backgroundColor: removeChecked ? "#dc3545" : "",
              borderColor: removeChecked ? "#dc3545" : "",
            }}
          />
          <label className="form-check-label" htmlFor={`${id}-remove`}>
            Xóa ảnh hiện tại
          </label>
        </div>
      )}
      <div
        className="mt-2"
        style={{
          width: "90px",
          aspectRatio: "3 / 4",
          overflow: "hidden",
        }}
      >
        <img
          src={preview || "/uploads/hocsinh/default-avatar.jpg"}
          alt="Xem trước ảnh đại diện"
          className="img-thumbnail object-fit-cover w-100 h-100"
        />
      </div>
    </>
  );
}
