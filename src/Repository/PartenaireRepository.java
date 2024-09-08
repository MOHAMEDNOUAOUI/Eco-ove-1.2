package Repository;

import Model.Contrats;
import Model.Partenaire;
import Repository.Interface.PartenaireRepositoryInterface;
import Config.Database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Enum.TypeTransport;
import Enum.StatutPartenaire;
import Enum.StatutContrat;

public class PartenaireRepository implements PartenaireRepositoryInterface {

    private static ContratsRepository contratsRepository = new ContratsRepository();

    public static Partenaire fromResultSet(ResultSet rs) throws SQLException {

        Partenaire partenaire = new Partenaire();
        partenaire.setId(rs.getObject("id" , UUID.class));
        partenaire.setNomCompagnie(rs.getString("nom_compagnie"));
        partenaire.setContactCommercial(rs.getString("contact_commercial"));
        partenaire.setTypeTransport(TypeTransport.valueOf(rs.getString("type_transport")));
        partenaire.setStatutPartenaire(StatutPartenaire.valueOf(rs.getString("statut_partenaire")));
        partenaire.setZoneGeographique(rs.getString("zone_geographique"));
        partenaire.setConditionsSpeciales(rs.getString("conditions_speciales"));
        partenaire.setDateCreation(rs.getDate("date_creation").toLocalDate());


        return partenaire;

    }





    @Override
    public Partenaire findPartenaireById(UUID id) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Partenaire partenaire = null;

        try {
            conn = Database.getConnection();
            String sql = "select partenaire.* ,C.id as contrat_id , C.date_debut,C.date_fin , C.tarif_special , C.conditions_accord , C.renouvelable , C.statut_contrat  from partenaire LEFT JOIN contrats as C on C.partenaireid = partenaire.id\n" +
                    "WHERE partenaire.id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                partenaire = fromResultSet(rs);


                do {
                    UUID contratID = rs.getObject("contrat_id" , UUID.class);
                    if(contratID != null){
                        Contrats contrat = new Contrats();
                        contrat.setId(contratID);
                        contrat.setDate_debut(String.valueOf(rs.getDate("date_debut").toLocalDate()));
                        contrat.setDate_fin(String.valueOf(rs.getDate("date_fin").toLocalDate()));
                        contrat.setTarif_special(rs.getFloat("tarif_special"));
                        contrat.setConditions_accord(rs.getString("conditions_accord"));
                        contrat.setRenouvelable(rs.getBoolean("renouvelable"));
                        contrat.setStatut_contrat(StatutContrat.valueOf(rs.getString("statut_contrat")));


                        partenaire.SetContrats(contrat);
                    }
                }while(rs.next());

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return partenaire;
    }

    @Override
    public void addToDatabase(Partenaire partenaire) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Database.getConnection();

            String sql = "INSERT INTO partenaire (id , nom_compagnie, contact_commercial, type_transport, zone_geographique, conditions_speciales, statut_partenaire, date_creation) " +
                    "VALUES (? , ? , ?, ?::type_transport , ? , ? , ?::statut_partenaire , ? )";

            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, partenaire.getId());
            pstmt.setString(2, partenaire.getNomCompagnie());
            pstmt.setString(3, partenaire.getContactCommercial());
            pstmt.setString(4, partenaire.getTypeTransport().toString());
            pstmt.setString(5, partenaire.getZoneGeographique());
            pstmt.setString(6, partenaire.getConditionsSpeciales());
            pstmt.setString(7, partenaire.getStatutPartenaire().toString());
            java.sql.Date sqlDate = java.sql.Date.valueOf(partenaire.getDateCreation());
            pstmt.setDate(8, sqlDate);
            pstmt.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String deletePartenaire(Partenaire partenaire) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;


        conn = Database.getConnection();
        String sql = "Update partenaire SET statut_partenaire = 'SUSPENDU' WHERE id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setObject(1 , partenaire.getId());

        pstmt.executeUpdate();


        return "This partenaire is deleted succefully";
    }

    @Override
    public List<Partenaire> getAllPartenaire() throws SQLException {
        List<Partenaire> partenaires = new ArrayList<Partenaire>();
        Connection conn = null;
        Statement stmt= null;
        ResultSet rs = null;


        try{
            conn = Database.getConnection();
            String sql = "SELECT partenaire.* ,C.id as contrat_id , C.date_debut,C.date_fin , C.tarif_special , C.conditions_accord , C.renouvelable , C.statut_contrat  from partenaire LEFT JOIN contrats as C on C.partenaireid = partenaire.id";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Partenaire partenaire = fromResultSet(rs);
                UUID contratID = rs.getObject("contrat_id" , UUID.class);
                if(contratID != null){

                    Contrats contrat = new Contrats();
                    contrat.setId(contratID);
                    contrat.setDate_debut(String.valueOf(rs.getDate("date_debut").toLocalDate()));
                    contrat.setDate_fin(String.valueOf(rs.getDate("date_fin").toLocalDate()));
                    contrat.setTarif_special(rs.getFloat("tarif_special"));
                    contrat.setConditions_accord(rs.getString("conditions_accord"));
                    contrat.setRenouvelable(rs.getBoolean("renouvelable"));
                    contrat.setStatut_contrat(StatutContrat.valueOf(rs.getString("statut_contrat")));


                    partenaire.SetContrats(contrat);
                }
                partenaires.add(partenaire);
            }



        }catch(SQLException e){
            e.printStackTrace();
        }

        return partenaires;
    }



    @Override
    public void updatePartenaire(Partenaire partenaire  , String column , String value) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;


        conn = Database.getConnection();

        String sql = "UPDATE partenaire SET " + column + " = ? WHERE id = ?";
        pstmt = conn.prepareStatement(sql);
        if(column.equals("date_creation")){
            pstmt.setDate(1 , Date.valueOf(value));
        }else if (column.equals("type_transport") || column.equals("statut_partenaire")) {
            pstmt.setObject(1, value, java.sql.Types.OTHER);
        }
        else{
            pstmt.setString(1 , value);
        }

        pstmt.setObject(2 , partenaire.getId());


        pstmt.executeUpdate();

        System.out.println("New Partner "+column+" has been succefully updated to "+value);
    }








}
