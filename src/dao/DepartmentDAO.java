package dao;

import model.Department;

import java.util.List;

public interface DepartmentDAO {
     void saveDepartment(Department department);
     void updateDepattment(Department department);
     Department findDepartmentById(int id);
     List<Department> findAll();
}
