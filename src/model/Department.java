package model;

public class Department {
    private int id;
    private String name;
    private int id_department;
    public Department(int id_department, String name){
        this.id_department = id_department;
        this.name =  name;
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




    public int getId_department() {
        return id_department;
    }

    public void setId_department(int id_department) {
        this.id_department = id_department;
    }

    @Override
        public String toString() {
            return "Department{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
}
