# 📘 Giáo án #06: Map

> ⬅️ Trước: [05 - equals/hashCode](../05-equals-hashcode/README.md) | ➡️ Tiếp theo: [07 - Collections/Arrays](../07-collections-arrays/README.md)

## 🎯 Mục tiêu buổi học
- Hiểu Map là cấu trúc "key → value", key hoạt động như một Set (nhờ đúng bài trước: equals/hashCode).
- Dùng thành thạo `getOrDefault`, `computeIfAbsent`, `merge` để tránh code if/null-check dài dòng.
- Biết `Map.of()` bất biến của Java 9/11.

## 🧠 Ẩn dụ đời sống
Map giống như một **cuốn danh bạ điện thoại**: mỗi cái tên (key) chỉ có **đúng một số điện thoại** ghi kèm (value) — nếu bạn cập nhật số mới cho một cái tên đã có, số cũ bị **ghi đè**, không có chuyện tồn tại hai dòng cho cùng một tên. Nhưng số điện thoại (value) thì có thể trùng nhau giữa nhiều tên khác nhau (hai người có thể dùng chung một số bàn công ty).

Vì key phải là duy nhất và cần so sánh "trùng hay không", nó **dựa hoàn toàn vào `equals()`/`hashCode()`** bạn học ở bài trước — nếu key là object tự định nghĩa mà quên override đúng cặp đó, `map.get(key)` sẽ tìm không ra dù bạn chắc chắn đã `put()` nó vào.

## ⚖️ So sánh cảm giác: HashMap vs LinkedHashMap vs TreeMap
Y hệt bộ ba `HashSet`/`LinkedHashSet`/`TreeSet` ở bài trước — vì Set thực chất được xây trên nền Map:

| | HashMap | LinkedHashMap | TreeMap |
|---|---|---|---|
| Thứ tự duyệt | Không đảm bảo | Theo thứ tự chèn vào | Theo thứ tự sắp xếp của key |
| Khi nào dùng | Chỉ cần tra cứu nhanh, không quan tâm thứ tự | Cần nhớ lịch sử chèn (ví dụ: cache LRU) | Cần key luôn hiện ra có thứ tự |

## 🔬 Bên dưới lớp vỏ
- `HashMap` bên trong là một **mảng các "ngăn" (bucket)** — `hashCode()` của key được đưa qua một hàm băm phụ rồi lấy phần dư theo kích thước mảng để quyết định **rơi vào ngăn nào**. Đây chính là lý do trực tiếp bài `equals/hashCode` phải học TRƯỚC bài này.
- Từ Java 8, nếu một ngăn chứa quá nhiều entry va chạm (mặc định > 8), JVM tự động chuyển ngăn đó từ danh sách liên kết sang **cây đỏ-đen (red-black tree)** để tra cứu nhanh hơn — một chi tiết tối ưu ẩn hoàn toàn sau lớp vỏ `Map`.

## ⚙️ Điểm nhấn Java 11
- `Map.of("a", 1, "b", 2)` (Java 9+) — bất biến, tối đa 10 cặp key-value, gọn hơn nhiều so với khởi tạo `HashMap` rồi `put()` từng dòng cho dữ liệu tĩnh.
- `map.getOrDefault(key, fallback)`, `map.computeIfAbsent(key, k -> ...)`, `map.merge(key, value, BiFunction)` — các default method này ra đời để **xóa bỏ boilerplate** kiểu:
```java
// Cách cũ, dài dòng
if (!map.containsKey(key)) {
    map.put(key, new ArrayList<>());
}
map.get(key).add(item);
```
```java
// Cách Java 8+ nên dùng
map.computeIfAbsent(key, k -> new ArrayList<>()).add(item);
```

## 💻 Code minh họa
```java
// Đếm tần suất xuất hiện của từng từ — bài toán kinh điển sẽ gặp lại ở Stream/Collectors
var wordCount = new java.util.HashMap<String, Integer>();
for (String word : java.util.List.of("java", "ocp", "java", "map", "java")) {
    wordCount.merge(word, 1, Integer::sum); // có rồi thì +1, chưa có thì để 1
}
System.out.println(wordCount); // {java=3, ocp=1, map=1}
```

## 🧪 Thử thách thực hành
1. **Viết mới**: viết method đếm tần suất xuất hiện của từ trong một câu (như code minh họa) **bằng vòng lặp thuần** (chưa dùng Stream — sẽ quay lại làm bằng Stream ở bài 14 để so sánh).
2. **Debug**: cho một `HashMap<Point, String>` (dùng lại class `Point` bài 05) mà `get()` luôn trả `null` dù đã `put()` — chẩn đoán nguyên nhân (thường là do quên hashCode) và sửa.

## 🗣️ Dạy lại
Giải thích cho người khác: vì sao key trong Map "phải cư xử giống hệt phần tử trong Set"?

## ✅ Checklist hoàn thành
- [ ] Dùng thành thạo `merge`/`computeIfAbsent` thay vì if/else thủ công.
- [ ] Liên kết được lý do Map cần equals/hashCode đúng từ bài 05.
