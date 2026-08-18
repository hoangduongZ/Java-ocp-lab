# 📘 Giáo án #05: equals/hashCode

> ⬅️ Trước: [04 - Set](../04-set/README.md) | ➡️ Tiếp theo: [06 - Map](../06-map/README.md)

## 🎯 Mục tiêu buổi học
- Hiểu vì sao `equals()` và `hashCode()` phải **luôn đi cùng nhau**, không thể override một mà bỏ cái kia.
- Thấy tận mắt hậu quả khi vi phạm hợp đồng (contract) này trong `HashSet`/`HashMap`.
- Dùng được `Objects.equals()` / `Objects.hash()` để viết gọn và an toàn.

## 🧠 Ẩn dụ đời sống
Hãy tưởng tượng một tòa nhà văn phòng khổng lồ (đây chính là `HashSet`/`HashMap` bên trong). Khi bạn hỏi bảo vệ "anh Nam có ở đây không?", bảo vệ **không đi gõ cửa từng phòng** — anh ta dùng một mẹo cực nhanh: nhìn vào **số tầng ghi trên thẻ của Nam** (`hashCode()`) để đoán ngay Nam đang ở tầng nào (bucket nào), rồi chỉ gõ cửa các phòng ở đúng tầng đó, và gõ cửa để **xác nhận đúng người** (`equals()`).

Bây giờ nếu bạn nói "tôi chính là Nam" (`equals()` trả về `true`) nhưng thẻ của bạn lại ghi **một số tầng khác** với thẻ Nam gốc (`hashCode()` khác nhau) — bảo vệ sẽ đi tìm sai tầng, không bao giờ thấy bạn, dù bạn "về bản chất" đã có mặt trong tòa nhà. Đây chính xác là bug: hai object `equals()` nhau nhưng `hashCode()` khác nhau → `HashSet` chứa "hai bản sao" của cùng một người, `HashMap.get()` không tìm ra entry đã `put()`.

## 🔬 Bên dưới lớp vỏ
- **Hợp đồng bắt buộc**: nếu `a.equals(b) == true` thì bắt buộc `a.hashCode() == b.hashCode()`. Chiều ngược lại **không bắt buộc** (hai object có thể trùng hashCode mà không equals — gọi là "va chạm"/collision, hoàn toàn bình thường).
- `HashSet.add()` / `HashMap.put()` đều dùng `hashCode()` trước để tìm đúng "tầng" (bucket), rồi mới dùng `equals()` để so sánh với các phần tử đã có trong tầng đó.

## ⚙️ Công cụ hỗ trợ (không phải riêng Java 11, nhưng bắt buộc phải biết)
- `Objects.equals(a, b)` và `Objects.hash(...)` thực ra có từ **Java 7**, không phải tính năng mới của Java 11 — nhưng vẫn nằm trong phạm vi kiến thức nền OCP 11, nên đừng nhầm đây là "hàng mới".
- `Objects.equals(a, b)` — null-safe, khỏi phải tự viết `if (a == null) ...` trước khi gọi `.equals()`.
- `Objects.hash(field1, field2, ...)` — tạo hashCode tổng hợp từ nhiều field chỉ trong 1 dòng, thay vì tự viết công thức nhân 31 thủ công dễ sai.

## 💻 Code minh họa
```java
public class Point {
    private final int x, y;
    public Point(int x, int y) { this.x = x; this.y = y; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point p = (Point) o;
        return x == p.x && y == p.y; // "cùng người" khi cùng tọa độ
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, y); // "số tầng" phải khớp với equals ở trên
    }
}
```

## 🧪 Thử thách thực hành
1. **Debug**: cho class `Point` chỉ override `equals()` mà **không** override `hashCode()`. Cho vào `HashSet`, thêm 2 điểm giống tọa độ, in `set.size()` ra để tận mắt thấy nó bằng 2 (chứ không phải 1 như kỳ vọng). Sửa lại cho đúng.
2. **Viết mới**: viết class `Money(String currency, int cents)`, override đúng cặp `equals`/`hashCode`, chứng minh bằng cách cho vào `HashSet` và `HashMap` rồi `get()` lại thành công.

## 🗣️ Dạy lại
Giải thích lại bằng ẩn dụ của riêng bạn (không cần là tòa nhà văn phòng) tại sao override `equals()` mà quên `hashCode()` lại làm hỏng `HashSet`.

## ✅ Checklist hoàn thành
- [ ] Tái hiện được bug "size() sai" khi thiếu hashCode.
- [ ] Viết đúng cặp equals/hashCode dùng `Objects.hash()`.
