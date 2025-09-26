package dao.implments;

import dao.AgentDAO;
import model.Agent;
import model.enums.AgentType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        String sql = "UPDATE agent SET first_name = ?, last_name = ?, email= ?, password = ?, agent_type = ?, id_department= ? WHERE agent_id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, agent.getFirst_name());
            stmt.setString(2, agent.getLast_name());
            stmt.setString(3, agent.getEmail());
            stmt.setString(4, agent.getPassword());
            stmt.setString(5, agent.getAgent_type().name());
            stmt.setInt(6, agent.getId_department());
            stmt.setInt(7, agent.getId());
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Agent> findAll() {
        List<Agent> agents = new ArrayList<>();
        String sql = "SELECT * FROM agent";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Agent agent = new Agent(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        AgentType.valueOf(rs.getString("agent_type")),
                        rs.getInt("id_department")
                );
                agents.add(agent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agents;
    }


    @Override
    public Agent findById(int id) {
        String sql = "SELECT * FROM agent WHERE id_agent = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Agent(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        AgentType.valueOf(rs.getString("agent_type")),
                        rs.getInt("id_department")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void deleteAgent(int id) {
        String sql = "DELETE FROM agent WHERE id_agent= ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}

