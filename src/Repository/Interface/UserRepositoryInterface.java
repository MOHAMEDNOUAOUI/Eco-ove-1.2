package Repository.Interface;

import Model.Users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface UserRepositoryInterface {

    public static Users fromResultSet(ResultSet rs) throws SQLException {
        return null;
    }

    public void addToDatabase(Users users) throws SQLException;
    public String deleteUser(Users users) throws SQLException;
    public void updateUser(Users users, String column , String value) throws SQLException;
    public List<Users> getAllUsers() throws SQLException;
    public Users getUserById(UUID id) throws SQLException;

}
