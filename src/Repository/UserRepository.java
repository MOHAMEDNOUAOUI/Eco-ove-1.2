package Repository;

import Config.Database;
import Model.Billets;
import Model.Reservation;
import Model.Trajet;
import Model.Users;
import Repository.Interface.UserRepositoryInterface;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import Enum.*;

import javax.xml.crypto.Data;

public class UserRepository implements UserRepositoryInterface {

    private static ReservationRepository reservationRepository = new ReservationRepository();

    public static Users fromResultSet(ResultSet rs) throws SQLException {


        UUID reservationId = rs.getObject("reservation_id" , UUID.class);
        Reservation reservation = reservationRepository.getReservationById(reservationId);

        Users user = new Users();

        user.setId(rs.getObject("id" , UUID.class));
        user.setNom(rs.getString("nom"));
        user.setPrenom(rs.getString("prenom"));
        user.setEmail(rs.getString("email"));
        user.setNumero_de_telephon(rs.getString("numero_de_telephon"));
        user.setStatut_user(StatutUser.valueOf(rs.getString("statut_user")));

        user.setReservationList(reservation);



        return user;

    }






    @Override
    public void addToDatabase(Users users) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Database.getConnection();

            String sql = "INSERT INTO users (id , nom , prenom , email , numero_de_telephon , statut_user) " +
                    "VALUES (? ,?, ?, ?, ?, ?::statut_user)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, users.getId());
            pstmt.setString(2 , users.getNom());
            pstmt.setString(3, users.getPrenom());
            pstmt.setString(4, users.getEmail());
            pstmt.setString(5 , users.getNumero_de_telephon());
            pstmt.setString(6 , users.getStatut_user().toString());
            pstmt.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String deleteUser(Users users) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = Database.getConnection();
            String sql = "Update users set statut_user = 'UNAVAILABLE' where id = ?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setObject(1 , users.getId());
            pstmt.executeUpdate();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return "";
    }



    @Override
    public List<Users> getAllUsers() throws SQLException {
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            List<Users> usersList = new ArrayList<>();
            Map<UUID , Users> usersMap = new HashMap<>();
            try {
                conn = Database.getConnection();
                String sql = "select R.id as reservation_id,R.date_reservation,R.origin,R.destination,R.statut_reservation, users.* from users LEFT JOIN reservation as R on R.user_id = users.id";
                pstmt = conn.prepareStatement(sql);
                pstmt.executeQuery();

                rs = pstmt.getResultSet();

                while(rs.next()){

                    UUID userid = rs.getObject("id" , UUID.class);
                    UUID reservationid = rs.getObject("reservation_id" , UUID.class);

                    Users user = usersMap.get(userid);
                    if(user == null) {
                        user = fromResultSet(rs);
                        usersMap.put(userid , user);
                    }

                    if(reservationid != null) {
                        Reservation res = new Reservation();
                        res.setId(reservationid);
                        res.setDate_reservation(rs.getObject("date_reservation" , LocalDate.class));
                        res.setOrigin(rs.getObject("origin" , String.class));
                        res.setDestination(rs.getObject("destination" , String.class));
                        res.setStatut_reservation(StatutReservation.valueOf(rs.getString("statut_reservation")));

                        user.setReservationList(res);
                    }



                }
                        usersList.addAll(usersMap.values());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }



        return usersList;
    }

    @Override
    public Users getUserByEmail(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Users user = null;

        try {
            conn = Database.getConnection();
            String sql = "select R.id as reservation_id,R.date_reservation,R.origin,R.destination,R.statut_reservation, users.* from users LEFT JOIN reservation as R on R.user_id = users.id where users.email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = fromResultSet(rs);

                while(rs.next()) {
                    UUID reservationid = rs.getObject("reservation_id" , UUID.class);
                    if (reservationid != null) {
                        Reservation res = new Reservation();
                        res.setId(rs.getObject("reservation_id" , UUID.class));
                        res.setDate_reservation(rs.getObject("date_reservation" , LocalDate.class));
                        res.setOrigin(rs.getObject("origin" , String.class));
                        res.setDestination(rs.getObject("destination" , String.class));
                        res.setStatut_reservation(StatutReservation.valueOf(rs.getString("statut_reservation")));

                        user.setReservationList(res);
                    }
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }



    @Override
    public void updateUser(Users users, String column, String value) throws SQLException {

    }
}
