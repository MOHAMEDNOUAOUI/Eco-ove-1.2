package Service.Interface;

import Model.Contrats;

import java.sql.SQLException;

public interface OffreServiceinterface {



    public void addOffre() throws SQLException, InterruptedException;
    public void getOffre() throws SQLException;
    public void getAllOffres() throws SQLException, InterruptedException;
    public void updateOffre() throws InterruptedException, SQLException;
    public void deleteOffre() throws SQLException;



}
