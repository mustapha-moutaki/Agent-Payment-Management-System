package service;

import model.Department;
import model.Payment;
import model.enums.PaymentType;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PaymentService {
    Boolean addPayment(Payment payment);
    void updatePayment(Payment payment);
    List<Payment> getAll();
    Payment findPaymentById(int id);
    void deletePayment(int id);
    List<Payment>filterByType(String type);
    List<Payment>filterByDate(Date date);
    List<Payment>filterByAmount(double min, double max);
    Boolean isEligible (int agentid, PaymentType type);
    public Map<Department, Double> getTotalPaymentsByDepartment();
}
