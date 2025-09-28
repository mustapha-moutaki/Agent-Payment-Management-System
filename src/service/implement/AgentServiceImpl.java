package service.implement;

import dao.AgentDAO;
import dao.implments.AgentDAOImpl;
import model.Agent;
import service.AgentService;

import java.sql.Connection;
import java.util.List;

public class AgentServiceImpl implements AgentService {
    private AgentDAO agentDAO;

        public AgentServiceImpl(Connection connection){
        this.agentDAO = new AgentDAOImpl(connection);
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
}
