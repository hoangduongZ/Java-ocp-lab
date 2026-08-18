# 📘 Giáo án #03: List

> ⬅️ Trước: [02 - Generics cơ bản](../02-generics-co-ban/README.md) | ➡️ Tiếp theo: [04 - Set](../04-set/README.md)

## 🎯 Mục tiêu buổi học
- Hiểu List là gì trong tư duy thiết kế: có thứ tự, cho phép trùng lặp.
- Phân biệt "cảm giác" giữa `ArrayList` và `LinkedList`.
- Biết bẫy `List.of()` bất biến (immutable) của Java 9/11.

## 🧠 Ẩn dụ đời sống
List giống như **hàng ghế chờ khám bệnh có số thứ tự**: bạn lấy số 1, 2, 3... theo đúng thứ tự đến, và **hai người trùng tên vẫn được xếp hai chỗ khác nhau** (cho phép trùng lặp phần tử). Bạn luôn có thể hỏi "ai đang ở ghế số 5?" (truy cập theo index).

## ⚖️ So sánh cảm giác: ArrayList vs LinkedList
| | ArrayList | LinkedList |
|---|---|---|
| Cảm giác | Một dãy ghế liền nhau, đánh số sẵn | Một chuỗi toa tàu móc nối nhau |
| Truy cập theo index | Rất nhanh (nhảy thẳng tới ghế) | Chậm (phải đi qua từng toa) |
| Thêm/xóa ở giữa | Chậm (phải dồn ghế) | Nhanh (chỉ móc/tháo toa) |
| Khi nào dùng | Đọc nhiều, ít chèn/xóa giữa danh sách | Chèn/xóa đầu-cuối liên tục (hàng đợi, stack) |

## 🔬 Bên dưới lớp vỏ (Heap)
- `ArrayList` bên trong dùng một **mảng `Object[]` có thể phình ra**: khi mảng đầy, JVM âm thầm cấp phát một mảng MỚI to hơn (thường ~1.5 lần) trên Heap rồi copy toàn bộ phần tử cũ sang (`System.arraycopy`) — đây là lý do `add()` "gần như" O(1) nhưng thỉnh thoảng có một lần chậm bất thường khi phải phình mảng.
- `LinkedList` không có mảng nào cả — mỗi phần tử là một **Node riêng lẻ nằm rải rác trên Heap**, giữ 2 tham chiếu (tới Node trước và sau). Vì vậy cùng lưu 1000 số nguyên, `LinkedList` tốn bộ nhớ nhiều hơn hẳn `ArrayList` do "phí" thêm 2 con trỏ mỗi phần tử.

## ⚙️ Điểm nhấn Java 11
- `List.of("a", "b", "c")` (Java 9+) tạo ra một List **bất biến thật sự** — không phải "khóa tạm", mà `add()`/`remove()`/`set()` đều ném `UnsupportedOperationException`. Khác hẳn `Arrays.asList()` (chỉ khóa thêm/xóa, vẫn `set()` được) — sẽ đào sâu ở bài 07.
- Vì sao Java thêm cái này: trước đây muốn có List bất biến phải bọc qua `Collections.unmodifiableList(new ArrayList<>(...))`, rất dài dòng cho một nhu cầu rất phổ biến (hằng số, dữ liệu cấu hình).

## 💻 Code minh họa
```java
var mutablePlaylist = new java.util.ArrayList<String>();
mutablePlaylist.add("Song A");
mutablePlaylist.add("Song A"); // trùng lặp OK — List cho phép

var fixedPlaylist = java.util.List.of("Song A", "Song B");
try {
    fixedPlaylist.add("Song C"); // "hàng ghế" này đã niêm phong từ nhà máy
} catch (UnsupportedOperationException e) {
    System.out.println("List.of() là bất biến, không thể sửa!");
}
```

## 🧪 Thử thách thực hành
1. **Viết mới**: viết class `Playlist` quản lý danh sách bài hát bằng `ArrayList`, có method thêm/xóa theo vị trí.
2. **Debug**: cho đoạn code nhận một `List.of(...)` từ nơi khác rồi cố `.add()` vào — sửa lại đúng ý đồ (copy sang `ArrayList` nếu cần list có thể sửa).

## 🗣️ Dạy lại
Tự giải thích: nếu bạn cần một danh sách chỉ đọc để truyền vào nhiều nơi trong code, dùng `List.of()` hay `Collections.unmodifiableList()`? Vì sao?

## ✅ Checklist hoàn thành
- [ ] Nói được sự khác biệt "cảm giác" giữa ArrayList và LinkedList.
- [ ] Gặp và xử lý được `UnsupportedOperationException` từ `List.of()`.
