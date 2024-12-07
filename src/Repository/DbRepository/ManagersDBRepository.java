package Repository.DbRepository;

import Enums.ManagerRank;
import Model.Manager;
import com.microsoft.sqlserver.jdbc.ISQLServerConnection;

import java.sql.*;

public class ManagersDBRepository extends UserDBRepository<Manager> {

    public ManagersDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected Manager extractFromResultSet(ResultSet resultSet) throws SQLException {
        int userID = resultSet.getInt("userID");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String managerRankStr = resultSet.getString("managerRank");
        ManagerRank managerRank = ManagerRank.valueOf(managerRankStr);

        return new Manager(email, name, userID, password, managerRank);
    }

    @Override
    public Manager read(Integer id) {
        String sql = """
    SELECT u.userID, u.name, u.email, u.password, u.points, u.userType, m.managerRank
    FROM Users u
    JOIN Managers m ON u.userID = m.userID
    WHERE u.userID = ?
    """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            } else {
                return null; // No manager found
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}