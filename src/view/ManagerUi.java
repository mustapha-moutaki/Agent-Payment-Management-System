package view;

import Utils.DateUtils;
import Utils.InputUtils;
import controller.MainController;
import model.Agent;
import model.Department;
import model.Payment;
import model.enums.AgentType;
import model.enums.PaymentType;
import security.AuthentificationService;

import javax.swing.text.Utilities;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

public class ManagerUi {
    private MainController controller;
    private AuthentificationService auth;
    public ManagerUi(MainController controller, AuthentificationService auth){
        this.controller = controller;
        this.auth = auth;
    }

    public void managerMenu(Agent manager){
        int choice;
        do {
            System.out.println("\n╔════════════════════════════════════════════════════════╗");
            System.out.println("║                     Manager Menu                         ║");
            System.out.println("╠══════════════════════════════════════════════════════════╣");
            System.out.println("║ 1️⃣  Add Payment (SALARY / PRIME) to any agent            ║");
            System.out.println("║ 2️⃣  Add Payment (BONUS / INDEMNITE) if eligible          ║");
            System.out.println("║ 3️⃣  Add Agent to your Department                         ║");
            System.out.println("║ 4️⃣  Delete Agent from your Department                    ║");
            System.out.println("║ 5️⃣  Update Agent Info in your Department                 ║");
            System.out.println("║ 6️⃣  View Department Payments                             ║");
            System.out.println("║ 7️⃣  Edit or Delete Payments if needed                    ║");
            System.out.println("║ 8️⃣  Filter Payments by Type / Date / Amount              ║");
            System.out.println("║ 9️⃣  View Payments per Agent in the Department            ║");
            System.out.println("║ 🔟  View Statistics for Department                       ║");
            System.out.println("║    - Total payments per agent                            ║");
            System.out.println("║    - Total payments for the department                   ║");
            System.out.println("║    - Average payments per agent                          ║");
            System.out.println("║    - Rank agents by total payments                       ║");
            System.out.println("║ 0️⃣  Logout                                               ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝");

            choice = InputUtils.readInt("Enter your choice: ");
            InputUtils.readString("");
            switch (choice){
                case 1:
                    System.out.println("\n╔════════════════════════════════════════════════════╗");
                    System.out.println("║                    Add Payment                     ║");
                    System.out.println("╠════════════════════════════════════════════════════╣");


                    System.out.println("-----All Agents in System-----");
                    controller.getAllAgent().forEach(a ->
                            System.out.println(a.getFirst_name() + " - " +
                                    (a.getDepartment() != null ? a.getDepartment().getId_department() : "null"))
                    );
                    System.out.println("here we check");


                    Agent current = auth.getCurrentAgent();
                    System.out.println("Current agent: " + current.getFirst_name() + " - department id: " +
                            (current.getDepartment() != null ? current.getDepartment().getId_department() : "null"));
                    int managerDepartment_id = auth.getCurrentAgent().getDepartment().getId_department();

                    // filter agents
                    List<Agent> managerAgents = controller.getAllAgent().stream()
                            .filter(a -> a.getDepartment() != null &&
                                    a.getDepartment().getId_department() == managerDepartment_id)
                            .toList();

                    if(!managerAgents.isEmpty()){
                        System.out.println("-----Your department agents list-----");
                        for (int i = 0; i < managerAgents.size(); i++) {
                            System.out.println((i+1) + " > " + managerAgents.get(i).getFirst_name() + " - " + managerAgents.get(i).getLast_name());
                        }


                        int uchoice = InputUtils.readInt("Enter the number of the agent-> ");
                        if(uchoice > 0 && uchoice <= managerAgents.size()){
                            int chosenAgent = managerAgents.get(uchoice - 1).getId();


                            Double amount = InputUtils.readDouble("Enter amount: ");


                            System.out.println("Enter payment type:");
                            System.out.println("(1) - SALARY");
                            System.out.println("(2) - PRIME");
                            int paytype = InputUtils.readInt("Enter: ");
                            PaymentType paymentType1 = (paytype == 1) ? PaymentType.SALARY : PaymentType.PRIME;


                            Payment payment = new Payment(0, paymentType1, amount, LocalDate.now(), false, chosenAgent);
                            addPaymentSalaryPrime(payment);
                            System.out.println("Payment added successfully!");

                        } else {
                            System.out.println("Invalid choice, try again");
                        }
                    } else {
                        System.out.println("No agent found in your department.");
                    }
                    break;

                case 2:System.out.println("\n╔════════════════════════════════════════════════════╗");
                    System.out.println("║                    Add Payment egligible                   ║");
                    System.out.println("╠══════════════════════════════════════════════════════╣");
                    System.out.println("-----Your department agents list-----");

                    int managerDepartmentId = auth.getCurrentAgent().getDepartment().getId_department();

                    List<Agent> managerAgents1 = controller.getAllAgent().stream()
                            .filter(agent -> agent.getDepartment() != null &&
                                    agent.getDepartment().getId_department() == managerDepartmentId)
                            .toList();

                    if(managerAgents1.isEmpty()){
                        System.out.println("No agent found");
                        return;
                    }

                    for (int i = 0; i < managerAgents1.size(); i++) {
                        System.out.println((i+1) + "> " + managerAgents1.get(i).getFirst_name() + " - " + managerAgents1.get(i).getLast_name());
                    }

                    int uchoice = InputUtils.readInt("Enter the number of the agent-> ");
                    if(uchoice <= 0 || uchoice > managerAgents1.size()){
                        System.out.println("Invalid choice, try again");
                        return;
                    }

                    int chosenAgent = managerAgents1.get(uchoice - 1).getId();

                    double amount = InputUtils.readDouble("Enter amount: ");

                    System.out.println("Enter payment type:");
                    System.out.println("(1) - BONUS");
                    System.out.println("(2) - INDEMNITE");
                    int paytype = InputUtils.readInt("Enter: ");

                    PaymentType paymentType;
                    if(paytype == 1){
                        paymentType = PaymentType.BONUS;
                    } else if(paytype == 2){
                        paymentType = PaymentType.INDEMNITE;
                    } else {
                        System.out.println("Invalid payment type");
                        return;
                    }


                    Boolean eligible = controller.isEligible(chosenAgent, paymentType);

                    if(eligible){
                        Payment payment = new Payment(0, paymentType, amount, LocalDate.now(), true, chosenAgent);

                        Boolean isSuccessed = addPaymentEligible(payment);
                        if(isSuccessed){
                            System.out.println("Payment added successfully!");
                        } else {
                            System.out.println(" Payment could not be added. Try again.");
                        }
                    } else {
                        System.out.println(" Agent not eligible for this payment (needs at least 6 previous payments).");
                    }

                break;
                case 3:System.out.println("\n╔════════════════════════════════════════════════════╗");
                    System.out.println("║                    Add Agent to department           ║");
                    System.out.println("╠══════════════════════════════════════════════════════╣");
                    List<Agent> unassignedWorkers = controller.getAllAgent().stream()
                            .filter(a -> a.getAgent_type() == AgentType.OUVRIER && a.getDepartment() == null)
                            .toList();

                    if(unassignedWorkers.isEmpty()){
                        System.out.println("No unassigned agents found");
                        return;
                    }

                    // display agentsf
                    System.out.println("-----Unassigned Agents List-----");
                    for(int i=0; i<unassignedWorkers.size(); i++){
                        System.out.println((i+1) + " > " + unassignedWorkers.get(i).getFirst_name() + " " + unassignedWorkers.get(i).getLast_name());
                    }

                    int agentChoice = InputUtils.readInt("Select agent to assign: ");
                    if(agentChoice <= 0 || agentChoice > unassignedWorkers.size()){
                        System.out.println("Invalid choice");
                        return;
                    }

                    Agent chosenAgent1 = unassignedWorkers.get(agentChoice-1);

                    // display all departments
                    List<Department> departments = controller.DepartmentList();
                    if(departments.isEmpty()){
                        System.out.println("No departments available");
                        return;
                    }

                    //display departments
                    System.out.println("-----Departments List-----");
                    for(int i=0; i<departments.size(); i++){
                        System.out.println((i+1) + " > " + departments.get(i).getName());
                    }

                    int deptChoice = InputUtils.readInt("Select department to assign agent: ");
                    if(deptChoice <= 0 || deptChoice > departments.size()){
                        System.out.println("Invalid choice");
                        return;
                    }

                    Department chosenDept = departments.get(deptChoice-1);

                    // 5- تعيين القسم وحفظ التحديث
                    chosenAgent1.setDepartment(chosenDept);
                    addAgentToDepartment();
                break;
                case 4: removeAgentFromDepartment();
                break;
                case 5: updateAgentInfoDepartment();
                break;
                case 6: viewDepartmentPayments();
                break;
                case 7: managePayments();
                case 8: filterPayments();
                break;
                case 9: viewPaymentsPerAgent();
                break;
                case 10: statistics();
                break;
                case 0: System.exit(0);
                default:
                    System.out.println("Invalid choice, try again");
            }
        }while (choice != 0);
    }

    // 1- add salary or prime
    void addPaymentSalaryPrime(Payment payment){
        controller.addPayment(payment);
    }

    // 2- addPaymentEligible
    Boolean addPaymentEligible(Payment payment){
        return controller.addPayment(payment);
    }


    // 3- add agent to department
    void addAgentToDepartment(){

    }

    // 4- remove agent from my department
    void removeAgentFromDepartment(){

    }

    // 5- update agent info of my department
    void updateAgentInfoDepartment(){

    }

    // 6- viewDepartmentPayments
    void viewDepartmentPayments(){

    }

    // 7- managePayments
    void managePayments(){

    }

    // 8- filter
    void filterPayments(){
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║                    Filter                            ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("1- Filter by Type");
        System.out.println("2- Filter by Amount");
        System.out.println("3- Filter by Date");
        System.out.println("Enter your choise: ");
        int choice = InputUtils.readInt("Enter: ");
        switch (choice){
            case 1:
                System.out.println("╠═════════════════║Filter-by-Type║═════════════════╣");
                System.out.println("1 -Salary");
                System.out.println("2 -Prime");
                System.out.println("3 -Bonus");
                System.out.println("4 -Indemnite");
                System.out.println("Enter your choice: ");
                String schoice = InputUtils.readString("Enter: ");

                switch (Integer.parseInt(schoice)){
                    case 1:
                        System.out.println("---------Type = Salary-------------");
                        controller.typeFiltredPaymentsList("Salary");
                        System.out.println("-----------------------------------");
                    break;
                    case 2:
                        System.out.println("---------Type = Prime-------------");
                        controller.typeFiltredPaymentsList("Prime");
                        System.out.println("-----------------------------------");
                    break;
                    case 3: System.out.println("---------Type = Bonus-------------");
                        controller.typeFiltredPaymentsList("Bonus");
                        System.out.println("-----------------------------------");
                    break;
                    case 4: System.out.println("---------Type = Indemnite-------------");
                        controller.typeFiltredPaymentsList("Indemnite");
                        System.out.println("-----------------------------------");
                    break;
                    case 0: System.exit(0);
                    default:
                        System.out.println("Invalid choice");
                }
            break;

            case 2: System.out.println("╠═════════════════║Filter-by-Amount║═════════════════╣");
                double minMount = InputUtils.readDouble("Enter Min: ");
                double maxMount = InputUtils.readDouble("Enter Max: ");
                controller.amountFiltredPaymentsList(minMount, maxMount);
                System.out.println("-----------------------------------");
            break;
            case 3:System.out.println("╠═════════════════║ Filter by Date ║═════════════════╣");
                String inputDate = InputUtils.readString("Enter Date (dd/MM/yyyy): ");

                Date date = DateUtils.parse(inputDate);
                if (date != null) {
                    controller.dateFiltredPaymentsList(date);
                }

                break;
            case 0: System.exit(0);
            default:
                System.out.println("Invalid choice, try again pls");
        }
    }

    // 9- view payment per single agent in dp
    void viewPaymentsPerAgent(){

    }

    // 10-stats
    void statistics(){

    }

}
