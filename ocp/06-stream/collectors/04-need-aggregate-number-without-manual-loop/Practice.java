import java.util.List;
import java.util.stream.Collectors;

public class Practice {

    public static void main(String[] args) {

        /*
         * SCENARIO
         *
         * Tôi cần: từ danh sách số tiền hóa đơn, tính ra (a) tổng
         * số tiền và (b) số tiền trung bình — không viết loop
         * for/foreach tay để cộng dồn, dùng đúng collector cho
         * từng loại kết quả.
         *
         * Input của tôi:
         *     100, 250, 75
         *
         * Output tôi mong muốn:
         *     (a) tổng = 425
         *     (b) trung bình = 141.666...
         */

        List<Integer> amounts = List.of(100, 250, 75);

        // ANALOG EXAMPLE (nhu cầu khác, chỉ để thấy cú pháp):
        String sampleJoinedTags = List.of("java", "ocp").stream()
                .collect(Collectors.joining(" | ")); // = "java | ocp"

        // TODO: viết code cho (a) — tổng số tiền.


        // TODO: viết code cho (b) — số tiền trung bình.


        /*
         * HINT LADDER — chỉ mở dần, mỗi lần một hint.
         */

        // HINT 1: (a) và (b) là 2 method RIÊNG BIỆT, không phải
        // cùng 1 method với tham số khác nhau.

        // HINT 2: (a) bắt đầu bằng "summing...", (b) bắt đầu bằng
        // "averaging...". Cả hai đều nhận vào một hàm để lấy ra
        // giá trị số từ mỗi phần tử.

        // HINT 3: Collectors.summingInt(mapper) cho (a);
        // Collectors.averagingInt(mapper) cho (b).
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
 * 1. RETRIEVE — Method bạn định dùng cho (a) và (b) là gì? Viết
 *    ra TRƯỚC khi verify. Kiểu trả về là nguyên thủy (`int`,
 *    `double`) hay wrapper (`Integer`, `Double`)?
 *
 * 2. WHY THIS, NOT THAT — Có một method khác cùng họ, tên bắt đầu
 *    bằng "counting", chỉ trả về SỐ LƯỢNG phần tử. Tại sao nó
 *    không giải quyết được cả (a) và (b)?
 *
 * 3. BREAK IT — Nếu `amounts` là danh sách RỖNG, kết quả của (a)
 *    và (b) là gì? Dự đoán trước khi thử — hai câu trả lời có
 *    "an toàn" như nhau không, hay một trong hai có khả năng gây
 *    lỗi runtime?
 *
 * 4. TEACH IT BACK — Giải thích cho một Junior tại sao kết quả
 *    của (a)/(b) luôn là kiểu wrapper chứ không phải nguyên thủy —
 *    không dùng "API nó thế".
 */
