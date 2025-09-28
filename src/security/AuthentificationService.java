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
}
