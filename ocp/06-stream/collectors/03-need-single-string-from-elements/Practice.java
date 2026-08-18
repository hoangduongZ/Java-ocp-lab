import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Practice {

    public static void main(String[] args) {

        /*
         * SCENARIO
         *
         * Tôi cần: ghép danh sách tên thành MỘT chuỗi hiển thị,
         * theo hai định dạng khác nhau ở hai màn hình của app.
         *
         * Input của tôi:
         *     "Anna", "Bao", "Chi"
         *
         * Output tôi mong muốn:
         *     Yêu cầu 1 (màn hình A): "Anna, Bao, Chi"
         *     (dấu phẩy + khoảng trắng giữa các tên, KHÔNG có gì ở
         *     đầu/cuối chuỗi)
         *
         *     Yêu cầu 2 (màn hình B, xuất hiện SAU): "[Anna, Bao, Chi]"
         *     (cùng dữ liệu, nhưng có cặp ngoặc vuông bao ngoài)
         */

        List<String> names = List.of("Anna", "Bao", "Chi");

        // ANALOG EXAMPLE (nhu cầu khác, chỉ để thấy cú pháp):
        Set<Integer> sampleUniqueScores = List.of(10, 20, 20, 30).stream()
                .collect(Collectors.toSet()); // = {10, 20, 30}

        // TODO: viết code cho Yêu cầu 1.


        // TODO: viết code cho Yêu cầu 2.


        /*
         * HINT LADDER — chỉ mở dần, mỗi lần một hint.
         */

        // HINT 1: category là "nối các phần tử String thành 1
        // String duy nhất" — không phải gom nhóm, không phải build
        // Map.

        // HINT 2: cả hai yêu cầu dùng CÙNG MỘT method, tên bắt đầu
        // bằng "join...", chỉ khác nhau ở SỐ LƯỢNG tham số truyền
        // vào.

        // HINT 3: Collectors.joining(delimiter) cho Yêu cầu 1;
        // Collectors.joining(delimiter, prefix, suffix) cho Yêu
        // cầu 2. Đây là 2 overload của cùng 1 method.
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
 * 1. RETRIEVE — Method/overload bạn định dùng cho Yêu cầu 1 và
 *    Yêu cầu 2 là gì? Viết ra TRƯỚC khi verify.
 *
 * 2. VERIFY BY REASONING (extra — overload 3 tham số dễ nhầm thứ
 *    tự) — Đọc signature của overload bạn chọn cho Yêu cầu 2. Ba
 *    tham số của nó lần lượt đóng vai trò gì?
 *
 * 3. WHY THIS, NOT THAT — Nếu bạn tưởng đây là 2 method KHÁC NHAU
 *    (một cho mỗi yêu cầu) thay vì 2 overload của cùng 1 method —
 *    điều đó có làm sai code không? Sự khác biệt đó quan trọng ở
 *    điểm nào?
 *
 * 4. BREAK IT — Nếu danh sách `names` RỖNG, output của Yêu cầu 1
 *    và Yêu cầu 2 là gì? Dự đoán trước khi thử — chúng có giống
 *    nhau không?
 *
 * 5. TEACH IT BACK — Giải thích cho một Junior tại sao Java không
 *    có 3 method tên khác nhau cho 3 cách nối chuỗi này, mà dùng 1
 *    method với 3 overload.
 */
