package Repository;

import Model.Contrats;
import Model.Partenaire;
import Repository.Interface.ContratsRepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Config.Database;

import Enum.StatutContrat;
import Service.PartenaireService;

public class ContratsRepository implements ContratsRepositoryInterface {

    public static PartenaireRepository repository = new PartenaireRepository();


    public static Contrats fromResultSet(ResultSet rs) throws SQLException {

        UUID partenaireId = rs.getObject("partenaireid", UUID.class);
        Partenaire partenaire = repository.findPartenaireById(partenaireId);

        Contrats contrat = new Contrats();
        contrat.setId(rs.getObject("id" , UUID.class));
        contrat.setDate_debut(String.valueOf(rs.getDate("date_debut").toLocalDate()));
        contrat.setDate_fin(String.valueOf(rs.getDate("date_fin").toLocalDate()));
        contrat.setTarif_special(rs.getFloat("tarif_special"));
        contrat.setConditions_accord(rs.getString("conditions_accord"));
        contrat.setRenouvelable(rs.getBoolean("renouvelable"));
        contrat.setStatut_contrat(StatutContrat.valueOf(rs.getString("statut_contrat")));
        contrat.setPartenaire(partenaire);

        return contrat;

    }









    @Override
    public Contrats findOneContrat(UUID idContrat) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Contrats contrat = null;

        try{
            conn = Database.getConnection();
            String sql = "SELECT contrats.id as contrat_id , partenaire.id as partenaire_id , * from contrats LEFT JOIN partenaire on partenaire.id = contrats.partenaireid WHERE contrats.id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setObject(1 , idContrat);
            rs = stmt.executeQuery();

            if(rs.next()){

                Partenaire partenaire = PartenaireRepository.fromResultSet(rs);


                contrat = fromResultSet(rs);

                contrat.setPartenaire(partenaire);

            }





        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return contrat;
    }

    @Override
    public List<Contrats> findAllContrats() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Contrats> contratsList = new ArrayList<>();

        try {
            conn = Database.getConnection();
            String sql = "SELECT * FROM contrats";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Contrats contrat = fromResultSet(rs);
                contratsList.add(contrat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }





        return contratsList;
    }

    @Override
    public void addtodatabase(Contrats contrat) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Database.getConnection();

            String sql = "INSERT INTO contrats (id , date_debut , date_fin , tarif_special , conditions_accord , renouvelable , statut_contrat , partenaireid) " +
                    "VALUES (? ,?, ?, ?, ?, ?, ?::statut_contrat, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, contrat.getId());
            pstmt.setDate(2, java.sql.Date.valueOf(String.valueOf(contrat.getDate_debut())));
            pstmt.setDate(3, java.sql.Date.valueOf(String.valueOf(contrat.getDate_fin())));
            pstmt.setFloat(4, contrat.getTarif_special());
            pstmt.setString(5, contrat.getConditions_accord());
            pstmt.setBoolean(6, contrat.isRenouvelable());
            pstmt.setString(7, contrat.getStatut_contrat().toString());
            pstmt.setObject(8, contrat.GetPartenaire().getId());
            pstmt.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public String deleteContrat(Contrats contrat) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;


        conn = Database.getConnection();

        String sql = "UPDATE contrats SET statut_contrat = 'termine' WHERE id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setObject(1,contrat.getId());
        pstmt.executeUpdate();


        return "Contrat Deleted succefully";
    }



    @Override
    public void updateContrat(Contrats contrat, String column, String value) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null ;
        conn = Database.getConnection();
        try {
            String sql = "UPDATE contrats SET " + column + " = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);

            if(column.equals("date_debut") || column.equals("date_fin")){
                pstmt.setDate(1 , Date.valueOf(value));
            }
            else if(column.equals("id") || column.equals("partenaireid")) {
                pstmt.setObject(1 , UUID.fromString(value));
            }
            else if (column.equals("renouvelable")){
                pstmt.setBoolean(1 , Boolean.parseBoolean(value));
            }
            else if(column.equals("tarif_special")) {
                pstmt.setFloat(1 , Float.parseFloat(value));
            }
            else if(column.equals("statut_contrat")) {
                pstmt.setObject(1 , value , java.sql.Types.OTHER);
            }
            else {
                pstmt.setString(1 , value);
            }

            pstmt.setObject(2 , contrat.getId());



            pstmt.executeUpdate();


            System.out.println("Contract "+column+" has been succefully updated to "+value);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
