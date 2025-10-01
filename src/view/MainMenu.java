package view;

import Utils.DateUtils;
import Utils.InputUtils;
import Utils.PasswordUtils;
import dao.AgentDAO;
import model.Agent;
import model.enums.AgentType;
import security.AuthentificationService;
import controller.MainController;
public class MainMenu {
    private AgentDAO agentDAO;
    private AuthentificationService authService;
    private MainController controller;



    public MainMenu(AgentDAO agentDAO, AuthentificationService authService, MainController controller){
        this.agentDAO = agentDAO;
        this.authService = authService;
        this.controller = controller;
    }

    int choice;
    public void start(){

        do{
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║      Welcome to Agent Payment    ║");
            System.out.println("║            System                ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  1️⃣  Login                       ║");
            System.out.println("║  0️⃣  Exit                        ║");
            System.out.println("╚══════════════════════════════════╝");
            choice = InputUtils.readInt("Enter your choice: ");
            switch (choice){
                case 1: login();
                    break;
                case 0: System.out.println("good by");
                        System.exit(0);
                default:
                    System.out.println("invalid choice ");
            }


        }while (choice != 0);
    }


    private void login() {
        boolean success = false;

        do {
            System.out.println("== Please enter your info ==");
            String email = InputUtils.readString("Email: ");
            String password = InputUtils.readString("Password: ");
            Agent agentLoggedIn = authService.login(email, password);

            if(agentLoggedIn != null){
                System.out.println("==> Welcome " + agentLoggedIn.getFirst_name()+ "<==");
                success = true;
                switch (agentLoggedIn.getAgent_type()){
                    case OUVRIER -> new AgentUi(controller).agentMenu(agentLoggedIn);
                    case RESPONSABLE_DEPARTEMENT -> new ManagerUi(controller, authService).managerMenu(agentLoggedIn);
                    case DIRECTEUR -> new DirectorUi(controller, authService).directorMenu(agentLoggedIn);
                }
            } else {
                System.out.println("↪ Invalid email or password. Try again.");
            }
        } while(!success);
    }



}
