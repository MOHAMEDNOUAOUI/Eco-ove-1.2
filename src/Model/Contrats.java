package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Enum.StatutContrat;

public class Contrats {

    private UUID id;
    private LocalDate date_debut;
    private LocalDate date_fin;
    private float tarif_special;
    private String conditions_accord;
    private boolean renouvelable;
    private StatutContrat statut_contrat;

    private Partenaire partenaire;
    private List<Offres> offres;
    private List<Billets> billets;



    public Contrats() {
        this.id = UUID.randomUUID();
        this.offres = new ArrayList<>();
        this.billets = new ArrayList<>();
    }




    public UUID getId() {
        return id;
    }
    public LocalDate getDate_debut() {
        return date_debut;
    }
    public LocalDate getDate_fin() {
        return date_fin;
    }
    public float getTarif_special() {
        return tarif_special;
    }
    public String getConditions_accord() {
        return conditions_accord;
    }
    public boolean isRenouvelable() {
        return renouvelable;
    }
    public StatutContrat getStatut_contrat() {
        return statut_contrat;
    }
    public Partenaire GetPartenaire() {
        return partenaire;
    }
    public List<Offres> GetOffres() {
        return offres;
    }
    public List<Billets> GetBillets() {
        return billets;
    }




    public void setId(UUID id) {
        this.id = id;
    }
    public void setDate_debut(String date_debut) {
        this.date_debut = LocalDate.parse(date_debut);
    }
    public void setDate_fin(String date_fin) {
        this.date_fin = LocalDate.parse(date_fin);
    }
    public void setTarif_special(float tarif_special) {
        this.tarif_special = tarif_special;
    }
    public void setConditions_accord(String conditions_accord) {
        this.conditions_accord = conditions_accord;
    }
    public void setRenouvelable(boolean renouvelable) {
        this.renouvelable = renouvelable;
    }
    public void setStatut_contrat(StatutContrat statut_contrat) {
        this.statut_contrat = statut_contrat;
    }
    public void setPartenaire(Partenaire partenaire) {
        this.partenaire = partenaire;
    }
    public void setOffres(Offres offre) {
        this.offres.add(offre);
    }
    public void setBillets(Billets billet) {
        this.billets.add(billet);
    }





}
