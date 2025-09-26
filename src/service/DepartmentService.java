package service;

import model.Department;

import java.util.List;

public interface DepartmentService {
    void addDepartment(Department department);
    void updateDepartment(Department department);
    List<Department> findAll();
    Department findDepartmentById(int id);
    void deleteDepartment(int it);
}
