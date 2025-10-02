package view;

import Utils.InputUtils;
import controller.MainController;
import model.Agent;
import model.Payment;
import security.AuthentificationService;

import java.util.List;

public class AgentUi {

    private AuthentificationService auth;   // Service to handle login
    private MainController controller;      // Controller for business logic

    // ===== Constructor =====
    public AgentUi(MainController controller) {
        this.controller = controller;
//        this.auth = auth;
    }

    // ===== Menu for logged-in Agent =====
    public void agentMenu(Agent agent) {

        Agent currentAgent = controller.authAgent();
        int choice;

        do {
            System.out.println("\n╔════════════════════════════════════════════════════════╗");
            System.out.println("║                     Agent Menu                         ║");
            System.out.println("╠════════════════════════════════════════════════════════╣");
            System.out.println("║ 1️⃣  View my personal info                              ║");
            System.out.println("║ 2️⃣  View my payments                                   ║");
            System.out.println("║ 3️⃣  View total payments                                ║");
            System.out.println("║ 0️⃣  Logout                                             ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");

            choice = InputUtils.readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> showPersonalInfo(currentAgent);
                case 2 -> viewPayments(currentAgent);
                case 3 -> calculateTotalPayments(currentAgent);
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    // ===== CRUD Methods =====
    public void addAgent(Agent agent) {
        controller.addAgent(agent);
    }

    public void deleteAgent(int id) {
        controller.deleteAgent(id);
    }

    public void updateAgent(Agent agent) {
        controller.updateAgent(agent);
    }

    public Agent getAgentById(int id) {
        return controller.getAgentById(id);
    }

    // ===== Helper Methods =====
    private void showPersonalInfo(Agent agent) {
        System.out.println("\n--- Personal Info ---");
        System.out.println("Full Name: " + agent.getFirst_name() + " " + agent.getLast_name());
        System.out.println("Email: " + agent.getEmail());
        System.out.println("Agent type: " + agent.getAgent_type());
        if (agent.getDepartment() != null) {
            System.out.println("Department: " + agent.getDepartment().getName());
        }
    }

    private void viewPayments(Agent agent) {

        List<Payment> payments = controller.paymentList().stream()
                .filter(p -> p.getAgentId() == agent.getId()).toList();
        if (payments == null || payments.isEmpty()) {
            System.out.println("No payments yet.");
        } else {
            System.out.println("\n--- Payments History ---");
            for(Payment payment: payments){
                System.out.println("|----------------------⩒---------------------------|");
                System.out.println("| payment type:   | "+"| "+payment.getType().name()+"  |");
                System.out.println("| payment amount: | "+"| "+payment.getAmount() + "MAD"+"  |");
                System.out.println("| payment date:   | "+"| "+payment.getDate()+"  |");
            }
            System.out.println("|-------------------------------------------------|");
        }
    }

    private void calculateTotalPayments(Agent agent) {
        double paymentsTotal = controller.paymentList().stream()
                .filter(p -> p.getAgentId() == agent.getId()).mapToDouble(Payment::getAmount).sum();

        System.out.println("-------------------------------");
        System.out.println("Total Payments: =>"+paymentsTotal+" MAD");
        System.out.println("--------------------------------");

    }
}
