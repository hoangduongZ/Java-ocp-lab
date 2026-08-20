public class Practice {

    public static void main(String[] args) {
        int[] arrs = new int[]{1,2,3,4,5,6,7,8,9};
        reverseInPlace(arrs);

        safeStore();
    }

    public static void reverseInPlace(int[] arr){
        int length= arr.length;
        for(int i = 0; i< length/ 2; i++){
            int tmp = arr[i];
            arr[i] = arr[length - i - 1];
            arr[length - i - 1] = tmp;
        }
        for(int i = 0; i< length; i++){
            System.out.println(arr[i]);
        }
    }

    public static void safeStore() {
        Object[] boxOfLetters = new String[3];
        boxOfLetters[0] = "A";

        Object incoming = Integer.valueOf(10);
        Class<?> componentType = boxOfLetters.getClass().getComponentType();

        if (componentType.isInstance(incoming)) {
            boxOfLetters[1] = incoming;
        } else {
            System.out.println("Khong the them " + incoming.getClass().getSimpleName()
                    + " vao mang kieu " + componentType.getSimpleName());
        }
    }

}

/*
 * ============================================================
 * AI REVIEW
 * ============================================================
 *
 * ĐÁNH GIÁ CHUNG: Logic reverse đúng và đạt yêu cầu "in-place"
 * của README (không cấp phát mảng mới). Có 1 điểm cần chú ý về
 * thiết kế (trộn trách nhiệm mutate + print) và 1 bẫy OCP đáng
 * nhớ liên quan tới println(array), mà bạn đã tự né được bằng
 * cách xoá dòng gọi thừa ở main.
 *
 * ĐÚNG:
 * - Swap dùng biến tmp, hoán đổi arr[i] với arr[length-i-1].
 * - Vòng lặp chỉ chạy tới length/2 (chia nguyên) nên không bị
 *   swap ngược lại lần thứ hai.
 * - length=9 (lẻ) → i chạy 0..3, phần tử giữa index 4 giữ
 *   nguyên — chính xác, không cần xử lý riêng cho mảng lẻ.
 *
 * CẦN CHÚ Ý:
 * - reverseInPlace() vừa mutate vừa print — trộn hai trách
 *   nhiệm. Tên method chỉ hứa "đảo ngược", nhưng nó tự quyết
 *   định luôn cách hiển thị kết quả. Nên để caller tự gọi
 *   System.out.println(Arrays.toString(arr)) SAU KHI
 *   reverseInPlace() trả về, thay vì in cứng bên trong method.
 *
 * BẪY OCP:
 * - Nếu gọi println(arrs) trực tiếp trên mảng (như bản trước khi
 *   bạn xoá dòng đó ở main), nó KHÔNG in nội dung mảng. int[]
 *   không override toString(), nên println(arrs) gọi
 *   Object.toString() mặc định → ra dạng "[I@<hashcode-hex>"
 *   (vd "[I@1b6d3586"), không phải "9 8 7 ...". Muốn in đúng
 *   nội dung phải dùng Arrays.toString(arrs).
 */
