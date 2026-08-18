# 📘 Giáo án #01: Array

> ⬅️ Bắt đầu lộ trình | ➡️ Tiếp theo: [02 - Generics cơ bản](../02-generics-co-ban/README.md)

## 🎯 Mục tiêu buổi học
- Hiểu vì sao Array có kích thước cố định và điều đó ảnh hưởng gì đến bộ nhớ.
- Nắm được bẫy kinh điển OCP: **array covariance** gây `ArrayStoreException`.
- Phân biệt được khai báo mảng 1 chiều, đa chiều, và mảng "jagged".

## 🧠 Ẩn dụ đời sống
Array giống như một **toa tàu có số ghế cố định khi mua vé**: bạn khai báo "toa này có 5 ghế" (`new int[5]`) thì vĩnh viễn chỉ có 5 ghế, đánh số từ 0. Muốn thêm khách, bạn không thể "nới" toa tàu ra — phải mua hẳn một toa tàu mới, dài hơn, rồi bê từng khách cũ sang (đây chính là việc `Arrays.copyOf` làm phía sau).

## 🔬 Bên dưới lớp vỏ (Heap/Stack)
- Biến `int[] arr` trên **Stack** chỉ là một **tham chiếu** (địa chỉ), bản thân toa tàu (dữ liệu thật) nằm trên **Heap**, là một khối bộ nhớ liên tục (contiguous).
- Vì liên tục nên truy cập theo chỉ số `arr[i]` là phép tính địa chỉ O(1) — cực nhanh, không cần dò từng phần tử.
- Mảng object (`String[]`, `Object[]`) là mảng các **tham chiếu**, không phải mảng object thật — mỗi ô trỏ tới một object nằm đâu đó khác trên Heap.

## ⚙️ Điểm nhấn Java 11
- `var` giúp khai báo mảng gọn hơn: `var scores = new int[]{90, 85, 77};` — trình biên dịch tự suy ra `int[]`.
- Lưu ý: `var` **không suy luận được** nếu chỉ viết `var arr = {1, 2, 3};` (thiếu `new int[]`) — đây là lỗi biên dịch hay gặp khi mới học `var`.

## 💻 Code minh họa
```java
public class ArrayDemo {
    public static void main(String[] args) {
        // Khai báo mảng Object chứa tham chiếu String (an toàn vì đúng kiểu)
        Object[] boxOfLetters = new String[3];
        boxOfLetters[0] = "A"; // OK

        try {
            // Bẫy covariance: compiler cho phép vì String[] "là một" Object[],
            // nhưng tại RUNTIME, JVM vẫn nhớ mảng thật sự là String[]
            boxOfLetters[1] = Integer.valueOf(10); // biên dịch OK, chạy FAIL
        } catch (ArrayStoreException e) {
            System.out.println("Toa tàu này chỉ chở String thôi! " + e);
        }
    }
}
```

## 🧪 Thử thách thực hành
1. **Viết mới**: viết method `reverseInPlace(int[] arr)` đảo ngược mảng mà không tạo mảng mới.
2. **Debug**: cho đoạn code có bug covariance ở trên nhưng KHÔNG có try/catch — sửa lại để chương trình không crash mà in ra cảnh báo hợp lý.

## 🗣️ Dạy lại
Hãy tự viết một đoạn code khác (không phải String/Integer) minh họa lại `ArrayStoreException`, rồi giải thích bằng lời tại sao compiler "bị lừa" mà runtime thì không.

## ✅ Checklist hoàn thành
- [ ] Giải thích được ẩn dụ "toa tàu cố định ghế" cho người khác hiểu.
- [ ] Tự tay tạo được và bắt được `ArrayStoreException`.
- [ ] Biết khi nào dùng `var` với mảng thì hợp lệ.
