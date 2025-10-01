package view;

import Utils.DateUtils;
import Utils.InputUtils;
import controller.MainController;
import model.Agent;
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
            switch (choice){
               case 1:  System.out.println("\n╔════════════════════════════════════════════════════╗");
                   System.out.println("║                    Add Payment                            ║");
                   System.out.println("╠══════════════════════════════════════════════════════╣");
//                   controller.getAllAgent();
                   /*
                   i  have to get all agents that are in the same manager id
                    */

                   System.out.println("-----Your department agents list-----");
//                    int managerDepartment_id = 0;
                   // get the manager partment id
                    int managerDepartment_id  = auth.getCurrentAgent().getDepartment().getId_department();
                    //filter the agent in the same manager department
                   List<Agent> managerAgents = controller.getAllAgent().stream()
                           .filter(agent -> agent.getDepartment() != null &&
                                   agent.getDepartment().getId_department() == managerDepartment_id)
                           .toList();
//ghj
                    if(!managerAgents.isEmpty()){
                        for (int i = 0; i < managerAgents.size(); i++) {
                            System.out.println((i+1) +">"+ managerAgents.get(i).getFirst_name()+ " - "+managerAgents.get(i).getLast_name());
                        }
                        System.out.println("Enter the number of the agent-> ");
                        int uchoice  =InputUtils.readInt("Enter: ");
                        if(uchoice > 0 && uchoice <= managerAgents.size()){
                            int chosenAgent = managerAgents.get(uchoice - 1).getId();
                            System.out.println("Enter amount: ");
                            Double amount = InputUtils.readDouble("Enter: ");

                            System.out.println("Enter payment type=>");
                            System.out.println("(1) - SALARY");
                            System.out.println("(2) - PRIME");
                            int paytype = InputUtils.readInt("Enter: ");
                            PaymentType paymentType1 = (paytype == 1)? PaymentType.SALARY : PaymentType.PRIME;
                            Payment payment = new Payment(0,paymentType1, amount, LocalDate.now(),false,chosenAgent);
                            addPaymentSalaryPrime(payment);

                        }else{
                            System.out.println("invalid choice, try again");
                        }
                    }else{
                        System.out.println("No agent found");
                    }

                break;
                case 2:addPaymentEligible();
                break;
                case 3:
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
    void addPaymentEligible(){

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
