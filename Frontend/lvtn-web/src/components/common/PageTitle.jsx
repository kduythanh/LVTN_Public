export default function PageTitle({ title, subtitle, info }) {
  return (
    <div className="text-center">
      <div className="display-6 fw-bold my-2">{title}</div>
      {subtitle && <div className="fst-italic my-2">{subtitle}</div>}
      {info && <div className="fw-bold text-danger my-2">{info}</div>}
    </div>
  );
}
