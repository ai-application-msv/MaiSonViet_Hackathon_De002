package refactoring;

public interface InsuranceStrategy {
    double calculateFee(double totalFee);
}

class BHYTInsurance implements InsuranceStrategy {
    @Override
    public double calculateFee(double totalFee) {
        return totalFee * 0.2; // Trả 20%
    }
}

class BaoVietInsurance implements InsuranceStrategy {
    @Override
    public double calculateFee(double totalFee) {
        return Math.max(0, totalFee - 500000); // Giảm 500k
    }
}

class NoInsurance implements InsuranceStrategy {
    @Override
    public double calculateFee(double totalFee) {
        return totalFee; // Không giảm giá
    }
}
