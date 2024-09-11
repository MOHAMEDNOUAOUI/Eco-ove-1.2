package Service;

import Model.Billets;
import Model.Trajet;

import java.util.*;

public class PathFindingService {
    private GraphService graph;

    public PathFindingService(GraphService graph) {
        this.graph = graph;
    }

    public List<List<Trajet>> findAllPaths(String origin, String destination) {
        List<List<Trajet>> allPaths = new ArrayList<>();
        if (origin.equals(destination)) {
            return allPaths;
        }

        Queue<List<Trajet>> queue = new LinkedList<>();
        queue.offer(new ArrayList<>());

        Set<String> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            List<Trajet> path = queue.poll();
            String current = path.isEmpty() ? origin : path.get(path.size() - 1).getVille_arrivee();

            if (current.equals(destination)) {
                allPaths.add(new ArrayList<>(path));
                continue;
            }

            if (!visited.contains(current)) {
                visited.add(current);

                for (Trajet trajet : graph.getAdjList().getOrDefault(current, Collections.emptyList())) {
                    if (!visited.contains(trajet.getVille_arrivee())) {
                        List<Trajet> newPath = new ArrayList<>(path);
                        newPath.add(trajet);
                        queue.offer(newPath);
                    }
                }
            }
        }

        return allPaths;
    }

    public void displayAllPathsAndTickets(String origin, String destination) {
        List<List<Trajet>> allPaths = findAllPaths(origin, destination);
        if (allPaths.isEmpty()) {
            System.out.println("No paths found from " + origin + " to " + destination);
            return;
        }

        System.out.println("All paths from " + origin + " to " + destination + ":");
        for (int i = 0; i < allPaths.size(); i++) {
            System.out.println("Path " + (i + 1) + ":");
            List<Trajet> path = allPaths.get(i);
            for (Trajet trajet : path) {
                System.out.print("From " + trajet.getVille_depart() + " to " + trajet.getVille_arrivee() +
                        " with tickets: ");
                List<Billets> billetsList = trajet.getBilletsList();
                for (Billets billets : billetsList) {
                    System.out.print(billets.getId() + " with " + billets.getType_transport() + ", ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}