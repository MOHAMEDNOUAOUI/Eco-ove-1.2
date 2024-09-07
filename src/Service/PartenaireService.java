package Service;

import Model.Contrats;
import Model.Partenaire;
import Repository.PartenaireRepository;
import Service.Interface.PartenaireServiceInterface;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import Enum.TypeTransport;
import Enum.StatutPartenaire;

public class PartenaireService implements PartenaireServiceInterface {


    public Scanner scanner = new Scanner(System.in);
    public PartenaireRepository repository = new PartenaireRepository();
    private Connection conn;



    @Override

    public void addpartenaire() throws InterruptedException, SQLException {
        System.out.println("Enter Compagnie name");
        String nom_compagnie = scanner.nextLine();
        System.out.println("Enter Contact Commercial");
        String contact_commercial = scanner.nextLine();

        System.out.println("Enter type of transport : (" + Arrays.toString(TypeTransport.values()) + ") ");
        for (TypeTransport type : TypeTransport.values()) {
            System.out.println(type);
        }
        TypeTransport typeTransport = null;


        while (typeTransport == null) {
            String typeTransportStr = scanner.nextLine().toUpperCase();
            try {
                typeTransport = TypeTransport.valueOf(typeTransportStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid transport type. Please enter again.");
            }
        }

        System.out.print("Enter geographic zone: ");
        String zoneGeographique = scanner.nextLine();

        System.out.print("Enter special conditions: ");
        String conditionsSpeciales = scanner.nextLine();

        System.out.println("Enter status : (" + Arrays.toString(StatutPartenaire.values()) + ")");
        for (StatutPartenaire statut : StatutPartenaire.values()) {
            System.out.println(statut);
        }


        StatutPartenaire statutPartenaire = null;
        while (statutPartenaire == null) {
            String statutPartenaireStr = scanner.nextLine().toUpperCase();
            try {
                statutPartenaire = StatutPartenaire.valueOf(statutPartenaireStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status. Defaulting to ACTIF.");
                statutPartenaire = StatutPartenaire.ACTIF;
            }
        }


        LocalDate date_creation = LocalDate.now();


        Partenaire partenaire = new Partenaire();
        partenaire.setNomCompagnie(nom_compagnie);
        partenaire.setContactCommercial(contact_commercial);
        partenaire.setTypeTransport(typeTransport);
        partenaire.setConditionsSpeciales(conditionsSpeciales);
        partenaire.setZoneGeographique(zoneGeographique);
        partenaire.setStatutPartenaire(statutPartenaire);
        partenaire.setDateCreation(date_creation);

        repository.addToDatabase(partenaire);



        System.out.println("Partenaire " + partenaire.getNomCompagnie() + " succefully created.");
        Thread.sleep(2000);
    }



    @Override
    public void updatepartenaire() throws SQLException, InterruptedException {
        System.out.println("Enter Partenaire Id:");
        String partenaireId = scanner.nextLine();

        UUID PartenaireUUID = UUID.fromString(partenaireId);
        Partenaire partenaire = repository.findPartenaireById(PartenaireUUID);
        if(partenaire != null) {


            boolean check = false;

            while(!check) {
                System.out.println();
                System.out.println(" -----------------------------");
                System.out.println("|       Modification Kit      |");
                System.out.println("|                             |");
                System.out.println("|  1 : Nom_compagnie          |");
                System.out.println("|  2 : contact_commercial     |");
                System.out.println("|  3 : type_transport         |");
                System.out.println("|  4 : zone_geographique      |");
                System.out.println("|  5 : conditions_speciales   |");
                System.out.println("|  6 : statut_partenaire      |");
                System.out.println("|  7 : date_creation          |");
                System.out.println("|  8 : leave                  |");
                System.out.println("|                             |");
                System.out.println(" -----------------------------");
                System.out.print("Enter Your Choice : ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                    {
                        System.out.print("Enter The new compagnie name : ");
                        String value = scanner.nextLine();
                        repository.updatePartenaire(partenaire , "nom_compagnie" , value );
                        Thread.sleep(2000);
                        break;
                    }

                    case 2 : {

                        System.out.print("Enter The new Contact Commercial : ");
                        String value = scanner.nextLine();
                        repository.updatePartenaire(partenaire , "contact_commercial" , value );
                        Thread.sleep(2000);
                        break;




                    }

                    case 3 : {
                        System.out.print("Enter The new type_transport : " + Arrays.toString(TypeTransport.values()));
                        String value = scanner.nextLine();
                        repository.updatePartenaire(partenaire , "type_transport" , value );
                        Thread.sleep(2000);
                        break;
                    }

                    case 4 : {
                        System.out.print("Enter The new zone_geographique: ");
                        String value = scanner.nextLine();
                        repository.updatePartenaire(partenaire, "zone_geographique" , value );
                        Thread.sleep(2000);
                        break;
                    }

                    case 5 : {
                        System.out.print("Enter The new conditions_speciales: ");
                        String value = scanner.nextLine();
                        repository.updatePartenaire(partenaire , "conditions_speciales" , value );
                        Thread.sleep(2000);
                        break;
                    }

                    case 6 : {
                        System.out.print("Enter The new statut_partenaire: " + Arrays.toString(StatutPartenaire.values()));
                        String value = scanner.nextLine();
                        repository.updatePartenaire(partenaire, "statut_partenaire" , value );
                        Thread.sleep(2000);
                        break;
                    }


                    case 7 : {
                        System.out.print("Enter The new date_creation (0000-00-00): ");
                        String value = scanner.nextLine();

                        LocalDate newdate = LocalDate.parse(value);
                        repository.updatePartenaire(partenaire , "date_creation" , String.valueOf(newdate));
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
    }

    @Override
    public void deletepartenaire() throws SQLException, InterruptedException {
        System.out.println("First of all, we shall check if we have this partenaire in our database.");
        System.out.println("Could you please give me the ID of the partenaire?");
        String idString = scanner.nextLine();
        scanner.nextLine();

        UUID id = UUID.fromString(idString);
        Partenaire partenaire =  repository.findPartenaireById(id);

        if (partenaire == null) {
            System.out.println("Sorry, we couldn't find this partenaire.");
        }
        else {
            System.out.println("Yes, this partenaire exists in our database. Are you sure you want to delete it? (yes/no)");



            boolean check = false;

            while (!check) {

                String choice = scanner.nextLine().trim().toLowerCase();
                if ("yes".equals(choice)) {
                    repository.deletePartenaire(partenaire);
                    System.out.println("Partenaire has been deleted.");
                    Thread.sleep(2000);
                    check = true;
                } else if ("no".equals(choice)) {
                    System.out.println("Deletion is being canceled.");
                    Thread.sleep(2000);
                    check = true;
                } else {
                    System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
                    System.out.print("Are you sure you want to delete it? (yes/no): ");
                }


            }

        }

    }

    @Override
    public void displayPartenaire() throws SQLException, InterruptedException {
        System.out.println("Enter Partenaire Id:");
        String partenaireId = scanner.nextLine();

        UUID partenaireUUID = UUID.fromString(partenaireId);
        Partenaire partenaire = repository.findPartenaireById(partenaireUUID);



        if(partenaire != null) {

            System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                                                                 Partners List                                                                                               ║");
            System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
            System.out.printf("║ %-36s │ %-25s │ %-15s │ %-20s │ %-25s │ %-30s │ %-15s ║%n",
                    "Partner ID", "Company Name", "Transport Type", "Geographic Area", "Special Conditions", "Commercial Contact", "Partnership Status");
            System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");


            System.out.printf("║ %-36s │ %-25s │ %-15s │ %-20s │ %-25s │ %-30s │ %-15s ║%n",
                    partenaire.getId(),
                    partenaire.getNomCompagnie(),
                    partenaire.getContactCommercial(),
                    partenaire.getTypeTransport().name(),
                    partenaire.getZoneGeographique(),
                   partenaire.getConditionsSpeciales(),
                    partenaire.getStatutPartenaire().name(),
                    partenaire.getDateCreation().toString());
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println();
            System.out.println("All the contrats associated to this Partenaire are below");
            System.out.println();




            List<Contrats> contrats = partenaire.GetContrats();
            if(contrats != null && !contrats.isEmpty())
            {


                System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
                System.out.printf("║ %-36s │ %-10s │ %-10s │ %-10s │ %-15s │ %-15s ║%n",
                        "Contract ID", "Start Date", "End Date", "Special Price", "Renewable", "Contract Status");





                for(Contrats contrat : contrats){
                    System.out.printf("║ %-36s │ %-10s │ %-10s │ %-10s │ %-15s │ %-15s ║%n",
                            contrat.getId(),
                           contrat.getDate_debut().toString(),
                            contrat.getDate_fin().toString(),
                            contrat.getTarif_special(),
                            contrat.getStatut_contrat().name(),
                            contrat.getConditions_accord());

                    System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.println();
                }
            }
            else {
                System.out.println("No contracts associated with this Partenaire.");
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
                }
                else {
                    System.out.println("Alright Sir you choose what you wantt");
                    System.out.println("if you changed your mind all you have to do is type (quit)");
                }
            }

        } else {
            System.out.println("Partenaire not found.");
        }



    }

    private static String truncate(String value, int length) {
        if (value.length() <= length) {
            return value;
        } else {
            return value.substring(0, length - 3) + "..."; // Add ellipsis if truncated
        }
    }



    @Override
    public void displayAllPartenaire() throws SQLException, InterruptedException {
        List<Partenaire> partenaireList = repository.getAllPartenaire();

        // Set fixed widths for each column
        int idWidth = 36; // UUID length
        int nameWidth = 20;
        int contactWidth = 20;
        int typeWidth = 15;
        int zoneWidth = 15;
        int conditionsWidth = 30;
        int statutWidth = 10;
        int dateWidth = 12;

        System.out.printf("| %-"+idWidth+"s | %-"+nameWidth+"s | %-"+contactWidth+"s | %-"+typeWidth+"s | %-"+zoneWidth+"s | %-"+conditionsWidth+"s | %-"+statutWidth+"s | %-"+dateWidth+"s |\n",
                "ID", "Compagnie", "Contact", "Transport", "Zone", "Conditions", "Statut", "Date");
        System.out.println(new String(new char[idWidth + nameWidth + contactWidth + typeWidth + zoneWidth + conditionsWidth + statutWidth + dateWidth + 11]).replace('\0', '-'));

        for (Partenaire partenaire : partenaireList) {
            System.out.printf("| %-"+idWidth+"s | %-"+nameWidth+"s | %-"+contactWidth+"s | %-"+typeWidth+"s | %-"+zoneWidth+"s | %-"+conditionsWidth+"s | %-"+statutWidth+"s | %-"+dateWidth+"s |\n",
                    partenaire.getId(),
                    truncate(partenaire.getNomCompagnie(), nameWidth),
                    truncate(partenaire.getContactCommercial(), contactWidth),
                    partenaire.getTypeTransport().name(),
                    truncate(partenaire.getZoneGeographique(), zoneWidth),
                    truncate(partenaire.getConditionsSpeciales(), conditionsWidth),
                    partenaire.getStatutPartenaire().name(),
                    partenaire.getDateCreation().toString());
        }

        System.out.println(new String(new char[idWidth + nameWidth + contactWidth + typeWidth + zoneWidth + conditionsWidth + statutWidth + dateWidth + 11]).replace('\0', '-'));


        boolean check = false;

        System.out.println("Woud you like to leave ? type (quit)");

        while (!check) {
            String choice = scanner.nextLine().trim().toLowerCase();
            if ("quit".equals(choice)) {
                System.out.println("Quitting.");
                System.out.println();

                Thread.sleep(2000);


                check = true;
            }
            else {
                System.out.println("Alright Sir you choose what you wantt");
                System.out.println("if you changed your mind all you have to do is type (quit)");
            }
        }


    }







}
