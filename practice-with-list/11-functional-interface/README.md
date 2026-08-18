# 📘 Giáo án #11: Functional Interface

> ⬅️ Trước: [10 - Lambda](../10-lambda/README.md) | ➡️ Tiếp theo: [12 - Method Reference](../12-method-reference/README.md)

## 🎯 Mục tiêu buổi học
- Hiểu vì sao Lambda "cần một cái khuôn" để rót vào — đó chính là Functional Interface.
- Ghi nhớ và dùng đúng 4 interface lõi: `Supplier`, `Consumer`, `Function`, `Predicate`.
- Biết dùng `Predicate.not()` (Java 11) để code lọc dễ đọc hơn.

## 🧠 Ẩn dụ đời sống
Functional Interface giống như một **tờ mô tả công việc chỉ ghi đúng MỘT đầu việc bắt buộc**: "ai làm được việc duy nhất này thì được nhận" — không quan tâm ứng viên (lambda) trông như thế nào. `@FunctionalInterface` giống như bạn **đóng dấu công chứng lên tờ mô tả công việc** đó: cam kết với mọi người rằng bản mô tả này mãi mãi chỉ có 1 đầu việc — nếu sau này ai đó vô tình thêm đầu việc thứ 2 vào, compiler sẽ báo lỗi ngay (dù annotation này không bắt buộc, thiếu nó code vẫn chạy, nhưng mất đi "lưới an toàn").

## 🔬 4 khuôn phổ biến nhất (`java.util.function`)
| Interface | Đầu việc | Ẩn dụ ngắn |
|---|---|---|
| `Supplier<T>` | `T get()` | Máy bán hàng tự động — không nhận gì, nhả ra một món |
| `Consumer<T>` | `void accept(T t)` | Máy hủy giấy — nhận vào, không trả lại gì |
| `Function<T,R>` | `R apply(T t)` | Máy đổi tiền — đưa vào loại này, nhận ra loại khác |
| `Predicate<T>` | `boolean test(T t)` | Cổng soát vé — đưa vào, trả lời đúng/sai |

## 🔬 Bên dưới lớp vỏ
Một sự thật hay bị hiểu nhầm: lambda **không được biên dịch thành một class ẩn danh (.class riêng) như anonymous class thời xưa**. Compiler chỉ sinh ra một chỉ thị bytecode đặc biệt (`invokedynamic`), và class thật sự implement functional interface đó chỉ được **JVM tạo ra lúc chạy** (qua cơ chế `LambdaMetafactory`), ngay lần đầu tiên lambda đó được gọi tới. Đây là lý do lambda "nhẹ" hơn anonymous class về số lượng file `.class` sinh ra.

## ⚙️ Điểm nhấn Java 11
- `Predicate.not(...)` — trước Java 11, phủ định một method reference rất xấu: `.filter(s -> !s.isBlank())`. Từ Java 11: `.filter(Predicate.not(String::isBlank))` — đọc tự nhiên như tiếng Anh "not blank", và cho phép dùng trực tiếp method reference thay vì phải bọc lại thành lambda chỉ để thêm dấu `!`.

## 💻 Code minh họa
```java
import java.util.function.*;

Predicate<String> isBlank = String::isBlank;
Predicate<String> isNotBlank = Predicate.not(String::isBlank); // Java 11

var lines = java.util.List.of("Hello", "", "  ", "OCP");
lines.stream()
     .filter(isNotBlank) // đọc rất tự nhiên: "lọc lấy dòng KHÔNG rỗng"
     .forEach(System.out::println);
```

## 🧪 Thử thách thực hành
1. **Viết mới**: tự định nghĩa một Functional Interface `TriFunction<A, B, C, R>` (nhận 3 tham số, trả 1 kết quả) — Java không có sẵn interface này.
2. **Debug**: cho một interface đã đóng dấu `@FunctionalInterface` nhưng ai đó thêm method abstract thứ 2 vào, gây lỗi biên dịch — giải thích lỗi và sửa (thêm `default` cho method thừa, hoặc bỏ bớt).

## 🗣️ Dạy lại
Giải thích: vì sao một interface có 2 abstract method thì **không thể** dùng lambda để implement?

## ✅ Checklist hoàn thành
- [ ] Dùng đúng 1 trong 4 interface lõi theo đúng ngữ cảnh bài toán.
- [ ] Tự viết được một Functional Interface tùy chỉnh.
