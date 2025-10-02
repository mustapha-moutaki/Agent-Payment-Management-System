package dao.implments;

import dao.AgentDAO;
import dao.DepartmentDAO;
import model.Agent;
import model.Department;
import model.Payment;
import model.enums.AgentType;
import model.enums.PaymentType;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.sql.Statement.RETURN_GENERATED_KEYS;


public class AgentDAOImpl implements AgentDAO {
    private Connection connection;

    public AgentDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveAgent(Agent agent) {
        String sql = "INSERT INTO agent(first_name, last_name, email, password, agent_type, id_department)VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, agent.getFirst_name());
            stmt.setString(2, agent.getLast_name());
            stmt.setString(3, agent.getEmail());
            stmt.setString(4, agent.getPassword());
            stmt.setString(5, agent.getAgent_type().name());
//            stmt.setInt(6, agent.getId_department());
            if (agent.getDepartment() != null) {
                stmt.setInt(6, agent.getDepartment().getId_department());
            } else {
                stmt.setNull(6, java.sql.Types.INTEGER);
            }
            stmt.executeUpdate();
            /*
            because we don't wanna to insert id when we create agent we use
            generatedKyes to get the id from db and set it for agent
             */
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                int generatedId= rs.getInt(1);
                agent.setId(generatedId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAgent(Agent agent) {
        String sql = "UPDATE agent SET first_name = ?, last_name = ?, email= ?, password = ?, agent_type = ?, id_department= ? WHERE id_agent = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, agent.getFirst_name());
            stmt.setString(2, agent.getLast_name());
            stmt.setString(3, agent.getEmail());
            stmt.setString(4, agent.getPassword());
            stmt.setString(5, agent.getAgent_type().name());
//            stmt.setInt(6, agent.getId_department());
//            stmt.setInt(6, agent.getDepartment() != null ? agent.getDepartment().getId_department() : 0);
            if(agent.getDepartment() != null){
                stmt.setInt(6, agent.getDepartment().getId_department());
            } else {
                stmt.setNull(6, java.sql.Types.INTEGER);  // to set null not the 0
            }

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
//                System.out.println("*******************"+ agentId);
                Agent agent = agentMap.get(agentId);

                if (agent == null) {
                    agent = new Agent(
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            AgentType.valueOf(rs.getString("agent_type"))
//                            rs.getInt("id_department")
                    );
                    agent.setId(agentId);

                    // department
                    int depId = rs.getInt("dep_id");
                    String depName = rs.getString("department_name");
                    if(depId != 0){
                        agent.setDepartment(new Department(depId, depName));
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
                            rs.getInt("id_agent"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            AgentType.valueOf(rs.getString("agent_type")),
                            rs.getInt("id_department")
                    );

                    // department
                    int depId = rs.getInt("id_department");
                    String depName = rs.getString("department_name");
                    if(depId != 0){
                        agent.setDepartment(new Department(depId, depName));
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


    @Override
    public Agent findByEmail(String email) {
        String sql = "SELECT * FROM agent WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Agent agent = new Agent(
                        rs.getInt("id_agent"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        AgentType.valueOf(rs.getString("agent_type")),
                        rs.getInt("id_department") // the column that we need
                );


                int deptId = rs.getInt("id_department");
                if (!rs.wasNull()) {
                    DepartmentDAO deptDao = new DepartmentDAOImpl(connection);
                    Department dept = deptDao.findDepartmentById(deptId);
                    agent.setDepartment(dept);
                }

                return agent;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public List<Agent> findManagersByDepartmentId(int departmentId) {
        List<Agent> managers = new ArrayList<>();
        String sql = "SELECT * FROM agent WHERE agent_type = 'RESPONSABLE_DEPARTEMENT' AND id_department = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, departmentId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                int dep = 0;
                String depId = rs.getString("id_department");
                if(Integer.parseInt(depId) != 0) dep = new Department(depId).getId_department();

                managers.add(new Agent(
                        rs.getInt("id_agent"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        AgentType.RESPONSABLE_DEPARTEMENT,
                        dep
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return managers;
    }


}

