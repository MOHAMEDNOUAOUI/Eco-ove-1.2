package Repository;

import Model.Reservation;
import Repository.Interface.ReservationRepositoryInterface;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ReservationRepository implements ReservationRepositoryInterface {



    @Override
    public void addToDatabase(Reservation reservation) throws SQLException {

    }

    @Override
    public String deleteReservation(Reservation reservation) throws SQLException {
        return "";
    }

    @Override
    public void updateReservation(Reservation reservation, String column, String value) throws SQLException {

    }

    @Override
    public List<Reservation> getAllReservations() throws SQLException {
        return List.of();
    }

    @Override
    public Reservation getReservationById(UUID id) throws SQLException {
        return null;
    }

    @Override
    public void CancelReservation(Reservation reservation) throws SQLException {

    }
}
