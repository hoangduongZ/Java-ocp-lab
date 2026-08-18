import java.util.List;
import java.util.stream.Collectors;

public class Practice {

    public static void main(String[] args) {

        /*
         * SCENARIO
         *
         * Tôi cần: build một danh sách cấu hình từ stream, và
         * CHẮC CHẮN không ai (kể cả chính tôi ở dòng code sau) có
         * thể vô tình thêm/xóa phần tử vào danh sách đó sau khi đã
         * build xong.
         *
         * Input của tôi:
         *     "dev", "staging", "prod"
         *
         * Output tôi mong muốn:
         *     một danh sách chứa đúng 3 phần tử trên, và bất kỳ
         *     lời gọi add()/remove() nào sau đó lên danh sách này
         *     đều phải bị chặn lại, không được âm thầm thành công.
         */

        List<String> rawInput = List.of("dev", "staging", "prod");

        // ANALOG EXAMPLE (nhu cầu khác, chỉ để thấy cú pháp):
        String sampleJoined = List.of("java", "ocp").stream()
                .collect(Collectors.joining(" | ")); // = "java | ocp"

        // TODO: viết một stream pipeline tạo ra danh sách kết quả
        // với ràng buộc trên.


        /*
         * Sau khi có kết quả, thử thêm dòng dưới (đổi tên biến kết
         * quả nếu cần) và dự đoán TRƯỚC khi uncomment:
         *
         * - Đây là compile error hay runtime exception?
         * - Nếu là runtime, exception cụ thể là gì?
         */

        // result.add("qa");


        /*
         * HINT LADDER — chỉ mở dần, mỗi lần một hint.
         */

        // HINT 1: method "hiển nhiên" nhất cùng họ (loại thu thập
        // thành List cơ bản) KHÔNG cho bạn sự đảm bảo này — danh
        // sách nó trả về vẫn sửa được bình thường.

        // HINT 2: có một method khác cùng họ, tên dài hơn, ghép
        // thêm tiền tố "Unmodifiable" vào giữa tên method cơ bản.

        // HINT 3: Collectors.toUnmodifiableList().
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
 *    TRƯỚC khi verify.
 *
 * 2. WHY THIS, NOT THAT — Nếu bạn collect bằng method cơ bản
 *    (không có ràng buộc bất biến) rồi tự bọc kết quả qua một
 *    method của `Collections` để làm nó bất biến — cách này có
 *    tương đương với method bạn chọn không? Khác nhau ở đâu?
 *
 * 3. BREAK IT — Uncomment dòng `result.add(...)` ở trên (đổi tên
 *    biến cho khớp). Dự đoán TRƯỚC: compile error hay runtime
 *    exception? Sau khi biết đáp án, giải thích tại sao Java
 *    không bắt lỗi này ngay lúc compile.
 *
 * 4. TEACH IT BACK — Một Junior nói: "Cứ dùng List bình thường,
 *    đừng động vào nó là được, không cần method đặc biệt." Giải
 *    thích cho họ tại sao "không động vào" (self-discipline) khác
 *    với "không thể động vào" (compiler/runtime enforcement).
 */
