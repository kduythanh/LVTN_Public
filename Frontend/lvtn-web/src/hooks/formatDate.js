export default function formatDate(dateStr) {
  if (!dateStr) return "";
  // nếu dữ liệu trả về từ API là "01/01/2011"
  const parts = dateStr.split("/");
  if (parts.length === 3) {
    const [day, month, year] = parts;
    return `${year}-${month.padStart(2, "0")}-${day.padStart(2, "0")}`;
  }
  // nếu API đã trả yyyy-MM-dd thì giữ nguyên
  return dateStr;
}
