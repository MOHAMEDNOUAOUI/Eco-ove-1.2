package Repository.Interface;

import Model.Billets;
import Model.Contrats;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface BilletsRepositoryInterface {


    public static Billets fromResultSet(ResultSet rs) throws SQLException {
        return null;
    }

    public Billets findOneBillet(UUID idbillet) throws SQLException;
    public List<Billets> findAllBillets() throws SQLException;
    public void addtodatabase(Billets billet) throws SQLException;
    public void updateCBillet(Billets billet , String column , String value) throws SQLException;
    public String deleteBillet(Billets billet) throws SQLException;


}
