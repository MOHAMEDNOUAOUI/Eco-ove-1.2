package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import Enum.*;

public class Reservation {

    private UUID id;
    private Users users;
    private List<Billets> billetsList = new ArrayList<>();
    private LocalDate date_reservation;
    private String origin;
    private String destination;
    private StatutReservation statut_reservation;

    public Reservation() {this.id = UUID.randomUUID();}

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public Users getUser() {
        return users;
    }
    public void setUser(Users users) {
        this.users = users;
    }
    public List<Billets> getBilletsList() {
        return billetsList;
    }
    public void setBilletsList(Billets billet) {
        this.billetsList.add(billet);
    }
    public LocalDate getDate_reservation() {
        return date_reservation;
    }
    public void setDate_reservation(LocalDate date_reservation) {
        this.date_reservation = date_reservation;
    }
    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public StatutReservation getStatut_reservation() {
        return statut_reservation;
    }
    public void setStatut_reservation(StatutReservation statut_reservation) {
        this.statut_reservation = statut_reservation;
    }

}
