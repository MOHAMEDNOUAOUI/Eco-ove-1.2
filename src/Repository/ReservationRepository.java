package Repository;

import Config.Database;
import Model.*;
import Repository.Interface.ReservationRepositoryInterface;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import Enum.*;


public class ReservationRepository implements ReservationRepositoryInterface {

    public static PartenaireRepository repository = new PartenaireRepository();
    public static OffresRepository repositoryOffres = new OffresRepository();
    public static BilletsRepository repositoryBillets = new BilletsRepository();
    public static TrajetRepository repositoryTrajet = new TrajetRepository();


    public static Reservation fromResultSet(ResultSet rs) throws SQLException {


        Reservation res = new Reservation();
        res.setId(rs.getObject("id" , UUID.class));
        res.setDate_reservation(rs.getObject("date_reservation" , LocalDate.class));
        res.setOrigin(rs.getObject("origin" , String.class));
        res.setDestination(rs.getObject("destination" , String.class));
        res.setStatut_reservation(StatutReservation.valueOf(rs.getString("statut_reservation")));

        return res;

    }


    @Override
    public void addToDatabase(Reservation reservation) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Database.getConnection();

            String sql = "INSERT INTO RESERVATION (id , date_reservation , origin , destination , statut_reservation , user_id) " +
                    "VALUES (? ,?, ?, ?, ?::statut_reservation, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, reservation.getId());
            pstmt.setDate(2 , Date.valueOf(LocalDate.now()));
            pstmt.setString(3, reservation.getOrigin());
            pstmt.setString(4, reservation.getDestination());
            pstmt.setString(5 , reservation.getStatut_reservation().toString());
            pstmt.setObject(6 , reservation.getUser().getId());
            pstmt.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String deleteReservation(Reservation reservation) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;


        conn = Database.getConnection();

        String sql = "UPDATE Reservation SET statut_reservation = 'CANCELLED' WHERE id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setObject(1,reservation.getId());
        pstmt.executeUpdate();


        return "Reservation Canceled succefully";
    }

    @Override
    public void updateReservation(Reservation reservation, String column, String value) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null ;
        conn = Database.getConnection();
        try {
            String sql = "UPDATE Reservation SET " + column + " = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);

            if(column.equals("date_reservation")){
                pstmt.setDate(1 , Date.valueOf(value));
            }
            else if(column.equals("id") || column.equals("user_id")) {
                pstmt.setObject(1 , UUID.fromString(value));
            }
            else if(column.equals("statut_reservation")) {
                pstmt.setObject(1 , value , java.sql.Types.OTHER);
            }
            else {
                pstmt.setString(1 , value);
            }

            pstmt.setObject(2 , reservation.getId());



            pstmt.executeUpdate();


            System.out.println("Reservation "+column+" has been succefully updated to "+value);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Reservation> getAllReservations() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Reservation> ReservationList = new ArrayList<>();
        Map<UUID, Reservation> reservationMap = new HashMap<>();
        Map<UUID, Users> userMap = new HashMap<>();

        try {
            conn = Database.getConnection();
            String sql = "select B.id as billet_id ,B.prix_achat ,B.prix_vente , B.date_vente , B.statut_billet , B.type_transport ,B.trajet_id ,B.contratid ,U.nom , U.prenom, U.email , U.numero_de_telephon , U.statut_user , reservation.* from reservation JOIN billets as B ON B.reservation_id = reservation.id JOIN users as U On U.id = reservation.user_id";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                UUID reservationId = rs.getObject("reservation_id", UUID.class);
                UUID userId = rs.getObject("user_id", UUID.class);
                UUID billetId = rs.getObject("billet_id", UUID.class);

                Reservation reservation = reservationMap.get(reservationId);
                if (reservation == null) {
                    reservation = fromResultSet(rs);
                    reservationMap.put(reservationId, reservation);
                }


                Users user = userMap.get(userId);
                if (user == null) {
                    user = new Users();
                    user.setId(userId);
                    user.setNom(rs.getString("nom"));
                    user.setPrenom(rs.getString("prenom"));
                    user.setEmail(rs.getString("email"));
                    user.setNumero_de_telephon(rs.getString("numero_de_telephon"));
                    user.setStatut_user(StatutUser.valueOf(rs.getString("statut_user")));
                    userMap.put(userId, user);
                    reservation.setUser(user);
                }


                if (billetId != null) {
                    Billets billet = new Billets();
                    billet.setId(billetId);
                    billet.setPrix_achat(rs.getBigDecimal("prix_achat"));
                    billet.setPrix_vente(rs.getBigDecimal("prix_vente"));
                    billet.setDate_vente(rs.getDate("date_vente").toLocalDate());
                    billet.setStatut_billet(StatutBillets.valueOf(rs.getString("statut_billet")));
                    billet.setType_transport(TypeTransport.valueOf(rs.getString("type_transport")));

                    UUID trajetid = rs.getObject("trajet_id", UUID.class);
                    UUID reservationid = rs.getObject("reservation_id", UUID.class);

                    if (trajetid != null) {
                        Trajet trajet = repositoryTrajet.getTrajetById(trajetid);
                        billet.setTrajet(trajet);
                    }

                    reservation.setBilletsList(billet);
                }


            }

            ReservationList.addAll(reservationMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }





        return ReservationList;
    }

    @Override
    public Reservation getReservationById(UUID id) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Reservation reservation = null;

        try {
            conn = Database.getConnection();
            String sql = "select B.id as billet_id ,B.prix_achat ,B.prix_vente , B.date_vente , B.statut_billet , B.type_transport ,B.trajet_id ,B.contratid ,U.nom , U.prenom, U.email , U.numero_de_telephon , U.statut_user , reservation.* from reservation JOIN billets as B ON B.reservation_id = reservation.id JOIN users as U On U.id = reservation.user_id WHERE reservation.id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {

                reservation = fromResultSet(rs);
                UUID userId = rs.getObject("user_id", UUID.class);

                if (userId == null) {
                    Users user = new Users();
                    user.setId(userId);
                    user.setNom(rs.getString("nom"));
                    user.setPrenom(rs.getString("prenom"));
                    user.setEmail(rs.getString("email"));
                    user.setNumero_de_telephon(rs.getString("numero_de_telephon"));
                    user.setStatut_user(StatutUser.valueOf(rs.getString("statut_user")));
                    reservation.setUser(user);
                }


                while(rs.next()) {
                    UUID billetId = rs.getObject("billet_id", UUID.class);
                    UUID trajetid = rs.getObject("trajet_id", UUID.class);


                        Billets billet = new Billets();
                        billet.setId(billetId);
                        billet.setPrix_achat(rs.getBigDecimal("prix_achat"));
                        billet.setPrix_vente(rs.getBigDecimal("prix_vente"));
                        billet.setDate_vente(rs.getDate("date_vente").toLocalDate());
                        billet.setStatut_billet(StatutBillets.valueOf(rs.getString("statut_billet")));
                        billet.setType_transport(TypeTransport.valueOf(rs.getString("type_transport")));
                        if (trajetid != null) {
                            Trajet trajet = repositoryTrajet.getTrajetById(trajetid);
                            billet.setTrajet(trajet);
                        }

                        reservation.setBilletsList(billet);


                }


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservation;
    }


}
