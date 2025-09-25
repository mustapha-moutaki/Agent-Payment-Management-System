import dao.AgentDAO;
import dao.DBConnection;
import dao.DepartmentDAO;
import dao.implments.AgentDAOImpl;
import dao.implments.DepartmentDAOImpl;
import model.Agent;
import model.Department;
import model.enums.AgentType;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // 1- we make connection with database
        Connection conn = DBConnection.getConnection();

        AgentDAO agenDao = new AgentDAOImpl(conn);

        //-----------Agent creation----------------------
//        Agent newAgent = new Agent("hello", "mohammed", "ahmendmohammed@gmail.com", "123456", AgentType.OUVRIER, 0);
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
//            DepartmentDAOImpl deparDAO= new DepartmentDAOImpl(conn);
//            Department depar1 = new Department("it", 1);
//            deparDAO.saveDepartment(depar1);

        DepartmentDAO deparDao = new DepartmentDAOImpl(conn);
//        Department depar1 = new Department("marketing", 1);// creating department
//        deparDao.saveDepartment(depar1);

        //System.out.println(deparDao.findAll());// find the all department
//        Department deparfinded = deparDao.findDepartmentById(2);
//        System.out.println(deparfinded);// find the department by it\s id
 //       deparDao.deleteDepartment(2);// delete the department




    }
}