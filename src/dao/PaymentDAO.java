package dao;

import model.Department;
import model.Payment;

import java.util.List;
import java.util.Map;

public interface PaymentDAO {
     void savePayment(Payment payment);
     void updatePayment(Payment payment);
     Payment findByid(int id);
     List<Payment> findAll();
     void deletePayment(int id);
    public Map<Department, Double> getTotalPaymentsByDepartment();
}
