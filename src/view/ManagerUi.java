package view;

import Utils.InputUtils;
import controller.MainController;
import model.Agent;

public class ManagerUi {
    private MainController controller;

    public ManagerUi(MainController controller){
        this.controller = controller;
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
                case 1:
                    System.out.println("i am the manager ");
                    break;
            }
        }while (choice != 0);
    }
}
