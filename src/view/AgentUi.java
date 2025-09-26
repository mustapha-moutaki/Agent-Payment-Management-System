package view;

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
    public void getAgentList(){
        List<Agent>agentList = controller.getAllAgent();
        System.out.println("++++++++++++Agent info++++++++++++++++");
        for(Agent agent: agentList){
            System.out.println("<------------------------->");
            System.out.println("first name: "+ agent.getFirst_name());
            System.out.println("first name: "+ agent.getLast_name());
            System.out.println("first name: "+ agent.getEmail());
            System.out.println("first name: "+ agent.getAgent_type().name());// it's enum
            System.out.println("<------------------------->");
//            System.out.println("first name: "+ agent.getFirst_name());using joins to return his department
        }
        System.out.println("+++++++++++++++++++++++++++++++++++");
    }



}
