package dao.implments;

import dao.AgentDAO;
import model.Agent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AgentDAOImpl implements AgentDAO {
    private Connection connection;

    public AgentDAOImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void saveAgent(Agent agent) {
        String sql = "INSERT INTO agent(first_name, last_name, email, password, agent_type, id_department)VALUES(?, ?, ?, ?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, agent.getFirst_name());
            stmt.setString(2, agent.getLast_name());
            stmt.setString(3, agent.getEmail());
            stmt.setString(4, agent.getPassword());
            stmt.setString(5, agent.getAgent_type().name());
            stmt.setInt(6, agent.getId_department());
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateAgent(Agent agent) {

    }

    @Override
    public List<Agent> findAll() {
        return List.of();
    }

    @Override
    public Agent findById(int id) {
        return null;
    }
}
