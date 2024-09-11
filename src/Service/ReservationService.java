package Service;

import Model.Trajet;
import Repository.PartenaireRepository;
import Repository.TrajetRepository;
import Service.Interface.ReservationServiceInterface;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class ReservationService implements ReservationServiceInterface {

    public Scanner scanner = new Scanner(System.in);
    public PartenaireRepository partenaireRepository = new PartenaireRepository();
    private Connection conn;
    public TrajetRepository trajetRepository = new TrajetRepository();
    public GraphService graphService = new GraphService();

    @Override
    public void makereservation() throws Exception {
        System.out.println("Welcome Sir to  EcoMove .");
        System.out.println("please first i need to take some information");
        System.out.println("Can you give me the origin place you wanna go from");
        String origin = scanner.nextLine();
        System.out.println("Now give me the final destination place you wanna go to");
        String destination = scanner.nextLine();




        List<Trajet> trajetList = trajetRepository.getAllTrajets();



        for (Trajet trajet : trajetList) {
            graphService.addConnection(trajet);
        }

        PathFindingService pathFindingService = new PathFindingService(graphService);
        pathFindingService.displayAllPathsAndTickets(origin, destination);

    }


}

