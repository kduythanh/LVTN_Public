export default function FormPartTitle({ title }) {
  return (
    <div
      className="row d-flex align-items-center bg-success text-white mb-3"
      style={{ height: "40px" }}
    >
      <strong>{title}</strong>
    </div>
  );
}
