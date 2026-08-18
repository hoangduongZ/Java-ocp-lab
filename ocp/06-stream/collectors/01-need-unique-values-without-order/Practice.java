import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Practice {

    public static void main(String[] args) {

        /*
         * SCENARIO
         *
         * Tôi cần: lấy ra các giá trị KHÁC NHAU (không trùng lặp)
         * từ một danh sách số nguyên. Không quan tâm thứ tự xuất
         * hiện trong kết quả.
         *
         * Input của tôi:
         *     5, 3, 5, 1, 3, 5
         *
         * Output tôi mong muốn:
         *     một tập hợp chứa đúng 3 giá trị: 5, 3, 1 — mỗi giá
         *     trị xuất hiện đúng một lần, thứ tự không quan trọng.
         */

        List<Integer> input = List.of(5, 3, 5, 1, 3, 5);

        // ANALOG EXAMPLE (nhu cầu khác, chỉ để thấy cú pháp):
        long sampleWordCount = List.of("go", "java", "go").stream()
                .collect(Collectors.counting()); // = 3

        // TODO: viết một stream pipeline giải quyết đúng SCENARIO trên.
        Set<Integer> uniqueInput = new TreeSet<>(input);
        System.out.println(uniqueInput);

        /*
         * HINT LADDER — chỉ mở dần, mỗi lần một hint.
         * Đừng mở hint 2 nếu chưa tự thử với hint 1.
         */

        // HINT 1: đây cần một collector đổi "danh sách" thành "một
        // cấu trúc dữ liệu tự loại trùng" — bạn không cần tự viết
        // logic kiểm tra trùng lặp bằng if/contains.

        // HINT 2: có vài method cùng tiền tố "to..." trong cùng một
        // class, mỗi method cho ra một loại container khác nhau.
        // Bạn cần loại tự loại trùng, không cam kết thứ tự.

        // HINT 3: Collectors.toSet() — dùng HashSet bên dưới, nên
        // KHÔNG cam kết thứ tự phần tử trong kết quả.
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
 *    TRƯỚC khi verify bằng Javadoc/IDE.
 *
 * 2. WHY THIS, NOT THAT — `Collectors.toList()` cũng "hợp lệ về
 *    compile" nếu gán cho kiểu `Collection<Integer>`. Tại sao nó
 *    KHÔNG giải quyết đúng SCENARIO này dù compile được?
 *
 * 3. BREAK IT — Nếu SCENARIO đổi thành "cần các giá trị khác
 *    nhau, in ra theo thứ tự TĂNG DẦN" — method bạn chọn còn dùng
 *    nguyên được không? Nếu không, method nào trong cùng họ mới
 *    giải quyết được?
 *
 * 4. TEACH IT BACK — Giải thích cho một Junior tại sao "loại
 *    trùng" và "giữ thứ tự" là hai nhu cầu khác nhau, dẫn tới hai
 *    lựa chọn khác nhau — không dùng "method đó đúng hơn".
 */
