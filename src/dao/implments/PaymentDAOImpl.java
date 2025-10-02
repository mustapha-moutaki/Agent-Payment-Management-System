package dao.implments;

import dao.PaymentDAO;
import model.Department;
import model.Payment;
import model.enums.PaymentType;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentDAOImpl implements PaymentDAO {

    private Connection connection;

    public PaymentDAOImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void savePayment(Payment payment) {
        String sql = "INSERT INTO payment(payment_type, amount, payment_date, condition_validated, id_agent) VALUES (?, ?, ?, ?, ?)";
        System.out.println("it's saving");
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, payment.getType().name());
            stmt.setDouble(2, payment.getAmount());
            stmt.setDate(3, Date.valueOf(payment.getDate()));
            stmt.setBoolean(4, payment.getCondition_validated());
            stmt.setInt(5, payment.getAgentId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePayment(Payment payment) {
        String sql = "UPDATE payment SET payment_type = ?, amount = ?, payment_date = ?, condition_validated = ?, id_agent = ? WHERE id_payment = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, payment.getType().name());
            stmt.setDouble(2, payment.getAmount());
            stmt.setDate(3, Date.valueOf(payment.getDate()));
            stmt.setBoolean(4, payment.getCondition_validated());
            stmt.setInt(5, payment.getAgentId());
            stmt.setInt(6, payment.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Payment findByid(int id) {
        String sql = "SELECT * FROM payment WHERE id_payment = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();// to exicute the quiery
            if (rs.next()) {// check if got the data
                return new Payment(
                        rs.getInt("id_payment"),
                        PaymentType.valueOf(rs.getString("payment_type")),
                        rs.getDouble("amount"),
                        rs.getDate("payment_date").toLocalDate(),
                        rs.getBoolean("condition_validated"),
                        rs.getInt("id_agent")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Payment> findAll() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payment";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Payment payment = new Payment(
                        rs.getInt("id_payment"),
                        PaymentType.valueOf(rs.getString("payment_type")),
                        rs.getDouble("amount"),
                        rs.getDate("payment_date").toLocalDate(),
                        rs.getBoolean("condition_validated"),
                        rs.getInt("id_agent")
                );
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payments;
    }

    @Override
    public void deletePayment(int id) {
        String sql = "DELETE FROM payment WHERE payment_id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public Map<Department, Double> getTotalPaymentsByDepartment() {
        Map<Department, Double> result = new HashMap<>();
        String sql = "SELECT d.id_department, d.name, COALESCE(SUM(p.amount), 0) AS total_payments " +
                "FROM department d " +
                "LEFT JOIN agent a ON d.id_department = a.id_department " +
                "LEFT JOIN payment p ON a.id_agent = p.id_agent " +
                "GROUP BY d.id_department, d.name";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Department dept = new Department(
                        rs.getInt("id_department"),
                        rs.getString("name")
                );
                double total = rs.getDouble("total_payments");
                result.put(dept, total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
