# Tại sao override Comparator.compare() một lần tại Collections.sort() lại nên là lambda, không phải anonymous class?

## 1. The strange thing

```java
List<String> names = new ArrayList<>(List.of("Charlie", "Al", "Bob"));

Collections.sort(names, new Comparator<String>() {
    @Override
    public int compare(String a, String b) {
        return a.length() - b.length();
    }
});

System.out.println(names);
```

**KHÔNG CHẠY CODE.**

Hãy dự đoán:

1. Compile được không?
> Compile được
> AI review: Đúng. Anonymous class implement đúng chữ ký `compare(String, String)` mà `Comparator<String>` yêu cầu (sau erasure, `T` = `String`), nên đây là compile-time hợp lệ — không liên quan gì tới việc code có chạy đúng logic hay không.
2. Output là gì?
> Output là mảng được sắp xếp theo kí tự alpha tăng dần
> AI review: Kết quả cuối `[Al, Bob, Charlie]` đúng, nhưng lý do sai. `compare()` ở đây so sánh `a.length() - b.length()`, tức sắp theo ĐỘ DÀI, không so sánh ký tự alpha. Với bộ dữ liệu "Charlie"(7), "Al"(2), "Bob"(3), thứ tự theo độ dài (2,3,7) vô tình trùng với thứ tự alpha — đó là trùng hợp của dữ liệu, không phải cơ chế của comparator. Với bộ {"Bob", "Ann"} (cùng độ dài 3), `compare()` trả 0 nên Collections.sort() (stable) giữ nguyên thứ tự gốc, chứ không sắp theo alpha.
3. Nếu đổi `new Comparator<String>() { ... }` thành `(a, b) -> a.length() - b.length()`, output có khác không?
> Ouput vẫn thế
> AI review: Đúng, output giống nhau vì HÀNH VI (behavior) giống nhau — cả hai đều so sánh theo `a.length() - b.length()`. Cái khác nhau nằm ở "vật mang" hành vi đó: anonymous class sinh ra một object thật với type riêng (`Example$1`, có `getClass()` riêng), còn lambda không tạo class có tên, được biên dịch qua `invokedynamic`. `Collections.sort()` chỉ quan tâm kết quả của `compare()`, không quan tâm object đó là gì.
4. Nếu hai cách viết cho cùng kết quả, tại sao Java lại có tới hai cách để làm cùng một việc?
> Tôi nghĩ lambda ngắn gọn hơn
> AI review: "Ngắn gọn hơn" đúng nhưng chỉ là hiện tượng bề mặt, không phải lý do Java thiết kế ra hai cách. Lý do thật: `Comparator` là functional interface (đúng 1 abstract method), nên compiler cho phép target-type lambda vào nó mà không cần tạo object có danh tính riêng. Cái bạn đánh đổi khi dùng lambda là object không còn field/method riêng của chính nó — nếu sau này cần state riêng hoặc nhiều method, bắt buộc phải quay lại anonymous class (xem mục 7 "Design feeling").

---

## 2. Why this feels strange

Trực giác phổ biến:

> "Tôi cần override `compare()`, chỉ dùng một lần ngay tại lời gọi `Collections.sort()`, không tái sử dụng ở đâu khác → vậy phải tạo một object override method, tức là anonymous class."

Trực giác này **không sai về mặt compile** — anonymous class luôn làm được việc này.

Nhưng nó sai về **thiết kế**: chính cái mô tả "override một method, dùng một lần, không tái sử dụng" lại là định nghĩa của trường hợp mà `lambda` được sinh ra để giải quyết, không phải anonymous class.

Anonymous class không phải là công cụ mặc định cho "one-off override". Nó là công cụ cho khi bạn cần **nhiều hơn một hành vi**.

---

## 3. Mental model

```text
Comparator<String>
   |
   | (interface có ĐÚNG 1 abstract method: compare)
   v
Functional interface
```

Khi một interface chỉ có đúng 1 abstract method, compiler cho phép bạn "target-type" nó bằng lambda:

```text
(a, b) -> a.length() - b.length()
     |
     | compiler biết: đây phải khớp compare(String, String)
     v
Comparator<String>  (implemented, nhưng KHÔNG có class riêng)
```

Còn anonymous class thì luôn tạo ra một **object thật, có type riêng**:

```text
new Comparator<String>() { ... }
     |
     v
Heap: Example$1 object
      + compare()
      + (có thể có thêm field / method riêng)
```

Câu hỏi mấu chốt không phải "tôi override bao nhiêu lần", mà là:

> Tôi chỉ cần MỘT hành vi (behavior), hay tôi cần một OBJECT có danh tính, có thể mang thêm state/method riêng?

---

## 4. Real-world analogy

Lambda giống thuê một **cộng tác viên tự do (freelancer)**: giao đúng một việc — "so sánh hai chuỗi theo độ dài" — làm xong là thôi, không cần hồ sơ nhân viên, không có mã số riêng.

Anonymous class giống tuyển một **nhân viên tạm thời có mã số nhân viên riêng**. Nhân viên này có thể biết thêm vài kỹ năng phụ (extra method), nhưng phòng nhân sự (compiler) chỉ cho các phòng ban khác liên hệ nhân viên đó **qua đúng chức danh đã ký hợp đồng** (interface reference type) — dù nhân viên biết nhiều hơn thế.

---

## 5. What Java is really doing

- `Comparator<T>` là **functional interface** (đúng 1 abstract method: `compare`).
- Với functional interface, compiler cho phép **target typing**: lambda không sinh ra một class mới có tên, nó được biên dịch qua `invokedynamic`, không có object danh tính riêng dạng `Example$1`.
- Anonymous class luôn sinh ra một class file thật (`Example$1.class`) và một object thật trên heap, có `getClass()`, `hashCode()` riêng, và **có thể khai báo thêm method/field của chính nó**.
- Vì vậy: nếu hành vi bạn cần **gói gọn trong đúng 1 method, không cần state riêng, không cần method phụ do object đó tự sở hữu** → lambda là lựa chọn đúng thiết kế, không phải "cách viết tắt của anonymous class".

---

## 6. OCP Trick Catcher

Bẫy thường gặp: đề thi cho một anonymous class có thêm một **private helper method** bên trong (ví dụ `describe()`) được gọi từ trong `compare()`, rồi hỏi "đoạn này có thể viết lại bằng lambda không?".

Người ra đề muốn bạn nghĩ:

> "Có helper method phụ → chắc lambda không làm được → phải giữ anonymous class."

Đây là kết luận **vội**. Sự thật:

- Lambda không thể **tự khai báo** một method mới thuộc về chính nó (không có `this` riêng với method phụ).
- Nhưng lambda **vẫn gọi được** một method đã tồn tại sẵn ở nơi khác (method tĩnh, method của enclosing class...).

Vậy câu hỏi thật không phải "có logic phụ hay không", mà là:

> Logic phụ đó có buộc phải là method **riêng của chính object đang implement interface**, hay nó có thể là một method độc lập mà lambda gọi tới?

---

## 7. Design feeling

Trước Java 8, muốn truyền một "hành vi" (behavior) vào một method như `Collections.sort()`, bạn buộc phải tạo object — dù chỉ cần một dòng logic. Anonymous class ra đời để giải quyết đúng nhu cầu đó, nhưng cú pháp rất nặng cho một việc đơn giản.

Lambda sinh ra để tách hai nhu cầu vốn bị anonymous class gộp chung:

```text
Tôi cần TRUYỀN MỘT HÀNH VI          → lambda
Tôi cần MỘT OBJECT CÓ DANH TÍNH      → anonymous class
                                       (state riêng, nhiều method riêng,
                                        cần this phân biệt, cần override
                                        nhiều abstract method)
```

`Comparator` trong `Collections.sort()` gần như luôn rơi vào nhóm đầu.

---

## 8. Before opening Example.java

Hãy tự trả lời trước:

1. Nếu tôi thêm một `private` method vào bên trong anonymous class Comparator, lambda có thể thay thế được không?
-> lambda, lambda chỉ hỗ trợ functional interface, tức 1 hành vi
-> AI review: Kết luận "không thay thế được" đúng cho đúng trường hợp này, nhưng lý do "lambda chỉ hỗ trợ functional interface" chưa trúng chỗ — `Comparator` vẫn là functional interface như cũ, không có gì thay đổi ở phía interface. Giới hạn thật nằm ở phía lambda: lambda không thể TỰ KHAI BÁO một method mới thuộc về chính nó (không có object riêng để gắn method `weight()` vào). Đó là lý do Experiment 3 trong Example.java giữ nguyên anonymous class.
2. Nếu method phụ đó được kéo ra thành một method `static` của class ngoài, câu trả lời ở câu 1 có đổi không? Tại sao?
-> Chắc vẫn không hỗ trợ
-> AI review: Câu trả lời này sai — chính là bẫy mà README mục 6 "OCP Trick Catcher" mô tả. Khi `weight()` được kéo ra thành method `static` của class `Example`, lambda KHÔNG cần tự khai báo `weight()` nữa — nó chỉ gọi một method đã có sẵn ở enclosing class, giống như gọi bất kỳ method static nào khác. Vì vậy `Comparator<String> comparator4 = (a, b) -> weight(a) - weight(b);` compile được (đã verify bằng javac trong Example.java, Experiment 4). Giới hạn thật của lambda chỉ là "không tự khai báo method mới cho chính nó", không phải "không được dùng logic phụ nào cả".
3. Reference type của biến `comparator` là `Comparator<String>` — nếu object thật (anonymous class) có thêm method không thuộc `Comparator`, tôi có gọi được method đó qua biến `comparator` không?
-> Có hỗ trợ
-> AI review: Câu trả lời này sai. Compiler kiểm tra lời gọi method dựa trên REFERENCE TYPE đã khai báo (`Comparator<String>`), không dựa trên object thật trên heap — dù object thật có method `describe()`. Đây chính là bẫy "reference giống thẻ chức danh" ở mục 4: dù object biết làm nhiều hơn, bạn chỉ được gọi qua đúng những method mà `Comparator<String>` công khai. Đã verify: gọi `comparator.describe(5)` trong Practice.java báo lỗi `cannot find symbol: method describe(int)`, đúng như dự đoán từ mental model này.
