export default function NoData({ content }) {
  return (
    <div className="d-flex flex-column justify-content-center align-items-center flex-fill w-100 h-100 overflow-hidden my-2 bg-body-secondary">
      <div className="fs-4 text-danger fw-bold text-center h-100">
        {content}
      </div>
    </div>
  );
}
