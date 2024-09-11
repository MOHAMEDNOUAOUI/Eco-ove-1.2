package Service;

import Model.Billets;
import Model.Contrats;
import Model.Trajet;
import Repository.BilletsRepository;
import Repository.ContratsRepository;
import Repository.OffresRepository;
import Repository.TrajetRepository;
import Service.Interface.TrajetServiceinterface;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import Enum.*;

public class TrajetService implements TrajetServiceinterface {



    public Scanner scanner = new Scanner(System.in);
    public ContratsRepository contratsRepository = new ContratsRepository();
    public OffresRepository offresRepository = new OffresRepository();
    public ContratsService contratsService = new ContratsService();
    public OffresService offresService = new OffresService();
    public TrajetRepository trajetRepository = new TrajetRepository();
    public BilletsRepository billetsRepository = new BilletsRepository();


    @Override
    public void addTrajet() throws SQLException, InterruptedException {
        System.out.println();
        System.out.println("Welcome to Trajet creating System");
        System.out.println();


        boolean choice = false;



        while(!choice) {

                System.out.println("First ville Depart ");
                String ville_depart = scanner.nextLine();

                System.out.println("Second ville arrive : ");
                String ville_arrive = scanner.nextLine();

                System.out.println("distance between these two (in KM please) : ");
                String distance_between = scanner.nextLine();

                System.out.println("Enter travel time in ISO-8601 format (e.g., PT2H30M for 2 hours 30 minutes):");
                String traveltime = scanner.nextLine();

                System.out.println("Choose Type transport : ( " + Arrays.toString(TrajetStatus.values())  + ")");


                TrajetStatus trajet_status = null;

                while(trajet_status == null) {
                    String trajet_statusStr = scanner.nextLine().toUpperCase();

                    try {
                        trajet_status = TrajetStatus.valueOf(trajet_statusStr);

                    }catch (IllegalArgumentException e){
                        System.out.println("invalid contrat statut . Defaulting to AVAILABLE");
                        trajet_status = TrajetStatus.AVAILABLE;
                    }
                }






               Trajet trajet = new Trajet();
               trajet.setVille_depart(ville_depart);
               trajet.setVille_arrivee(ville_arrive);
               trajet.setDistanceKm(Double.valueOf(distance_between));
               trajet.setTrajet_status(trajet_status);
               trajet.setTravelTime(traveltime);
               trajetRepository.addToDatabase(trajet);

                System.out.println("Trajet from " + trajet.getVille_depart() + " to " + trajet.getVille_arrivee() + "  succefully created.");
                choice = true;
                Thread.sleep(2000);
        }
    }

    @Override
    public void gettrajet() throws SQLException {
        System.out.println("Please give me the origin city");
        String origin = scanner.nextLine();
        System.out.println("Please give me the destination city");
        String destination = scanner.nextLine();

        if(origin.equals(destination)){
            System.out.println("Please stop playing around");
            return;
        }

            Trajet trajet = trajetRepository.getTrajetByCordination(origin , destination);

        if(trajet != null) {
            System.out.println("┌──────────┬───────────────┬───────────────┬─────────┬──────────────────┬─────────────┐");
            System.out.println("│                                  TRAJET DETAILS                                     │");
            System.out.println("├──────────┬───────────────┬───────────────┬─────────┬──────────────────┬─────────────┤");
            System.out.println("│ ID       │ Departure     │ Arrival       │ Dist(km)│ Time             │ Status      │");
            System.out.println("├──────────┼───────────────┼───────────────┼─────────┼──────────────────┼─────────────┤");
                System.out.printf("│ %-8s │ %-13s │ %-13s │ %7.2f │ %-16s │ %-11s │\n",
                        trajet.getId().toString().substring(0, 8),
                        trajet.getVille_depart(),
                        trajet.getVille_arrivee(),
                        trajet.getDistanceKm(),
                        trajet.getTravelTime(),
                        trajet.getTrajet_status());
            System.out.println("└──────────┴───────────────┴───────────────┴─────────┴──────────────────┴─────────────┘");
        }
    }

    @Override
    public void getAllTrajets() throws SQLException, InterruptedException {
        List<Trajet> AllTrajets = trajetRepository.getAllTrajets();
        if (!AllTrajets.isEmpty()) {
            System.out.println("┌──────────┬───────────────┬───────────────┬─────────┬──────────────────┬─────────────┐");
            System.out.println("│                                  TRAJET DETAILS                                     │");
            System.out.println("├──────────┬───────────────┬───────────────┬─────────┬──────────────────┬─────────────┤");
            System.out.println("│ ID       │ Departure     │ Arrival       │ Dist(km)│ Time             │ Status      │");
            System.out.println("├──────────┼───────────────┼───────────────┼─────────┼──────────────────┼─────────────┤");
            for (Trajet trajet : AllTrajets) {
                System.out.printf("│ %-8s │ %-13s │ %-13s │ %7.2f │ %-16s │ %-11s │\n",
                        trajet.getId().toString().substring(0, 8),
                        trajet.getVille_depart(),
                        trajet.getVille_arrivee(),
                        trajet.getDistanceKm(),
                        trajet.getTravelTime(),
                        trajet.getTrajet_status());
            }
            System.out.println("└──────────┴───────────────┴───────────────┴─────────┴──────────────────┴─────────────┘");
        } else {
            System.out.println("No trajets found.");
        }
    }

    @Override
    public void updateTrajet() throws InterruptedException, SQLException {
        System.out.println("Please give me the origin city");
        String origin = scanner.nextLine();
        System.out.println("Please give me the destination city");
        String destination = scanner.nextLine();

        if(origin.equals(destination)){
            System.out.println("Please stop playing around");
            return;
        }

        Trajet trajet = trajetRepository.getTrajetByCordination(origin , destination);
        System.out.println("Please wait im searching for it ....");
        Thread.sleep(3000);


        boolean check = false;

        if(trajet != null) {
            while(!check) {
                System.out.println();
                System.out.println(" -----------------------------");
                System.out.println("|       Modification Kit      |");
                System.out.println("|                             |");
                System.out.println("|  1 : ville_depart           |");
                System.out.println("|  2 : ville_arrive           |");
                System.out.println("|  3 : distance in km         |");
                System.out.println("|  4 : travel time            |");
                System.out.println("|  5 : trajet status          |");
                System.out.println("|  6 : return                 |");
                System.out.println("|                             |");
                System.out.println(" -----------------------------");
                System.out.print("Enter Your Choice : ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                    {
                        System.out.println("The current ville depart  is " + trajet.getVille_depart());
                        System.out.print("Enter a new one : ");
                        String value = scanner.nextLine();
                        trajetRepository.updateTrajet(trajet , "ville_depart" , value);
                        trajet.setVille_depart(value);
                        Thread.sleep(2000);
                        break;
                    }

                    case 2 : {
                        System.out.println("The current ville arrive  is " + trajet.getVille_arrivee());
                        System.out.print("Enter a new one : ");
                        String value = scanner.nextLine();
                        trajetRepository.updateTrajet(trajet , "ville_arrive" , value);
                        trajet.setVille_arrivee(value);
                        Thread.sleep(2000);
                        break;

                    }

                    case 3 : {

                        System.out.println("The current Distance between " + trajet.getVille_depart() + " and " + trajet.getVille_arrivee() + " is " + trajet.getDistanceKm());
                        System.out.print("Enter new one: ");
                        String value = scanner.nextLine();
                        trajetRepository.updateTrajet(trajet , "distancekm" , value );
                         trajet.setDistanceKm(Double.valueOf(value));
                        Thread.sleep(2000);
                        break;
                    }

                    case 4 : {
                        System.out.println("The current travel time is  :  " + trajet.getTravelTime());
                        System.out.print("Enter The new one : ");
                        String value = scanner.nextLine();
                        trajetRepository.updateTrajet(trajet , "traveltime" , value );
                       trajet.setTravelTime(value);
                        Thread.sleep(2000);

                        break;
                    }

                    case 5 : {


                        System.out.println("The current trajet statut is :  " + trajet.getTrajet_status());
                        System.out.print("Enter The new statut : ("+ Arrays.toString(TrajetStatus.values()) +") ");
                        String value = scanner.nextLine();
                        trajetRepository.updateTrajet(trajet , "trajet_status" , value );
                        trajet.setTrajet_status(TrajetStatus.valueOf(value));
                        Thread.sleep(2000);

                        break;
                    }


                    case 6 :
                        return;


                    default:
                        System.out.println("invalid Choice Please try again");
                }


            }
        }


    }

    @Override
    public void deleteTrajet() throws SQLException, InterruptedException {
        System.out.println("Please give me the origin city");
        String origin = scanner.nextLine();
        System.out.println("Please give me the destination city");
        String destination = scanner.nextLine();

        if(origin.equals(destination)){
            System.out.println("Please stop playing around");
            return;
        }

        Trajet trajet = trajetRepository.getTrajetByCordination(origin , destination);

        if(trajet != null && trajet.getTrajet_status().equals("AVAILABLE")) {
            System.out.println("Yes it exist i found it");
            System.out.println("Would you like to delete it ?? (YES / NO)");
            String choice = scanner.nextLine().toUpperCase();

            if(choice.equals("YES")){
                trajetRepository.deleteTrajet(trajet);
            }
            else {
                System.out.println("Going back !!!!");
                Thread.sleep(3000);
                return;
            }
        }
        else {
            System.out.println("Sorry we couldnt find this trajet or its already deleted");
        }

    }
}
