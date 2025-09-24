package dao;

import model.Agent;

import java.util.List;

public interface AgentDAO {
     void saveAgent(Agent agent);
     void updateAgent(Agent agent);
     List<Agent> findAll();
     Agent findById(int id);
}
