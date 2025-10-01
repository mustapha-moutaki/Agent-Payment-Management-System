package service.implement;

import dao.PaymentDAO;
import dao.implments.PaymentDAOImpl;
import model.Payment;
import service.PaymentService;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PaymentServiceImpl implements PaymentService {

    private PaymentDAO paymentDao;
    List<Payment>payments = paymentDao.findAll();
//    public PaymentServiceImpl(Connection connection){
//        this.paymentDao = new PaymentDAOImpl(connection);
//    }
public PaymentServiceImpl(PaymentDAO paymentDAO){
        this.paymentDao = paymentDAO;
    }

    @Override
    public void addPayment(Payment payment) {
    // should put the validation here
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

    @Override
    public List<Payment> filterByType(String type) {

        List<Payment> typeFilteredPayments =payments.stream().filter(p-> p.getType().name().equals(type)).collect(Collectors.toList());
        return typeFilteredPayments;
    }

    @Override
    public List<Payment> filterByDate(Date date) {
        List<Payment>dateFilteredPayments = payments.stream().filter(p->p.getDate().equals(date)).collect(Collectors.toList());
        return dateFilteredPayments;
    }

    @Override
    public List<Payment> filterByAmount(double min, double max) {
        List<Payment>amountFilteredPayments = payments.stream().filter(p->p.getAmount()>min && p.getAmount()< max).collect(Collectors.toList());
        return amountFilteredPayments;
    }
}
