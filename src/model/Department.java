package model;

public class Department {
    private int id;
    private String name;
    private int id_manager; // fk

    public Department(String name, int id_manager){
        this.name =  name;
        this.id_manager = id_manager;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_manager() {
        return id_manager;
    }

    public void setId_manager(int id_manager) {
        this.id_manager = id_manager;
    }
}
