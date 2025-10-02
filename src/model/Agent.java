package model;

import model.enums.AgentType;

import java.util.ArrayList;
import java.util.List;

public class Agent extends Person{
    private AgentType agent_type;
//    private double salary;
    private int id;
    private int id_department;
    private Department department; // to set null to id_department and avoid 0
    private List<Payment> paymentsList;// agent payments list to store only payments of single agent

    public Agent(String first_name, String last_name, String email, String password, AgentType type){
        super(first_name, last_name, email, password);
        this.agent_type = type;
//        this.salary = salary;
//        this.id_department = id_department;
        this.paymentsList = new ArrayList<>();
    }

    public Agent(int id, String first_name, String last_name, String email, String password, AgentType type, int id_department){
        this(first_name, last_name, email, password, type);
        this.id = id;
        this.id_department=id_department;
    }

    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }
    public AgentType getAgent_type() {
        return agent_type;
    }

    public void setAgent_type(AgentType agent_type) {
        this.agent_type = agent_type;
    }



        public int getId_department() {
        return id_department;
    }

        public void setId_department(int id_department) {
            this.id_department = id_department;
        }


//    public double getSalary() {
//        return salary;
//    }
//
//    public void setSalary(double salary) {
//        this.salary = salary;
//    }

    // to set agent department
    public void setDepartment(Department department){
        this.department = department;
    }
    // get agent department
    public Department getDepartment(){
        return this.department;
    }


    // set payment
    public void addPayment(Payment payment){
        this.paymentsList.add(payment);
    }

    // get payment
    public List<Payment>getPayments(){
        return paymentsList;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "agent_type=" + agent_type +
                ", id_department=" + id_department +
                ", department=" + department +
                ", paymentsList=" + paymentsList +
                '}';
    }
}
