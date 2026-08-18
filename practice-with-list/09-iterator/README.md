# 📘 Giáo án #09: Iterator

> ⬅️ Trước: [08 - Comparable/Comparator](../08-comparable-comparator/README.md) | ➡️ Tiếp theo: [10 - Lambda](../10-lambda/README.md)

## 🎯 Mục tiêu buổi học
- Hiểu vòng lặp for-each thực chất được "dịch ngầm" thành gọi Iterator phía sau (nguyên lý hộp đen).
- Hiểu vì sao xóa phần tử trong for-each gây `ConcurrentModificationException`.
- Biết cách xóa an toàn bằng `Iterator.remove()`.

## 🧠 Ẩn dụ đời sống
Iterator giống như một **hướng dẫn viên bảo tàng** dẫn bạn đi qua từng phòng trưng bày theo đúng thứ tự — `hasNext()` là câu hỏi "còn phòng nào phía trước không?", `next()` là "dẫn tôi tới phòng kế tiếp". Điều đặc biệt: **chỉ hướng dẫn viên mới được phép cất một hiện vật đi ngay giữa tour** (`iterator.remove()`) một cách an toàn — vì anh ta biết chính xác vị trí đang đứng để cập nhật lại lộ trình. Nếu một **du khách khác** (code gọi trực tiếp `list.remove()`) tự ý bê hiện vật đi trong khi tour đang chạy mà không báo cho hướng dẫn viên, anh ta sẽ hoang mang khi phát hiện phòng đã bị xáo trộn so với những gì anh ghi nhớ → ném ra `ConcurrentModificationException` để báo động ngay, thay vì để bạn dẫn đoàn đi lạc trong im lặng.

## 🔬 Bên dưới lớp vỏ
- Vòng lặp `for (String s : list) { ... }` được compiler "dịch ngầm" thành:
```java
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String s = it.next();
    // thân vòng lặp
}
```
- Đây là lý do mọi class dùng được trong for-each đều phải implement `Iterable` (có method `iterator()`).
- `ConcurrentModificationException` được phát hiện qua một biến đếm nội bộ gọi là `modCount` — mỗi lần sửa cấu trúc list (add/remove ngoài ý muốn của iterator) thì `modCount` tăng, và `next()` sẽ kiểm tra rồi ném lỗi nếu phát hiện sai lệch.

## ⚙️ Điểm nhấn Java 11
- `ListIterator` (đã có từ lâu nhưng đáng nhắc lại) cho phép duyệt **hai chiều** (`hasPrevious()`/`previous()`) và `set()` phần tử tại chỗ — hữu ích hơn `Iterator` thường khi cần sửa giá trị (không phải xóa) trong lúc duyệt `List`.

## 💻 Code minh họa
```java
var names = new java.util.ArrayList<>(java.util.List.of("An", "Binh", "Cam", "Binh"));

// SAI — sẽ ném ConcurrentModificationException
// for (String n : names) { if (n.equals("Binh")) names.remove(n); }

// ĐÚNG — hướng dẫn viên tự xóa, biết rõ mình đang đứng đâu
var it = names.iterator();
while (it.hasNext()) {
    if (it.next().equals("Binh")) {
        it.remove(); // an toàn, không làm rối "tour"
    }
}
System.out.println(names); // [An, Cam]
```

## 🧪 Thử thách thực hành
1. **Debug**: sửa đoạn code for-each bị `ConcurrentModificationException` ở trên bằng `Iterator.remove()`.
2. **Viết mới**: viết một class `Range(int from, int to)` implement `Iterable<Integer>`, cho phép dùng for-each để duyệt qua các số nguyên từ `from` đến `to`.

## 🗣️ Dạy lại
Giải thích cho một người mới: vì sao `list.remove()` bên trong for-each lại nguy hiểm, còn `it.remove()` thì an toàn?

## ✅ Checklist hoàn thành
- [ ] Tái hiện và sửa được `ConcurrentModificationException`.
- [ ] Viết được một class tự implement `Iterable`.
