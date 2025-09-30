package dao;

import model.Agent;

import java.util.List;

public interface AgentDAO {
     void saveAgent(Agent agent);
     void updateAgent(Agent agent);
     void deleteAgent(int id);
     List<Agent> findAll();
     Agent findById(int id);
     Agent findByEmail(String email);
    public List<Agent> findManagersByDepartmentId(int departmentId);
}
