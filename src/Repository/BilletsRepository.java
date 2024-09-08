package Repository;

import Config.Database;
import Model.*;
import Repository.Interface.BilletsRepositoryInterface;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Enum.*;

import javax.xml.crypto.Data;

public class BilletsRepository implements BilletsRepositoryInterface {

    private static TrajetRepository trajetRepository = new TrajetRepository();
    private static ReservationRepository reservationRepository = new ReservationRepository();
    private static ContratsRepository contratsRepository = new ContratsRepository();


    public static Billets fromResultSet(ResultSet rs) throws SQLException {


        Billets b = new Billets();
        b.setId(rs.getObject("id", UUID.class));
        b.setPrix_achat(rs.getBigDecimal("prix_achat"));
        b.setPrix_vente(rs.getBigDecimal("prix_vente"));
        b.setDate_vente(rs.getDate("date_vente").toLocalDate());
        b.setStatut_billet(StatutBillets.valueOf(rs.getString("statut_billet")));
        b.setType_transport(TypeTransport.valueOf(rs.getString("type_transport")));



        return b;

    }









    @Override
    public Billets findOneBillet(UUID idbillet) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Billets billet = null;

        try {
            conn = Database.getConnection();
            String sql = "SELECT * from billets WHERE billets.id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, idbillet);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                billet = fromResultSet(rs);


                UUID trajetid = rs.getObject("trajet_id", UUID.class);
                UUID contratid = rs.getObject("contrat_id", UUID.class);
                UUID reservationid = rs.getObject("reservation_id", UUID.class);

                if (trajetid != null) {
                    Trajet trajet = trajetRepository.getTrajetById(trajetid);
                    billet.setTrajet(trajet);
                }

                if (contratid != null) {
                    Contrats contrat = contratsRepository.findOneContrat(contratid);
                    billet.setContrat(contrat);
                }

                if (reservationid != null) {
                    Reservation reservation = reservationRepository.getReservationById(reservationid);
                    billet.setReservation(reservation);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return billet;
    }

    @Override
    public List<Billets> findAllBillets() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Billets> billets = new ArrayList<Billets>();
        try{
            conn = Database.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from billets");
            while(rs.next()){
                Billets billet = fromResultSet(rs);
                UUID trajetid = rs.getObject("trajet_id", UUID.class);
                UUID contratid = rs.getObject("contrat_id", UUID.class);
                UUID reservationid = rs.getObject("reservation_id", UUID.class);

                if (trajetid != null) {
                    Trajet trajet = trajetRepository.getTrajetById(trajetid);
                    billet.setTrajet(trajet);
                }

                if (contratid != null) {
                    Contrats contrat = contratsRepository.findOneContrat(contratid);
                    billet.setContrat(contrat);
                }

                if (reservationid != null) {
                    Reservation reservation = reservationRepository.getReservationById(reservationid);
                    billet.setReservation(reservation);
                }



                billets.add(billet);
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        return billets;
    }

    @Override
    public void addtodatabase(Billets billet) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
            conn = Database.getConnection();
            String sql = "INSERT INTO BILLETS (id , prix_achat , prix_vente , date_vente , statut_billet , type_transport , trajet_id , reservation_id)" +
                    " VALUES (?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, billet.getId());
            pstmt.setBigDecimal(2, billet.getPrix_achat());
            pstmt.setBigDecimal(3, billet.getPrix_vente());
            pstmt.setDate(4,Date.valueOf(billet.getDate_vente()));
            pstmt.setString(5 , billet.getStatut_billet().toString());
            pstmt.setString(6, billet.getType_transport().toString());
            pstmt.setObject(7, billet.getTrajet().getId());
            pstmt.setObject(8, billet.getReservation().getId());
            pstmt.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public String deleteBillet(Billets billet) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;


        conn = Database.getConnection();
        String sql = "Update billets SET statut_billet = 'ANNULE' WHERE id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setObject(1 , billet.getId());

        pstmt.executeUpdate();


        return "This billet is deleted succefully";
    }



    @Override
    public void updateCBillet(Billets billet, String column, String value) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null ;
        conn = Database.getConnection();
        try {
            String sql = "UPDATE Billets SET " + column + " = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);

            if(column.equals("prix_vente") || column.equals("prix_achat")){
                pstmt.setBigDecimal(1 , BigDecimal.valueOf(Long.parseLong(value)));
            }
            else if (column.equals("date_vente")){
                pstmt.setDate(2 , Date.valueOf(value));
            }
            else if(column.equals("id") || column.equals("trajet_id") || column.equals("reservation_id")) {
                pstmt.setObject(1 , UUID.fromString(value));
            }
            else if(column.equals("statut_contrat") || column.equals("statut_billet")) {
                pstmt.setObject(1 , value , java.sql.Types.OTHER);
            }
            else {
                pstmt.setString(1 , value);
            }

            pstmt.setObject(2 , billet.getId());



            pstmt.executeUpdate();


            System.out.println("Billet "+column+" has been succefully updated to "+value);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
