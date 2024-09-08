package Service;

import Model.Billets;
import Model.Contrats;
import Model.Offres;
import Model.Trajet;
import Repository.BilletsRepository;
import Repository.ContratsRepository;
import Repository.OffresRepository;
import Repository.TrajetRepository;
import Service.Interface.BilletsServiceInterface;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import Enum.*;

public class BilletsService implements BilletsServiceInterface {


    public Scanner scanner = new Scanner(System.in);
    public ContratsRepository contratsRepository = new ContratsRepository();
    public OffresRepository offresRepository = new OffresRepository();
    public ContratsService contratsService = new ContratsService();
    public OffresService offresService = new OffresService();
    public TrajetRepository trajetRepository = new TrajetRepository();
    public BilletsRepository billetsRepository = new BilletsRepository();

    private Connection conn;



    @Override
    public void addBillet() throws SQLException, InterruptedException {
        System.out.println();
        System.out.println("Welcome to Billet creating System");
        System.out.println();
        System.out.println("To start ill be asking you for some informations to establish first likee , which contrat this Billet belongs and so.");


        boolean choice = false;



        while(!choice) {

            System.out.println("So i would like you to give me the Contract id : ");
            String value = scanner.nextLine();
            UUID contratid = UUID.fromString(value);
            Contrats contrat = contratsRepository.findOneContrat(contratid);
            if(contrat != null && contratsService.checkContractValid(contrat)) {

                System.out.println("Alright this contracts exist  , now you can procced");
                System.out.println("First prix achat ");
                BigDecimal prix_achat = scanner.nextBigDecimal();

                System.out.println("AND prix vente : ");
                BigDecimal prix_vente = scanner.nextBigDecimal();



                System.out.println("Choose Billet statut : ( " + Arrays.toString(StatutBillets.values())  + ")");


                StatutBillets statut_billet = null;

                while(statut_billet == null) {
                    String statut_billetStr = scanner.nextLine().toUpperCase();

                    try {
                        statut_billet = StatutBillets.valueOf(statut_billetStr);

                    }catch (IllegalArgumentException e){
                        System.out.println("invalid billet statut . Defaulting to ENATTENTE");
                        statut_billet = StatutBillets.ENATTENTE;
                    }
                }

                System.out.println("Choose Type transport : ( " + Arrays.toString(TypeTransport.values())  + ")");


                TypeTransport type_transport = null;

                while(type_transport == null) {
                    String type_transportStr = scanner.nextLine().toUpperCase();

                    try {
                        type_transport = TypeTransport.valueOf(type_transportStr);

                    }catch (IllegalArgumentException e){
                        System.out.println("invalid contrat statut . Defaulting to AVION");
                        type_transport = TypeTransport.AVION;
                    }
                }

                System.out.println("Now i would like you to give me the trajet ID for this billet");
                String trajet_idStr = scanner.nextLine();
                UUID trajet_id = UUID.fromString(trajet_idStr);
                Trajet trajet = trajetRepository.getTrajetById(trajet_id);

                while(trajet == null) {
                    System.out.println("Invalid trajet Id");
                    trajet_idStr = scanner.nextLine();
                    trajet_id = UUID.fromString(trajet_idStr);
                    trajet = trajetRepository.getTrajetById(trajet_id);
                    System.out.println(trajet.toString());
                }





                Billets billet = new Billets();
                billet.setTrajet(trajet);
                billet.setContrat(contrat);
                billet.setPrix_achat(prix_achat);
                billet.setPrix_vente(prix_vente);
                billet.setStatut_billet(statut_billet);
                billet.setType_transport(type_transport);
                billet.setContrat(contrat);
                billetsRepository.addtodatabase(billet);

                System.out.println("Billet :  " + billet.getId()+ " succefully created.");
                choice = true;
                Thread.sleep(2000);
            }else {
                System.out.println("Sorry Sir this Contract doesnt exist at all , Or it ended , Please try again");
            }
        }
    }

    public void getBillet() throws SQLException {

        Boolean choice = false;

        while (!choice) {

            System.out.println("Enter a Billet ID: ");
            UUID billetId = UUID.fromString(scanner.nextLine());
            Billets billet = billetsRepository.findOneBillet(billetId);

            if (billet != null) {

                System.out.println();
                System.out.println("───────────────────────────────────────────────");
                System.out.println("                  TICKET DETAILS                ");
                System.out.println("───────────────────────────────────────────────");
                System.out.println("Billet ID:                  " + billet.getId());
                System.out.println("Prix Achat:                 " + String.format("%.2f", billet.getPrix_achat()) + " EUR");
                System.out.println("Prix Vente:                 " + String.format("%.2f", billet.getPrix_vente()) + " EUR");

                if (billet.getDate_vente() != null) {
                    System.out.println("Date Vente:                 " + billet.getDate_vente());
                } else {
                    System.out.println("Date Vente:                 Not available");
                }

                System.out.println("Statut Billet:              " + billet.getStatut_billet());
                System.out.println("Type Transport:            " + billet.getType_transport());
                System.out.println("───────────────────────────────────────────────");
                System.out.println("TRAJET DETAILS:");
                System.out.println("Departure City:            " + billet.getTrajet().getVille_depart());
                System.out.println("Arrival City:              " + billet.getTrajet().getVille_arrivee());
                System.out.println("Distance:                  " + billet.getTrajet().getDistanceKm() + " km");
                System.out.println("Duration:                  " + billet.getTrajet().getTravelTime());
                System.out.println("───────────────────────────────────────────────");

                choice = true;
            } else {
                System.out.println("Sorry, this Billet is invalid.");
            }
        }
    }

    @Override
    public void getAllBillets() throws SQLException, InterruptedException {
        List<Billets> billetsList = billetsRepository.findAllBillets();

        if (billetsList.isEmpty()) {
            System.out.println("No Billets available.");
        }
        else {
            System.out.println("List of all Billets:");
            System.out.println("-------------------------------------------------");

            for (Billets billet : billetsList) {
                System.out.println();
                System.out.println("───────────────────────────────────────────────");
                System.out.println("                  TICKET DETAILS                ");
                System.out.println("───────────────────────────────────────────────");
                System.out.println("Billet ID:                  " + billet.getId());
                System.out.println("Prix Achat:                 " + String.format("%.2f", billet.getPrix_achat()) + " EUR");
                System.out.println("Prix Vente:                 " + String.format("%.2f", billet.getPrix_vente()) + " EUR");

                if (billet.getDate_vente() != null) {
                    System.out.println("Date Vente:                 " + billet.getDate_vente());
                } else {
                    System.out.println("Date Vente:                 Not available");
                }

                System.out.println("Statut Billet:              " + billet.getStatut_billet());
                System.out.println("Type Transport:            " + billet.getType_transport());
                System.out.println("───────────────────────────────────────────────");
                System.out.println("TRAJET DETAILS:");
                System.out.println("Departure City:            " + billet.getTrajet().getVille_depart());
                System.out.println("Arrival City:              " + billet.getTrajet().getVille_arrivee());
                System.out.println("Distance:                  " + billet.getTrajet().getDistanceKm() + " km");
                System.out.println("Duration:                  " + billet.getTrajet().getTravelTime());
                System.out.println("───────────────────────────────────────────────");
            }
        }
    }

    @Override
    public void updateBillet() throws InterruptedException, SQLException {
        System.out.println("Alright first thing first is what is the Billet id you wanna modify ?");
        String billetString = scanner.nextLine();

        System.out.println("Alright ill go check if it exist , please wait a bit");


        UUID idbillet = UUID.fromString(billetString);
       Billets billet = billetsRepository.findOneBillet(idbillet);


        Thread.sleep(4000);

        if(billet != null) {
            System.out.println("Alright Sir it defenityl exist , please look at the menu and choose what you wantt");


            boolean check = false;

            while(!check) {
                System.out.println();
                System.out.println(" -----------------------------");
                System.out.println("|       Modification Kit      |");
                System.out.println("|                             |");
                System.out.println("|  1 : prix_achat             |");
                System.out.println("|  2 : prix_vente             |");
                System.out.println("|  3 : Date_vente             |");
                System.out.println("|  4 : statut_billet          |");
                System.out.println("|  5 : type_transport         |");
                System.out.println("|  6 : trajet                 |");
                System.out.println("|  7 : return                 |");
                System.out.println("|                             |");
                System.out.println(" -----------------------------");
                System.out.print("Enter Your Choice : ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                    {
                        System.out.println("The current prix achat  is " + billet.getPrix_achat());
                        System.out.print("Enter The new prix : ");
                        BigDecimal value = scanner.nextBigDecimal();
                        billetsRepository.updateCBillet(billet , "prix_achat" , String.valueOf(value));
                        billet.setPrix_achat(value);
                        Thread.sleep(2000);
                        break;
                    }

                    case 2 : {
                        System.out.println("The current prix vente  is " + billet.getPrix_vente());
                        System.out.print("Enter The new prix : ");
                        BigDecimal value = scanner.nextBigDecimal();
                        billetsRepository.updateCBillet(billet , "prix_vente" , String.valueOf(value));
                        billet.setPrix_vente(value);
                        Thread.sleep(2000);
                        break;

                    }

                    case 3 : {

                        System.out.println("The current Date_vente is " + billet.getDate_vente());
                        System.out.print("Enter The new Date vente : ");
                        String value = scanner.nextLine();
                        billetsRepository.updateCBillet(billet , "date_vente" , value );
                        billet.setDate_vente(LocalDate.parse(value));
                        Thread.sleep(2000);





                        break;
                    }

                    case 4 : {
                        System.out.println("The current statut is :  " + billet.getStatut_billet());
                        System.out.print("Enter The new statut : ("+ Arrays.toString(StatutBillets.values()) +") ");
                        String value = scanner.nextLine();
                        billetsRepository.updateCBillet(billet , "statut_billet" , value );
                        billet.setStatut_billet(StatutBillets.valueOf(value));
                        Thread.sleep(2000);

                        break;
                    }

                    case 5 : {


                        System.out.println("The current type is :  " + billet.getType_transport());
                        System.out.print("Enter The new statut : ("+ Arrays.toString(TypeTransport.values()) +") ");
                        String value = scanner.nextLine();
                        billetsRepository.updateCBillet(billet , "type_transport" , value );
                        billet.setType_transport(TypeTransport.valueOf(value));
                        Thread.sleep(2000);

                        break;
                    }

                    case 6 : {

                        System.out.println("The current trajet is  :  " + billet.getTrajet().getId());
                        System.out.print("Enter The new Trajet id is  : ");
                        String value = scanner.nextLine();
                        billetsRepository.updateCBillet(billet , "trajet_id" , value );
                        Trajet trajet = trajetRepository.getTrajetById(UUID.fromString(value));
                        billet.setTrajet(trajet);
                        Thread.sleep(2000);

                        break;
                    }


                    case 7 :
                        return;


                    default:
                        System.out.println("invalid Choice Please try again");
                }


            }




        }
        else {
            System.out.println("Sorry we couldnt find this contrat id");
        }
    }

    @Override
    public void deleteBillet() throws SQLException {

    }
}
