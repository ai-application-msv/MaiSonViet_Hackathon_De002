# Phần 2: Điều tra và xử lý lỗi JWT Security Filter - Báo cáo nộp bài

## 1. Mục tiêu kỹ thuật
Ngoại lệ `MalformedJwtException` được ném ra tại `JwtAuthenticationFilter`. Do Filter nằm trước DispatcherServlet, lỗi này không thể bị bắt bởi `@ControllerAdvice`, khiến ứng dụng bị crash (HTTP 500) thay vì trả về HTTP 401 JSON như thiết kế.

**Giải pháp công nghệ/kiến trúc:** 
Mục tiêu là xây dựng cơ chế **Xử lý lỗi tập trung (Centralized Error Handling)**. Giải pháp là tiêm (inject) `HandlerExceptionResolver` vào `JwtAuthenticationFilter`. Khi có lỗi Token, Filter sẽ dùng Resolver này để "chuyển tiếp" (forward) ngoại lệ sang cho `@ControllerAdvice` xử lý. Điều này đảm bảo tính nhất quán (chung một format JSON đầu ra) và tuân thủ SRP (Filter không ôm đồm việc sinh chuỗi JSON).

## 2. Lịch sử Prompt (Prompt Chain)
Quá trình điều hướng AI được thực hiện qua các bước:

- **Prompt 1:** *"Trong class JwtAuthenticationFilter, khi parse token bị sai định dạng, nó ném ra MalformedJwtException và văng lỗi 500. Hãy giúp tôi bắt lỗi này để trả về HTTP 401."*
- **Prompt 2 (Khắc phục lỗi kiến trúc của AI):** *"Đoạn code bạn sinh ra đang dùng `try-catch` và `response.getWriter().write("{...}")`. Tôi không muốn hard-code JSON trong Filter như vậy vì sẽ gây trùng lặp code và mất tính tập trung. Có cách nào để đẩy lỗi này từ Filter sang cho `@ControllerAdvice` xử lý không?"*
- **Prompt 3:** *"Tốt lắm, cách dùng `HandlerExceptionResolver` rất hợp lý. Bây giờ hãy viết cho tôi class `GlobalExceptionHandler` với `@ControllerAdvice` để hứng lỗi `JwtException` được forward từ Filter sang, trả về format `{"error": "INVALID_TOKEN", "message": "..."}`."*

## 3. Phân tích lỗi AI
- **Lỗi của AI ở lần sinh code đầu tiên:** Khi nhận Prompt 1, AI đã "lười biếng" đưa ra giải pháp dễ nhất: Viết `try-catch` ngay trong Filter, bắt lỗi và gọi `response.setStatus(401); response.getWriter().print("{\"error\":...}");`. Giải pháp này "chạy được" nhưng **sai về mặt tư duy kiến trúc**, vì nó ép Filter phải kiêm nhiệm việc format response (vi phạm SRP) và phân mảnh logic bắt lỗi của dự án.
- **Cách sinh viên khắc phục:** Tôi đã từ chối giải pháp sinh JSON thủ công của AI. Bằng **Prompt 2**, tôi chủ động định hướng AI phải tìm ra con đường kết nối giữa Filter và `@ControllerAdvice`. Nhờ vậy, AI mới "tỉnh ngộ" và đề xuất giải pháp tiêm `HandlerExceptionResolver` - một kỹ thuật chuyên sâu và đúng chuẩn mực thiết kế của Spring Security.
