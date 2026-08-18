# 📘 Giáo án #14: Collectors

> ⬅️ Trước: [13 - Stream](../13-stream/README.md) | ➡️ Tiếp theo: [15 - Optional](../15-optional/README.md)

## 🎯 Mục tiêu buổi học
- Hiểu Collectors là "khuôn đúc" quyết định hình dạng cuối cùng của dữ liệu sau khi rời băng chuyền Stream.
- Dùng thành thạo `toList`, `joining`, `groupingBy`, `counting`.
- Biết `toUnmodifiableList/Set/Map` (Java 10/11).

## 🧠 Ẩn dụ đời sống
Nếu Stream ở bài trước là băng chuyền, thì Collectors chính là **những cái khuôn đúc** đặt ở cuối băng chuyền: cùng nguyên liệu lỏng (dữ liệu đã qua xử lý) nhưng đổ vào khuôn khác nhau sẽ ra hình dạng khác nhau — khuôn `toList()` cho ra một dãy, khuôn `joining(", ")` **hàn tất cả lại thành một chuỗi dài duy nhất**, khuôn `groupingBy()` giống như đổ nguyên liệu vào **nhiều ngăn có nhãn khác nhau** cùng lúc dựa theo một tiêu chí (mỗi ngăn là một nhóm).

## 🔬 Bên dưới lớp vỏ
`collect()` không "gom" phần tử theo kiểu tạo hàng loạt List trung gian rồi gộp lại — nó vận hành theo mô hình **mutable reduction**: một collector chỉ là bộ 3 hàm `supplier` (tạo container rỗng, ví dụ `ArrayList::new`), `accumulator` (nhét thêm 1 phần tử vào container, ví dụ `List::add`) và `combiner` (gộp 2 container lại khi chạy song song). Đây là lý do `collect()` tiết kiệm bộ nhớ hơn nhiều so với tự `reduce()` bằng cách tạo List mới ở mỗi bước.

## ⚙️ Điểm nhấn Java 11 / 10
`Collectors.toUnmodifiableList()` / `toUnmodifiableSet()` / `toUnmodifiableMap()` (Java 10) — sản phẩm ra khỏi khuôn đã **niêm phong sẵn**, không thể nắn sửa thêm, cùng triết lý bất biến với `List.of()` ở bài 03. Trước đây muốn bất biến sau khi `collect(toList())` phải bọc thêm một lớp `Collections.unmodifiableList(...)` thủ công.

## 💻 Code minh họa
```java
import java.util.stream.Collectors;

record Person(String name, String city) {}

var people = java.util.List.of(
        new Person("An", "Hà Nội"), new Person("Binh", "Hà Nội"), new Person("Cam", "Huế"));

// Khuôn "gộp chuỗi": nối tên bằng dấu phẩy
String allNames = people.stream()
        .map(Person::name)
        .collect(Collectors.joining(", "));
System.out.println(allNames); // "An, Binh, Cam"

// Khuôn "nhiều ngăn": nhóm người theo thành phố, đếm số người mỗi ngăn
var countByCity = people.stream()
        .collect(Collectors.groupingBy(Person::city, Collectors.counting()));
System.out.println(countByCity); // {Hà Nội=2, Huế=1}
```

## 🧪 Thử thách thực hành
1. **Viết lại bài cũ**: quay lại bài toán "đếm tần suất từ" ở [Giáo án #06 - Map](../06-map/README.md) — giải lại **bằng Stream + `Collectors.groupingBy(Function.identity(), Collectors.counting())`**, so sánh độ ngắn gọn với cách dùng vòng lặp + `merge()` trước đây.
2. **Viết mới**: từ danh sách `Person`, tạo báo cáo nhóm theo `city`, mỗi ngăn chứa **danh sách tên** (không phải số đếm) — dùng `Collectors.groupingBy(Person::city, Collectors.mapping(Person::name, Collectors.toList()))`.

## 🗣️ Dạy lại
So sánh cho người khác: `Collectors.toList()` và `Collectors.toUnmodifiableList()` khác nhau ở điểm nào, và khi nào bạn chọn cái nào?

## ✅ Checklist hoàn thành
- [ ] Tái hiện được bài toán đếm tần suất từ bằng cả 2 cách (Map thủ công và Stream+Collectors) để tự cảm nhận sự khác biệt.
- [ ] Dùng đúng `groupingBy` kết hợp downstream collector (`counting`, `mapping`).
