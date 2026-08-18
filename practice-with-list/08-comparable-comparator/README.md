# 📘 Giáo án #08: Comparable / Comparator

> ⬅️ Trước: [07 - Collections/Arrays](../07-collections-arrays/README.md) | ➡️ Tiếp theo: [09 - Iterator](../09-iterator/README.md)

## 🎯 Mục tiêu buổi học
- Phân biệt rõ "thứ tự tự nhiên" (Comparable) với "thứ tự theo ngữ cảnh bên ngoài" (Comparator).
- Dùng thành thạo `Comparator.comparing().thenComparing().reversed()`.
- Tránh được `ClassCastException` khi so sánh sai kiểu.

## 🧠 Ẩn dụ đời sống
`Comparable` giống như **thứ tự trong giấy khai sinh** của một người — nó gắn liền, chỉ có **một** thứ tự "mặc định" (ví dụ sắp theo tên), do chính class tự khai báo (`implements Comparable`, viết `compareTo` bên trong).

`Comparator` giống như một **giám khảo bên ngoài** được mời riêng cho một cuộc thi cụ thể: hôm nay giám khảo A xếp hạng thí sinh theo chiều cao, ngày mai giám khảo B lại xếp theo cân nặng — mà bản thân thí sinh (class) **không hề bị sửa đổi gì cả**. Bạn có thể mời bao nhiêu giám khảo (Comparator) tùy thích cho cùng một class.

## ⚖️ So sánh cảm giác
| | Comparable | Comparator |
|---|---|---|
| Nằm ở đâu | Bên trong chính class (`implements Comparable<T>`) | Bên ngoài, độc lập với class |
| Số lượng | Chỉ 1 (một "thứ tự mặc định") | Nhiều, tùy ngữ cảnh |
| Method | `compareTo(T o)` | `compare(T a, T b)` |
| Có sửa class gốc không | Có (tính "xâm lấn") | Không cần đụng vào class gốc |

## 🔬 Bên dưới lớp vỏ
`Collections.sort()`/`Arrays.sort()` cho object dùng thuật toán **TimSort** — một sort **ổn định (stable)**: nếu hai phần tử được so sánh bằng nhau (`compare` trả về 0), chúng giữ nguyên thứ tự tương đối ban đầu. Tính "ổn định" này là lý do `thenComparing()` hoạt động đúng ý — sắp theo tiêu chí phụ mà không phá vỡ kết quả đã sắp theo tiêu chí chính.

## ⚙️ Điểm nhấn Java 11
- `Comparator.comparing(Person::getAge).thenComparing(Person::getName)` — nhờ method reference (bài 12) và lambda, việc ghép nhiều tiêu chí sắp xếp trở nên đọc như văn xuôi, thay vì phải tự viết if/else so sánh từng field như thời Java 7.
- `.reversed()` đảo ngược một Comparator có sẵn mà không cần viết lại logic.

## 💻 Code minh họa
```java
public class Person implements Comparable<Person> {
    String name; int age;
    Person(String name, int age) { this.name = name; this.age = age; }

    @Override
    public int compareTo(Person other) {
        return this.name.compareTo(other.name); // "thứ tự mặc định": theo tên
    }
}

var people = new java.util.ArrayList<Person>(java.util.List.of(
        new Person("Binh", 30), new Person("An", 25), new Person("An", 20)));

people.sort(null); // dùng Comparable mặc định (theo tên)

// Giám khảo riêng: sắp theo tuổi trước, trùng tuổi thì mới xét tên
people.sort(java.util.Comparator.comparingInt((Person p) -> p.age)
        .thenComparing(p -> p.name));
```

## 🧪 Thử thách thực hành
1. **Viết mới**: cho class `Product(String name, double price, int stock)`, implement `Comparable` theo `price`, đồng thời viết thêm 2 `Comparator` khác nhau (theo `name`, theo `stock` giảm dần).
2. **Debug**: cho một `List` chứa lẫn lộn 2 kiểu object khác nhau nhưng ép cùng generic type `Object`, khi `Collections.sort()` bị `ClassCastException` — giải thích nguyên nhân và sửa bằng cách ràng buộc kiểu đúng.

## 🗣️ Dạy lại
Tự đặt câu hỏi và trả lời: "Nếu một class đã có `Comparable` rồi, tại sao tôi vẫn cần `Comparator`?"

## ✅ Checklist hoàn thành
- [ ] Viết đúng `compareTo` không vi phạm hợp đồng (đối xứng, bắc cầu).
- [ ] Ghép được nhiều Comparator bằng `thenComparing`.
