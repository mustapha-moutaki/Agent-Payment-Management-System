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

//        AgentDAO agenDao = new AgentDAOImpl(conn);
//        Agent newAgent = new Agent("ahmen", "mohammed", "ahmendmohammed@gmail.com", "123456", AgentType.OUVRIER, 0);
//        agenDao.saveAgent(newAgent);
//        System.out.println("agent created successfully");



            DepartmentDAOImpl deparDAO= new DepartmentDAOImpl(conn);
            Department depar1 = new Department("it", 1);
            deparDAO.saveDepartment(depar1);

    }
}