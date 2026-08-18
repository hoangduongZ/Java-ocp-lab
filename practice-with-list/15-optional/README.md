# 📘 Giáo án #15: Optional

> ⬅️ Trước: [14 - Collectors](../14-collectors/README.md) | ➡️ Tiếp theo: [16 - Generics nâng cao](../16-generics-nang-cao/README.md)

## 🎯 Mục tiêu buổi học
- Hiểu Optional giải quyết vấn đề gì của `null` ("sai lầm tỷ đô" theo lời chính người tạo ra nó).
- Tránh anti-pattern `if (opt.isPresent()) opt.get();` — dùng phong cách functional (`map`, `orElse`, `ifPresent`) đúng tinh thần thiết kế.
- Biết `Optional.isEmpty()` (Java 11).

## 🧠 Ẩn dụ đời sống
`null` giống như việc ai đó **đưa cho bạn một bàn tay nắm chặt, không nói gì** — bạn không biết bên trong có quà hay trống rỗng cho đến khi tự mở ra xem, và nếu bạn vô tư thò tay vào một nắm tay trống rỗng (`null.something()`), bạn "bị kim đâm" ngay lập tức (`NullPointerException`) mà chẳng có cảnh báo trước. `Optional` giống như thay bàn tay nắm chặt bằng **một cái hộp quà trong suốt, có dán nhãn rõ ràng "CÓ QUÀ" hoặc "HỘP RỖNG"** — bạn buộc phải nhìn nhãn (dùng API của Optional) trước khi được phép lấy đồ ra, không còn cách nào "vô tình" thò tay vào chỗ trống mà không biết.

## 🔬 Bên dưới lớp vỏ
`Optional<T>` không có phép màu JVM nào cả — nó chỉ là một **class bình thường** chứa duy nhất 1 field tham chiếu tới giá trị (hoặc để trống nếu rỗng). Chính vì "bình thường" như vậy, người thiết kế ra nó (Brian Goetz) khuyến cáo **không nên dùng làm kiểu field, tham số constructor, hay tham số method** — vì bản thân biến `Optional` đó vẫn có thể là `null` (bạn lại phải null-check chính cái Optional!), và nó không implement `Serializable`. Optional chỉ thật sự phát huy tác dụng khi làm **kiểu trả về của method**.

## ⚙️ Điểm nhấn Java 11
`Optional.isEmpty()` (Java 11) — trước đó chỉ có `isPresent()`, muốn viết "nếu KHÔNG có giá trị" phải viết `!opt.isPresent()`, khá gượng. `isEmpty()` cho phép viết `if (opt.isEmpty()) { ... }` đọc tự nhiên hơn hẳn, đúng tinh thần Java 11 "giảm bớt phủ định ngược".

## 💻 Code minh họa
```java
import java.util.Optional;

record User(String name, String email) {}

Optional<User> findUserById(int id) {
    // giả lập: có thể tìm thấy hoặc không
    return id == 1 ? Optional.of(new User("An", "an@mail.com")) : Optional.empty();
}

// ANTI-PATTERN — chỉ là null-check đội lốt Optional, không tận dụng thiết kế
Optional<User> u1 = findUserById(2);
if (u1.isPresent()) {
    System.out.println(u1.get().email());
}

// ĐÚNG TINH THẦN — functional style, không cần if/else lồng nhau
findUserById(2)
        .map(User::email)
        .ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Không tìm thấy email"));
```

## 🧪 Thử thách thực hành
1. **Debug/Refactor**: cho một method trả `null` nếu không tìm thấy, gọi kèm nhiều lớp `if (x != null)` lồng nhau (null-check pyramid) — refactor toàn bộ sang trả về `Optional` và dùng `map`/`orElse` để phẳng hóa.
2. **Viết mới**: viết method `Optional<Double> averageOf(List<Integer> numbers)` trả `Optional.empty()` nếu list rỗng (tránh chia cho 0), có nơi gọi dùng `orElseGet` để cung cấp giá trị mặc định.

## 🗣️ Dạy lại
Giải thích cho người khác: tại sao `if (opt.isPresent()) { opt.get(); }` bị coi là "dùng sai" Optional dù về mặt kỹ thuật nó chạy đúng?

## ✅ Checklist hoàn thành
- [ ] Không còn dùng `opt.get()` mà không kiểm tra.
- [ ] Refactor được ít nhất một đoạn null-check lồng nhau sang Optional functional style.
