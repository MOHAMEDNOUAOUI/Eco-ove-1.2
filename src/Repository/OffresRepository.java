package Repository;

import Model.Contrats;
import Model.Offres;
import Model.Partenaire;
import Repository.Interface.OffresRepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Enum.TypeReduction;
import Enum.StatutOffre;
import Enum.StatutContrat;

import Config.Database;

public class OffresRepository implements OffresRepositoryInterface {

    public static ContratsRepository contratsRepository = new ContratsRepository();

    public static Offres fromResultSet(ResultSet rs) throws SQLException {

        Offres offer = new Offres();
        offer.setId(rs.getObject("id", UUID.class));
        offer.setNom_offre(rs.getString("nom_offre"));
        offer.setDescription(rs.getString("description"));
        offer.setDate_debut(rs.getDate("date_debut").toLocalDate());
        offer.setDate_fin(rs.getDate("date_fin").toLocalDate());
        offer.setValeur_reduction(rs.getInt("valeur_reduction"));
        offer.setConditions(rs.getString("conditions"));
        offer.setStatut_offre(StatutOffre.valueOf(rs.getString("statut_offre")));
        offer.setType_reduction(TypeReduction.valueOf(rs.getString("type_reduction")));

        return offer;

    }







    @Override
    public void addToDatabase(Offres offre) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;





        try {
            conn = Database.getConnection();
            String sql = "INSERT INTO OFFRES (id , nom_offre , description , date_debut , date_fin , valeur_reduction , conditions , statut_offre , type_reduction , contratid ) VALUES (?,?,?,?,?,?,?,?::statut_offre,?::type_reduction,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1 , offre.getId());
            pstmt.setString(2 , offre.getNom_offre());
            pstmt.setString(3 , offre.getDescription());
            pstmt.setDate(4, Date.valueOf(offre.getDate_debut()));
            pstmt.setDate(5 , Date.valueOf(offre.getDate_fin()));
            pstmt.setInt(6, offre.getValeur_reduction());
            pstmt.setString(7 , offre.getConditions());
            pstmt.setString(8 , offre.getStatut_offre().toString());
            pstmt.setString(9 , offre.getType_reduction().toString());
            pstmt.setObject(10 , offre.getContrat().getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }



    @Override
    public String deleteOffre(Offres offre) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;


        conn = Database.getConnection();

        String sql = "UPDATE offres SET statut_offre = 'EXPIREE' WHERE id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setObject(1,offre.getId());
        pstmt.executeUpdate();


        return "Offre Deleted succefully";
    }

    @Override
    public void updateOffre(Offres offre , String column , String value) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null ;
        conn = Database.getConnection();
        try {
            String sql = "UPDATE offres SET " + column + " = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);

            if(column.equals("date_debut") || column.equals("date_fin")){
                pstmt.setDate(1 , Date.valueOf(value));
            }
            else if(column.equals("id") || column.equals("contratid")) {
                pstmt.setObject(1 , UUID.fromString(value));
            }
            else if (column.equals("valeur_reduction")){
                pstmt.setInt(1 , Integer.parseInt(value));
            }
            else if(column.equals("statut_offre")) {
                pstmt.setObject(1 , value , java.sql.Types.OTHER);
            }
            else if(column.equals("type_reduction")) {
                pstmt.setObject(1 , value , java.sql.Types.OTHER);
            }
            else {
                pstmt.setString(1 , value);
            }

            pstmt.setObject(2 , offre.getId());



            pstmt.executeUpdate();


            System.out.println("The Offre   "+column+" has been succefully updated to "+value);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Offres> getAllOffres() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Offres> offersList = new ArrayList<>();

        try {
            conn = Database.getConnection();
            String sql = "Select C.date_debut as cdate_debut , C.date_fin as cdate_fin , C.tarif_special , C.conditions_accord , C.renouvelable , C.statut_contrat , C.partenaireid ,  Offres.* from Offres JOIN contrats as C on C.id = offres.contratid";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();



            while (rs.next()) {
                Offres offre = fromResultSet(rs);

                UUID contratID = rs.getObject("contratid" , UUID.class);
                if(contratID != null){
                    Contrats contrat = new Contrats();
                    contrat.setId(contratID);
                    contrat.setDate_debut(String.valueOf(rs.getDate("cdate_debut").toLocalDate()));
                    contrat.setDate_fin(String.valueOf(rs.getDate("cdate_fin").toLocalDate()));
                    contrat.setTarif_special(rs.getFloat("tarif_special"));
                    contrat.setConditions_accord(rs.getString("conditions_accord"));
                    contrat.setRenouvelable(rs.getBoolean("renouvelable"));
                    contrat.setStatut_contrat(StatutContrat.valueOf(rs.getString("statut_contrat")));


                    offre.setContrat(contrat);
                }
                offersList.add(offre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }





        return offersList;
    }

    @Override
    public Offres getOffreById(UUID id) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Offres offre = null;


        conn = Database.getConnection();
        String sql = "Select C.date_debut as cdate_debut , C.date_fin as cdate_fin , C.tarif_special , C.conditions_accord , C.renouvelable , C.statut_contrat , C.partenaireid ,  Offres.* from Offres JOIN contrats as C on C.id = offres.contratid WHERE Offres.id = ?";

        pstmt = conn.prepareStatement(sql);
        pstmt.setObject(1 , id);
        rs = pstmt.executeQuery();



        if(rs.next()) {

            offre = fromResultSet(rs);

            UUID contratID = rs.getObject("contratid" , UUID.class);
            if(contratID != null){
                Contrats contrat = new Contrats();
                contrat.setId(contratID);
                contrat.setDate_debut(String.valueOf(rs.getDate("cdate_debut").toLocalDate()));
                contrat.setDate_fin(String.valueOf(rs.getDate("cdate_fin").toLocalDate()));
                contrat.setTarif_special(rs.getFloat("tarif_special"));
                contrat.setConditions_accord(rs.getString("conditions_accord"));
                contrat.setRenouvelable(rs.getBoolean("renouvelable"));
                contrat.setStatut_contrat(StatutContrat.valueOf(rs.getString("statut_contrat")));


                offre.setContrat(contrat);
            }

        }

        return offre;
    }
}
