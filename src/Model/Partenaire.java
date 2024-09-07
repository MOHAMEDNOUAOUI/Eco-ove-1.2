package Model;

import Enum.TypeTransport;
import Enum.StatutPartenaire;


import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Partenaire {

    private UUID id;
    private String nom_compagnie;
    private String contact_commercial;
    private TypeTransport type_transport;
    private String zone_geographique;
    private String conditions_speciales;
    private StatutPartenaire statut_partenaire;
    private LocalDate date_creation;
    private List<Contrats> contrats;


    public Partenaire() {
        this.id = UUID.randomUUID();
        this.contrats = new ArrayList<>();
    }




    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomCompagnie() {
        return nom_compagnie;
    }

    public void setNomCompagnie(String nom_compagnie) {
        this.nom_compagnie = nom_compagnie;
    }

    public String getContactCommercial() {
        return contact_commercial;
    }

    public void setContactCommercial(String contact_commercial) {
        this.contact_commercial = contact_commercial;
    }

    public TypeTransport getTypeTransport() {
        return type_transport;
    }

    public void setTypeTransport(TypeTransport type_transport) {
        this.type_transport = type_transport;
    }

    public String getZoneGeographique() {
        return zone_geographique;
    }

    public void setZoneGeographique(String zone_geographique) {
        this.zone_geographique = zone_geographique;
    }

    public String getConditionsSpeciales() {
        return conditions_speciales;
    }

    public void setConditionsSpeciales(String conditions_speciales) {
        this.conditions_speciales = conditions_speciales;
    }

    public StatutPartenaire getStatutPartenaire() {
        return statut_partenaire;
    }

    public void setStatutPartenaire(StatutPartenaire statut_partenaire) {
        this.statut_partenaire = statut_partenaire;
    }

    public LocalDate getDateCreation() {
        return date_creation;
    }

    public void setDateCreation(LocalDate date_creation) {
        this.date_creation = date_creation;
    }


    public List<Contrats> GetContrats() {
        return contrats;
    }

    public void SetContrats (Contrats contrats) {
        this.contrats.add(contrats);
    }



}
