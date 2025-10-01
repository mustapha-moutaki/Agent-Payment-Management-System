package service;

import model.Payment;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface PaymentService {
    void addPayment(Payment payment);
    void updatePayment(Payment payment);
    List<Payment> getAll();
    Payment findPaymentById(int id);
    void deletePayment(int id);
    List<Payment>filterByType(String type);
    List<Payment>filterByDate(Date date);
    List<Payment>filterByAmount(double min, double max);

}
