package view;

import Utils.InputUtils;
import controller.MainController;
import model.Agent;
import model.Department;
import model.Payment;
import model.enums.AgentType;
import security.AuthentificationService;
import service.AgentService;
import service.DepartmentService;

import java.sql.ResultSet;
import java.util.List;

public class DirectorUi {

    private MainController controller;
    private AuthentificationService authService;
    private DepartmentService departmentService;
    private AgentService agentService;
    public DirectorUi(MainController controller, AuthentificationService authService){
        this.controller = controller;
        this.authService = authService;
    }

    public void directorMenu(Agent director){
        int choice;
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║                     Director Menu                       ║");
        System.out.println("╠════════════════════════════════════════════════════════╣");
        System.out.println("║ 1️⃣  Create Department                                   ║");// done
        System.out.println("║ 2️⃣  Update Department                                   ║"); // done
        System.out.println("║ 3️⃣  Delete Department                                   ║"); // done
        System.out.println("║ 4️⃣  Assign Manager to Department                        ║"); // done -test
        System.out.println("║ 5️⃣  Add or Update Agent across Departments              ║"); // not yet
        System.out.println("║ 6️⃣  View All Departments                                ║"); // done
        System.out.println("║ 7️⃣  View All Agents                                     ║"); // done
        System.out.println("║ 8️⃣  View All Payments                                   ║");//  - Filter by Department, Agent, Type, Amount, Date
        System.out.println("║ 9️⃣  View Company-wide Statistics                        ║");//Total number of Agents and Departments+Payment distribution (Salary / Allowances / Bonus / Compensation+ - Identify agent with highest total payments
        System.out.println("║ 0️⃣  Logout                                              ║"); // done
        System.out.println("╚════════════════════════════════════════════════════════╝");
        choice = InputUtils.readInt("Enter your choice: ");
        boolean exit = false;
        while(!exit){
            do{
                switch (choice){
                    case 1: createDepartment();// done ready for test
                        break;
                    case 2: updateDepartment();// done ready for test
                        break;
                    case 3: deleteDepartment();// done ready for test
                        break;
                    case 4: assignManagerToDepartment();// done ready for test
                        break;
                    case 5: AddorUpdateAgentacrossDepartments();// no yet add agent to anydepartment and edits it's data
                        break;
                    case 6: displayAllDepartments();// done ready for test
                        break;
                    case 7: displayAllAgents();// ready fro test
                        break;
                    case 8: displayAllPayments();// done ready for test
                        break;
                    case 9: ViewCompanyWideStatistics();// not yet
                        break;
                    case 0:
                        System.out.println("Exiting ...");
                    default:
                        System.out.println("Invalid choice, try again");
                }
            }while (choice != 0);
        }


    }

    // 1- create department
    public void createDepartment(){
        System.out.println("Enter department name ");
        String depaName = InputUtils.readString("Name: ");
        Department newDepartment = new Department(depaName);
        controller.addDepartment(newDepartment);
        System.out.println("↪ Department created successfully");
        System.out.println("\n↪ Press Enter to return to menu...");
        InputUtils.readString("");

        // it should return me to the menu
    }

    // 2- updateDepartment
    public void updateDepartment(){
        int choice;
        List<Department> departments = controller.DepartmentList();
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║         Departments List       ║");
        System.out.println("╚════════════════════════════════╝");

        if (!departments.isEmpty()) {
            for (int i = 0; i < departments.size(); i++) {
                System.out.println((i + 1) + "- " + departments.get(i).getName());

            }

             choice = InputUtils.readInt("Enter your choice: ");

            if (choice > 0 && choice <= departments.size()) {
                Department chosenDepartment = departments.get(choice - 1);
                System.out.println("Updating Department ID=" + chosenDepartment.getId_department());
                String newName = InputUtils.readString("Enter new name for the department: ");
                chosenDepartment.setName(newName);
                controller.updateDeaprtment(chosenDepartment);
                System.out.println("Department updated successfully!");
            } else {
                System.out.println("Invalid choice!");
            }
        } else {
            System.out.println("! No departments found !");
        }

    }

    // 3- deleteDepartment
    public void deleteDepartment(){
        int choice;
        List<Department> departments = controller.DepartmentList();
        if (!departments.isEmpty()) {
            for (int i = 0; i < departments.size(); i++) {
                System.out.println((i + 1) + "- " + departments.get(i).getName());
            }

            choice = InputUtils.readInt("Enter your choice: ");

            if (choice > 0 && choice <= departments.size()) {
                int chosenDepartment = departments.get(choice - 1).getId_department();
               controller.deleteDepartment(chosenDepartment);
                System.out.println("Department deleted successfully!");
            } else {
                System.out.println("Invalid choice!");
            }
        } else {
            System.out.println("! No departments found !");
        }
    }

    // 4- assginManagerToDepartment
    // check maincontrollr 5-
    //check department line 8
    public void AddorUpdateAgentacrossDepartments(){

    }


    public void assignManagerToDepartment() {
        List<Agent> agents = agentService.getAllAgents();

        // getting all managers
        List<Agent> managers = agents.stream()
                .filter(a -> a.getAgent_type() == AgentType.RESPONSABLE_DEPARTEMENT)
                .toList();

        if (managers.isEmpty()) {
            System.out.println("No manager available ");
            return;
        }

        // display managers list
        for (int i = 0; i < managers.size(); i++) {
            System.out.println((i + 1) + "- " + managers.get(i).getFirst_name() + " " + managers.get(i).getLast_name());
        }

        int choice = InputUtils.readInt("chose the manager: ");
        if (choice > 0 && choice <= managers.size()) {
            Agent chosenManager = managers.get(choice - 1);

            List<Department> departments = departmentService.findAll();
            if (departments.isEmpty()) {
                System.out.println("No departments found ");
                return;
            }

            // display departments list
            for (int i = 0; i < departments.size(); i++) {
                System.out.println((i + 1) + "- " + departments.get(i).getName());
            }

            int chosenDepa = InputUtils.readInt("chose department: ");
            if (chosenDepa > 0 && chosenDepa <= departments.size()) {
                Department chosenDepartment = departments.get(chosenDepa - 1);

                // chcek if the department has already manager
                boolean hasManager = agents.stream()
                        .anyMatch(a -> a.getDepartment() != null
                                && a.getDepartment().getId() == chosenDepartment.getId()
                                && a.getAgent_type() == AgentType.RESPONSABLE_DEPARTEMENT);

                if (hasManager) {
                    System.out.println("==>department " + chosenDepartment.getName() + "has aleady manager!");
                    return;
                }

                // assgin manager to department
                controller.assignManagerToDepartment(chosenManager, chosenDepartment);
                System.out.println("✅ manger " + chosenManager.getFirst_name() + "assigned to department:  " + chosenDepartment.getName());
            }
        }
    }

    // 5- addAgentToDepartment
    public void addAgentToDepartment(){

    }
    // 6- displayAllDepartments
     public void displayAllDepartments(){
        List<Department>departmentList = controller.DepartmentList();
         System.out.println("\n╔════════════════════════════════╗");
         System.out.println("║         Departments List        ║");
         System.out.println("╚════════════════════════════════╝");
         if(departmentList.isEmpty()){
             System.out.println("No departments available ");
         }

         for (Department department: departmentList){
             System.out.println("---> "+department.getName());
         }
         System.out.println("══════════════End═════════════════");

     }

     // 7- displayAllAgents
    public void displayAllAgents(){
        List<Agent>agentsList = controller.getAllAgent();
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║         Agents List             ║");
        System.out.println("╚════════════════════════════════╝");
        if(agentsList.isEmpty()){
            System.out.println("No agent available ");
        }

        for (Agent agent: agentsList){
            System.out.println("---");
            System.out.println("---> "+agent.getFirst_name());
            System.out.println("---> "+agent.getLast_name());
            System.out.println("---> "+agent.getAgent_type().name());
            System.out.println("---");
        }
        System.out.println("══════════════End═════════════════");
    }

    // 8- display payments list
    public void displayAllPayments(){
        List<Payment>paymentList = controller.paymentList();
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║         Payments List            ║");
        System.out.println("╚════════════════════════════════╝");
        if(paymentList.isEmpty()){
            System.out.println("No payments available ");
        }

        for (Payment payment: paymentList){
            System.out.println("---");
            System.out.println("---> "+payment.getType());
            System.out.println("---> "+payment.getAmount());
            System.out.println("---> "+payment.getDate());
            System.out.println("---> "+payment.getAgentId());
            System.out.println("---> "+payment.getCondition_validated());
            System.out.println("---");
        }
        System.out.println("══════════════End═════════════════");
    }


    // 9- ViewCompany-wideStatistics()
    public void ViewCompanyWideStatistics(){

    }




    private void addAgent(){
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║          Create  New  Agent       ║");
        System.out.println("╚══════════════════════════════════╝");

        String firstName = InputUtils.readString("First Name: ");
        String lastName  = InputUtils.readString("Last Name: ");
        String email     = InputUtils.readString("Email: ");
        String password  = InputUtils.readString("Password: ");

        // chose the role
        System.out.println("\nChoose Role:");
        System.out.println("1️⃣  OUVRIER (Worker)");
        System.out.println("2️⃣  RESPONSABLE_DEPARTEMENT (Manager)");
        System.out.println("3️⃣  DIRECTEUR (Director)");

        int roleChoice = InputUtils.readInt("Enter your choice: ");
        model.enums.AgentType role;

        switch (roleChoice) {
            case 1 -> role = model.enums.AgentType.OUVRIER;
            case 2 -> role = model.enums.AgentType.RESPONSABLE_DEPARTEMENT;
            case 3 -> role = AgentType.DIRECTEUR;
            default -> {
                System.out.println("Invalid choice! Defaulting to OUVRIER.");
                role = model.enums.AgentType.OUVRIER;
            }
        }

        authService.register(firstName, lastName, email, password, role);

    }



}
