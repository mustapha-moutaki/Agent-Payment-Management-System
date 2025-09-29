package service.implement;

import Utils.InputUtils;
import dao.DepartmentDAO;
import dao.AgentDAO;
import dao.implments.DepartmentDAOImpl;
import model.Agent;
import model.Department;
import model.enums.AgentType;
import service.DepartmentService;

import java.sql.Connection;
import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentDAO departmentDAO;
    private DepartmentService departmentService;
    private AgentDAO agentDAO;
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

    @Override
    public void assignAgentToDepartment(Agent agent, Department department) {
        if (agent == null || department == null) {
            System.out.println("⚠ Agent or Department is null!");
            return;
        }

        agent.setDepartment(department);
        agentDAO.updateAgent(agent); // add to database

        if (agent.getAgent_type() == AgentType.RESPONSABLE_DEPARTEMENT) {
            System.out.println("=> " + agent.getFirst_name() + " is now the Manager of " + department.getName());
        } else if (agent.getAgent_type() == AgentType.OUVRIER) {
            System.out.println("=> " + agent.getFirst_name() + " joined " + department.getName() + " as Worker");
        } else if (agent.getAgent_type() == AgentType.DIRECTEUR) {
            System.out.println("=> " + agent.getFirst_name() + " (Director) assigned to " + department.getName());
        }
    }

    @Override
    public void assignManagerToDepartment(Agent agent, Department department){

        if (agent == null || department == null) {
            System.out.println("⚠ Agent or Department is null!");
            return;
        }
        agent.setDepartment(department);
        agentDAO.updateAgent(agent);

            }


    @Override
    public void getDepartmentsStatistics() {

    }

        }


