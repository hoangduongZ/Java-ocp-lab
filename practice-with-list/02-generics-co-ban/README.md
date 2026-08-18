# 📘 Giáo án #02: Generics cơ bản

> ⬅️ Trước: [01 - Array](../01-array/README.md) | ➡️ Tiếp theo: [03 - List](../03-list/README.md)

## 🎯 Mục tiêu buổi học
- Hiểu Generics giải quyết vấn đề gì so với dùng `Object` + ép kiểu thủ công.
- Biết khái niệm **type erasure** — vì sao đây là "chiếc hộp đen" quan trọng nhất của Generics.
- Viết được một class generic đơn giản với bounded type.

## 🧠 Ẩn dụ đời sống
Trước khi có Generics, một cái hộp (`Object box`) là **hộp vô danh** — ai bỏ gì vào cũng được, nhưng khi lấy ra bạn phải **đoán và ép kiểu** (`(String) box.get()`), đoán sai là ăn `ClassCastException` giữa chừng, rất trễ mới phát hiện. Generics giống như bạn **dán nhãn ngay trên vỏ hộp** từ lúc đặt hàng ở xưởng: `Box<String>` nghĩa là "hộp này chỉ chứa String", nhà máy (compiler) kiểm tra ngay lúc đóng gói (compile-time), không đợi đến lúc mở hộp mới biết sai.

## 🔬 Bên dưới lớp vỏ (Type Erasure)
- Đây là sự thật gây sốc: đến lúc **runtime**, JVM **xóa sạch** thông tin `<T>` — `Box<String>` và `Box<Integer>` sau khi biên dịch **cùng là một class** `Box` với các chỗ dùng `T` được thay bằng `Object` (hoặc cận trên nếu có bound).
- Cái nhãn `<String>` chỉ tồn tại lúc bạn viết code và lúc compiler kiểm tra — nó là "hợp đồng giấy" giữa bạn và compiler, không phải thứ tồn tại thật trong bộ nhớ lúc chạy.
- Hệ quả: không thể làm `new T[10]`, không thể `if (obj instanceof T)` — vì runtime không còn biết `T` là gì.

## ⚙️ Điểm nhấn Java 11
- **Diamond operator `<>`**: từ Java 7 đã có, nhưng kết hợp với `var` (Java 10/11) thì gọn tối đa: `var box = new Box<String>();` — vẫn phải ghi `<String>` bên phải vì `var` suy luận kiểu từ vế phải, không có "target type" để đoán như khi khai báo `Box<String> box = new Box<>();`.

## 💻 Code minh họa
```java
// T là "chỗ trống điền tên" — sẽ được điền cụ thể lúc dùng
public class Box<T> {
    private T content;

    public void put(T item) {
        this.content = item; // không cần ép kiểu, compiler đảm bảo đúng loại
    }

    public T get() {
        return content; // trả về đúng kiểu T, không cần cast ở nơi gọi
    }
}

// Sử dụng — nhãn String được dán ngay từ đầu
Box<String> stringBox = new Box<>();
stringBox.put("Hello");
String s = stringBox.get(); // không cần (String) ép kiểu như thời Object
```

## 🧪 Thử thách thực hành
1. **Viết mới**: viết class generic `Pair<K, V>` chứa 2 giá trị bất kỳ, có `getKey()`/`getValue()`.
2. **Debug**: cho đoạn code dùng `Object` + ép kiểu bị `ClassCastException` — refactor sang dùng Generics để lỗi bị bắt ngay lúc biên dịch thay vì chạy mới biết.

## 🗣️ Dạy lại
Giải thích lại bằng lời của riêng bạn: "type erasure" là gì, và vì sao nó là lý do bạn không thể viết `new T[5]` bên trong một class generic.

## ✅ Checklist hoàn thành
- [ ] Viết được class generic có ít nhất 1 type parameter.
- [ ] Giải thích được type erasure mà không cần nhìn tài liệu.
- [ ] Biết vì sao Generics an toàn hơn `Object` + cast.
