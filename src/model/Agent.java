package model;

import model.enums.AgentType;

public class Agent extends Person{
    private AgentType agent_type;
//    private double salary;
    private int id_department;

    public Agent(String first_name, String last_name, String email, String password, AgentType type, int id_department){
        super(first_name, last_name, email, password);
        this.agent_type = type;
//        this.salary = salary;
        this.id_department = id_department;
    }


    public AgentType getAgent_type() {
        return agent_type;
    }

    public void setAgent_type(AgentType agent_type) {
        this.agent_type = agent_type;
    }



        public int getId_department() {
        return id_department;
    }

    public void setId_department(int id_department) {
        this.id_department = id_department;
    }


//    public double getSalary() {
//        return salary;
//    }
//
//    public void setSalary(double salary) {
//        this.salary = salary;
//    }


}
