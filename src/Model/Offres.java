package Model;


import java.time.LocalDate;
import java.util.UUID;

import Enum.StatutOffre;
import Enum.TypeReduction;

public class Offres {

    private UUID id;
    private String nom_offre;
    private String description;
    private LocalDate date_debut;
    private LocalDate date_fin;
    private int valeur_reduction;
    private String conditions;

    private StatutOffre statut_offre;
    private TypeReduction type_reduction;

    private Contrats contrat;


    public Offres () {
        this.id = UUID.randomUUID();
    }


    public void setId (UUID id) {
        this.id = id;
    }

    public void setNom_offre(String nom_offre) {
        this.nom_offre = nom_offre;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public void setDate_debut(LocalDate date_debut) {
        this.date_debut = date_debut;
    }

    public void setDate_fin (LocalDate date_fin) {
        this.date_fin = date_fin;
    }

    public void setValeur_reduction(int valeur_reduction) {
        this.valeur_reduction = valeur_reduction;
    }

    public void setConditions (String conditions) {
        this.conditions = conditions;
    }

    public void setStatut_offre (StatutOffre statut_offre) {
        this.statut_offre = statut_offre;
    }

    public void setType_reduction (TypeReduction type_reduction) {
        this.type_reduction = type_reduction;
    }

    public void setContrat( Contrats contrat ) {
        this.contrat = contrat;
    }


    public UUID getId() {
        return this.id;
    }

    public String getNom_offre() {
        return this.nom_offre;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDate getDate_debut() {
        return this.date_debut;
    }

    public LocalDate getDate_fin() {
        return this.date_fin;
    }

    public int getValeur_reduction() {
        return this.valeur_reduction;
    }

    public String getConditions() {
        return this.conditions;
    }

    public StatutOffre getStatut_offre() {
        return this.statut_offre;
    }

    public TypeReduction getType_reduction() {
        return this.type_reduction;
    }

    public Contrats getContrat() {
        return this.contrat;
    }

}
