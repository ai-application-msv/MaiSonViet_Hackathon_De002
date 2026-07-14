package refactoring;

public interface PaymentStrategy {
    void processPayment(double amount);
}

class VNPayStrategy implements PaymentStrategy {
    @Override
    public void processPayment(double amount) {
        System.out.println("Routing to VNPay Health portal for amount: " + amount);
    }
}

class CashStrategy implements PaymentStrategy {
    @Override
    public void processPayment(double amount) {
        System.out.println("Print receipt for counter. Amount: " + amount);
    }
}
