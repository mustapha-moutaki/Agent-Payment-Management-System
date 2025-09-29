package security;

import Utils.PasswordUtils;
import dao.AgentDAO;
import model.Agent;

public class AuthentificationService {
        private AgentDAO agentDAO;


        public AuthentificationService(AgentDAO agentDAO){
            this.agentDAO = agentDAO;
        }

        public Agent login(String email, String password){
            Agent agent = agentDAO.findByEmail(email);
            if(agent != null && PasswordUtils.verify(password, agent.getPassword())){
                return agent;

            }
            return  null;
        }

        public void register(String firstName, String lastName, String email, String password, model.enums.AgentType role){
            if(agentDAO.findByEmail(email) != null){
                System.out.println("==> email already exist try logging in ");
                return;
            }
            // hashing password
            String hashPassword = PasswordUtils.hash(password);
            // create new agent
            Agent newAgent = new Agent(firstName, lastName, email, hashPassword, role, 0);
            agentDAO.saveAgent(newAgent);
            System.out.println("↪ Registration successful! Welcome " + firstName + " as " + role.name());

        }
}
