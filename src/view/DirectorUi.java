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
import java.util.Scanner;

public class DirectorUi {

    private MainController controller;
    private AuthentificationService authService;
    private DepartmentService departmentService;
    private AgentService agentService;
    Scanner sc = new Scanner(System.in);
    public DirectorUi(MainController controller, AuthentificationService authService){
        this.controller = controller;
        this.authService = authService;

    }

    public void directorMenu(Agent director){

        int choice;
        do {

            System.out.println("\n╔════════════════════════════════════════════════════════╗");
            System.out.println("║                     Director Menu                       ║");
            System.out.println("╠════════════════════════════════════════════════════════╣");
            System.out.println("║ 1️⃣  Create Department                                   ║");// done
            System.out.println("║ 2️⃣  Update Department                                   ║"); // done
            System.out.println("║ 3️⃣  Delete Department                                   ║"); // done
            System.out.println("║ 4️⃣  Assign Manager to Department        (beta)          ║"); // Not work id not exist
            System.out.println("║ 5️⃣  Add or Update Agent across Departments (beta)       ║"); // not yet
            System.out.println("║ 6️⃣  View All Departments                                ║"); // done with test
            System.out.println("║ 7️⃣  View All Agents                                     ║"); // done with test
            System.out.println("║ 8️⃣  View All Payments                                   ║");//  - Filter by Department, Agent, Type, Amount, Date
            System.out.println("║ 9️⃣  View Company-wide Statistics  (not yet)             ║");
            System.out.println("║ 0️⃣  Logout                                              ║"); // done
            System.out.println("╚════════════════════════════════════════════════════════╝");
            choice = InputUtils.readInt("Enter your choice: ");

//        boolean exit = false;
//        while(!exit){
//            do{
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
//                sc.nextLine();
            if (choice > 0 && choice <= departments.size()) {
                Department chosenDepartment = departments.get(choice - 1);
//                System.out.println("Updating Department ID=" + chosenDepartment.getId_department());
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
                System.out.println("==> Department deleted successfully!");
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
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║         Manage Agents          ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.println("║1- Add Agent                    ║");
        System.out.println("║2- update Agent                 ║");
        System.out.println("╚════════════════════════════════╝");
        int choice = InputUtils.readInt("Enter your choice: ");
        switch (choice){
            case 1: directorAddAgent();
            break;
            case 2: directorEditAgent();
            case 0: System.exit(0);
            default:
                System.out.println("Invalid choice, please try again ");
        }
    }

    /*
    work place--------------
     */
    public void assignManagerToDepartment() {
        List<Agent> agents = controller.getAllAgent();

        // get all managers
        List<Agent> managers = agents.stream()
                .filter(a -> a.getAgent_type() == AgentType.RESPONSABLE_DEPARTEMENT)
                .toList();

        if (managers.isEmpty()) {
            System.out.println("No manager available.");
            return;
        }

        // display agent menu
        System.out.println("\nChoose a manager:");
        for (int i = 0; i < managers.size(); i++) {
            System.out.println((i + 1) + "- " + managers.get(i).getFirst_name() + " " + managers.get(i).getLast_name());
        }

        int choice = InputUtils.readInt("Enter manager choice: ");
        if (choice < 1 || choice > managers.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Agent chosenManager = managers.get(choice - 1);
        System.out.println("chose manager --------------------------------> "+ chosenManager.getId());
        List<Department> departments = controller.DepartmentList();
        if (departments.isEmpty()) {
            System.out.println("No departments found.");
            return;
        }

        // display departments menu
        System.out.println("\nChoose a department:");
        for (int i = 0; i < departments.size(); i++) {
            System.out.println((i + 1) + "- " + departments.get(i).getName());
        }

        int depChoice = InputUtils.readInt("Enter department choice: ");
        if (depChoice < 1 || depChoice > departments.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Department chosenDepartment = departments.get(depChoice - 1);
        //  check the department has agent
        boolean hasManager = controller.hasManagerInDepartment(chosenDepartment.getId_department());
        if (hasManager) {
            System.out.println("==> Department " + chosenDepartment.getName() + " already has a manager!");
            return;
        }

        // get agent from db
        Agent agentInDb = controller.getAgentById(chosenManager.getId());
        if (agentInDb == null) {
            System.out.println("Agent with ID " + chosenManager.getId() + " does not exist in DB.");
            return;
        }

        // assign manager to depar
        Boolean isSuccess = controller.assignManagerToDepartment(agentInDb, chosenDepartment);
        if(isSuccess){
            System.out.println("- Manager " + agentInDb.getFirst_name() + " assigned to department: " + chosenDepartment.getName());
        }else{
            System.err.println("error to assigne manager to department, try again");
        }

    }


//    public void assignManagerToDepartment() {
//        List<Agent> agents = controller.getAllAgent();
//
//        // getting all managers
//        List<Agent> managers = agents.stream()
//                .filter(a -> a.getAgent_type() == AgentType.RESPONSABLE_DEPARTEMENT)
//                .toList();
//
//        if (managers.isEmpty()) {
//            System.out.println("No manager available ");
//            return;
//        }
//
//        // display managers list
//        for (int i = 0; i < managers.size(); i++) {
//            System.out.println((i + 1) + "- " + managers.get(i).getFirst_name() + " " + managers.get(i).getLast_name());
//        }
//
//        int choice = InputUtils.readInt("chose the manager: ");
//        if (choice > 0 && choice <= managers.size()) {
//            Agent chosenManager = managers.get(choice - 1);
//
//            List<Department> departments = controller.DepartmentList();
//            if (departments.isEmpty()) {
//                System.out.println("No departments found ");
//                return;
//            }
//
//            // display departments list
//            for (int i = 0; i < departments.size(); i++) {
//                System.out.println((i + 1) + "- " + departments.get(i).getName());
//            }
//
//            int chosenDepa = InputUtils.readInt("chose department: ");
//            if (chosenDepa > 0 && chosenDepa <= departments.size()) {
//                Department chosenDepartment = departments.get(chosenDepa - 1);
//
//                // chcek if the department has already manager
//                boolean hasManager = agents.stream()
//                        .anyMatch(a -> a.getDepartment() != null
//                                && a.getDepartment().getId() == chosenDepartment.getId()
//                                && a.getAgent_type() == AgentType.RESPONSABLE_DEPARTEMENT);
//
//                if (hasManager) {
//                    System.out.println("==>department " + chosenDepartment.getName()+chosenDepartment.getId_department() + " has aleady manager!");
//                    return;
//                }
//
//                Agent agentInDb = controller.getAgentById(chosenManager.getId());
//                if (agentInDb == null) {
//                    System.out.println("Agent with id: " + chosenManager.getId() + " does not exist in DB");
//                    return;
//                }
//// assgin manager to department (use the one from DB)
//                controller.assignManagerToDepartment(agentInDb, chosenDepartment);
//                System.out.println("Manager " + agentInDb.getFirst_name() + " assigned to department: " + chosenDepartment.getName());
//
//                // assgin manager to department
////                controller.assignManagerToDepartment(chosenManager, chosenDepartment);
////                System.out.println("manger " + chosenManager.getFirst_name() + " assigned to department:  " + chosenDepartment.getName());
//            }
//        }
//    }

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
             System.out.println("---> |"+department.getName());
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
            System.out.println("-----------------------------");
            System.out.println("- id: "+agent.getId());
            System.out.println("- First Name: "+agent.getFirst_name());
            System.out.println("- Last Name: "+agent.getLast_name());
            System.out.println("- Type: "+agent.getAgent_type().name());
        }
        System.out.println("--------------------");
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
        System.out.println("----------------------------------");
        for (Payment payment: paymentList){

            System.out.println("- Payment type: "+payment.getType());
            System.out.println("- Payment Amount: "+payment.getAmount());
            System.out.println("- Date:  "+payment.getDate());
            System.out.println("- AgentId: "+payment.getAgentId());
            System.out.println("- IsValid:  "+payment.getCondition_validated());
            System.out.println("---        +          ---");
        }
        System.out.println("══════════════End═════════════════");
    }


    // 9- ViewCompany-wideStatistics()
    public void ViewCompanyWideStatistics(){

    }




    private void directorAddAgent() {
        List<Department> departmentList = controller.DepartmentList();
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║          Create  New  Agent      ║");
        System.out.println("╚══════════════════════════════════╝");

        String firstName = InputUtils.readString("First Name: ");
        String lastName  = InputUtils.readString("Last Name: ");
        String email     = InputUtils.readString("Email: ");
        String password  = InputUtils.readString("Password: ");

        if (departmentList.isEmpty()) {
            System.out.println(" No department available. Please create one first!");
            return;
        }

        System.out.println("\nChoose the department:");
        for (int i = 0; i < departmentList.size(); i++) {
            System.out.println((i + 1) + " - " + departmentList.get(i).getName());
        }

        int departmentChoice = InputUtils.readInt("Department: ");
        if (departmentChoice < 1 || departmentChoice > departmentList.size()) {
            System.out.println(" Invalid department choice!");
            return;
        }

        int chosenDepaId = departmentList.get(departmentChoice - 1).getId_department();
        System.out.println("department id:"+chosenDepaId);
        // Choose the role
        System.out.println("\nChoose Role:");
        System.out.println("1️⃣  OUVRIER (Worker)");
        System.out.println("2️⃣  RESPONSABLE_DEPARTEMENT (Manager)");
        System.out.println("3️⃣  DIRECTEUR (Director)");

        int roleChoice = InputUtils.readInt("Enter your choice: ");
        AgentType role;

        switch (roleChoice) {
            case 1 -> role = AgentType.OUVRIER;
            case 2 -> role = AgentType.RESPONSABLE_DEPARTEMENT;
            case 3 -> role = AgentType.DIRECTEUR;
            default -> {
                System.out.println(" Invalid choice! Defaulting to OUVRIER.");
                role = AgentType.OUVRIER;
            }
        }

        // register agent
        authService.register(firstName, lastName, email, password, role, chosenDepaId);

        // show login data
        System.out.println("\n--------------- Auth data ----------------------");
        System.out.println("Email    : | " + email + " |");
        System.out.println("Password : | " + password + " |");
        System.out.println("------------------------------------------------");
    }

    public void directorEditAgent(){
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║          Edit  Agent  Info       ║");
        System.out.println("╚══════════════════════════════════╝");


    }



}
