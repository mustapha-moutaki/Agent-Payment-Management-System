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
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ManagerUi {
    private MainController controller;
    private AuthentificationService auth;
    Scanner sc = new Scanner(System.in);


    public ManagerUi(MainController controller, AuthentificationService auth){
        this.controller = controller;
        this.auth = auth;
    }

    public void managerMenu(Agent manager){
        Agent current = auth.getCurrentAgent();
        int choice;
        do {
            System.out.println("\n╔════════════════════════════════════════════════════════╗");
            System.out.println("║                     Manager Menu                         ║");
            System.out.println("╠══════════════════════════════════════════════════════════╣");
            System.out.println("║ 1️⃣  Add Payment (SALARY / PRIME) to any agent            ║");
            System.out.println("║ 2️⃣  Add Payment (BONUS / INDEMNITE) if eligible          ║");
            System.out.println("║ 3️⃣  Add Agent to your Department                         ║");
            System.out.println("║ 4️⃣  Delete Agent from your Department                    ║");
            System.out.println("║ 5️⃣  Update Agent Info in your Department                 ║");// not yet
            System.out.println("║ 6️⃣  View Department Payments                             ║");
            System.out.println("║ 7️⃣  Filter Payments by Type / Date- / Amount             ║");
            System.out.println("║ 8️⃣  View Payments per Agent                              ║");
            System.out.println("║ 9️⃣  View Statistics for Department                       ║");
            System.out.println("║ 0️⃣  Logout                                               ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝");

            choice = InputUtils.readInt("Enter your choice: ");

            switch (choice){
                case 1:
                    System.out.println("\n╔════════════════════════════════════════════════════╗");
                    System.out.println("║                    Add Payment                     ║");
                    System.out.println("╠════════════════════════════════════════════════════╣");

                    int managerDepartment_id = auth.getCurrentAgent().getDepartment().getId_department();

                    // filter agents
                    List<Agent> managerAgents = controller.getAllAgent().stream()
                            .filter(a -> a.getDepartment() != null &&
                                    a.getDepartment().getId_department() == managerDepartment_id)
                            .toList();

                    if(!managerAgents.isEmpty()){
                        System.out.println("-----Your department agents list-----");
                        for (int i = 0; i < managerAgents.size(); i++) {
                            System.out.println((i+1) + " > " + managerAgents.get(i).getFirst_name() + " " + managerAgents.get(i).getLast_name()+"["+managerAgents.get(i).getAgent_type() + "]");
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
                        System.out.println((i+1) + "> " + managerAgents1.get(i).getFirst_name() + " " + managerAgents1.get(i).getLast_name()+ "[" +managerAgents1.get(i).getAgent_type() +"]");
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
                    }else{
                        Department chosenDept = departments.get(deptChoice-1);


                        chosenAgent1.setDepartment(chosenDept);
                        Boolean isSuccess = addAgentToDepartment(chosenAgent1,chosenDept);
                        if(isSuccess){
                            System.out.println("Agent assigned to department successfully");
                        }else{
                            System.out.println("Error");
                        }
                    }


                break;
                case 4:
                    System.out.println("\n----- Remove Agent From Department ---------");


                    int managerDeptId = auth.getCurrentAgent().getDepartment().getId_department();


                    List<Agent> managerAgents3 = controller.getAllAgent().stream()
                            .filter(a -> a.getDepartment() != null &&
                                    a.getDepartment().getId_department() == managerDeptId)
                            .toList();

                    if(managerAgents3.isEmpty()){
                        System.out.println("No agents found in your department.");
                        break;
                    }


                    System.out.println("-----Your Department Agents-----");
                    for(int i = 0; i < managerAgents3.size(); i++){
                        System.out.println((i+1) + " > " + managerAgents3.get(i).getFirst_name() + " " + managerAgents3.get(i).getLast_name());
                    }


                    int agentChoice1 = InputUtils.readInt("Select agent to remove: ");

                    if(agentChoice1 <= 0 || agentChoice1 > managerAgents3.size()){
                        System.out.println("Invalid choice");
                        break;
                    }

                    Agent chosenAgent2 = managerAgents3.get(agentChoice1-1);

                    System.out.println("agnt is removed id: "+ chosenAgent2.getId());
                    boolean isRemoved = removeAgentFromDepartment(chosenAgent2.getId());

                    if(isRemoved){
                        System.out.println("Agent removed from department successfully.");
                    } else {
                        System.out.println("Failed to remove agent from department.");
                    }
                    break;

                case 5:
                    updateAgentInfoDepartment();
                break;
                case 6: viewDepartmentPayments();// done
                break;
                case 7: filterPayments();// done
                break;
                case 8: viewPaymentsPerAgent();//done
                break;
                case 9: statistics();
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
    Boolean  addAgentToDepartment(Agent agent, Department department){
        return controller.assignManagerToDepartment(agent, department);
    }

    // 4- remove agent from my department
    Boolean removeAgentFromDepartment(int id){
        return controller.removeAgentFromDepartment(id);
    }

    // 5- update agent info of my department
    void updateAgentInfoDepartment(){

    }

    // 6- viewDepartmentPayments
    void viewDepartmentPayments(){
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║                     Departments payments list             ║");
        System.out.println("════════════════════════════════════════════════════════");

       Map<Department, Double> departmentPaymentList =  controller.getTotalPaymentsByDepartment();
        System.out.println("--------------------------------------");
       for(Map.Entry<Department, Double> entry : departmentPaymentList.entrySet()){
        Department depar = entry.getKey();
        Double total = entry.getValue();
           System.out.println("Department      |" + depar.getName());
           System.out.println("total Payments: |"+total);
           System.out.println("--------------------------------------");
       }
    }

    // 7- managePayments by list
    void managePayments(){

    }

    // 8- filter
    void filterPayments(){
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
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
                int schoice = InputUtils.readInt("Enter: ");

                switch (schoice){
                    case 1:
                        System.out.println("---------Type = Salary-------------");
                        List<Payment>paymentsSalary = controller.typeFiltredPaymentsList("Salary");
                        for(Payment pay : paymentsSalary){
                            System.out.println("-------");
                            System.out.println("payment amount: "+ pay.getAmount());
                            System.out.println("payment date: "+ pay.getDate());
                        }
                        System.out.println("-----------------------------------");
                    break;
                    case 2:
                        System.out.println("---------Type = Prime-------------");
                        List<Payment>paymentsPrime=controller.typeFiltredPaymentsList("Prime");
                        for(Payment pay : paymentsPrime){
                            System.out.println("-------");
                            System.out.println("payment amount: "+ pay.getAmount());
                            System.out.println("payment date: "+ pay.getDate());
                        }
                        System.out.println("-----------------------------------");
                    break;
                    case 3: System.out.println("---------Type = Bonus-------------");
                        List<Payment>paymentsBonus =  controller.typeFiltredPaymentsList("Bonus");
                        for(Payment pay : paymentsBonus){
                            System.out.println("-------");
                            System.out.println("payment amount: "+ pay.getAmount());
                            System.out.println("payment date: "+ pay.getDate());
                        }
                        System.out.println("-----------------------------------");
                    break;
                    case 4: System.out.println("---------Type = Indemnite-------------");
                        List<Payment>paymentsIndemnite= controller.typeFiltredPaymentsList("Indemnite");
                        for(Payment pay : paymentsIndemnite){
                            System.out.println("-------");
                            System.out.println("payment amount: "+ pay.getAmount());
                            System.out.println("payment date: "+ pay.getDate());
                        }
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
                List<Payment> paymentListAmounts = controller.amountFiltredPaymentsList(minMount, maxMount);
                for(Payment p : paymentListAmounts){
                    System.out.println("-------");
                    System.out.println("payment amount: "+ p.getAmount());
                    System.out.println("payment date: "+ p.getDate());
                }
                System.out.println("-----------------------------------");
            break;
            case 3:System.out.println("╠═════════════════║ Filter by Date ║═════════════════╣");
                String inputDate = InputUtils.readString("Enter Date (dd/MM/yyyy): ");
                sc.nextLine();
                System.err.println(" befor paring");
                Date date = DateUtils.parse(inputDate);
                System.err.println("after parsing");
                if (date != null) {
                    List<Payment> datePaymentList = controller.dateFiltredPaymentsList(date);
                    for(Payment p : datePaymentList){
                        System.out.println("-------");
                        System.out.println("payment amount: "+ p.getAmount());
                        System.out.println("payment date: "+ p.getDate());
                    }
                }

                break;
            case 0: System.exit(0);
            default:
                System.out.println("Invalid choice, try again pls");
        }
    }

    // 9- view payment per single agent in dp
    void viewPaymentsPerAgent() {
        System.out.println("╠═════════════════║Payment list per agent║═════════════════╣");

        List<Agent> agents = controller.getAllAgent();
        List<Payment> payments = controller.paymentList(); // كل الـPayments

        for (Agent agent : agents) {
            System.out.println("Agent: " + agent.getFirst_name() + " " + agent.getLast_name());

            List<Payment> agentPayments = payments.stream()
                    .filter(p -> p.getAgentId() == agent.getId())
                    .toList();

            if (agentPayments.isEmpty()) {
                System.out.println("  No payments found.");
            } else {
                for (Payment pay : agentPayments) {
                    System.out.println("  Amount: " + pay.getAmount() + ", Type: " + pay.getType() + ", Date: " + pay.getDate());
                }
            }
            System.out.println("----------------------------");
        }
    }


    // 10-stats
    void statistics() {
        System.out.println("╠═════════════════║ Statistics ║═════════════════╣");
        System.out.println("Enter your choice ");
        System.out.println("1 - total payment per department");
        System.out.println("2 - average payments per agent");
        int ichoice = InputUtils.readInt("enter: ");
        switch (ichoice) {
            case 1:
                viewDepartmentPayments();
                break;
            case 2:
                System.out.println("╠═════════════════║ Statistics ║═════════════════╣");
                System.out.println("Enter your choice ");
                System.out.println("1 - Total payment per department");
                System.out.println("2 - Average payments per agent");
                int ichoice1 = InputUtils.readInt("Enter: ");

                Agent current = auth.getCurrentAgent();
                int managerDeptId = current.getDepartment().getId_department();

                switch (ichoice1) {
                    case 1:
                        viewDepartmentPayments();
                        break;

                    case 2:
                        List<Agent> agentList = controller.getAllAgent();
                        List<Payment> payments = controller.paymentList();

                        System.out.println("╠═════════════════║ Average payments per agent ║═════════════════╣");

                        for (Agent agent : agentList) {
                            // get all agent in the manager department
                            if (agent.getDepartment() != null && agent.getDepartment().getId_department() == managerDeptId) {
                                double sum = 0;
                                int count = 0;


                                for (Payment p : payments) {
                                    if (p.getAgentId() == agent.getId()) {
                                        sum += p.getAmount();
                                        count++;
                                    }
                                }

                                double avg = 0;
                                if (count > 0) {
                                    avg = sum / count;
                                }

                                System.out.println("Agent: " + agent.getFirst_name() + " " + agent.getLast_name() +
                                        " | Average Payment: " + avg);
                            }
                        }
                        break;

                    default:
                        System.out.println("Invalid choice");
                }

        }

    }
}
