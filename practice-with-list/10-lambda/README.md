# 📘 Giáo án #10: Lambda

> ⬅️ Trước: [09 - Iterator](../09-iterator/README.md) | ➡️ Tiếp theo: [11 - Functional Interface](../11-functional-interface/README.md)

## 🎯 Mục tiêu buổi học
- Hiểu Lambda không phải "hàm độc lập" mà là cách viết gọn để implement một interface có đúng 1 method.
- Hiểu quy tắc "effectively final" khi lambda bắt biến từ ngoài vào.
- Chuyển đổi thành thạo giữa anonymous class và lambda.

## 🧠 Ẩn dụ đời sống
Trước đây, để giao một việc nhỏ (ví dụ: "so sánh hai người theo tuổi") cho ai đó làm, bạn phải **tuyển hẳn một nhân viên mới, đào tạo, ký hợp đồng** (viết nguyên một `anonymous class implements Comparator`) chỉ để họ làm đúng một việc rồi thôi. Lambda giống như thay vì tuyển người, bạn chỉ **viết một tờ giấy nhớ ghi sẵn quy trình** ("lấy tuổi hai người, trừ cho nhau") rồi đưa thẳng cho ai cần dùng. Gọn hơn nhiều, nhưng bản chất công việc vẫn y hệt — phía sau JVM vẫn tạo ra một object implement đúng interface đó, chỉ là bạn không phải gõ hết phần "khai sinh nhân viên" nữa.

## 🔬 Bên dưới lớp vỏ: "effectively final"
Đây là chi tiết hay bị hỏi thi OCP: lambda **không thực sự "đóng gói" (capture) biến ngoài theo kiểu tham chiếu sống** như một số ngôn ngữ khác — nó chỉ được phép đọc các biến cục bộ bên ngoài nếu biến đó **không hề bị gán lại giá trị** sau khi khai báo (dù không có từ khóa `final`, chỉ cần "coi như final" — effectively final). Lý do: tờ giấy nhớ (lambda) có thể được ai đó cầm đi dùng ở một thời điểm/luồng khác, rất lâu sau khi hàm gốc đã kết thúc và biến cục bộ trên Stack đã biến mất — Java buộc phải "chụp ảnh" giá trị tại thời điểm tạo lambda để đảm bảo an toàn, nên nếu biến còn bị thay đổi thì bức ảnh chụp sẽ không đáng tin.

```java
int count = 0;
Runnable r = () -> System.out.println(count); // OK, count effectively final
// count++;  // Nếu thêm dòng này, lambda phía trên sẽ báo lỗi biên dịch!
```

## ⚖️ So sánh cảm giác: Anonymous Class vs Lambda
| | Anonymous Class | Lambda |
|---|---|---|
| Cảm giác | "Tuyển hẳn một nhân viên" cho một việc lặt vặt | "Đưa tờ giấy nhớ" ghi sẵn việc cần làm |
| Implement được | Interface nhiều method, hoặc `extends` một class | Chỉ interface đúng 1 abstract method (functional interface) |
| `this` bên trong trỏ tới | Chính đối tượng anonymous class đó | Đối tượng bao ngoài (lambda không tạo phạm vi `this` riêng) |
| Độ dài | Dài, khai báo kiểu tường minh | Ngắn gọn, kiểu được suy luận |

## ⚙️ Điểm nhấn Java 11
- Từ Java 11, được phép dùng `var` cho tham số của lambda: `(var a, var b) -> a.compareTo(b)`. Lợi ích thực tế không phải để "gõ ít hơn" (vẫn phải gõ `var`), mà để có chỗ **gắn annotation** lên tham số lambda, điều mà cú pháp `(a, b) -> ...` không cho phép làm trực tiếp.

## 💻 Code minh họa
```java
var people = new java.util.ArrayList<>(java.util.List.of("Binh", "An", "Cam"));

// Cách cũ: anonymous class — dài dòng
people.sort(new java.util.Comparator<String>() {
    @Override
    public int compare(String a, String b) {
        return a.compareTo(b);
    }
});

// Cách mới: lambda — "tờ giấy nhớ" gọn gàng, cùng bản chất
people.sort((a, b) -> a.compareTo(b));
```

## 🧪 Thử thách thực hành
1. **Viết mới**: chuyển 3 đoạn code dùng anonymous class (`Runnable`, `Comparator`, một interface tự định nghĩa) sang lambda.
2. **Debug**: cho đoạn code lambda cố gắng `count++` một biến ngoài bị lỗi biên dịch "variable used in lambda should be effectively final" — sửa lại logic (gợi ý: dùng mảng 1 phần tử hoặc `AtomicInteger` nếu bắt buộc phải đổi giá trị).

## 🗣️ Dạy lại
Tự giải thích bằng ví dụ khác: vì sao lambda cần biến bên ngoài phải "effectively final"?

## ✅ Checklist hoàn thành
- [ ] Viết lambda thay thế được anonymous class.
- [ ] Giải thích đúng "effectively final" mà không học thuộc lòng.
