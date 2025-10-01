package service.implement;

import dao.AgentDAO;
import dao.PaymentDAO;
import dao.implments.PaymentDAOImpl;
import model.Agent;
import model.Payment;
import model.enums.PaymentType;
import service.PaymentService;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PaymentServiceImpl implements PaymentService {

    private PaymentDAO paymentDao;
    private AgentDAO agentDAO;
//    List<Payment>payments = paymentDao.findAll();
//    public PaymentServiceImpl(Connection connection){
//        this.paymentDao = new PaymentDAOImpl(connection);
//    }
public PaymentServiceImpl(PaymentDAO paymentDAO){
        this.paymentDao = paymentDAO;
//        this.agentDAO=agentDAO
    }

    @Override
    public Boolean addPayment(Payment payment) {
    // should put the validation here
        if(payment == null){
            System.out.println("Invalid opertion");
            return false;
        }
        paymentDao.savePayment(payment);
        return true;
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

        return getAll().stream()
                .filter(p -> p.getType().name().equals(type))
                .collect(Collectors.toList());

    }

    @Override
    public List<Payment> filterByDate(Date date) {
        // transfer java.utils.date to localDAte
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return getAll().stream()
                .filter(p -> p.getDate().equals(localDate))
                .collect(Collectors.toList());
    }


    @Override
    public List<Payment> filterByAmount(double min, double max) {
            return getAll().stream()
                .filter(p -> p.getAmount() > min && p.getAmount() < max)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean isEligible(int agentId, PaymentType type) {


        if(type != PaymentType.BONUS && type != PaymentType.INDEMNITE){
            return true;
        }

        List<Payment> allPayments = paymentDao.findAll();


        List<Payment> agentPayments = allPayments.stream()
                .filter(p -> p.getAgentId() == agentId)
                .toList();

        // checking numb of payments
        if(agentPayments.size() >= 6){
            return true;
        }

        // less than 6 paymets
        return false;
    }

}
