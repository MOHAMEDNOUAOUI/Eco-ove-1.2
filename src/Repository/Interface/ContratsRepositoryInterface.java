package Repository.Interface;

import Model.Contrats;
import Model.Partenaire;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface ContratsRepositoryInterface {

    public static Contrats fromResultSet(ResultSet rs) throws SQLException {
        return null;
    }

    public Contrats findOneContrat(UUID idContrat) throws SQLException;
    public List<Contrats> findAllContrats() throws SQLException;
    public void addtodatabase(Contrats contrat) throws SQLException;
    public void updateContrat(Contrats contrat , String column , String value) throws SQLException;
    public String deleteContrat(Contrats contrat) throws SQLException;
}
