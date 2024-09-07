package Repository.Interface;

import Model.Partenaire;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface PartenaireRepositoryInterface {


    public static Partenaire fromResultSet(ResultSet rs) throws SQLException {
        return null;
    }

    public Partenaire findPartenaireById(UUID id) throws SQLException;
    public void addToDatabase(Partenaire partenaire) throws SQLException;
    public void updatePartenaire(Partenaire partenaire , String column , String value) throws SQLException;
    public String deletePartenaire(Partenaire partenaire) throws SQLException;
    public List<Partenaire> getAllPartenaire() throws SQLException;

}
