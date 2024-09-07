package Service.Interface;

import Model.Contrats;

import java.sql.SQLException;

public interface ContratsServiceInterface {

    public void addContrat() throws SQLException;
    public void getContrat();
    public void getAllContrats();
    public void updateContrat();
    public void deleteContrat();
    public boolean checkContractValid(Contrats contrat);
}
