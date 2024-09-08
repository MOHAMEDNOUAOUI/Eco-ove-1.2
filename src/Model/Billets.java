package Model;


import Enum.StatutBillets;
import Enum.TypeTransport;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


public class Billets {
    private UUID id;
    private BigDecimal prix_achat;
    private BigDecimal prix_vente;
    private LocalDate date_vente;
    private StatutBillets statut_billet;
    private TypeTransport type_transport;
    private Trajet trajet;
    private Reservation reservation;
    private Contrats contrat;


    public Billets() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public BigDecimal getPrix_achat() {
        return prix_achat;
    }
    public void setPrix_achat(BigDecimal prix_achat) {
        this.prix_achat = prix_achat;
    }
    public BigDecimal getPrix_vente() {
        return prix_vente;
    }
    public void setPrix_vente(BigDecimal prix_vente) {
        this.prix_vente = prix_vente;
    }
    public LocalDate getDate_vente() {
        return date_vente;
    }
    public void setDate_vente(LocalDate date_vente) {
        this.date_vente = date_vente;
    }
    public StatutBillets getStatut_billet() {
        return statut_billet;
    }
    public void setStatut_billet(StatutBillets statut_billet) {
        this.statut_billet = statut_billet;
    }
    public TypeTransport getType_transport() {
        return type_transport;
    }
    public void setType_transport(TypeTransport type_transport) {
        this.type_transport = type_transport;
    }
    public Trajet getTrajet() {
        return trajet;
    }
    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }
    public Reservation getReservation() {
        return reservation;
    }
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
    public Contrats getContrat() {
        return contrat;
    }
    public void setContrat(Contrats contrat) {
        this.contrat = contrat;
    }

}
