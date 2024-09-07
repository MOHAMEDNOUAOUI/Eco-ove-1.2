package Repository.Interface;

import Model.Contrats;
import Model.Offres;
import Model.Partenaire;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface OffresRepositoryInterface {

    public static Offres fromResultSet(ResultSet rs) throws SQLException {
        return null;
    }

    public void addToDatabase(Offres offre) throws SQLException;
    public String deleteOffre(Offres offre) throws SQLException;
    public void updateOffre(Offres offre , String column , String value) throws SQLException;
    public List<Offres> getAllOffres() throws SQLException;
    public Offres getOffreById(UUID id) throws SQLException;

}
