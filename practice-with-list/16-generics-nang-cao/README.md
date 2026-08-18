# 📘 Giáo án #16: Generics nâng cao (Wildcards & PECS)

> ⬅️ Trước: [15 - Optional](../15-optional/README.md) | ➡️ Tiếp theo: [17 - OCP mixed problems](../17-ocp-mixed-problems/README.md)

## 🎯 Mục tiêu buổi học
- Hiểu wildcard `? extends T` và `? super T` bằng nguyên tắc **PECS** (Producer Extends, Consumer Super).
- Giải thích được vì sao `List<Object>` **không phải** là cha của `List<String>` (tính bất biến/invariance của Generics).
- Viết được method generic dùng wildcard đúng chỗ.

## 🧠 Ẩn dụ đời sống
Hãy tưởng tượng một nhà kho với hai loại **thẻ ra vào**:
- Thẻ `? extends T` (loại **"chỉ được lấy ra"**) — bạn được phép **lấy hàng ra** khỏi kho và chắc chắn nó là loại `T` (hoặc con của `T`), nhưng **không được bỏ hàng mới vào**, vì kho có thể thực chất đang chứa một loại con cụ thể hơn mà bạn không biết chính xác là gì (có thể là kho `List<Cam>` nhưng bạn chỉ được khai báo biết nó là `List<? extends Fruit>` — nếu bạn bỏ đại một `Apple` vào, có thể phá vỡ tính đúng đắn của kho `Cam`).
- Thẻ `? super T` (loại **"chỉ được bỏ vào"**) — bạn chắc chắn được phép **bỏ hàng loại `T` vào** kho một cách an toàn (vì kho chắc chắn là `T` hoặc một tổ tiên rộng hơn của `T`, luôn chứa được `T`), nhưng khi **lấy ra** thì chỉ dám chắc nó là `Object` (không biết chính xác kho là gì).

**PECS** = **P**roducer **E**xtends, **C**onsumer **S**uper: nếu tham số đó **sinh ra dữ liệu cho bạn dùng** (producer) → dùng `extends`; nếu tham số đó **nhận dữ liệu bạn đưa vào** (consumer) → dùng `super`.

## 🔬 Bên dưới lớp vỏ — vì sao Generics "bất biến" (invariant)
`List<Object> list = new ArrayList<String>();` **không biên dịch được**, dù `String` là `Object`. Lý do: nếu được phép, bạn có thể viết tiếp `list.add(Integer.valueOf(5));` (hợp lệ vì khai báo là `List<Object>`) — nhưng thực chất bên dưới là một `ArrayList<String>`, dẫn đến `ClassCastException` ở nơi khác đọc nó ra as `String`. Đây chính là hệ quả trực tiếp của **type erasure** đã học ở bài 02: compiler phải chặn từ sớm vì runtime không còn cách nào kiểm tra lại.

## ⚙️ Điểm nhấn Java 11
`List.copyOf()` / `Set.copyOf()` / `Map.copyOf()` (Java 10) chính là ví dụ PECS **có thật ngoài đời**, không chỉ là lý thuyết: chữ ký của nó là `static <E> List<E> copyOf(Collection<? extends E> coll)` — dùng `? extends E` vì tham số `coll` chỉ đóng vai trò **producer** (chỉ bị đọc ra để copy), y hệt ví dụ `sum()` ở phần code minh họa bên dưới, và là bà con gần với `List.of()` đã học ở [bài 03](../03-list/README.md).

## 💻 Code minh họa
```java
import java.util.List;

// Producer Extends: chỉ ĐỌC (sum) từ source, không ghi gì vào nó
static double sum(List<? extends Number> source) {
    double total = 0;
    for (Number n : source) total += n.doubleValue(); // chỉ lấy ra, an toàn
    return total;
}

// Consumer Super: chỉ GHI (add) vào dest, không đọc gì cụ thể ra
static void addIntegers(List<? super Integer> dest) {
    dest.add(1); // chắc chắn an toàn: dest ít nhất là "rộng bằng" Integer
    dest.add(2);
}

sum(List.of(1, 2.5, 3L));              // List<? extends Number> nhận mọi loại số
addIntegers(new java.util.ArrayList<Number>()); // List<? super Integer> nhận Number, Object...
```

## 🧪 Thử thách thực hành
1. **Viết mới**: viết method generic `copyAll(List<? super T> dest, List<? extends T> src)` copy toàn bộ phần tử từ `src` sang `dest` — áp dụng đúng PECS cho cả 2 tham số.
2. **Dạy lại ngay tại chỗ**: giải thích vì sao dòng `List<Object> list = new ArrayList<String>();` không biên dịch, liên hệ lại với type erasure ở bài 02.

## 🗣️ Dạy lại
Tự đặt ra một ví dụ đời sống khác (không phải nhà kho) để giải thích PECS cho một người bạn không rành generics.

## ✅ Checklist hoàn thành
- [ ] Áp dụng đúng `extends`/`super` theo PECS mà không cần đoán mò.
- [ ] Giải thích được tính bất biến của Generics bằng ví dụ cụ thể.
