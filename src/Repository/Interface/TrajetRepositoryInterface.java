package Repository.Interface;

import Model.Offres;
import Model.Trajet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface TrajetRepositoryInterface {

    public static Trajet fromResultSet(ResultSet rs) throws SQLException {
        return null;
    }

    public void addToDatabase(Trajet trajet) throws SQLException;
    public String deleteTrajet(Trajet trajet) throws SQLException;
    public void updateTrajet(Trajet trajet , String column , String value) throws SQLException;
    public List<Trajet> getAllTrajets() throws SQLException;
    public Trajet getTrajetById(UUID id) throws SQLException;
    public Trajet getTrajetByCordination(String depart , String arrival) throws SQLException;


}
