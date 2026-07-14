package refactoring;

public class ClinicBillingService {
    
    // Inject các phụ thuộc thông qua tham số (hoặc Constructor)
    public Invoice calculateAndPay(
            Patient patient, 
            VisitRecord visit, 
            InsuranceStrategy insurance, 
            PaymentStrategy paymentGateway,
            NotificationStrategy notifier) {
        
        if (!patient.isActive()) throw new RuntimeException("Patient record locked");
        
        // 1. Tính tổng phí gốc
        double totalFee = visit.getBaseFee();
        if (visit.getExtraServices() != null) {
            for (ServiceItem item : visit.getExtraServices()) {
                totalFee += item.getFee();
            }
        }
        
        // 2. Áp dụng bảo hiểm
        double finalFee = insurance.calculateFee(totalFee);
        
        // 3. Xử lý thanh toán
        paymentGateway.processPayment(finalFee);
        
        // 4. Gửi thông báo
        notifier.sendNotification(patient, "Your billing info... Total: " + finalFee);
        
        return new Invoice(patient, finalFee, "PAID");
    }
}
