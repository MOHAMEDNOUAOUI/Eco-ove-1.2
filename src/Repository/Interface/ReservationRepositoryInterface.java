package Repository.Interface;

import Model.Offres;
import Model.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface ReservationRepositoryInterface {

    public static Reservation fromResultSet(ResultSet rs) throws SQLException {
        return null;
    }

    public void addToDatabase(Reservation reservation) throws SQLException;
    public String deleteReservation(Reservation reservation) throws SQLException;
    public void updateReservation(Reservation reservation , String column , String value) throws SQLException;
    public List<Reservation> getAllReservations() throws SQLException;
    public Reservation getReservationById(UUID id) throws SQLException;


}
