package refactoring;

public interface NotificationStrategy {
    void sendNotification(Patient patient, String message);
}

class SmsNotification implements NotificationStrategy {
    @Override
    public void sendNotification(Patient patient, String message) {
        System.out.println("Sending SMS to " + patient.getPhone() + ": " + message);
    }
}

class ZaloZnsNotification implements NotificationStrategy {
    @Override
    public void sendNotification(Patient patient, String message) {
        System.out.println("Sending Zalo ZNS to " + patient.getPhone() + ": " + message);
    }
}
