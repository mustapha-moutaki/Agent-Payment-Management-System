import dao.AgentDAO;
import dao.DBConnection;
import dao.DepartmentDAO;
import dao.PaymentDAO;
import dao.implments.AgentDAOImpl;
import dao.implments.DepartmentDAOImpl;
import dao.implments.PaymentDAOImpl;
import model.Agent;
import model.Department;
import model.Payment;
import model.enums.AgentType;
import model.enums.PaymentType;

import java.sql.Connection;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // 1- we make connection with database
        Connection conn = DBConnection.getConnection();

        AgentDAO agenDao = new AgentDAOImpl(conn);

        //-----------Agent creation----------------------
//        Agent newAgent = new Agent("hello", "worlfd", "ahmendmohammed@gmail.com", "12456", AgentType.OUVRIER, 1);
//        agenDao.saveAgent(newAgent);
//        System.out.println("agent created successfully");

        //---------------delete------------------------------
//        agenDao.deleteAgent(3);
//        System.out.println("agent deleted successfully");
        //--------------------------------------------------


        // -------------find agent by id------------
//        Agent agentfind= agenDao.findById(1);
//        System.out.println(agentfind);
//        System.out.println("the agent find success");


// ------------------creating department-------------------
            DepartmentDAOImpl deparDAO= new DepartmentDAOImpl(conn);
//            Department depar1 = new Department(1, "it");
//            deparDAO.saveDepartment(depar1);

        DepartmentDAO deparDao = new DepartmentDAOImpl(conn);
//        Department depar1 = new Department("marketing", 1);// creating department
//        deparDao.saveDepartment(depar1);

        //System.out.println(deparDao.findAll());// find the all department
//        Department deparfinded = deparDao.findDepartmentById(2);
//        System.out.println(deparfinded);// find the department by it\s id
 //       deparDao.deleteDepartment(2);// delete the department

//        adding payment
//        PaymentDAO paymentDAO = new PaymentDAOImpl(conn);
//        Payment payment1= new Payment(1,PaymentType.SALARY, 10, LocalDate.now(), true, 3);
//        paymentDAO.savePayment(payment1);

        // display all agent deatils
        System.out.println(agenDao.findAll());
    }
}