package UI;

import Service.PartenaireService;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {

    private static final Scanner scanner = new Scanner(System.in);
    public static PartenaireService partenaireService = new PartenaireService();



    public static void menu() throws ClassNotFoundException, InterruptedException, SQLException {
        while (true) {
            System.out.println();
            System.out.println(" -----------------------------");
            System.out.println("|                             |");
            System.out.println("|           EcoMove           |");
            System.out.println("|  1 : Gestion du partenaire  |");
            System.out.println("|  2 : Gestion Du Contrats    |");
            System.out.println("|  3 : Gestion Du Offres      |");
            System.out.println("|  4 : Gestion Du Billets     |");
            System.out.println("|                             |");
            System.out.println(" -----------------------------");
            System.out.println();
            System.out.print("Enter Your Choice : ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                        choicegestionDupartner();
                    break;
                case 2:
                        choicegestionDucontrats();
                    break;
                case 3:
                    choicegestionDuOffres();
                    break;
                case 4:
                    choicegestionBillet();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    /*

██████╗░░█████╗░██████╗░████████╗███╗░░██╗███████╗██████╗░  ░█████╗░██████╗░███████╗░█████╗░
██╔══██╗██╔══██╗██╔══██╗╚══██╔══╝████╗░██║██╔════╝██╔══██╗  ██╔══██╗██╔══██╗██╔════╝██╔══██╗
██████╔╝███████║██████╔╝░░░██║░░░██╔██╗██║█████╗░░██████╔╝  ███████║██████╔╝█████╗░░███████║
██╔═══╝░██╔══██║██╔══██╗░░░██║░░░██║╚████║██╔══╝░░██╔══██╗  ██╔══██║██╔══██╗██╔══╝░░██╔══██║
██║░░░░░██║░░██║██║░░██║░░░██║░░░██║░╚███║███████╗██║░░██║  ██║░░██║██║░░██║███████╗██║░░██║
╚═╝░░░░░╚═╝░░╚═╝╚═╝░░╚═╝░░░╚═╝░░░╚═╝░░╚══╝╚══════╝╚═╝░░╚═╝  ╚═╝░░╚═╝╚═╝░░╚═╝╚══════╝╚═╝░░╚═╝
     */



    public static void choicegestionDupartner() throws ClassNotFoundException, InterruptedException, SQLException {

        boolean check = false;


        while (check == false) {

            System.out.println();
            System.out.println(" -----------------------------");
            System.out.println("|                             |");
            System.out.println("|      Gestion Partenaire     |");
            System.out.println("|  1 : Add Partenaire         |");
            System.out.println("|  2 : Modifier Partenaire    |");
            System.out.println("|  3 : Remove Partenaire      |");
            System.out.println("|  4 : Find a Partenaire      |");
            System.out.println("|  5 : List All Partenaires   |");
            System.out.println("|  6 : Return                 |");
            System.out.println("|                             |");
            System.out.println(" -----------------------------");
            System.out.print("Enter Your Choice : ");
            int choice = scanner.nextInt();
            scanner.nextLine();




            switch (choice) {
                case 1:
                    partenaireService.addpartenaire();
                    break;
                case 2:
                    partenaireService.updatepartenaire();
                    break;
                case 3:
                    partenaireService.deletepartenaire();
                    break;
                case 4:
                    partenaireService.displayPartenaire();
                    break;
                case 5:
                    partenaireService.displayAllPartenaire();
                    break;

                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }


        }


    }



  /*

░█████╗░░█████╗░███╗░░██╗████████╗██████╗░░█████╗░████████╗  ░█████╗░██████╗░███████╗░█████╗░
██╔══██╗██╔══██╗████╗░██║╚══██╔══╝██╔══██╗██╔══██╗╚══██╔══╝  ██╔══██╗██╔══██╗██╔════╝██╔══██╗
██║░░╚═╝██║░░██║██╔██╗██║░░░██║░░░██████╔╝███████║░░░██║░░░  ███████║██████╔╝█████╗░░███████║
██║░░██╗██║░░██║██║╚████║░░░██║░░░██╔══██╗██╔══██║░░░██║░░░  ██╔══██║██╔══██╗██╔══╝░░██╔══██║
╚█████╔╝╚█████╔╝██║░╚███║░░░██║░░░██║░░██║██║░░██║░░░██║░░░  ██║░░██║██║░░██║███████╗██║░░██║
░╚════╝░░╚════╝░╚═╝░░╚══╝░░░╚═╝░░░╚═╝░░╚═╝╚═╝░░╚═╝░░░╚═╝░░░  ╚═╝░░╚═╝╚═╝░░╚═╝╚══════╝╚═╝░░╚═╝
     */


    public static void choicegestionDucontrats() throws ClassNotFoundException, InterruptedException {


        boolean check = false;

        while(check == false) {
            System.out.println();
            System.out.println(" -----------------------------");
            System.out.println("|                             |");
            System.out.println("|       Gestion Contracts     |");
            System.out.println("|  1 : Add Contract           |");
            System.out.println("|  2 : Modifier Contract      |");
            System.out.println("|  3 : Remove Contract        |");
            System.out.println("|  4 : Find a Contract        |");
            System.out.println("|  5 : List All Contracts     |");
            System.out.println("|  6 : Return                 |");
            System.out.println("|                             |");
            System.out.println(" -----------------------------");
            System.out.print("Enter Your Choice : ");
            int choice = scanner.nextInt();
            scanner.nextLine();



            switch (choice) {
                case 1:

                    break;

                case 2:

                    break;

                case 3:

                    break;

                case 4:

                    break;

                case 5:

                    break;



                case 6:
                    return;

                default:
                    System.out.println("Invalid Choice , Please try again ");
            }
        };

    }


         /*



░█████╗░███████╗███████╗██████╗░███████╗░██████╗
██╔══██╗██╔════╝██╔════╝██╔══██╗██╔════╝██╔════╝
██║░░██║█████╗░░█████╗░░██████╔╝█████╗░░╚█████╗░
██║░░██║██╔══╝░░██╔══╝░░██╔══██╗██╔══╝░░░╚═══██╗
╚█████╔╝██║░░░░░██║░░░░░██║░░██║███████╗██████╔╝
░█████╗░███████╗███████╗██████╗░███████╗░██████╗

░██████╗██████╗░███████╗░█████╗░██╗░█████╗░██╗░░░░░███████╗░██████╗
██╔════╝██╔══██╗██╔════╝██╔══██╗██║██╔══██╗██║░░░░░██╔════╝██╔════╝
╚█████╗░██████╔╝█████╗░░██║░░╚═╝██║███████║██║░░░░░█████╗░░╚█████╗░
░╚═══██╗██╔═══╝░██╔══╝░░██║░░██╗██║██╔══██║██║░░░░░██╔══╝░░░╚═══██╗
██████╔╝██║░░░░░███████╗╚█████╔╝██║██║░░██║███████╗███████╗██████╔╝
╚═════╝░╚═╝░░░░░╚══════╝░╚════╝░╚═╝╚═╝░░╚═╝╚══════╝╚══════╝╚═════╝░

     */


    public static void choicegestionDuOffres() throws ClassNotFoundException {

        boolean check = false;

        while(check == false) {
            System.out.println();
            System.out.println(" -----------------------------");
            System.out.println("|                             |");
            System.out.println("|       Gestion Offres        |");
            System.out.println("|  1 : Add Offres             |");
            System.out.println("|  2 : Modifier Offre         |");
            System.out.println("|  3 : Remove Offre           |");
            System.out.println("|  4 : Find an offre          |");
            System.out.println("|  5 : List All Offers        |");
            System.out.println("|  6 : Return                 |");
            System.out.println("|                             |");
            System.out.println(" -----------------------------");
            System.out.print("Enter Your Choice : ");
            int choice = scanner.nextInt();
            scanner.nextLine();



            switch (choice) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:
                    return;

                default:
                    System.out.println("Invalid Choice , Please try again ");
            }
        }






    }




    public static void choicegestionBillet() throws SQLException, ClassNotFoundException, InterruptedException {

        boolean check = false;


        while (check == false) {

            System.out.println();
            System.out.println(" -----------------------------");
            System.out.println("|                             |");
            System.out.println("|      Gestion Billets        |");
            System.out.println("|  1 : Add Billet             |");
            System.out.println("|  2 : Modifier Billet        |");
            System.out.println("|  3 : Remove Billet          |");
            System.out.println("|  4 : Find a Billet          |");
            System.out.println("|  5 : List All Billets       |");
            System.out.println("|  6 : Return                 |");
            System.out.println("|                             |");
            System.out.println(" -----------------------------");
            System.out.print("Enter Your Choice : ");
            int choice = scanner.nextInt();
            scanner.nextLine();




            switch (choice) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;

                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }


        }


    }





}
