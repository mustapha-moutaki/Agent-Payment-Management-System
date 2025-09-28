package view;

import controller.MainController;
import model.Payment;

import java.util.List;

public class PaymentUi {

    private MainController controller;


    public void addPayment(Payment payment){
        controller.addPayment(payment);
    }
    public void deletePayment(int id){
        controller.deletePayment(id);
    }
    public void updatePayment(Payment payment){
        controller.updatePayment(payment);
    }
    public void paymentsList(){
        List<Payment> paymentList = controller.paymentList();
        for(Payment payment : paymentList){
            System.out.println("payment type: "+ payment.getType().name());
            System.out.println("payment amount: "+ payment.getAmount());
            System.out.println("payment name: "+ payment.getCondition_validated());
        }
    }
    public void getPayment(int id){
        controller.getPayment(id);
    }
}
