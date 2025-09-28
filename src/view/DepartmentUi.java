package view;

import controller.MainController;
import model.Department;

import java.util.List;

public class DepartmentUi {
    private MainController controller;

    public void addDepartment(Department department){
        controller.addDepartment(department);
    }
    public void deleteDepartment(int id){
        controller.deleteDepartment(id);
    }
    public void updateDepartment(Department department){
        controller.updateDeaprtment(department);
    }
    public void getDepartmentsList(){
        List<Department> departmentsList = controller.DepartmentList();
        System.out.println("++++++++++++Departments List++++++++++++++++");;
        for(Department department : departmentsList){
            System.out.println("=====> Department Name: "+ department.getName());
        }
        System.out.println("+++++++++++++++++++++++++++++++++++++");
    }
    public void getDepartmentById(int id){
        controller.getDepartmentByid(id);
    }
}
