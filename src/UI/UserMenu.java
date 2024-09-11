package UI;

import Service.*;

import java.sql.SQLException;
import java.util.Scanner;

public class UserMenu {
    private static final Scanner scanner = new Scanner(System.in);
    public static PartenaireService partenaireService = new PartenaireService();
    public static ContratsService contratsService = new ContratsService();
    public static OffresService offresService = new OffresService();
    public static BilletsService billetsService = new BilletsService();
    public static TrajetService trajetService = new TrajetService();
    public static ReservationService reservationService = new ReservationService();


    public static void menuuser() throws Exception {
        while (true) {
            System.out.println();
            System.out.println(" -----------------------------");
            System.out.println("|                             |");
            System.out.println("|           EcoMove           |");
            System.out.println("|                             |");
            System.out.println("|  1 : Reservation            |");
            System.out.println("|  2 : Profile Hub            |");
            System.out.println("|  3 : return                 |");
            System.out.println("|                             |");
            System.out.println(" -----------------------------");
            System.out.println();
            System.out.print("Enter Your Choice : ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    reservationService.makereservation();
                    break;
                case 2:
                    profileHub();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }


    }

    public static void profileHub() throws SQLException, ClassNotFoundException , InterruptedException {

    }



}


