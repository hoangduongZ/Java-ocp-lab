# 📚 GIÁO ÁN THỰC HÀNH — LỘ TRÌNH COLLECTIONS & STREAM (JAVA 11 OCP)

Bộ giáo án này được soạn dựa trên **đúng 2 nguồn**:
- `../system-prompt-agent.md` — nguyên tắc, giọng điệu, triết lý giảng dạy (Feynman Mentor).
- `index.md` — danh sách 17 mục theo thứ tự bắt buộc phải đi qua.

Mỗi mục trong `index.md` được tách thành **một thư mục riêng** (chia nhỏ để dễ tổ chức, dễ track tiến độ), file `README.md` bên trong là **giáo án** cho buổi thực hành đó — không phải lời giải, mà là kịch bản để mentor (persona trong `system-prompt-agent.md`) dẫn dắt buổi học theo đúng 4 bước:

1. **Lắng nghe & Đọc lỗi** → 2. **Bóc tách** → 3. **Chuyển hóa (ẩn dụ + code)** → 4. **Thử nghiệm (bài tập)**

## 🗺️ Lộ trình (đi đúng thứ tự, không nhảy cóc)

| # | Thư mục | Chủ đề |
|---|---------|--------|
| 01 | [01-array](01-array/README.md) | Array |
| 02 | [02-generics-co-ban](02-generics-co-ban/README.md) | Generics cơ bản |
| 03 | [03-list](03-list/README.md) | List |
| 04 | [04-set](04-set/README.md) | Set |
| 05 | [05-equals-hashcode](05-equals-hashcode/README.md) | equals/hashCode |
| 06 | [06-map](06-map/README.md) | Map |
| 07 | [07-collections-arrays](07-collections-arrays/README.md) | Collections / Arrays |
| 08 | [08-comparable-comparator](08-comparable-comparator/README.md) | Comparable / Comparator |
| 09 | [09-iterator](09-iterator/README.md) | Iterator |
| 10 | [10-lambda](10-lambda/README.md) | Lambda |
| 11 | [11-functional-interface](11-functional-interface/README.md) | Functional Interface |
| 12 | [12-method-reference](12-method-reference/README.md) | Method Reference |
| 13 | [13-stream](13-stream/README.md) | Stream |
| 14 | [14-collectors](14-collectors/README.md) | Collectors |
| 15 | [15-optional](15-optional/README.md) | Optional |
| 16 | [16-generics-nang-cao](16-generics-nang-cao/README.md) | Generics nâng cao |
| 17 | [17-ocp-mixed-problems](17-ocp-mixed-problems/README.md) | OCP mixed problems |

## 🧩 Cấu trúc chung của mỗi giáo án

Mỗi `README.md` con đều có các phần cố định, đúng "QUY TẮC BẮT BUỘC" trong `system-prompt-agent.md`:

- **Mục tiêu buổi học** — biết được gì sau buổi này.
- **Ẩn dụ đời sống** — bắt buộc, không giải thích API khô khan.
- **Bên dưới lớp vỏ** — chuyện gì xảy ra ở Heap/Stack/JVM (nguyên lý "chiếc hộp đen").
- **Điểm nhấn Java 11** — vì sao Java 11 có/cải tiến tính năng này.
- **Code minh họa** — snippet ngắn, có comment luồng dữ liệu.
- **So sánh cảm giác** — chỉ xuất hiện ở các bài có khái niệm dễ nhầm (List/Set, Comparable/Comparator...).
- **Thử thách thực hành** — bài tập viết mới + bài debug lỗi.
- **Dạy lại** — yêu cầu học viên tự viết ví dụ/giải thích lại (bắt buộc theo mục 4 của Strict Instructions).

## ✅ Cách dùng

Đi tuần tự từ 01 → 17. Mỗi thư mục là một buổi độc lập, không cần đọc trước các bài sau. File `Practice.java` / code làm bài tự tạo trong quá trình học nên lưu ngay trong thư mục của bài đó để tiện đối chiếu sau này.
