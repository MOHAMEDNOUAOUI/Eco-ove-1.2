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
import java.util.Arrays;
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

    }

    @Override
    public void getAllTrajets() throws SQLException, InterruptedException {

    }

    @Override
    public void updateTrajet() throws InterruptedException, SQLException {

    }

    @Override
    public void deleteTrajet() throws SQLException {

    }
}
