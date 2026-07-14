# Phần 1: Tái cấu trúc mã nguồn - Báo cáo nộp bài

## 1. Mục tiêu kỹ thuật
Mã nguồn hàm `calculateAndPay` ban đầu vi phạm nghiêm trọng nguyên lý **Open-Closed Principle (OCP)** khi gán cứng (hard-code) các loại bảo hiểm, thanh toán và thông báo bằng cấu trúc `if-else`. 

**Giải pháp công nghệ/kiến trúc:** Sử dụng **Strategy Pattern** kết hợp với **Dependency Injection**. 
Mục tiêu là đóng gói các logic biến đổi (Bảo hiểm, Thanh toán, Thông báo) thành các Interface độc lập (`InsuranceStrategy`, `PaymentStrategy`, `NotificationStrategy`). Hàm `calculateAndPay` sẽ không tự quyết định luồng logic nữa mà nhận các Strategy này qua tham số. Khi cần mở rộng (thêm Momo, thêm Zalo), ta chỉ việc tạo Class mới implement Interface mà không cần chạm vào hàm cốt lõi.

## 2. Lịch sử Prompt (Prompt Chain)
Tôi đã không dùng một prompt duy nhất mà chia nhỏ bài toán để kiểm soát code sinh ra:

- **Prompt 1 (Nhận diện & bóc tách Bảo hiểm):** *"Đây là hàm tính toán viện phí. Tôi nhận thấy nó vi phạm OCP ở phần tính bảo hiểm. Hãy tạo một Interface `InsuranceStrategy` và bóc tách các logic `BHYT`, `BaoViet` ra thành các class riêng."*
- **Prompt 2 (Bóc tách Thanh toán & Thông báo):** *"Rất tốt. Dựa trên cấu trúc đó, hãy tiếp tục bóc tách cổng thanh toán thành `PaymentStrategy` và hình thức thông báo thành `NotificationStrategy`."*
- **Prompt 3 (Xử lý Dependency Injection - Khắc phục lỗi AI):** *"Đoạn code bạn vừa sinh ra vẫn khởi tạo cứng các class (VD: `new BHYTInsurance()`) bên trong hàm `calculateAndPay`. Hãy sửa lại, truyền các Interface này qua tham số (Dependency Injection) để hàm hoàn toàn sạch bóng if-else."*

## 3. Phân tích lỗi AI
- **Lỗi của AI ở lần sinh code đầu tiên:** Sau khi tôi gửi Prompt 1 và 2, AI đã tạo ra các file Interface và Class rất chuẩn xác. Tuy nhiên, khi ghép vào `ClinicBillingService`, AI lại viết code kiểu:
  ```java
  if (type.equals("BHYT")) { 
      insurance = new BHYTInsurance(); 
  }
  ```
  Rõ ràng AI chỉ đẩy logic tính tiền ra ngoài, nhưng **logic khởi tạo (if-else) vẫn nằm nguyên trong hàm lõi**, đồng nghĩa với việc vẫn vi phạm OCP.
- **Cách sinh viên khắc phục:** Tôi đã nhận ra lỗi sai về kiến trúc này và lập tức dùng **Prompt 3** (ở phần Lịch sử Prompt) để ép AI phải từ bỏ việc tự khởi tạo (hard-code dependency). Tôi yêu cầu AI phải inject các dependency qua tham số của hàm `calculateAndPay(..., InsuranceStrategy insurance, ...)`. Kết quả là code đã hoàn toàn triệt tiêu được `if-else` đúng với tiêu chuẩn thiết kế.
