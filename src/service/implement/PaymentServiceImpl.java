package service.implement;

import dao.PaymentDAO;
import dao.implments.PaymentDAOImpl;
import model.Payment;
import service.PaymentService;

import java.sql.Connection;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {

    private PaymentDAO paymentDao;
    public PaymentServiceImpl(Connection connection){
        this.paymentDao = new PaymentDAOImpl(connection);
    }

    @Override
    public void addPayment(Payment payment) {
        paymentDao.savePayment(payment);
    }

    @Override
    public void updatePayment(Payment payment) {
        paymentDao.updatePayment(payment);
    }

    @Override
    public List<Payment> getAll() {
        return paymentDao.findAll();
    }

    @Override
    public Payment findPaymentById(int id) {
        return paymentDao.findByid(id);
    }

    @Override
    public void deletePayment(int id) {
         paymentDao.deletePayment(id);
    }
}
