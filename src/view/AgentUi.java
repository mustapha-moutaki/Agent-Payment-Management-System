package view;

import Utils.InputUtils;
import controller.MainController;
import model.Agent;
import model.Department;
import model.Payment;

import java.util.ArrayList;
import java.util.List;

public class AgentUi {
    private MainController controller;


    public AgentUi(MainController controller){
        this.controller = controller;
    }


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
//    public void getAgentList(){
//        List<Agent>agentList = controller.getAllAgent();
//        System.out.println("++++++++++++Agent info++++++++++++++++");
//        for(Agent agent: agentList){
//            System.out.println("<------------------------->");
//            System.out.println("first name: "+ agent.getFirst_name());
//            System.out.println("first name: "+ agent.getLast_name());
//            System.out.println("first name: "+ agent.getEmail());
//            System.out.println("first name: "+ agent.getAgent_type().name());// it's enum
//            System.out.println("<------------------------->");
////            System.out.println("first name: "+ agent.getFirst_name());using joins to return his department
//        }
//        System.out.println("+++++++++++++++++++++++++++++++++++");
//    }

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

        // department
        if (agent.getDepartment() != null) {
            System.out.println("Department: " + agent.getDepartment().getName());
        } else {
            System.out.println("Department: No Department");
        }

        // payments
        System.out.println("Payments:");
        if (agent.getPayments().isEmpty()) {
            System.out.println("  No payments yet");
        } else {
            System.out.printf("  %-5s | %-10s | %-8s | %-12s | %-5s\n", "ID", "Type", "Amount", "Date", "Valid");
            for (Payment p : agent.getPayments()) {
                System.out.printf("  %-5d | %-10s | %-8.2f | %-12s | %-5s\n",
                        p.getId(), p.getType().name(), p.getAmount(), p.getDate(), p.getCondition_validated());
            }
        }

        System.out.println("<------------------------->\n");
    }

    System.out.println("+++++++++++++++++++++++++++++++++++");
}




}
