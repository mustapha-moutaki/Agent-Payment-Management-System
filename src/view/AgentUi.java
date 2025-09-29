package view;

import Utils.InputUtils;
import controller.MainController;
import model.Agent;
import model.Payment;

import java.util.List;

public class AgentUi {
    private MainController controller;

    public AgentUi(MainController controller){
        this.controller = controller;
    }

    // ===== CRUD Methods =====
    public void addAgent(Agent agent){
        controller.addAgent(agent);
    }

    public void deleteAgent(int id){
        controller.deleteAgent(id);
    }

    public void updateAgent(Agent agent){
        controller.updateAgent(agent);
    }

    public Agent getAgentById(int id){
        return controller.getAgent(id);
    }

    public void getAgentList() {
        List<Agent> agentList = controller.getAllAgent();
        System.out.println("++++++++++++ Agent info ++++++++++++");

        for (Agent agent : agentList) {
            System.out.println("<------------------------->");
            System.out.println("ID: " + agent.getId());
            System.out.println("First name: " + agent.getFirst_name());
            System.out.println("Last name: " + agent.getLast_name());
            System.out.println("Email: " + agent.getEmail());
            System.out.println("Agent type: " + agent.getAgent_type().name());

            if (agent.getDepartment() != null) {
                System.out.println("Department: " + agent.getDepartment().getName());
            } else {
                System.out.println("Department: No Department");
            }

            System.out.println("Payments:");
            if (agent.getPayments().isEmpty()) {
                System.out.println("  No payments yet");
            } else {
                System.out.printf("  %-5s | %-10s | %-8s | %-12s | %-5s\n",
                        "ID", "Type", "Amount", "Date", "Valid");
                for (Payment p : agent.getPayments()) {
                    System.out.printf("  %-5d | %-10s | %-8.2f | %-12s | %-5s\n",
                            p.getId(), p.getType().name(), p.getAmount(), p.getDate(), p.getCondition_validated());
                }
            }

            System.out.println("<------------------------->\n");
        }

        System.out.println("+++++++++++++++++++++++++++++++++++");
    }

    // ===== Menu for logged-in Agent =====
    public void agentMenu(Agent agent) {
        int choice;
        do {
            System.out.println("\n╔════════════════════════════════════════════════════════╗");
            System.out.println("║                     Manager Menu                        ║");
            System.out.println("╠════════════════════════════════════════════════════════╣");
            System.out.println("║ 1️⃣  Add Agent to My Department                          ║");
            System.out.println("║ 2️⃣  Update Agent Details                                 ║");
            System.out.println("║ 3️⃣  Delete Agent                                         ║");
            System.out.println("║ 4️⃣  Add Payment to Agent                                  ║");
            System.out.println("║    - Salary & Allowances according to contract           ║");
            System.out.println("║    - Bonus & Compensation if eligible & approved        ║");
            System.out.println("║ 5️⃣  View All Agents in My Department                     ║");
            System.out.println("║ 6️⃣  View Payments of an Agent                             ║");
            System.out.println("║ 7️⃣  Filter/Sort Payments by Type/Amount/Date            ║");
            System.out.println("║ 8️⃣  Identify Unusual or Incorrect Payments              ║");
            System.out.println("║ 9️⃣  Department Statistics                                 ║");
            System.out.println("║    - Total payments for the department                   ║");
            System.out.println("║    - Average salary                                       ║");
            System.out.println("║    - Ranking agents by total payments                    ║");
            System.out.println("║ 0️⃣  Logout                                              ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");


            choice = InputUtils.readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> showPersonalInfo(agent);
                case 2 -> viewPayments(agent);
                case 3 -> calculateTotalPayments(agent);
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    // ===== Helper Methods =====
    private void showPersonalInfo(Agent agent) {
        System.out.println("\n--- Personal Info ---");
        System.out.println("ID: " + agent.getId());
        System.out.println("Name: " + agent.getFirst_name() + " " + agent.getLast_name());
        System.out.println("Email: " + agent.getEmail());
        System.out.println("Agent type: " + agent.getAgent_type());
        if (agent.getDepartment() != null) {
            System.out.println("Department: " + agent.getDepartment().getName());
        }
    }

    private void viewPayments(Agent agent) {
        List<Payment> payments = agent.getPayments();
        if (payments.isEmpty()) {
            System.out.println("No payments yet.");
        } else {
            System.out.println("\n--- Payments History ---");
            for (Payment p : payments) {
                System.out.printf("ID: %d | Type: %s | Amount: %.2f | Date: %s | Valid: %s\n",
                        p.getId(), p.getType(), p.getAmount(), p.getDate(), p.getCondition_validated());
            }
        }
    }

    private void calculateTotalPayments(Agent agent) {
        double total = agent.getPayments().stream()
                .mapToDouble(Payment::getAmount)
                .sum();
        System.out.println("Total Payments: " + total);
    }
}
