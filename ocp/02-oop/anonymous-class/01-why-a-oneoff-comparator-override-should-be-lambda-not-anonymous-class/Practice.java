import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Practice {

    public static void main(String[] args) {

        List<Integer> numbers = new ArrayList<>(List.of(5, 2, 9, 1));

        Comparator<Integer> comparator = new Comparator<Integer>() {

            @Override
            public int compare(Integer a, Integer b) {
                return describe(a) - describe(b);
            }

            public int describe(Integer value) {
                return value % 3;
            }
        };

        Collections.sort(numbers, comparator);

        System.out.println(numbers);

        // Dòng này sống hay chết? chết đét, chỉ dùng được method hợp đồng với interface thui, same same super class và child class, nếu instance của child class được refer bằng super class thì cũng chỉ giới hạn method của super class thôi, kiểu như down casting
        // -> AI review: Đúng — dòng này chết (compile error), và đúng cả
        //    mechanism: compiler chỉ cho gọi method có trong "hợp đồng"
        //    của reference type (Comparator<Integer>), giống hệt việc
        //    reference kiểu superclass chỉ thấy method của superclass dù
        //    object thật là subclass. Có một chỗ lệch thuật ngữ: hiện
        //    tượng bạn vừa mô tả (object cụ thể hơn bị "thu hẹp" quyền
        //    truy cập khi gán vào reference rộng hơn) là hệ quả của
        //    UPCASTING — đã xảy ra ngay lúc gán
        //    `Comparator<Integer> comparator = new Comparator<Integer>() {...}`,
        //    không phải downcasting. DOWNCASTING mới là thứ có thể GIẢI
        //    QUYẾT vấn đề này — ép comparator về lại type cụ thể hơn để
        //    gọi được describe(), đó chính là hướng cho Level 4 (Fix).
        comparator.describe(5);
    }
}

/*
 * ============================================================
 * YOUR TASK
 * ============================================================
 *
 * RULE:
 * KHÔNG CHẠY CODE NGAY.
 *
 *
 * LEVEL 1 — PREDICT
 *
 * 1. Toàn bộ file này compile được không? Toàn bộ file này không compile được, vì đang dính lỗi unchecked
 *
 * 2. Nếu KHÔNG compile:
 *    dòng nào chết, và vì sao chỉ dòng đó chết chứ không phải
 *    cả comparator? Dòng 43 chết, comparator sống vì anonymous có thể thêm được thuộc tính và method như class thường, chỉ là không có 1 định danh cụ thể thôi
 *
 * 3. Nếu bỏ dòng cuối (`comparator.describe(5);`) đi,
 *    output của phần còn lại là gì? Phần output còn lại chỉ là in ra mảng được sắp xếp theo số dư chia hết cho 3 thôi, public hay private trong class đó thì cũng chỉ dùng được trong scope của class đó
 *
 *
 * LEVEL 2 — EXPLAIN
 *
 * 4. `describe` được khai báo `public`, và object thật sự
 *    (runtime object) chắc chắn có method này.
 *    Vậy tại sao dòng gọi `comparator.describe(5)` vẫn có thể
 *    không hợp lệ? Compiler đang dựa vào điều gì để cho phép
 *    hoặc từ chối một lời gọi method?
 *      -> Quy tắc giống upcasting của cha và con như giải thích ở trên
 *
 * LEVEL 3 — MENTAL MODEL
 *
 * 5. Vẽ lại:
 *
 *      comparator
 *          |
 *          v
 *      reference type: ? Practice$1
 *
 *      object thật trên heap
 *          |
 *          v
 *      có những method nào? Có các method trong anonymous class đó luôn kèm vs method static của collection...
 *
 *    Hai tập method đó có bằng nhau không? Chưa hiểu câu hỏi lắm
 *
 *
 * LEVEL 4 — FIX
 *
 * 6. Sửa ĐÚNG MỘT khai báo (không sửa logic bên trong) để dòng
 *    `comparator.describe(5)` compile được, mà vẫn giữ nguyên
 *    toàn bộ phần khai báo `new Comparator<Integer>() { ... }`.
 *    > 
 *
 * LEVEL 5 — CHANGE ONE THING
 *
 * 7. Nếu thay `describe` từ `public` thành `private`, và gọi
 *    `describe` CHỈ từ bên trong `compare()` (không có dòng
 *    `comparator.describe(5)` ở main) — chương trình có còn
 *    compile không? Việc đổi `public`/`private` ở đây có làm
 *    thay đổi được kết luận ở Level 4 không? Tại sao?
 *
 *
 * LEVEL 6 — TEACH IT BACK
 *
 * 8. Một Junior hỏi bạn:
 *    "Object rõ ràng có method `describe`, sao gọi lại báo lỗi?"
 *
 *    Giải thích cho họ mà KHÔNG dùng:
 *    - "Java nó thế."
 *    - "Compiler không cho."
 *    - "Spec quy định."
 *
 *
 * LEVEL 7 — OCP EXAMINER
 *
 * 9. Nếu bạn là người ra đề OCP, bạn sẽ đổi đúng một dòng nào
 *    trong file này để biến một đáp án "compile lỗi" thành
 *    "compile được", mà không đổi kiểu dữ liệu của `numbers`?
 *
 * 10. Bài học ở case README (anonymous class vs lambda cho
 *     Comparator một lần) áp dụng vào đây như thế nào? Việc
 *     `describe` không gọi được từ ngoài có phải là lý do khiến
 *     bạn PHẢI chọn anonymous class thay vì lambda ở đây không?
 */
