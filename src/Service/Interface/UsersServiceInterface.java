package Service.Interface;

import java.sql.SQLException;

public interface UsersServiceInterface {


    public void addUser() throws SQLException, InterruptedException;
    public void getUser() throws SQLException;
    public void getAllUsers() throws SQLException, InterruptedException;
    public void updateUser() throws InterruptedException, SQLException;
    public void deleteUser() throws SQLException;

}
