package dao.implments;

import dao.AgentDAO;
import model.Agent;
import model.Department;
import model.Payment;
import model.enums.AgentType;
import model.enums.PaymentType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class AgentDAOImpl implements AgentDAO {
    private Connection connection;

    public AgentDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveAgent(Agent agent) {
        String sql = "INSERT INTO agent(first_name, last_name, email, password, agent_type, id_department)VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, agent.getFirst_name());
            stmt.setString(2, agent.getLast_name());
            stmt.setString(3, agent.getEmail());
            stmt.setString(4, agent.getPassword());
            stmt.setString(5, agent.getAgent_type().name());
            stmt.setInt(6, agent.getId_department());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAgent(Agent agent) {
        String sql = "UPDATE agent SET first_name = ?, last_name = ?, email= ?, password = ?, agent_type = ?, id_department= ? WHERE agent_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, agent.getFirst_name());
            stmt.setString(2, agent.getLast_name());
            stmt.setString(3, agent.getEmail());
            stmt.setString(4, agent.getPassword());
            stmt.setString(5, agent.getAgent_type().name());
            stmt.setInt(6, agent.getId_department());
            stmt.setInt(7, agent.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
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
    */
    @Override // should undersatnd this
    public List<Agent> findAll() {
        Map<Integer, Agent> agentMap = new LinkedHashMap<>(); // ID → Agent
        String sql = "SELECT a.*, d.id_department AS dep_id, d.name AS department_name, p.* " +
                "FROM agent a " +
                "LEFT JOIN department d ON a.id_department = d.id_department " +
                "LEFT JOIN payment p ON p.id_agent = a.id_agent " +
                "ORDER BY a.id_agent";

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int agentId = rs.getInt("id_agent");
                Agent agent = agentMap.get(agentId);

                if (agent == null) {
                    agent = new Agent(
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            AgentType.valueOf(rs.getString("agent_type")),
                            rs.getInt("id_department")
                    );

                    // department
                    int depId = rs.getInt("id_department");
                    if (depId != 0) {
                        agent.setDepartment(new Department(depId, rs.getString("department_name")));
                    }

                    agentMap.put(agentId, agent);
                }

                // payment
                int paymentId = rs.getInt("id_payment");
                if (paymentId != 0) {
                    Payment payment = new Payment(
                            paymentId,
                            PaymentType.valueOf(rs.getString("payment_type")),
                            rs.getDouble("amount"),
                            rs.getDate("payment_date").toLocalDate(),
                            rs.getBoolean("condition_validated"),
                            rs.getInt("id_agent")
                    );
                    agent.addPayment(payment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(agentMap.values());
    }


    @Override
    public Agent findById(int id) {
        String sql = "SELECT a.*, d.name AS department_name, p.* " +
                "FROM agent a " +
                "LEFT JOIN department d ON a.id_department = d.id_department " +
                "LEFT JOIN payment p ON p.id_agent = a.id_agent " +
                "WHERE a.id_agent = ?";

        Agent agent = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (agent == null) {
                    agent = new Agent(
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            AgentType.valueOf(rs.getString("agent_type")),
                            rs.getInt("id_department")
                    );

                    // department
                    int depId = rs.getInt("id_department");
                    if (depId != 0) {
                        agent.setDepartment(new Department(depId, rs.getString("department_name")));
                    }
                }

                // payments
                int paymentId = rs.getInt("id_payment");
                if (paymentId != 0) {
                    Payment payment = new Payment(
                            paymentId,
                            PaymentType.valueOf(rs.getString("payment_type")),
                            rs.getDouble("amount"),
                            rs.getDate("payment_date").toLocalDate(),
                            rs.getBoolean("condition_validated"),
                            rs.getInt("id_agent")
                    );
                    agent.addPayment(payment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return agent;
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

