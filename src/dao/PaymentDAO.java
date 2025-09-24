package dao;

import model.Payment;

import java.util.List;

public interface PaymentDAO {
     void savePayment(Payment payment);
     void updatePayment(Payment payment);
     Payment findByid(int id);
     List<Payment> findAll();
}
