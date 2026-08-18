# 📘 Giáo án #17: OCP Mixed Problems (Ôn tập tổng hợp)

> ⬅️ Trước: [16 - Generics nâng cao](../16-generics-nang-cao/README.md) | 🏁 Chặng cuối lộ trình

## 🎯 Mục tiêu buổi học
Đây **không phải** buổi học khái niệm mới — đây là buổi **"ghép hộp đen"**: mỗi bài toán nhỏ dưới đây cố tình trộn 2-3 chủ đề đã học từ bài 01 đến 16, đúng phong cách câu hỏi bẫy trong đề thi OCP thật (hỏi "đoạn này in ra gì / có biên dịch được không" chứ không hỏi lý thuyết suông).

## 🧠 Cách dùng giáo án này
Với mỗi bài toán, đi đúng 4 bước trong `system-prompt-agent.md`:
1. Đọc code, đoán kết quả TRƯỚC khi chạy.
2. Chạy thử, so sánh với dự đoán — sai ở đâu thì đó chính là lỗ hổng cần vá.
3. Truy ngược lại đúng bài học (01-16) liên quan để ôn lại ẩn dụ gốc.
4. Tự viết lại một biến thể khác của bài toán đó.

## 🧪 Bộ câu hỏi tổng hợp

**Câu 1 — Set + equals/hashCode + Generics cơ bản**
Một class `Point` override `equals()` nhưng quên `hashCode()`, được cho vào `Set<Point>`. Đoán `set.size()` sau khi add 2 điểm giống tọa độ. (Ôn: [05](../05-equals-hashcode/README.md), [04](../04-set/README.md))

**Câu 2 — Map + Stream + Collectors + method reference**
Cho `List<Person>`, dùng một dòng Stream để ra `Map<String, Long>` đếm số người theo `city`, so sánh với cách làm bằng vòng lặp `Map.merge()` thủ công ở bài 06. (Ôn: [06](../06-map/README.md), [13](../13-stream/README.md), [14](../14-collectors/README.md))

**Câu 3 — Comparator + Lambda + Optional**
Từ `List<Product>`, tìm sản phẩm có giá cao nhất bằng `stream().max(Comparator.comparingDouble(...))` — kết quả trả về kiểu gì? Vì sao không phải là `Product` trực tiếp? (Ôn: [08](../08-comparable-comparator/README.md), [10](../10-lambda/README.md), [15](../15-optional/README.md))

**Câu 4 — Iterator + List.of() + Collections**
Đoán lỗi xảy ra khi convert `List.of(1, 2, 3)` sang `ArrayList` bằng `Arrays.asList()` rồi cố `list.remove(0)` trong for-each. Có mấy lỗi tiềm ẩn trong đoạn code này? (Ôn: [03](../03-list/README.md), [07](../07-collections-arrays/README.md), [09](../09-iterator/README.md))

**Câu 5 — Generics nâng cao + Functional Interface**
Viết `<T extends Comparable<T>> T max(List<? extends T> list)` — giải thích vì sao cần cả bound `Comparable<T>` lẫn wildcard `? extends T` cùng lúc. (Ôn: [02](../02-generics-co-ban/README.md), [16](../16-generics-nang-cao/README.md), [08](../08-comparable-comparator/README.md))

**Câu 6 — Array + Stream + Optional**
Từ một `int[]` rỗng, dùng `Arrays.stream(arr).max()` — kết quả trả về kiểu gì, và tại sao? (Ôn: [01](../01-array/README.md), [13](../13-stream/README.md), [15](../15-optional/README.md))

**Câu 7 — Set.of() + IllegalArgumentException + so sánh cảm giác**
So sánh hành vi khi tạo `Set.of(1, 1)` (Java 9+ immutable factory) với `new HashSet<>(Arrays.asList(1, 1))` — bài nào throw, bài nào âm thầm bỏ qua? (Ôn: [04](../04-set/README.md), [07](../07-collections-arrays/README.md))

## 🏗️ Bài tập tổng kết — Mini Project
Xây dựng **"Bộ báo cáo điểm sinh viên"**:
- `record Student(String name, String className, List<Integer> scores)`.
- Dùng **Map** để nhóm sinh viên theo `className`.
- Dùng **Stream + Collectors.groupingBy** để tính điểm trung bình mỗi lớp.
- Dùng **Optional** để xử lý trường hợp một lớp không có sinh viên nào đạt điểm trên 8.
- Dùng **Comparator** để sắp xếp bảng xếp hạng theo điểm trung bình giảm dần.
- Toàn bộ danh sách kết quả cuối trả về dưới dạng **bất biến** (`List.of()` / `Collectors.toUnmodifiableList()`).

Đây là bài kiểm tra thật sự: nếu làm được mini project này trôi chảy mà không cần mở lại từng bài, nghĩa là toàn bộ lộ trình 01-16 đã "ngấm".

## 🗣️ Dạy lại cuối lộ trình
Chọn 1 trong 7 câu hỏi trên, đóng vai làm mentor và giải thích lại cho một người tưởng tượng chưa biết Java — đúng tinh thần "Feynman Technique" xuyên suốt `system-prompt-agent.md`.

## ✅ Checklist hoàn thành lộ trình
- [ ] Trả lời đúng và giải thích được cả 7 câu hỏi tổng hợp.
- [ ] Hoàn thành mini project báo cáo điểm sinh viên.
- [ ] Có thể "dạy lại" ít nhất 3 khái niệm bất kỳ trong 16 bài trước mà không cần xem lại tài liệu.
