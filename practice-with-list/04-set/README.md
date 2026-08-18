# 📘 Giáo án #04: Set

> ⬅️ Trước: [03 - List](../03-list/README.md) | ➡️ Tiếp theo: [05 - equals/hashCode](../05-equals-hashcode/README.md)

## 🎯 Mục tiêu buổi học
- Hiểu Set khác List ở chỗ nào trong tư duy thiết kế (không trùng, không cam kết thứ tự theo index).
- Phân biệt "cảm giác" giữa `HashSet`, `LinkedHashSet`, `TreeSet`.
- Thấy trước lý do vì sao bài tiếp theo (equals/hashCode) là bắt buộc phải học ngay sau Set.

## 🧠 Ẩn dụ đời sống
Set giống như **danh sách khách mời VIP ở cửa một câu lạc bộ**: tên đã có trong danh sách rồi thì ghi thêm lần nữa cũng vô nghĩa (không có "hai vé" cho cùng một người) — đây là bản chất "không trùng lặp". Người gác cửa (`HashSet`) không quan tâm khách đến theo thứ tự nào, chỉ quan tâm "đã có tên này chưa". Nếu bạn cần gác cửa nhớ **đúng thứ tự khách đến** thì dùng `LinkedHashSet`; nếu cần danh sách luôn **hiện ra theo thứ tự bảng chữ cái** thì dùng `TreeSet`.

## ⚖️ So sánh cảm giác: HashSet vs LinkedHashSet vs TreeSet
| | HashSet | LinkedHashSet | TreeSet |
|---|---|---|---|
| Thứ tự duyệt | Không đảm bảo | Theo thứ tự chèn vào | Theo thứ tự sắp xếp (tự nhiên/Comparator) |
| Tốc độ | Nhanh nhất | Chậm hơn chút (giữ thêm liên kết) | Chậm nhất (cây cân bằng) |
| Yêu cầu | equals/hashCode đúng | equals/hashCode đúng | Comparable hoặc Comparator |

## 🔬 Bên dưới lớp vỏ
- Sự thật gây bất ngờ: `HashSet` **không tự lưu trữ gì độc lập cả** — bên trong nó chỉ là một `HashMap<E, Object>` được bọc lại, dùng phần tử của bạn làm **key**, còn **value** luôn là một object hằng vô nghĩa (gọi là `PRESENT`). Nói cách khác: "Set chỉ là Map mà ta lờ đi phần value".
- Tương tự, `TreeSet` bên trong là một `TreeMap` được bọc lại theo đúng cách đó.

## ⚙️ Điểm nhấn Java 11
- `Set.of(1, 2, 3)` (Java 9+) tạo Set bất biến — nhưng có một bẫy thi OCP rất hay: `Set.of(1, 1)` **ném `IllegalArgumentException` ngay khi tạo** (phát hiện trùng lặp tại thời điểm khởi tạo), khác hẳn `new HashSet<>(List.of(1, 1))` (âm thầm loại bỏ phần tử trùng, không báo lỗi gì cả).

## 💻 Code minh họa
```java
var hashSet = new java.util.HashSet<String>();
var linkedHashSet = new java.util.LinkedHashSet<String>();
var treeSet = new java.util.TreeSet<String>();

for (String name : java.util.List.of("Cam", "An", "Binh", "An")) {
    hashSet.add(name);
    linkedHashSet.add(name);
    treeSet.add(name);
}

System.out.println(hashSet);       // thứ tự không đoán trước được
System.out.println(linkedHashSet); // [Cam, An, Binh] — đúng thứ tự chèn
System.out.println(treeSet);       // [An, Binh, Cam] — sắp xếp bảng chữ cái
```

## 🧪 Thử thách thực hành
1. **Viết mới**: từ một `List<String>` có phần tử trùng, tạo ra một `Set` loại bỏ trùng lặp nhưng **vẫn giữ đúng thứ tự xuất hiện đầu tiên**.
2. **Debug**: cho đoạn code `Set.of("a", "a")` đang crash lúc khởi tạo — giải thích vì sao, và sửa sang cách tạo Set cho phép "âm thầm" loại trùng nếu đó là ý muốn thật sự.

## 🗣️ Dạy lại
Giải thích cho một người mới: tại sao `Set` lại "quan tâm" đến việc hai phần tử có bằng nhau hay không nhiều hơn cả `List`? (Gợi ý: đây chính là cầu nối sang bài `equals/hashCode` tiếp theo.)

## ✅ Checklist hoàn thành
- [ ] Chọn đúng loại Set (`Hash`/`Linked`/`Tree`) theo yêu cầu bài toán.
- [ ] Biết `Set.of()` sẽ throw khi có phần tử trùng, khác với constructor `HashSet`.
