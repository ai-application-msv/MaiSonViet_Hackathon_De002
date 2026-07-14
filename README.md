## 📂 Cấu trúc thư mục dự án

Dự án này được chia thành các thư mục chính:

- `src/refactoring/`: Chứa mã nguồn cho **Phần 1**, giải quyết vấn đề bằng **Strategy Pattern**.
- `src/security/`: Chứa mã nguồn cho **Phần 2**, giải quyết vấn đề bảo mật tập trung hóa.
- `docs/`: Chứa các tài liệu báo cáo giải thích quá trình tương tác với AI và phân tích nguyên nhân gốc rễ.

---

## 🚀 Phần 1: Tái cấu trúc mã nguồn (Refactoring)
**Mục tiêu:** Áp dụng nguyên lý OCP (Open-Closed Principle) vào hàm tính toán viện phí `calculateAndPay`.

Thay vì sử dụng các vòng lặp `if-else` cứng nhắc để xử lý từng loại bảo hiểm, thanh toán và thông báo, hệ thống đã được tái cấu trúc sử dụng **Strategy Pattern**. Điều này giúp việc thêm tính năng mới (như thêm cổng thanh toán Momo) hoàn toàn không ảnh hưởng tới hàm lõi cốt lõi.

**Tài liệu báo cáo:** [Quá trình giao tiếp AI - Phần 1](docs/Part1_Refactoring_Process.md)
**Mã nguồn chính:**
- [ClinicBillingService.java](src/refactoring/ClinicBillingService.java)
- [InsuranceStrategy.java](src/refactoring/InsuranceStrategy.java)
- [PaymentStrategy.java](src/refactoring/PaymentStrategy.java)
- [NotificationStrategy.java](src/refactoring/NotificationStrategy.java)

---

## 🛡️ Phần 2: Xử lý lỗi bảo mật tập trung (Security Handling)
**Mục tiêu:** Bắt lỗi Token sai định dạng (`MalformedJwtException`) tại tầng Filter và trả về chuẩn JSON một cách tập trung, không để Server crash `500 Internal Server Error`.

Bằng cách tiêm `HandlerExceptionResolver` vào Filter, ứng dụng có thể "ủy quyền" (forward) lỗi từ tầng Filter sang cho cơ chế xử lý lỗi toàn cục `@ControllerAdvice` của Spring, giúp đảm bảo tính đồng nhất (Consistency) và tuân thủ SRP (Single Responsibility Principle).

**Tài liệu báo cáo:** [Điều tra và xử lý lỗi JWT - Phần 2](docs/Part2_Security_Fix.md)
**Mã nguồn chính:**
- [JwtAuthenticationFilter.java](src/security/JwtAuthenticationFilter.java)
- [GlobalExceptionHandler.java](src/security/GlobalExceptionHandler.java)

---

## 🧠 Phần 3: Phân tích và thiết kế hệ thống với AI (System Analysis)
**Mục tiêu:** Đóng vai trò System Analyst để bóc tách yêu cầu nghiệp vụ của dự án "Rikkei Care", từ đó đề xuất giải pháp công nghệ, trích xuất Entity và vẽ sơ đồ ERD dựa trên sức mạnh của Prompting AI.

**Nội dung thực hiện:**
- **Nhiệm vụ 1:** Thiết kế Tech Stack tối ưu với PostgreSQL, Redis, WebSocket cho bài toán tracking hàng đợi Real-time.
- **Nhiệm vụ 2:** Bóc tách 4 Entity cốt lõi (`User`, `Doctor`, `Appointment`, `Invoice`) phục vụ việc tính toán phụ phí ngoài giờ và đặc quyền thẻ Vàng.
- **Nhiệm vụ 3:** Vẽ và kết xuất sơ đồ quan hệ thực thể (ERD) bằng cú pháp Mermaid.

**Tài liệu & Hình ảnh:** 
- 📄 [Phân tích hệ thống với AI chi tiết - Phần 3](docs/Part3_System_Analysis.md)
- 🖼️ [Hình ảnh Sơ đồ ERD](docs/erd_diagram.png)
