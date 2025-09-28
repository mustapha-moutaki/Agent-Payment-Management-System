package service.implement;

import dao.DepartmentDAO;
import dao.implments.DepartmentDAOImpl;
import model.Department;
import service.DepartmentService;

import java.sql.Connection;
import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentDAO departmentDAO;

    public DepartmentServiceImpl(Connection connection){
        this.departmentDAO = new DepartmentDAOImpl(connection);
    }

    @Override
    public void addDepartment(Department department) {
        departmentDAO.saveDepartment(department);
    }

    @Override
    public void updateDepartment(Department department) {
        departmentDAO.updateDepattment(department);
    }

    @Override
    public List<Department> findAll() {
        return departmentDAO.findAll();
    }

    @Override
    public Department findDepartmentById(int id) {
        return departmentDAO.findDepartmentById(id);
    }

    @Override
    public void deleteDepartment(int id) {
        departmentDAO.deleteDepartment(id);
    }
}
