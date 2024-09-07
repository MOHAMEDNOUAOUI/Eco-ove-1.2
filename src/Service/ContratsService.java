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
import java.util.Scanner;
import java.util.UUID;

import Enum.StatutContrat;

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

        if(partenaire.getStatutPartenaire().equals("ACTIF")) {
            Contrats contrat = new Contrats();

            contrat.setDate_debut(String.valueOf(date_debut));
            contrat.setDate_fin(String.valueOf(date_fin));
            contrat.setTarif_special(tarif_special);
            contrat.setConditions_accord(conditions_accord);
            contrat.setRenouvelable(renouvelable);
            contrat.setStatut_contrat(statut_contrat);
            contrat.setPartenaire(partenaire);

            repository.addtodatabase(contrat);
        }



    }

    @Override
    public void getContrat() {

    }

    @Override
    public void getAllContrats() {

    }

    @Override
    public void updateContrat() {

    }

    @Override
    public void deleteContrat() {

    }

    @Override
    public boolean checkContractValid(Contrats contrat) {
        LocalDate TodaysDate = LocalDate.now();
        LocalDate contratdate_debut = Date.valueOf(contrat.getDate_debut()).toLocalDate();
        LocalDate contratdate_fin = Date.valueOf(contrat.getDate_fin()).toLocalDate();
        String statut = contrat.getStatut_contrat().toString();


        if(TodaysDate.isAfter(contratdate_debut) && TodaysDate.isBefore(contratdate_fin)  && statut.equals("ENCOURS")) {
            return true;
        }
        else {
            return false;
        }
    }



}
