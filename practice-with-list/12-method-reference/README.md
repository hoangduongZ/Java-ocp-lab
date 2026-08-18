# 📘 Giáo án #12: Method Reference

> ⬅️ Trước: [11 - Functional Interface](../11-functional-interface/README.md) | ➡️ Tiếp theo: [13 - Stream](../13-stream/README.md)

## 🎯 Mục tiêu buổi học
- Nhận diện và viết được đủ 4 dạng method reference.
- Hiểu dạng "unbound instance method" (dạng khó nhất) — vì sao tham số đầu tiên lại "biến thành" đối tượng gọi method.

## 🧠 Ẩn dụ đời sống
Nếu lambda là "tờ giấy nhớ ghi quy trình", thì method reference giống như **bấm số gọi nhanh (speed dial)**: khi tờ giấy nhớ của bạn chỉ đơn giản là "gọi đúng một hàm đã có sẵn, không thêm bớt gì", thay vì viết cả câu lệnh, bạn chỉ cần trỏ thẳng tới hàm đó bằng `::`.

## ⚖️ 4 dạng method reference
| Dạng | Cú pháp | Lambda tương đương | Ví dụ |
|---|---|---|---|
| Static method | `Class::staticMethod` | `x -> Class.staticMethod(x)` | `Integer::parseInt` |
| Instance method — đã có object cụ thể | `obj::method` | `x -> obj.method(x)` | `System.out::println` |
| Instance method — **chưa** có object, object là tham số đầu | `Class::instanceMethod` | `(obj, x) -> obj.method(x)` | `String::toUpperCase` |
| Constructor | `Class::new` | `x -> new Class(x)` | `ArrayList::new` |

Dạng thứ 3 là "khó nhất": khi bạn viết `String::toUpperCase` cho một `Function<String, String>`, tham số đầu vào của Function **chính là object** sẽ gọi `.toUpperCase()` lên nó — không phải tham số truyền cho method.

## 🔬 Bên dưới lớp vỏ
Method reference (`::`) **dùng chung đúng một cơ chế bytecode** (`invokedynamic` + `LambdaMetafactory`) với lambda ở bài trước — nó chỉ là một cách viết khác, không hề tạo ra loại bytecode "đặc biệt hơn" hay "nhanh hơn" lambda. Nói cách khác: `String::toUpperCase` và `s -> s.toUpperCase()` là hai cách viết cho CÙNG một thứ dưới lớp vỏ.

## ⚙️ Điểm nhấn Java 11
Method reference tận dụng tối đa các API mới thêm ở Java 9-11 vì chúng đều là method 1 tham số/0 tham số gọn gàng — ví dụ `String::isBlank`, `String::strip` đều dùng được trực tiếp làm `Predicate<String>`/`UnaryOperator<String>` mà không cần viết lambda bọc ngoài.

## 💻 Code minh họa
```java
import java.util.function.*;

// 1. Static method
Function<String, Integer> parse = Integer::parseInt;

// 2. Instance method - đã có object
String greeting = "Hello";
Supplier<String> upper = greeting::toUpperCase;

// 3. Instance method - chưa có object (tham số đầu = object gọi method)
Function<String, String> upperUnbound = String::toUpperCase;
System.out.println(upperUnbound.apply("ocp")); // "OCP" — "ocp" chính là object gọi toUpperCase()

// 4. Constructor
Supplier<java.util.ArrayList<String>> factory = java.util.ArrayList::new;
```

## 🧪 Thử thách thực hành
1. **Viết mới**: cho 4 đoạn lambda tương ứng với 4 dạng trong bảng trên, tự viết lại thành method reference.
2. **Dạy lại ngay tại chỗ**: với `BiFunction<String, String, Boolean> f = String::equals;`, giải thích `f.apply("a", "b")` thực chất gọi cái gì lên cái gì (dạng unbound instance method).

## 🗣️ Dạy lại
Giải thích dạng "unbound instance method reference" cho một người mới bằng ví dụ của riêng bạn, không dùng lại `String::toUpperCase`.

## ✅ Checklist hoàn thành
- [ ] Nhận diện đúng cả 4 dạng khi đọc code người khác.
- [ ] Tự tin giải thích dạng unbound instance method — dạng hay bị hỏi nhất trong đề OCP.
