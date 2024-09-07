package Service.Interface;

import Model.Partenaire;

import java.sql.SQLException;

public interface PartenaireServiceInterface {


    public void addpartenaire() throws InterruptedException, SQLException;
    public void displayAllPartenaire() throws SQLException, InterruptedException;
    public void updatepartenaire() throws SQLException, InterruptedException;
    public void deletepartenaire() throws SQLException, InterruptedException;
    public void displayPartenaire() throws SQLException, InterruptedException;


}
