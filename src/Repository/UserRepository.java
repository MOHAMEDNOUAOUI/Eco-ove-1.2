package Repository;

import Config.Database;
import Model.Billets;
import Model.Reservation;
import Model.Trajet;
import Model.Users;
import Repository.Interface.UserRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

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

        user.setReservationList(reservation);



        return user;

    }






    @Override
    public void addToDatabase(Users users) throws SQLException {

    }

    @Override
    public String deleteUser(Users users) throws SQLException {
        return "";
    }



    @Override
    public List<Users> getAllUsers() throws SQLException {

        return List.of();
    }

    @Override
    public Users getUserById(UUID id) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Users user = null;

        try {
            conn = Database.getConnection();
            String sql = "SELECT RESERVATION.id as reservation_id , * FROM USERS JOIN RESERVATION ON Users.id = RESERVATION.user_id WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = fromResultSet(rs);
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
