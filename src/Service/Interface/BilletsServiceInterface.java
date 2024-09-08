package Service.Interface;

import Model.Contrats;

import java.sql.SQLException;

public interface BilletsServiceInterface {
    public void addBillet() throws SQLException, InterruptedException;
    public void getBillet() throws SQLException;
    public void getAllBillets() throws SQLException, InterruptedException;
    public void updateBillet() throws InterruptedException, SQLException;
    public void deleteBillet() throws SQLException;

}
