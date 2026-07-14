package refactoring;

import java.util.List;

class Patient {
    private String phone;
    private boolean active;

    public Patient(String phone, boolean active) {
        this.phone = phone;
        this.active = active;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isActive() {
        return active;
    }
}

class VisitRecord {
    private double baseFee;
    private List<ServiceItem> extraServices;

    public double getBaseFee() {
        return baseFee;
    }

    public List<ServiceItem> getExtraServices() {
        return extraServices;
    }
}

class ServiceItem {
    private double fee;

    public double getFee() {
        return fee;
    }
}

class Invoice {
    private Patient patient;
    private double totalFee;
    private String status;

    public Invoice(Patient patient, double totalFee, String status) {
        this.patient = patient;
        this.totalFee = totalFee;
        this.status = status;
    }
}
