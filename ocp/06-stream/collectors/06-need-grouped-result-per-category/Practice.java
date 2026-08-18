import java.util.List;
import java.util.stream.Collectors;

public class Practice {

    public static void main(String[] args) {

        /*
         * SCENARIO
         *
         * Tôi cần: từ danh sách string, (a) nhóm các string theo
         * độ dài của chúng, sau đó (b) chỉ cần biết SỐ LƯỢNG string
         * trong mỗi nhóm độ dài — không cần danh sách string đầy
         * đủ nữa.
         *
         * Input của tôi:
         *     "a", "bb", "cc", "ddd"
         *
         * Output tôi mong muốn:
         *     (a) nhóm theo độ dài: 1 -> ["a"], 2 -> ["bb", "cc"],
         *         3 -> ["ddd"]
         *     (b) đếm theo độ dài: 1 -> 1, 2 -> 2, 3 -> 1
         */

        List<String> input = List.of("a", "bb", "cc", "ddd");

        // ANALOG EXAMPLE (nhu cầu khác, chỉ để thấy cú pháp):
        String sampleJoinedLetters = List.of("a", "b", "c").stream()
                .collect(Collectors.joining("-")); // = "a-b-c"

        // TODO: viết code cho (a) — nhóm theo độ dài.


        // TODO: viết code cho (b) — đếm số lượng theo độ dài.


        /*
         * HINT LADDER — chỉ mở dần, mỗi lần một hint.
         */

        // HINT 1: category là "gom phần tử theo 1 tiêu chí thành
        // NHIỀU nhóm" — không phải collector chỉ tạo đúng 2 nhóm
        // true/false.

        // HINT 2: (a) và (b) dùng CÙNG một method ở tầng ngoài,
        // tên bắt đầu bằng "grouping...". Khác nhau ở việc có hay
        // không có một tham số thứ hai — một collector KHÁC lồng
        // vào bên trong để xử lý "trong mỗi nhóm".

        // HINT 3: Collectors.groupingBy(classifier) cho (a);
        // Collectors.groupingBy(classifier, Collectors.counting())
        // cho (b).
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
 * 1. RETRIEVE — Method/overload bạn định dùng cho (a) và (b) là
 *    gì? Viết ra TRƯỚC khi verify.
 *
 * 2. WHY THIS, NOT THAT — Có một method khác GẦN GIỐNG, cũng
 *    "chia phần tử thành các nhóm", nhưng LUÔN LUÔN cho ra đúng 2
 *    nhóm (kể cả khi một nhóm rỗng). Method đó là gì, và tại sao
 *    nó không dùng được cho (a), nơi số nhóm phụ thuộc DỮ LIỆU?
 *
 * 3. BREAK IT — Đổi input thành `List.of("a", "b", "c")` (mọi
 *    phần tử cùng độ dài 1). Kết quả của (a) và (b) giờ trông như
 *    thế nào? Có còn "nhiều nhóm" như mong đợi ban đầu không?
 *
 * 4. TEACH IT BACK — Giải thích cho một Junior tại sao (a) và (b)
 *    không cần 2 method hoàn toàn khác nhau, mà chỉ khác nhau ở
 *    một tham số "lồng vào bên trong" — không dùng "API nó thế".
 *
 * 5. OCP EXAMINER (extra — groupingBy/partitioningBy là bẫy thi
 *    kinh điển) — Người ra đề OCP sẽ đổi đúng một chi tiết nào
 *    trong SCENARIO để biến (b) từ "đếm theo độ dài" thành một
 *    nhu cầu mà collector đếm đơn thuần không còn đủ?
 */
