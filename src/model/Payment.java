package model;

import model.enums.PaymentType;

import java.time.LocalDate;

public class Payment {
    private int id;
    private PaymentType type;
    private double amount;
    private LocalDate date;
    private boolean condition_validated = false;
    private int agentId; //fk

    public Payment(int id,PaymentType type, double amount, LocalDate date, boolean condition_validated, int agentId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.condition_validated = condition_validated;
        this.agentId = agentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean getCondition_validated() {
        return condition_validated;
    }

    public void setCondition_validated(boolean condition_validated) {
        this.condition_validated = condition_validated;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        agentId = agentId;
    }

    @Override
    public String toString(){
        return id + " | " + type + " | " + amount + " | " + date + " | " + condition_validated + " | Agent:" + agentId;
    }
}
