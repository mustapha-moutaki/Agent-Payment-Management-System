package service;

import model.Agent;

import java.util.List;

public interface AgentService {
    /**
     *
     * @param agent to create new agent
     */
    void addAgent(Agent agent);

    /**
     *
     * @param id to delete agent by id
     */
    void deleteAgent(int id);

    /**
     *
     * @param agent to update agent data
     */
    void updateAgent(Agent agent);

    /**
     *
     * @return all agents list
     */
    List<Agent> getAllAgents();

    /**
     *
     * @param id to find agent by id
     * @return
     */
    Agent getAgentById(int id);

}
