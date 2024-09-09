package UI;

import Service.*;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {

    private static final Scanner scanner = new Scanner(System.in);
    public static PartenaireService partenaireService = new PartenaireService();
    public static ContratsService contratsService = new ContratsService();
    public static OffresService offresService = new OffresService();
    public static BilletsService billetsService = new BilletsService();
    public static TrajetService trajetService = new TrajetService();
    public static ReservationService reservationService = new ReservationService();


    public static void menu() throws Exception {
        while (true) {
            System.out.println();
            System.out.println(" -----------------------------");
            System.out.println("|                             |");
            System.out.println("|           EcoMove           |");
            System.out.println("|  1 : Gestion du partenaire  |");
            System.out.println("|  2 : Gestion Du Contrats    |");
            System.out.println("|  3 : Gestion Du Offres      |");
            System.out.println("|  4 : Gestion Du Billets     |");
            System.out.println("|  5 : Gestion Du Trajets     |");
            System.out.println("|  6 : Gestion Du Utilisateur |");
            System.out.println("|  7 : Gestion Du Reservation |");
            System.out.println("|  8 : return                 |");
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
                case 5 :
                    choicegestionTrajets();
                    break;
                case 6 :
                    choicegestionUsers();
                    break;
                case 7 :
                    choicegestionReservation();
                    break;
                    case 8 :
                        return;
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


    public static void choicegestionDucontrats() throws ClassNotFoundException, InterruptedException, SQLException {


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
                    contratsService.addContrat();
                    break;

                case 2:
                    contratsService.updateContrat();
                    break;

                case 3:
                    contratsService.deleteContrat();
                    break;

                case 4:
                    contratsService.getContrat();
                    break;

                case 5:
                    contratsService.getAllContrats();
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


    public static void choicegestionDuOffres() throws ClassNotFoundException, SQLException, InterruptedException {

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
                    offresService.addOffre();
                    break;
                case 2:
                    offresService.updateOffre();
                    break;
                case 3:
                    offresService.deleteOffre();
                    break;
                case 4:
                    offresService.getOffre();
                    break;
                case 5:
                    offresService.getAllOffres();
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
                    billetsService.addBillet();
                    break;
                case 2:
                    billetsService.updateBillet();
                    break;
                case 3:
                    billetsService.deleteBillet();
                    break;
                case 4:
                    billetsService.getBillet();
                    break;
                case 5:
                    billetsService.getAllBillets();
                    break;

                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }


        }


    }


    public static void choicegestionTrajets() throws SQLException , ClassNotFoundException , InterruptedException {
        boolean check = false;


        while (check == false) {

            System.out.println();
            System.out.println(" -----------------------------");
            System.out.println("|                             |");
            System.out.println("|      Gestion Trajets        |");
            System.out.println("|  1 : Add Trajets            |");
            System.out.println("|  2 : Modifier Trajet        |");
            System.out.println("|  3 : Remove Trajet          |");
            System.out.println("|  4 : Find a Trajet          |");
            System.out.println("|  5 : List All Trajets       |");
            System.out.println("|  6 : Return                 |");
            System.out.println("|                             |");
            System.out.println(" -----------------------------");
            System.out.print("Enter Your Choice : ");
            int choice = scanner.nextInt();
            scanner.nextLine();




            switch (choice) {
                case 1:
                    trajetService.addTrajet();
                    break;
                case 2:
                    trajetService.updateTrajet();
                    break;
                case 3:
                   trajetService.deleteTrajet();
                    break;
                case 4:
                    trajetService.gettrajet();
                    break;
                case 5:
                    trajetService.getAllTrajets();
                    break;

                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }


        }
    }


    public static void  choicegestionUsers() throws SQLException , ClassNotFoundException , InterruptedException {
        boolean check = false;


        while (check == false) {

            System.out.println();
            System.out.println(" -----------------------------");
            System.out.println("|                             |");
            System.out.println("|     Gestion Utilisateurs    |");
            System.out.println("|  1 : Modifier User          |");
            System.out.println("|  2 : Remove User            |");
            System.out.println("|  3 : Find User              |");
            System.out.println("|  4 : List All Users         |");
            System.out.println("|  5 : Return                 |");
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
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }


        }
    }

    public static void choicegestionReservation() throws Exception {
        boolean check = false;


        while (check == false) {

            System.out.println();
            System.out.println(" -----------------------------");
            System.out.println("|                             |");
            System.out.println("|      Gestion Reservation    |");
            System.out.println("|  1 : Find Reservation       |");
            System.out.println("|  2 : List All Reservations  |");
            System.out.println("|  3 : Cancel Reservation     |");
            System.out.println("|  4 : Return                 |");
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
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }


        }



    }





}
