package controller;

import com.sun.tools.javac.Main;
import model.Agent;
import model.Department;
import model.Payment;
import model.enums.AgentType;
import model.enums.PaymentType;
import service.AgentService;
import service.DepartmentService;
import service.PaymentService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

// how to create a controller that handle all services (busines logic of all models)
public class MainController {
    /**
     * here we import all Services we have
     */
    private AgentService agentService;
    private DepartmentService departmentService;
    private PaymentService paymentService;

    /* constructor
     */

    public MainController(AgentService agentService, DepartmentService departmentService, PaymentService paymentService){
        this.agentService = agentService;
        this.departmentService = departmentService;
        this.paymentService = paymentService;
    }

    /**
     *
     * ==================== agent controller =========================
     */
    // 1- create agent
    public void addAgent(Agent agent){
        agentService.addAgent(agent);
    }

    // 2- delete agent
    public void deleteAgent(int id){
        agentService.deleteAgent(id);
    }

    // 3-update an agent
    public void updateAgent(Agent agent){
        agentService.updateAgent(agent);
    }

    // 4-get all agent list
    public List<Agent> getAllAgent(){
        return agentService.getAllAgents();
    }

    // 5- get agent by id
    public Agent getAgentById(int id){
        return agentService.getAgentById(id);
    }

    public boolean hasManagerInDepartment(int departmentId) {
        return agentService.findManagersByDepartmentId(departmentId).size() > 0;
    }


    /**
     * ================== Department Controller ========================
     */

    // 1- Create Department
    public void addDepartment(Department department){
        departmentService.addDepartment(department);
    }

    // 2- delete department
    public void deleteDepartment(int id){
        departmentService.deleteDepartment(id);
    }

    // 3- update department
    public void updateDeaprtment(Department department){
        departmentService.updateDepartment(department);
    }

    // 4-Get list of departments
    public List<Department>DepartmentList(){
        return departmentService.findAll();
    }
    // 5- getDepartment by id
    public void getDepartmentById(int id){
        departmentService.findDepartmentById(id);
    }

    public Boolean assignManagerToDepartment(Agent agent, Department department){
        return departmentService.assignManagerToDepartment(agent, department);
    }
    public Boolean removeAgentFromDepartment(int id){
        return agentService.removeAgentFromDepartment(id);
    }
    public Map<Department, Double> getTotalPaymentsByDepartment(){
        return  paymentService.getTotalPaymentsByDepartment();
    }

    // 5- get departmnt by id
    // we must add a foregin key manager_id to department table in db
//    public Department getDepartmentByid(int id){
//        return departmentService.findDepartmentById(id);
//    }
//
//    public void assignManagerToDepartment(Department department, Agent manager) {
//        if (manager.getAgent_type() != AgentType.RESPONSABLE_DEPARTEMENT) {
//            System.out.println("This agent is not eligible to be a manager!");
//            return;
//        }
//        department.setResponsible(manager);
//        System.out.println("Manager assigned successfully!");
//    }

    /**
     * ======================== Payment Controller ===================
     */

    // 1- create Payment
    public Boolean addPayment(Payment payment){
        return paymentService.addPayment(payment);
    }

    // 2- delete Payment
    public void deletePayment(int id){
        paymentService.deletePayment(id);
    }

    // 3- update Payment
    public void updatePayment(Payment payment){
         paymentService.updatePayment(payment);
    }

    // 4- Get payments list
    public List<Payment>paymentList(){
        return paymentService.getAll();
    }

    // 5- get payment by id
    public Payment getPayment(int id){
        return paymentService.findPaymentById(id);
    }

    // 6- payments filtred by type
    public List<Payment>typeFiltredPaymentsList(String type){
        return paymentService.filterByType(type);
    }

    // 7- payments filtred by amout
    public List<Payment>amountFiltredPaymentsList(double min, double max){
        return paymentService.filterByAmount(min, max);
    }

    // 8- payments filtred by date
    public List<Payment>dateFiltredPaymentsList(Date date){
        return paymentService.filterByDate(date);
    }
    //9- Isegligible
    public Boolean isEligible(int agentId, PaymentType type){
        return paymentService.isEligible(agentId, type);
    }
    /**
     * done
     */


}
