package Service;

import Model.Billets;
import Model.Trajet;

import java.util.*;

public class PathFindingService {
    private GraphService graph;

    public PathFindingService(GraphService graph) {
        this.graph = graph;
    }

    public List<Trajet> findShortestPath(String origin, String destination) {
        List<Trajet> path = new ArrayList<>();
        if (origin.equals(destination)) {
            return path;
        }


        Map<String, Trajet> previous = new HashMap<>();

        Queue<String> queue = new LinkedList<>();

        Set<String> visited = new HashSet<>();

        queue.add(origin);
        visited.add(origin);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            for (Trajet trajet : graph.getAdjList().getOrDefault(current, Collections.emptyList())) {
                String neighbor = trajet.getVille_arrivee();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    previous.put(neighbor, trajet);

                    if (neighbor.equals(destination)) {
                        return constructPath(previous, origin, destination);
                    }
                }
            }
        }

        return path;
    }


    private List<Trajet> constructPath(Map<String, Trajet> previous, String origin, String destination) {
        List<Trajet> path = new LinkedList<>();
        for (String at = destination; at != null; at = previous.containsKey(at) ? previous.get(at).getVille_depart() : null) {
            Trajet trajet = previous.get(at);
            if (trajet != null) {
                path.add(trajet);
            }
        }
        Collections.reverse(path);
        return path;
    }

    public void displayPathAndTickets(String origin, String destination) {
        List<Trajet> path = findShortestPath(origin, destination);
        if (path.isEmpty()) {
            System.out.println("No path found from " + origin + " to " + destination);
            return;
        }

        System.out.println("Path from " + origin + " to " + destination + ":");
        for (Trajet trajet : path) {
            System.out.print("From " + trajet.getVille_depart() + " to " + trajet.getVille_arrivee() +
                    " with tickets: ");
            List<Billets> billetsList = trajet.getBilletsList();
            for (Billets billets : billetsList) {
                System.out.print(billets.getId() + " with  " + billets.getType_transport());
            }
            System.out.println();
        }
    }
}
