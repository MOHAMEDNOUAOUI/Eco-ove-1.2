package Model;

import java.util.List;
import java.util.UUID;
import Enum.*;

public class Users {

    private UUID id;
    private String Nom;
    private String Prenom;
    private String Email;
    private String Numero_de_telephon;
    private List<Reservation> reservationList;
    private StatutUser statut_user;


    public Users() {
        this.id = UUID.randomUUID();
    }


    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getNom() {
        return Nom;
    }
    public void setNom(String nom) {
        Nom = nom;
    }
    public String getPrenom() {
        return Prenom;
    }
    public void setPrenom(String prenom) {
        Prenom = prenom;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public String getNumero_de_telephon() {
        return Numero_de_telephon;
    }
    public void setNumero_de_telephon(String numero_de_telephon) {
        Numero_de_telephon = numero_de_telephon;
    }
    public List<Reservation> getReservationList() {
        return reservationList;
    }
    public void setReservationList(Reservation reservation) {
        this.reservationList.add(reservation);
    }
    public StatutUser getStatut_user() {
        return statut_user;
    }
    public void setStatut_user(StatutUser statut_user) {
        this.statut_user = statut_user;
    }



}
