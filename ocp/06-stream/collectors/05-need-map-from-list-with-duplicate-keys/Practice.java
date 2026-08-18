import java.util.List;
import java.util.stream.Collectors;

public class Practice {

    public static void main(String[] args) {

        /*
         * SCENARIO
         *
         * Tôi cần: build một map từ danh sách string, với KEY là
         * độ dài của string, VALUE là chính string đó.
         *
         * Input của tôi:
         *     "a", "bb", "cc"
         *
         * Lưu ý: "bb" và "cc" CÙNG có độ dài 2 — hai key sẽ trùng
         * nhau.
         *
         * Output tôi mong muốn:
         *     một map có key 1 và key 2 — chương trình không được
         *     crash vì hai phần tử cùng key.
         */

        List<String> input = List.of("a", "bb", "cc");

        // ANALOG EXAMPLE (nhu cầu khác, chỉ để thấy cú pháp):
        double sampleAverage = List.of(1, 2, 3).stream()
                .collect(Collectors.averagingInt(n -> n)); // = 2.0

        // TODO: viết code giải quyết SCENARIO trên. Nếu hai phần
        // tử cùng rơi vào một key, hãy tự quyết định: giữ phần tử
        // nào, hay ghép cả hai lại — nhưng đừng để chương trình
        // crash lúc runtime.


        /*
         * HINT LADDER — chỉ mở dần, mỗi lần một hint.
         */

        // HINT 1: category đúng (build map từ stream) là đúng
        // hướng — nhưng nếu bạn chỉ dùng phiên bản 2 tham số
        // (key mapper, value mapper) của method đó, code compile
        // được, còn RUN thì thế nào với dữ liệu này?

        // HINT 2: method đó có một overload nhận THÊM một tham số
        // thứ ba — một hàm xử lý khi 2 phần tử map vào cùng key.

        // HINT 3: Collectors.toMap(keyMapper, valueMapper,
        // mergeFunction) — mergeFunction nhận (existing, incoming)
        // và bạn tự quyết định trả về gì.
    }
}

/*
 * ============================================================
 * YOUR TASK
 * ============================================================
 *
 * RULE:
 * KHÔNG mở Hint 2 nếu chưa tự thử ít nhất một lần với Hint 1.
 *
 *
 * 1. RETRIEVE — Method/overload bạn định dùng là gì? Viết ra
 *    TRƯỚC khi verify. Nếu chỉ dùng overload 2 tham số với đúng
 *    dữ liệu SCENARIO — code này COMPILE được không?
 *
 * 2. VERIFY BY REASONING (extra — merge function dễ nhầm thứ tự
 *    tham số) — Đọc signature overload 3 tham số. `mergeFunction`
 *    nhận (existing, incoming) theo thứ tự nào, và bạn quyết định
 *    trả về gì khi 2 phần tử cùng key?
 *
 * 3. WHY THIS, NOT THAT — Overload 2 tham số compile được nhưng
 *    có thể vỡ lúc RUN. Đây là lỗi compile time hay runtime?
 *    Compiler đã "biết" 2 key sẽ trùng nhau chưa, tại sao?
 *
 * 4. BREAK IT — Đổi input thành `List.of("a", "bb", "ccc")`
 *    (không còn key trùng). Overload 2 tham số giờ chạy được
 *    không? Điều này chứng minh gì về BẢN CHẤT của lỗi ở câu 3 —
 *    nó phụ thuộc vào code hay phụ thuộc vào DỮ LIỆU?
 *
 * 5. TEACH IT BACK — Một Junior hỏi: "Sao cùng một method
 *    `toMap()`, có lúc chạy được, có lúc RuntimeException?" Giải
 *    thích cho họ mà không dùng "phải nhớ overload nào".
 */
