package dao.implments;

import dao.DepartmentDAO;
import model.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DepartmentDAOImpl implements DepartmentDAO {

    private Connection connection;

    public DepartmentDAOImpl(Connection connection){
        this.connection = connection;
    }


    @Override
    public void saveDepartment(Department department) {
        String sql = "INSERT INTO department (name)VALUES (?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, department.getName());
//            stmt.setInt(2, department.getId_manager());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDepattment(Department department) {
        String sql = "UPDATE department SET name = ? WHERE department_id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1,department.getName());
//            stmt.setInt(2,department.getId_manager());
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Department findDepartmentById(int id) {
        String sql = "SELECT * FROM department WHERE id_department = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return new Department(
                        rs.getInt("id_department"),
                        rs.getString("name")
//                        rs.getInt("id_manager")
                );
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Department> findAll() {
        List<Department>departments = new ArrayList<>();
        String sql = "SELECT * FROM department";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
        ResultSet rs = stmt.executeQuery();
        while (rs.next()){
            Department department = new Department(
                    rs.getInt("id_department"),
                    rs.getString("name")
//                    rs.getInt("id_manager")
            );
            departments.add(department);
        }
        } catch (Exception e) {
            e.printStackTrace();;
        }
        return departments;
    }

    @Override
    public void deleteDepartment(int id) {
        String sql = "DELETE FROM department WHERE id_department = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
