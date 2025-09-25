package service;

import model.Payment;

import java.util.List;

public interface PaymentService {
    void addPayment(Payment payment);
    void updatePayment(Payment payment);
    List<Payment> getAll();
    Payment findPaymentById(int id);
    void deletePayment(int id);


}
