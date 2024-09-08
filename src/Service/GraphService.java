package Service;

import Model.Billets;
import Model.Trajet;

import java.util.*;

public class GraphService {

    private Map<String, List<Trajet>> adjList;

    public GraphService() {
        adjList = new HashMap<>();
    }


    public void addConnection(Trajet trajet) {
        adjList.putIfAbsent(trajet.getVille_depart(), new ArrayList<>());
        adjList.putIfAbsent(trajet.getVille_arrivee(), new ArrayList<>());
        adjList.get(trajet.getVille_depart()).add(trajet);
    }

    public Map<String, List<Trajet>> getAdjList() {
        return adjList;
    }

    public void display() {
        for (Map.Entry<String, List<Trajet>> entry : adjList.entrySet()) {
            String city = entry.getKey();
            List<Trajet> trajets = entry.getValue();
            System.out.print(city + " -> ");
            for (Trajet trajet : trajets) {
                List<Billets> billetsList = trajet.getBilletsList();
                for (Billets billets : billetsList) {
                    System.out.print(trajet.getVille_arrivee() + " with Billet ID: " + billets.getId() + " ");
                }
            }
            System.out.println();
        }
    }

}
