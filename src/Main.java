import Utils.PasswordUtils;
import controller.MainController;
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
import security.AuthentificationService;
import service.AgentService;
import service.DepartmentService;
import service.PaymentService;
import service.implement.AgentServiceImpl;
import service.implement.DepartmentServiceImpl;
import service.implement.PaymentServiceImpl;
import view.MainMenu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();

// DAO layer
        AgentDAO agentDAO = new AgentDAOImpl(conn);
        DepartmentDAO departmentDAO = new DepartmentDAOImpl(conn);
        PaymentDAO paymentDAO = new PaymentDAOImpl(conn);

// Service layer
        AgentService agentService = new AgentServiceImpl(agentDAO);
        DepartmentService departmentService = new DepartmentServiceImpl(departmentDAO, agentDAO);
        PaymentService paymentService = new PaymentServiceImpl(paymentDAO);

// Authentication service
        AuthentificationService authService = new AuthentificationService(agentDAO, departmentService);

// Controller layer
        MainController controller = new MainController(agentService, departmentService, paymentService, authService);

// View layer
        MainMenu menu = new MainMenu(agentDAO, authService, controller);
        menu.start();

    }
}