package Service.Interface;

import Model.Contrats;

import java.sql.SQLException;

public interface ContratsServiceInterface {

    public void addContrat() throws SQLException;
    public void getContrat() throws SQLException;
    public void getAllContrats() throws SQLException, InterruptedException;
    public void updateContrat() throws InterruptedException, SQLException;
    public void deleteContrat() throws SQLException;
    public boolean checkContractValid(Contrats contrat);
}
