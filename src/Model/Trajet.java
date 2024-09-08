package Model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import Enum.*;

public class Trajet {
    private UUID id;
    private String ville_depart;
    private String ville_arrivee;
    private Double distanceKm;
    private Duration travelTime;
    private List<Billets> billetsList;
    private TrajetStatus trajet_status;

    public Trajet() {
        this.id = UUID.randomUUID();
        this.billetsList = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getVille_depart() {
        return ville_depart;
    }
    public void setVille_depart(String ville_depart) {
        this.ville_depart = ville_depart;
    }
    public String getVille_arrivee() {
        return ville_arrivee;
    }
    public void setVille_arrivee(String ville_arrivee) {
        this.ville_arrivee = ville_arrivee;
    }
    public Double getDistanceKm() {
        return distanceKm;
    }
    public void setDistanceKm(Double distanceKm) {
        this.distanceKm = distanceKm;
    }
    public Duration getTravelTime() {
        return travelTime;
    }
    public void setTravelTime(Duration travelTime) {
        this.travelTime = travelTime;
    }
    public List<Billets> getBilletsList() {
        return billetsList;
    }
    public void setBilletsList(Billets billet) {
        this.billetsList.add(billet);
    }
    public TrajetStatus getTrajet_status() {
        return trajet_status;
    }
    public void setTrajet_status(TrajetStatus trajet_status) {
        this.trajet_status = trajet_status;
    }

}
