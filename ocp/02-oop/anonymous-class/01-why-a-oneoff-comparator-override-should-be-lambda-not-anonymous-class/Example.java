import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Example {

    public static void main(String[] args) {

        /*
         * EXPERIMENT 1 — ANONYMOUS CLASS
         *
         * KHÔNG CHẠY NGAY.
         *
         * Predict:
         * - Compile được không? compile được
         *   -> AI review: Đúng. Anonymous class implement đúng signature
         *      compare(String, String) của Comparator<String>, nên hợp lệ
         *      ở compile time — không liên quan gì tới việc runtime sau đó
         *      chạy đúng logic hay không.
         * - Output là gì? là danh sách được sắp xếp theo độ dài kí tự
         *   -> AI review: Đúng, và lần này lý do cũng đúng. compare() trả
         *      a.length() - b.length(), nên Collections.sort() sắp theo độ
         *      dài tăng dần. Output thật: [Al, Bob, Charlie] (độ dài 2, 3, 7).
         * - `comparator1` đang tham chiếu tới một object có type là gì
         *   trên heap (không phải Comparator<String>, mà là type thật)? type là int
         *   -> AI review: Sai. "int" là type TRẢ VỀ của method compare(),
         *      không phải type của OBJECT mà comparator1 tham chiếu tới.
         *      Object thật trên heap là một instance của anonymous class do
         *      compiler sinh ra (tên nội bộ dạng Example$1), object đó
         *      implement Comparator<String> — chứ chính nó không phải là int.
         */
        List<String> names1 = new ArrayList<>(List.of("Charlie", "Al", "Bob"));

        Comparator<String> comparator1 = new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.length() - b.length();
            }
        };

        Collections.sort(names1, comparator1);
        System.out.println(names1);

        /*
         * EXPLANATION 1
         *
         * Compiler:
         * `new Comparator<String>() { ... }` tạo ra một class thật
         * (kiểu Example$1), object thật nằm trên heap.
         *
         * Runtime:
         * Collections.sort() gọi comparator1.compare(x, y) qua dynamic
         * dispatch, giống mọi object bình thường khác.
         *
         * Mental model:
         * Reference type = Comparator<String>.
         * Runtime object  = Example$1 (một class riêng, có identity).
         */


        /*
         * EXPERIMENT 2 — CHANGE ONE THING: anonymous class → lambda
         *
         * Chỉ thay đúng MỘT yếu tố so với Experiment 1:
         * cách tạo ra object implement compare().
         *
         * Predict trước khi đọc tiếp:
         * - Compile được không? compile được vì đây là lambda
         *   -> AI review: Đúng, nhưng "vì đây là lambda" chưa chạm bản
         *      chất. Compile được vì Comparator<String> là functional
         *      interface (đúng 1 abstract method) — compiler mới cho
         *      phép target-type lambda vào nó. Nếu Comparator có 2
         *      abstract method, cùng cú pháp lambda này sẽ không compile.
         * - Output có khác Experiment 1 không? output không khác, chỉ là không tạo ra đối tượng trên heap vs class name kiểu dạng <className>$<number> thôi
         *   -> AI review: Output không đổi — đúng, vì compare() cùng
         *      logic. Nhưng "không tạo ra đối tượng trên heap" thì sai:
         *      compare() vẫn phải được gọi trên một object thật lúc
         *      runtime, nên vẫn có object trên heap. Khác biệt thật là
         *      object đó không đến từ một class file cố định dạng
         *      Example$N.class do compiler sinh sẵn — JVM tạo class ẩn
         *      lúc runtime qua invokedynamic/LambdaMetafactory, không
         *      phải named inner class như anonymous class.
         */
        List<String> names2 = new ArrayList<>(List.of("Charlie", "Al", "Bob"));

        Comparator<String> comparator2 = (a, b) -> a.length() - b.length();

        Collections.sort(names2, comparator2);
        System.out.println(names2);

        /*
         * EXPLANATION 2
         *
         * Compiler:
         * Comparator<String> có đúng 1 abstract method (compare) →
         * là functional interface → compiler cho phép target-type
         * lambda vào đúng interface này.
         *
         * Runtime:
         * Không có class Example$2 nào được sinh ra như một named
         * inner class. Lambda được biên dịch qua invokedynamic,
         * không mang danh tính object riêng theo kiểu anonymous class.
         *
         * Kết quả: output giống Experiment 1, vì HÀNH VI giống nhau.
         * Cái khác nhau là compiler/runtime tạo ra "cái gì" để mang
         * hành vi đó đi, không phải kết quả sort.
         */


        /*
         * EXPERIMENT 3 — THÊM MỘT METHOD PHỤ VÀO ANONYMOUS CLASS
         *
         * Bây giờ comparator cần gọi thêm một logic phụ, đặt trong
         * một method riêng bên trong chính object implement Comparator.
         *
         * Predict:
         * - Đoạn này compile được không? vẫn compile được, vì anonymous class cho tạo thêm thuộc tính và hành vi chỉ là không mang được nó ra bên ngoài, nếu không nằm trong phạm vi của interface
         *   -> AI review: Đúng, và đúng cả mechanism. Anonymous class được
         *      khai báo thêm method riêng như weight(), nhưng theo mental
         *      model "reference giống thẻ chức danh" ở README mục 4, phần
         *      thêm đó chỉ gọi được TỪ BÊN TRONG chính object (compare()
         *      gọi weight()), không gọi được từ ngoài qua reference type
         *      Comparator<String> — giống hệt bẫy comparator.describe()
         *      trong Practice.java.
         * - Sau đó, nếu viết lại đúng logic này bằng lambda,
         *   có compile được không? Không, vì lambda chỉ có 1 hành vi duy nhất của functional interface
         *   -> AI review: Kết luận "Không compile" đúng cho version này,
         *      nhưng lý do "lambda chỉ có 1 hành vi của functional
         *      interface" vẫn là cách hiểu chưa đúng — Comparator không
         *      hề đổi, vẫn là functional interface như cũ. Nguyên nhân
         *      thật: weight() ở đây chỉ tồn tại BÊN TRONG anonymous
         *      class; khi thay bằng lambda thì không còn nơi nào định
         *      nghĩa weight() nữa, nên compiler báo cannot find symbol.
         *      Bản chất là "lambda không tự khai báo method riêng cho
         *      chính nó", không phải "chỉ hỗ trợ 1 hành vi" — so sánh
         *      với Counterexample ngay dưới để thấy rõ.
         */
        List<String> names3 = new ArrayList<>(List.of("Charlie", "Al", "Bob"));

        Comparator<String> comparator3 = new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return weight(a) - weight(b);
            }

            private int weight(String s) {
                return s.length() * 10;
            }
        };

        Collections.sort(names3, comparator3);
        System.out.println(names3);

        /*
         * Thử uncomment dòng dưới sau khi đã dự đoán:
         *
         * Comparator<String> comparator3AsLambda =
         *     (a, b) -> weight(a) - weight(b);
         *
         * `weight` ở đây không tồn tại — vì lambda không thể tự khai
         * báo một method riêng thuộc về chính nó như anonymous class.
         */


        /*
         * COUNTEREXAMPLE — ATTACK THE MODEL
         *
         * Mental model tạm thời (có thể) đang là:
         * "Có method phụ bên trong logic so sánh → buộc phải dùng
         * anonymous class."
         *
         * Đây là counterexample: kéo `weight` ra thành một method
         * static của chính class Example, rồi viết lại bằng lambda.
         *
         * Predict trước:
         * - Đoạn dưới có compile không? có compile được
         *   -> AI review: Đúng. comparator4 gọi thẳng weight() — lúc này
         *      là static method của class Example, đã tồn tại sẵn, nên
         *      lambda chỉ cần bind vào, không cần tự khai báo gì thêm.
         * - Nếu có, điều đó chứng minh điều gì về giới hạn thật sự
         *   của lambda? không định nghĩa được thêm method như anonymous class nhưng có thể dùng được method của đối tượng khác hoặc static method
         *   -> AI review: Chính xác — đây là câu trả lời đúng nhất trong
         *      toàn bộ case này. Giới hạn thật của lambda là "không tự
         *      khai báo được method riêng thuộc về chính nó", không phải
         *      "chỉ hỗ trợ 1 hành vi". Câu trả lời này sửa lại đúng hai
         *      chỗ bạn từng trả lời sai trước đó: Experiment 3 (câu 2) và
         *      README mục 8 (câu 1, câu 2), nơi bạn từng nói lambda "chỉ
         *      hỗ trợ 1 hành vi" hoặc "vẫn không hỗ trợ" khi kéo method
         *      ra thành static.
         */
        List<String> names4 = new ArrayList<>(List.of("Charlie", "Al", "Bob"));

        Comparator<String> comparator4 = (a, b) -> weight(a) - weight(b);

        Collections.sort(names4, comparator4);
        System.out.println(names4);
    }

    private static int weight(String s) {
        return s.length() * 10;
    }
}
