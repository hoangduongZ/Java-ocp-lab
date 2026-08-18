# 📘 Giáo án #13: Stream

> ⬅️ Trước: [12 - Method Reference](../12-method-reference/README.md) | ➡️ Tiếp theo: [14 - Collectors](../14-collectors/README.md)

## 🎯 Mục tiêu buổi học
- Hiểu Stream là một **đường ống xử lý dữ liệu**, không phải cấu trúc lưu trữ.
- Hiểu "lazy evaluation" — vì sao thiếu terminal operation thì cả pipeline không chạy gì cả.
- Hiểu Stream chỉ dùng được **một lần**.

## 🧠 Ẩn dụ đời sống
Stream giống như một **băng chuyền trong nhà máy**: dữ liệu đi qua từng trạm (`filter` — trạm kiểm tra loại bỏ hàng lỗi, `map` — trạm đóng gói lại thành hình dạng khác, `sorted` — trạm xếp thứ tự). Nhưng có một sự thật gây bất ngờ cho người mới: **băng chuyền không tự chạy** — nó chỉ khởi động khi có một trạm cuối cùng thật sự "tiêu thụ" sản phẩm (`forEach`, `collect`, `count`...), gọi là **terminal operation**. Nếu bạn chỉ setup `filter().map().sorted()` mà không có bất kỳ terminal operation nào, băng chuyền đứng im vĩnh viễn — không có gì chạy, không lỗi, không log, im lặng tuyệt đối (đây là bẫy hay gặp nhất khi mới học Stream).

Và vì là băng chuyền một lượt hàng cụ thể, **bạn không thể chạy lại cùng một Stream lần thứ hai** — giống như hàng đã ra khỏi băng chuyền thì băng chuyền đó "hết nhiệm vụ", muốn xử lý lại phải tạo Stream mới từ nguồn.

## ⚙️ Điểm nhấn Java 11 / 9
- `takeWhile(predicate)` / `dropWhile(predicate)` (Java 9) — cực hữu ích với dữ liệu **đã sắp xếp**: lấy/bỏ liên tục cho đến khi gặp phần tử đầu tiên không thỏa điều kiện rồi **dừng ngay** (short-circuit), khác hẳn `filter` phải quét hết toàn bộ.
- `Stream.ofNullable(value)` (Java 9) — tạo một Stream 0 hoặc 1 phần tử tùy `value` có null hay không, giúp gộp một giá trị đơn lẻ có thể null vào pipeline mà không cần `if` riêng.

## 💻 Code minh họa
```java
var numbers = java.util.List.of(2, 4, 6, 7, 8, 10);

// peek() chỉ để MINH HỌA thứ tự thực thi lazy — không dùng peek() cho logic thật trong code sản xuất
long count = numbers.stream()
        .peek(n -> System.out.println("Đang xét: " + n))
        .filter(n -> n % 2 == 0)
        .takeWhile(n -> n < 8) // dừng NGAY khi gặp số không thỏa, dù còn 10 phía sau
        .count(); // <-- terminal operation: chỉ khi có dòng này, cả pipeline mới chạy

System.out.println("Kết quả: " + count);
```

## 🧪 Thử thách thực hành
1. **Viết mới**: từ `List<String>` tên người, dùng pipeline `filter` (tên dài hơn 2 ký tự) + `map` (viết hoa) + `sorted` + in ra từng người.
2. **Debug**: cho đoạn code gọi `.count()` hai lần trên cùng một biến `Stream` đã tạo sẵn — sửa lỗi `IllegalStateException: stream has already been operated upon or closed`.

## 🗣️ Dạy lại
Giải thích: nếu bạn viết `list.stream().filter(...).map(...)` mà không có dòng nào phía sau, chuyện gì xảy ra khi chạy chương trình? Vì sao?

## ✅ Checklist hoàn thành
- [ ] Giải thích đúng khái niệm lazy evaluation bằng ẩn dụ của riêng bạn.
- [ ] Không còn tái sử dụng một Stream đã bị "tiêu thụ".
