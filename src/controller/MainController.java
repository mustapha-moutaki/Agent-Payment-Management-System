package controller;

import com.sun.tools.javac.Main;
import model.Agent;
import model.Department;
import model.Payment;
import service.AgentService;
import service.DepartmentService;
import service.PaymentService;

import java.util.List;

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
    public Agent getAgent(int id){
        return agentService.getAgentById(id);
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

    // 5- get departmnt by id
    public Department getDepartmentByid(int id){
        return departmentService.findDepartmentById(id);
    }


    /**
     * ======================== Payment Controller ===================
     */

    // 1- create Payment
    public void addPayment(Payment payment){
        paymentService.addPayment(payment);
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


    /**
     * done
     */


}
