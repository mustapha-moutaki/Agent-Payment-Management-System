package security;

import Utils.PasswordUtils;
import dao.AgentDAO;
import model.Agent;
import model.Department;
import service.DepartmentService;

public class AuthentificationService {
        private AgentDAO agentDAO;
        private DepartmentService departmentService;

        public AuthentificationService(AgentDAO agentDAO, DepartmentService departmentService){
            this.agentDAO = agentDAO;
            this.departmentService = departmentService;
        }

        public Agent login(String email, String password){
            Agent agent = agentDAO.findByEmail(email);
            if(agent != null && PasswordUtils.verify(password, agent.getPassword())){
                return agent;

            }
            return  null;
        }

        public void register(String firstName, String lastName, String email, String password, model.enums.AgentType role, int id_department){

            if(agentDAO.findByEmail(email) != null){
                System.out.println("==> email already exist try logging in ");
                return;
            }
            Department department = null;
            if(id_department != 0){
                department = departmentService.findDepartmentById(id_department);
                if(department == null){
                    System.out.println("==>Department with ID " + id_department + " not found. Agent will have no department.");
                }
            }
            // hashing password
            String hashPassword = PasswordUtils.hash(password);
            // create new agent
            Agent newAgent = new Agent(firstName, lastName, email, hashPassword, role);
            newAgent.setDepartment(department);
            agentDAO.saveAgent(newAgent);
            System.out.println("↪ Registration successful! Welcome " + firstName + " as " + role.name());

        }
}
