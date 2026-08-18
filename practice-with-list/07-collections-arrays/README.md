# 📘 Giáo án #07: Collections / Arrays (utility classes)

> ⬅️ Trước: [06 - Map](../06-map/README.md) | ➡️ Tiếp theo: [08 - Comparable/Comparator](../08-comparable-comparator/README.md)

## 🎯 Mục tiêu buổi học
- Phân biệt rõ: `List`/`Set`/`Map`/mảng là **phụ tùng xe**, còn `Collections`/`Arrays` là **hộp đồ nghề** (toàn static method) thao tác lên phụ tùng đó.
- Thấy rõ bẫy `Arrays.asList()` — một trong những câu hỏi OCP hay gặp nhất.
- Phân biệt "view bất biến" (`Collections.unmodifiableList`) với "bản sao bất biến thật" (`List.of()`).

## 🧠 Ẩn dụ đời sống
`Collections` và `Arrays` giống như **hộp đồ nghề của thợ máy** — cờ lê, tua vít (`sort`, `reverse`, `fill`, `binarySearch`). Chúng không phải là chiếc xe (List/mảng), mà là công cụ tác động lên chiếc xe. Bạn không "new" ra một cái `Collections` hay `Arrays` — chúng chỉ có static method, giống như bạn không "mua một cái tua vít làm của riêng", bạn chỉ mượn dùng.

## ⚖️ So sánh cảm giác: `Arrays.asList()` vs `List.of()` vs `Collections.unmodifiableList()`
| | `Arrays.asList(arr)` | `List.of(...)` | `Collections.unmodifiableList(list)` |
|---|---|---|---|
| Cảm giác | "Khung nhìn" (view) gắn chặt vào mảng gốc | Bản sao niêm phong hoàn toàn | "Lớp kính chắn" bọc quanh list gốc |
| `set(i, x)` | ✅ Cho phép (ghi thẳng vào mảng gốc!) | ❌ `UnsupportedOperationException` | ❌ Ném exception qua lớp kính |
| `add()`/`remove()` | ❌ Kích thước cố định theo mảng | ❌ | ❌ Qua lớp kính, nhưng... |
| Sửa qua list gốc | — | — | ⚠️ **Vẫn thấy thay đổi** nếu sửa list gốc trực tiếp — chỉ "kính chắn" phía view, không đóng băng bản gốc |

Đây chính là bẫy hay ra đề thi: `Collections.unmodifiableList` không làm cho **dữ liệu gốc** bất biến, nó chỉ chặn thao tác **thông qua chính view đó**.

## 🔬 Bên dưới lớp vỏ
`Arrays.asList(arr)` trả về **không phải** `java.util.ArrayList` quen thuộc, mà là một class lồng riêng biệt (`Arrays.ArrayList`) — class này **cầm thẳng tham chiếu tới mảng gốc bạn truyền vào**, không copy gì cả, và chỉ override `set()`/`get()` chứ cố tình không override `add()`/`remove()`. Đó là lý do `set()` thì chạy (và ảnh hưởng ngược lại mảng gốc), còn `add()` thì bị chặn.

## ⚙️ Điểm nhấn Java 11
- Ưu tiên `List.of()`/`Set.of()`/`Map.of()` cho dữ liệu hằng, tĩnh — code đọc rõ ý định "cái này không bao giờ đổi" hơn nhiều so với bọc qua `Collections.unmodifiableXxx`.

## 💻 Code minh họa
```java
Integer[] scores = {90, 70, 85};
var backedList = java.util.Arrays.asList(scores);
backedList.set(0, 100); // OK — nhưng ghi thẳng vào mảng scores!
System.out.println(scores[0]); // in ra 100 — mảng gốc đã đổi!

try {
    backedList.add(60); // kích thước cố định theo mảng gốc
} catch (UnsupportedOperationException e) {
    System.out.println("asList() không cho thêm phần tử mới!");
}
```

## 🧪 Thử thách thực hành
1. **Viết mới**: dùng `Collections.sort()` + `Comparator` để sắp xếp một `List<Integer>` giảm dần (chuẩn bị tư duy cho bài 08 Comparator).
2. **Debug**: cho đoạn code sửa một phần tử qua `Arrays.asList()` rồi thắc mắc "sao mảng gốc của tôi tự thay đổi?" — giải thích nguyên nhân bằng ẩn dụ "view" ở trên.

## 🗣️ Dạy lại
Giải thích sự khác nhau giữa "bọc kính chắn" (`unmodifiableList`) và "niêm phong bản sao" (`List.of()`) cho một người chưa biết Java.

## ✅ Checklist hoàn thành
- [ ] Không còn nhầm `Arrays.asList()` với một List có thể `add()` tự do.
- [ ] Phân biệt được view bất biến và bản sao bất biến thật.
