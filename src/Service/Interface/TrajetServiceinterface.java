package Service.Interface;

import Model.Contrats;

import java.sql.SQLException;

public interface TrajetServiceinterface {


    public void addTrajet() throws SQLException, InterruptedException;
    public void gettrajet() throws SQLException;
    public void getAllTrajets() throws SQLException, InterruptedException;
    public void updateTrajet() throws InterruptedException, SQLException;
    public void deleteTrajet() throws SQLException, InterruptedException;


}
