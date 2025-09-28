package view;

import Utils.InputUtils;
import controller.MainController;
import model.Agent;
import model.enums.AgentType;
import security.AuthentificationService;

public class DirectorUi {

    private MainController controller;
    private AuthentificationService authService;

    public DirectorUi(MainController controller, AuthentificationService authService){
        this.controller = controller;
        this.authService = authService;
    }

    public void directorMenu(Agent director){
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║                     Director Menu                       ║");
        System.out.println("╠════════════════════════════════════════════════════════╣");
        System.out.println("║ 1️⃣  Create Department                                   ║");
        System.out.println("║ 2️⃣  Update Department                                   ║"); // /edit department
        System.out.println("║ 3️⃣  Delete Department                                   ║");
        System.out.println("║ 4️⃣  Assign Manager to Department                        ║");
        System.out.println("║ 5️⃣  Add Agent to any Department                          ║");
        System.out.println("║ 6️⃣  Add Manager to any Department                        ║");
        System.out.println("║ 7️⃣  Assign Agent to a Department                         ║");
        System.out.println("║ 8️⃣  Display All Departments                               ║");
        System.out.println("║ 9️⃣  Display All Agents                                    ║");
        System.out.println("║ 🔟  Display Payment Statistics per Department & Agent    ║");
        System.out.println("║    - Total payments per department                        ║");
        System.out.println("║    - Identify agent with highest total payments          ║");
        System.out.println("║ 0️⃣  Logout                                              ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
    }


    private void addAgent(){
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║          Create  New  Agent       ║");
        System.out.println("╚══════════════════════════════════╝");

        String firstName = InputUtils.readString("First Name: ");
        String lastName  = InputUtils.readString("Last Name: ");
        String email     = InputUtils.readString("Email: ");
        String password  = InputUtils.readString("Password: ");

        // chose the role
        System.out.println("\nChoose Role:");
        System.out.println("1️⃣  OUVRIER (Worker)");
        System.out.println("2️⃣  RESPONSABLE_DEPARTEMENT (Manager)");
        System.out.println("3️⃣  DIRECTEUR (Director)");

        int roleChoice = InputUtils.readInt("Enter your choice: ");
        model.enums.AgentType role;

        switch (roleChoice) {
            case 1 -> role = model.enums.AgentType.OUVRIER;
            case 2 -> role = model.enums.AgentType.RESPONSABLE_DEPARTEMENT;
            case 3 -> role = AgentType.DIRECTEUR;
            default -> {
                System.out.println("Invalid choice! Defaulting to OUVRIER.");
                role = model.enums.AgentType.OUVRIER;
            }
        }

        authService.register(firstName, lastName, email, password, role);

    }
}
