package Service;

import Model.Contrats;
import Model.Partenaire;
import Repository.ContratsRepository;
import Repository.PartenaireRepository;
import Service.Interface.ContratsServiceInterface;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import Enum.StatutContrat;
import Enum.StatutPartenaire;

public class ContratsService implements ContratsServiceInterface {


    public Scanner scanner = new Scanner(System.in);
    public ContratsRepository repository = new ContratsRepository();
    public PartenaireRepository partenaireRepository = new PartenaireRepository();
    private Connection conn;

    @Override
    public void addContrat() throws SQLException {
        System.out.println("So welcome Sir to the contrat adding system . i will be ur guide for this one and im happy to do so!");
        LocalDate date_debut = null;
        LocalDate date_fin = null;

        while (date_debut == null) {
            System.out.println("First of all you should Enter the start date for the contract (YYYY-MM-DD):");
            String dateDebutString = scanner.nextLine();
            try {
                date_debut = LocalDate.parse(dateDebutString);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }

        while (date_fin == null) {
            System.out.println("Enter the end date for the contract (YYYY-MM-DD):");
            String dateFinString = scanner.nextLine();
            try {
                date_fin = LocalDate.parse(dateFinString);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }


        System.out.println("Now you may enter a tarif special for the contrat");
        Float tarif_special = null;
        while (tarif_special == null) {
            try {
                tarif_special = scanner.nextFloat();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid float value.");
                scanner.nextLine();
            }
        }


        System.out.println("And also you should enter a conditons accord for the contrats");
        String conditions_accord = scanner.nextLine();


        System.out.println("Is this contract renewable? (true/false):");
        boolean renouvelable = false;
        while (!scanner.hasNextBoolean()) {
            System.out.println("Invalid input. Please enter 'true' or 'false'.");
            scanner.next();
        }
        renouvelable = scanner.nextBoolean();
        scanner.nextLine();

        System.out.println("Choose a contrat statut from those examples " + Arrays.toString(StatutContrat.values()));

        StatutContrat statut_contrat = null;

        while(statut_contrat == null) {
            String statut_contratstr = scanner.nextLine().toLowerCase();

            try {
                statut_contrat = StatutContrat.valueOf(statut_contratstr);

            }catch (IllegalArgumentException e){
                System.out.println("invalid contrat statut . Defaulting to encours");
                statut_contrat = StatutContrat.encours;
            }
            statut_contrat = StatutContrat.valueOf(statut_contratstr);
        }

        System.out.println("Now all you have to do is to provide me with the partner id associated with this contrat we building");

        Partenaire partenaire = null;

        while(partenaire == null){

            try{
                String partenaireId  = scanner.nextLine();
                UUID partenaireUUID = UUID.fromString(partenaireId);
                partenaire = partenaireRepository.findPartenaireById(partenaireUUID);
            }catch (IllegalArgumentException e) {
                System.out.println("Can you try again we couldnt find the partenaire you looking for ");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        System.out.println(partenaire.getStatutPartenaire());

        if(partenaire.getStatutPartenaire() == StatutPartenaire.valueOf("ACTIF")) {
            Contrats contrat = new Contrats();

            contrat.setDate_debut(String.valueOf(date_debut));
            contrat.setDate_fin(String.valueOf(date_fin));
            contrat.setTarif_special(tarif_special);
            contrat.setConditions_accord(conditions_accord);
            contrat.setRenouvelable(renouvelable);
            contrat.setStatut_contrat(statut_contrat);
            contrat.setPartenaire(partenaire);

            repository.addtodatabase(contrat);
            System.out.println(contrat + "succefully created");
        }



    }

    @Override
    public void getContrat() throws SQLException {
        System.out.println("Please give me the Contrat id");
        String id = scanner.nextLine();


        int idWidth = 36; // UUID length
        int nameWidth = 20;
        int contactWidth = 20;
        int typeWidth = 15;
        int zoneWidth = 15;
        int conditionsWidth = 30;
        int statutWidth = 14;
        int dateWidth = 12;

        UUID contratID = UUID.fromString(id);

        Contrats contrat = repository.findOneContrat(contratID);

        if(contrat != null){


            System.out.printf("| %-" + idWidth + "s | %-" + dateWidth + "s | %-" + dateWidth + "s | %-" + typeWidth + "s | %-" + statutWidth + "s | %-" + conditionsWidth + "s| %-" + nameWidth + "s |\n",
                    "ID", "DATE_DEBUT", "DATE_FIN", "TARIF_SPECIAL", "STATUT_CONTRAT", "CONDITION_SPECIAL", "PARTENAIRE");
            System.out.println(new String(new char[idWidth + dateWidth + dateWidth + typeWidth + statutWidth + conditionsWidth + nameWidth]).replace('\0', '-'));



            System.out.printf("| %-" + idWidth + "s | %-" + dateWidth + "s | %-" + dateWidth + "s | %-" + typeWidth + "s | %-" + statutWidth + "s | %-" + conditionsWidth + "s | %-" + nameWidth + "s |\n",
                    contrat.getId(),
                    contrat.getDate_debut().toString(),
                    contrat.getDate_fin().toString(),
                    contrat.getTarif_special(),
                    contrat.getStatut_contrat().name(),
                    contrat.getConditions_accord(),
                    contrat.GetPartenaire().getNomCompagnie()
            );



            System.out.println(new String(new char[idWidth + nameWidth + contactWidth + typeWidth + zoneWidth + conditionsWidth + statutWidth + dateWidth + 11]).replace('\0', '-'));


        }
    }

    @Override
    public void getAllContrats() throws SQLException, InterruptedException {
        int idWidth = 36; // UUID length
        int nameWidth = 20;
        int contactWidth = 20;
        int typeWidth = 15;
        int zoneWidth = 15;
        int conditionsWidth = 30;
        int statutWidth = 14;
        int dateWidth = 12;


        System.out.printf("| %-" + idWidth + "s | %-" + dateWidth + "s | %-" + dateWidth + "s | %-" + typeWidth + "s | %-" + statutWidth + "s | %-" + conditionsWidth + "s| %-" + nameWidth + "s |\n",
                "ID", "DATE_DEBUT", "DATE_FIN", "TARIF_SPECIAL", "STATUT_CONTRAT", "CONDITION_SPECIAL", "PARTENAIRE");
        System.out.println(new String(new char[idWidth + dateWidth + dateWidth + typeWidth + statutWidth + conditionsWidth + nameWidth]).replace('\0', '-'));


        List<Contrats> ContratsList = repository.findAllContrats();

        for (Contrats contrat : ContratsList) {
            System.out.printf("| %-" + idWidth + "s | %-" + dateWidth + "s | %-" + dateWidth + "s | %-" + typeWidth + "s | %-" + statutWidth + "s | %-" + conditionsWidth + "s | %-" + nameWidth + "s |\n",
                    contrat.getId(),
                    contrat.getDate_debut().toString(),
                    contrat.getDate_fin().toString(),
                    contrat.getTarif_special(),
                    contrat.getStatut_contrat().name(),
                    contrat.getConditions_accord(),
                    contrat.GetPartenaire().getNomCompagnie()
            );

            System.out.println(new String(new char[idWidth + dateWidth + dateWidth + typeWidth + statutWidth + conditionsWidth + nameWidth + 11]).replace('\0', '-'));
        }


        boolean check = false;

        System.out.println("Woud you like to leave ? type (quit)");

        while (!check) {
            String choice = scanner.nextLine().trim().toLowerCase();
            if ("quit".equals(choice)) {
                System.out.println("Quitting.");
                System.out.println();

                Thread.sleep(2000);


                check = true;
            } else {
                System.out.println("Alright Sir you choose what you wantt");
                System.out.println("if you changed your mind all you have to do is type (quit)");
            }
        }
    }

    @Override
    public void updateContrat() throws InterruptedException, SQLException {
        System.out.println("Alright first thing first is what is the contrat id you wanna modify ?");
        String contratIdString = scanner.nextLine();

        System.out.println("Alright ill go check if it exist , please wait a bit");
        Thread.sleep(4000);

        UUID idcontrat = UUID.fromString(contratIdString);
        Contrats contrat = repository.findOneContrat(idcontrat);

        if(contrat != null) {
            System.out.println("Alright Sir it defenityl exist , please look at the menu and choose what you wantt");


            boolean check = false;

            while(!check) {
                System.out.println();
                System.out.println(" -----------------------------");
                System.out.println("|       Modification Kit      |");
                System.out.println("|                             |");
                System.out.println("|  1 : Date_debut             |");
                System.out.println("|  2 : Date_fin               |");
                System.out.println("|  3 : tarif_special          |");
                System.out.println("|  4 : conditions_accord      |");
                System.out.println("|  5 : renouvelable           |");
                System.out.println("|  6 : statut_contrat         |");
                System.out.println("|  7 : Partenaire             |");
                System.out.println("|  8 : leave                  |");
                System.out.println("|                             |");
                System.out.println(" -----------------------------");
                System.out.print("Enter Your Choice : ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                    {
                        System.out.println("The current date_debut is " + contrat.getDate_debut());
                        System.out.print("Enter The new date_debut : ");
                        String value = scanner.nextLine();
                        repository.updateContrat(contrat , "date_debut" , value );
                        contrat.setDate_debut(value);
                        Thread.sleep(2000);
                        break;
                    }

                    case 2 : {

                        System.out.println("The current date_fin is " + contrat.getDate_fin());
                        System.out.print("Enter The new date : ");
                        String value = scanner.nextLine();
                        repository.updateContrat(contrat , "date_fin" , value );
                        contrat.setDate_fin(value);
                        Thread.sleep(2000);




                        break;
                    }

                    case 3 : {

                        System.out.println("The current tarif special is " + contrat.getTarif_special());
                        System.out.print("Enter The new tarif special : ");
                        String value = scanner.nextLine();
                        repository.updateContrat(contrat , "tarif_special" , value );
                        contrat.setTarif_special(Float.parseFloat(value));
                        Thread.sleep(2000);





                        break;
                    }

                    case 4 : {
                        System.out.println("The current conditions_accord is :  " + contrat.getConditions_accord());
                        System.out.print("Enter The new conditions_accord : ");
                        String value = scanner.nextLine();
                        repository.updateContrat(contrat , "conditions_accord" , value );
                        contrat.setConditions_accord(value);
                        Thread.sleep(2000);

                        break;
                    }

                    case 5 : {



                        System.out.println("The current renouvelable is :  " + contrat.isRenouvelable());
                        System.out.print("Enter The new renouvelable type : ");
                        String value = scanner.nextLine();
                        repository.updateContrat(contrat , "renouvelable" , value );
                        contrat.setRenouvelable(Boolean.parseBoolean(value));
                        Thread.sleep(2000);

                        break;
                    }

                    case 6 : {

                        System.out.println("The current Statut of the contrat is :  " + contrat.getStatut_contrat());
                        System.out.print("Enter The new Statut of the contrat (" + Arrays.toString(StatutContrat.values()) + ")");
                        String value = scanner.nextLine();
                        repository.updateContrat(contrat , "statut_contrat" , value );
                        contrat.setStatut_contrat(StatutContrat.valueOf(value));
                        Thread.sleep(2000);

                        break;
                    }


                    case 7 : {

                        System.out.println("The current Partenaire is :  " + contrat.GetPartenaire().getNomCompagnie());
                        System.out.print("Enter The new Partenaire id is : ");
                        String value = scanner.nextLine();
                        repository.updateContrat(contrat , "partenaireid" , value );
                        Partenaire partenaire = partenaireRepository.findPartenaireById(UUID.fromString(value));
                        contrat.setPartenaire(partenaire);
                        Thread.sleep(2000);

                        break;
                    }




                    case 8 :
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
    public void deleteContrat() throws SQLException {
        System.out.println("Please give me the id of the contrat");
        String value = scanner.nextLine();

        UUID idcontrat = UUID.fromString(value);
        Contrats contrat = repository.findOneContrat(idcontrat);



        int idWidth = 36; // UUID length
        int nameWidth = 20;
        int contactWidth = 20;
        int typeWidth = 15;
        int zoneWidth = 15;
        int conditionsWidth = 30;
        int statutWidth = 14;
        int dateWidth = 12;


        if(contrat != null) {



            System.out.printf("| %-" + idWidth + "s | %-" + dateWidth + "s | %-" + dateWidth + "s | %-" + typeWidth + "s | %-" + statutWidth + "s | %-" + conditionsWidth + "s| %-" + nameWidth + "s |\n",
                    "ID", "DATE_DEBUT", "DATE_FIN", "TARIF_SPECIAL", "STATUT_CONTRAT", "CONDITION_SPECIAL", "PARTENAIRE");
            System.out.println(new String(new char[idWidth + dateWidth + dateWidth + typeWidth + statutWidth + conditionsWidth + nameWidth]).replace('\0', '-'));



            System.out.printf("| %-" + idWidth + "s | %-" + dateWidth + "s | %-" + dateWidth + "s | %-" + typeWidth + "s | %-" + statutWidth + "s | %-" + conditionsWidth + "s | %-" + nameWidth + "s |\n",
                    contrat.getId(),
                    contrat.getDate_debut().toString(),
                    contrat.getDate_fin().toString(),
                    contrat.getTarif_special(),
                    contrat.getStatut_contrat().name(),
                    contrat.getConditions_accord(),
                    contrat.GetPartenaire().getNomCompagnie()
            );



            System.out.println(new String(new char[idWidth + nameWidth + contactWidth + typeWidth + zoneWidth + conditionsWidth + statutWidth + dateWidth + 11]).replace('\0', '-'));





            System.out.println();
            System.out.println("Are you sure you want to delete this contrat ? (yes/no)");
            String choice = scanner.nextLine().toLowerCase().trim();

            if(choice.equals("yes")){
                repository.deleteContrat(contrat);
                System.out.println("The contrat succefully deleted");
            }else{
                System.out.println("Alright Sir , if its not yes then its a noo :D");
            }
        }
    }

    @Override
    public boolean checkContractValid(Contrats contrat) {
        LocalDate TodaysDate = LocalDate.now();
        LocalDate contratdate_debut = Date.valueOf(contrat.getDate_debut()).toLocalDate();
        LocalDate contratdate_fin = Date.valueOf(contrat.getDate_fin()).toLocalDate();
        String statut = contrat.getStatut_contrat().toString();


        if(TodaysDate.isAfter(contratdate_debut) && TodaysDate.isBefore(contratdate_fin)  && statut.equals("encours")) {
            return true;
        }
        else {
            return false;
        }
    }



}
