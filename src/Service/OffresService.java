package Service;

import Model.Contrats;
import Model.Offres;
import Repository.ContratsRepository;
import Repository.OffresRepository;
import Repository.PartenaireRepository;
import Service.Interface.OffreServiceinterface;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;


import Enum.*;

public class OffresService implements OffreServiceinterface {


    public Scanner scanner = new Scanner(System.in);
    public ContratsRepository contratsRepository = new ContratsRepository();
    public OffresRepository offresRepository = new OffresRepository();
    public ContratsService contratsService = new ContratsService();
    private Connection conn;


    @Override
    public void addOffre() throws SQLException, InterruptedException {
        System.out.println();
        System.out.println("Welcome to Offre creating System");
        System.out.println();
        System.out.println("To start ill be asking you for some informations to establish first likee , which contrat this offre belongs and so.");


        boolean choice = false;



        while(!choice) {

            System.out.println("So i would like you to give me the Contract id : ");
            String value = scanner.nextLine();
            UUID contratid = UUID.fromString(value);
            Contrats contrat = contratsRepository.findOneContrat(contratid);
            if(contrat != null && contratsService.checkContractValid(contrat)) {

                System.out.println("Alright this contracts exist  , now you can procced");
                System.out.println("First we shall give this offer a name : ");
                String nom_offre = scanner.nextLine();

                System.out.println("A little description : ");
                String description = scanner.nextLine();

                System.out.println("Now for the debut date for the offer (0000-00-00) and it should be after (" + contrat.getDate_debut() +") and before (" + contrat.getDate_fin() +")");

                LocalDate date_debut = LocalDate.parse(scanner.nextLine());
                while(date_debut.isAfter(contrat.getDate_fin()) || date_debut.isBefore(contrat.getDate_debut())) {
                    System.out.println("Please stay in range of dates");
                    date_debut = LocalDate.parse(scanner.nextLine());
                }

                System.out.println("Same for the fin date for the offer (0000-00-00) and it should be after (" + contrat.getDate_debut() +") and before (" + contrat.getDate_fin() +")");
                LocalDate date_fin = LocalDate.parse(scanner.nextLine());
                while(date_fin.isBefore(contrat.getDate_debut()) || date_fin.isAfter(contrat.getDate_fin())) {
                    System.out.println("Please stay in range of dates");
                    date_fin = LocalDate.parse(scanner.nextLine());
                }

                System.out.println("And for the valeur reduction");
                int valeur_reduction = scanner.nextInt();
                scanner.nextLine();


                System.out.println("A conditions for the offer :");
                String conditions = scanner.nextLine();


                System.out.println("Choose an offer statut : ( " + Arrays.toString(StatutOffre.values())  + ")");


                StatutOffre statut_offre = null;

                while(statut_offre == null) {
                    String statut_offreStr = scanner.nextLine().toUpperCase();

                    try {
                        statut_offre = StatutOffre.valueOf(statut_offreStr);

                    }catch (IllegalArgumentException e){
                        System.out.println("invalid contrat statut . Defaulting to ACTIV");
                        statut_offre = StatutOffre.ACTIV;
                    }
                }

                System.out.println("Choose an offer reduction type : ( " + Arrays.toString(TypeReduction.values())  + ")");


                TypeReduction type_reduction = null;

                while(type_reduction == null) {
                    String type_reductionStr = scanner.nextLine().toUpperCase();

                    try {
                        type_reduction = TypeReduction.valueOf(type_reductionStr);

                    }catch (IllegalArgumentException e){
                        System.out.println("invalid contrat statut . Defaulting to MONTANTFIX");
                        type_reduction = TypeReduction.MONTANTFIX;
                    }
                }




                Offres offer = new Offres();
                offer.setNom_offre(nom_offre);
                offer.setDescription(description);
                offer.setDate_debut(date_debut);
                offer.setDate_fin(date_fin);
                offer.setValeur_reduction(valeur_reduction);
                offer.setStatut_offre(statut_offre);
                offer.setType_reduction(type_reduction);
                offer.setConditions(conditions);
                offer.setContrat(contrat);
                offresRepository.addToDatabase(offer);
                System.out.println("Offer :  " + offer.getNom_offre()+ " succefully created.");
                choice = true;
                Thread.sleep(2000);
            }else {
                System.out.println("Sorry Sir this Contract doesnt exist at all , Or it ended , Please try again");
            }
        }
    }

    @Override
    public void getOffre() throws SQLException {


        Boolean choice = false;

        while(!choice) {


            System.out.println("Enter an offer id : ");
            UUID OfferId = UUID.fromString(scanner.nextLine());
            Offres offer = offresRepository.getOffreById(OfferId);


            if(offer !=null) {


                System.out.println("Offer found:");
                System.out.println();
                System.out.println("-------------------------------------------------");
                System.out.println("Offer ID: " + offer.getId());
                System.out.println("Name: " + offer.getNom_offre());
                System.out.println("Description: " + offer.getDescription());
                System.out.println("Start Date: " + offer.getDate_debut());
                System.out.println("End Date: " + offer.getDate_fin());
                System.out.println("Reduction Value: " + offer.getValeur_reduction());
                System.out.println("Conditions: " + offer.getConditions());
                System.out.println("Reduction Type: " + offer.getType_reduction());
                System.out.println("Status: " + offer.getStatut_offre());
                System.out.println("Contract ID: " + offer.getContrat().getId());
                System.out.println("-------------------------------------------------");

                choice = true;
            }
            else {
                System.out.println("Sorry this Offer is invalid ");
            }

        }
    }

    @Override
    public void getAllOffres() throws SQLException, InterruptedException {
        List<Offres> offers = offresRepository.getAllOffres();

        if (offers.isEmpty()) {
            System.out.println("No offers available.");
        }
        else {
            System.out.println("List of all offers:");
            System.out.println("-------------------------------------------------");

            for (Offres offer : offers) {
                System.out.println("Offer ID: " + offer.getId());
                System.out.println("Name: " + offer.getNom_offre());
                System.out.println("Description: " + offer.getDescription());
                System.out.println("Start Date: " + offer.getDate_debut());
                System.out.println("End Date: " + offer.getDate_fin());
                System.out.println("Reduction Value: " + offer.getValeur_reduction());
                System.out.println("Conditions: " + offer.getConditions());
                System.out.println("Reduction Type: " + offer.getType_reduction());
                System.out.println("Status: " + offer.getStatut_offre());
                System.out.println("Contract ID: " + offer.getContrat().getId());
                System.out.println("-------------------------------------------------");
            }
        }
    }

    @Override
    public void updateOffre() throws InterruptedException, SQLException {
        System.out.println("Alright first thing first is what is the Offre id you wanna modify ?");
        String offreIdString = scanner.nextLine();

        System.out.println("Alright ill go check if it exist , please wait a bit");
        Thread.sleep(4000);

        UUID idoffre = UUID.fromString(offreIdString);
        Offres offre = offresRepository.getOffreById(idoffre);

        if(offre != null) {
            System.out.println("Alright Sir it defenityl exist , please look at the menu and choose what you wantt");


            boolean check = false;

            while(!check) {
                System.out.println();
                System.out.println(" -----------------------------");
                System.out.println("|       Modification Kit      |");
                System.out.println("|                             |");
                System.out.println("|  1 : Offre name             |");
                System.out.println("|  2 : Description            |");
                System.out.println("|  3 : Date_debut             |");
                System.out.println("|  4 : Date_fin               |");
                System.out.println("|  5 : valeur_reduction       |");
                System.out.println("|  6 : conditions             |");
                System.out.println("|  7 : Statut_offre           |");
                System.out.println("|  8 : Type_Offre             |");
                System.out.println("|  9 : return                 |");
                System.out.println("|                             |");
                System.out.println(" -----------------------------");
                System.out.print("Enter Your Choice : ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                    {
                        System.out.println("The current offre name  is " + offre.getNom_offre());
                        System.out.print("Enter The new Name : ");
                        String value = scanner.nextLine();
                        offresRepository.updateOffre(offre , "nom_offre" , value );
                        offre.setNom_offre(value);
                        Thread.sleep(2000);
                        break;
                    }

                    case 2 : {

                        System.out.println("The current Description is " + offre.getDescription());
                        System.out.print("Enter The new Description : ");
                        String value = scanner.nextLine();
                        offresRepository.updateOffre(offre , "description" , value );
                        offre.setDescription(value);
                        Thread.sleep(2000);




                        break;
                    }

                    case 3 : {

                        System.out.println("The current Date debut is " + offre.getDate_debut());
                        System.out.print("Enter The new Date debut : ");
                        String value = scanner.nextLine();
                        offresRepository.updateOffre(offre , "date_debut" , value );
                        offre.setDate_debut(LocalDate.parse(value));
                        Thread.sleep(2000);





                        break;
                    }

                    case 4 : {
                        System.out.println("The current fin date is :  " + offre.getDate_fin());
                        System.out.print("Enter The new fin date : ");
                        String value = scanner.nextLine();
                        offresRepository.updateOffre(offre , "date_fin" , value );
                        offre.setDate_fin(LocalDate.parse(value));
                        Thread.sleep(2000);

                        break;
                    }

                    case 5 : {



                        System.out.println("The current reduction value is :  " + offre.getValeur_reduction());
                        System.out.print("Enter The new value type : ");
                        String value = scanner.nextLine();
                        offresRepository.updateOffre(offre, "valeur_reduction" , value );
                        offre.setValeur_reduction(Integer.parseInt(value));
                        Thread.sleep(2000);

                        break;
                    }

                    case 6 : {

                        System.out.println("The current Conditions are  :  " + offre.getConditions());
                        System.out.print("Enter The new Conditions are : ");
                        String value = scanner.nextLine();
                        offresRepository.updateOffre(offre , "conditions" , value );
                        offre.setConditions(value);
                        Thread.sleep(2000);

                        break;
                    }


                    case 7 : {

                        System.out.println("The current Offre Statut is :  " + offre.getStatut_offre());
                        System.out.print("Enter The new Statut from these : ( " + Arrays.toString(StatutOffre.values()) + " ) ");
                        String value = scanner.nextLine();
                        offresRepository.updateOffre(offre , "statut_offre" , value );
                        offre.setStatut_offre(StatutOffre.valueOf(value));
                        Thread.sleep(2000);

                        break;
                    }


                    case 8:
                        System.out.println("The current Reduction type is :  " + offre.getType_reduction());
                        System.out.print("Enter The new Type from these : ( " + Arrays.toString(TypeReduction.values()) + " ) ");
                        String value = scanner.nextLine();
                        offresRepository.updateOffre(offre , "type_reduction" , value );
                        offre.setType_reduction(TypeReduction.valueOf(value));
                        Thread.sleep(2000);

                        break;



                    case 9 :
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
    public void deleteOffre() throws SQLException {
        System.out.println("Please enter an Offer ID : ");





        boolean choice = false;

        while(!choice) {

            String value = scanner.nextLine();
            UUID Offer_id = UUID.fromString(value);
            Offres offre = offresRepository.getOffreById(Offer_id);



            if(offre != null) {
                System.out.println("Ow Here you go we found the Offer you looking for ");
                System.out.println();
                System.out.println("Are you sure you want to delete this offer ? (yes / no )");
                String choix = scanner.nextLine().toLowerCase().trim();

                if(choix.equals("yes")) {
                    System.out.println(offresRepository.deleteOffre(offre));
                }
                else {
                    return;
                }




                choice = true;
            }else {
                System.out.println("We couldnt find this offer , please try again ");

            }
        }
    }
}
