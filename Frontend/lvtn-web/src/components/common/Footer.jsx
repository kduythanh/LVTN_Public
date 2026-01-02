export default function Footer() {
  return (
    <div className="container-fluid">
      <div className="row bg-success text-light">
        <DiaChi />
        <MapEmbed />
      </div>
    </div>
  );
}

export function DiaChi() {
  return (
    <div className="col-sm-12 col-xl-8 d-flex flex-column justify-content-center py-1 py-lg-0">
      <div className="row">
        <div className="col-12 col-md-2 d-flex">
          <img
            className="mx-auto my-auto"
            src="/images/SGDDT.png"
            alt="logo"
            style={{ maxHeight: "95px" }}
          />
        </div>
        <div className="col-12 col-md-10">
          <div className="text-center text-md-start fw-bold fs-4 py-1">
            SỞ GIÁO DỤC VÀ ĐÀO TẠO THÀNH PHỐ CẦN THƠ
          </div>
          <div className="text-center text-md-start py-1">
            Địa chỉ: Số 39, đường 3 tháng 2, phường Ninh Kiều, thành phố Cần Thơ
          </div>
          <div className="text-center text-md-start py-1">
            Điện thoại: 0292.3830.753 - Fax: 0292.3830.451
          </div>
          <div className="text-center text-md-start py-1">
            Email: vanphong.socantho@moet.edu.vn, sogddt@cantho.gov.vn
          </div>
        </div>
      </div>
    </div>
  );
}

export function MapEmbed() {
  return (
    <div className="col-sm-12 col-xl-4 d-flex gx-0 gy-0">
      <iframe
        src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d535.5515338451224!2d105.77310787661276!3d10.031516566976697!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31a08822f47b39cf%3A0x5ce8c0c138cbff56!2zU-G7nyBHacOhbyBk4bulYyB2w6AgxJDDoG8gdOG6oW8gVFAuIEPhuqduIFRoxqE!5e0!3m2!1svi!2s!4v1758266299441!5m2!1svi!2s"
        className="p-1 w-100 h-100"
        allowFullScreen
        loading="lazy"
        referrerPolicy="no-referrer-when-downgrade"
      ></iframe>
    </div>
  );
}
