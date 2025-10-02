package service.implement;

import dao.AgentDAO;
import dao.implments.AgentDAOImpl;
import model.Agent;
import service.AgentService;

import java.sql.Connection;
import java.util.List;

public class AgentServiceImpl implements AgentService {
//    private AgentDAO agentDAO;
    private final AgentDAO agentDAO;
        public AgentServiceImpl(AgentDAO agentDAO){
        this.agentDAO = agentDAO;
    }


    @Override
    public void addAgent(Agent agent) {
        agentDAO.saveAgent(agent);
    }

    @Override
    public void deleteAgent(int id) {
        agentDAO.deleteAgent(id);
    }

    @Override
    public void updateAgent(Agent agent) {
        agentDAO.updateAgent(agent);
    }

    @Override
    public List<Agent> getAllAgents() {
        return agentDAO.findAll();
    }

    @Override
    public Agent getAgentById(int id) {
        return agentDAO.findById(id);
    }

    @Override
    public Agent findAgentByEmail(String email) {
        return agentDAO.findByEmail(email);
    }

    @Override
    public List<Agent> findManagersByDepartmentId(int departmentId) {
        return agentDAO.findManagersByDepartmentId(departmentId);
    }

    @Override
    public Boolean removeAgentFromDepartment(int id) {
        Agent agent = agentDAO.findById(id);
        if(agent == null){
            return false;
        }
            agent.setDepartment(null);
             agentDAO.updateAgent(agent);
            return true;
    }
}
