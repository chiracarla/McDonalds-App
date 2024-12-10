package Repository.DbRepository;

import Enums.ManagerRank;
import Exceptions.DatabaseException;
import Model.Manager;
import com.microsoft.sqlserver.jdbc.ISQLServerConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagersDBRepository extends UserDBRepository<Manager> {

    public ManagersDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(Manager obj) {
        // Insert into Users table first
        super.create(obj);

        String sql = "INSERT INTO Managers (userID, managerRank) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, obj.getUserID());
            statement.setString(2, obj.getRank().name());
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
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
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void update(Manager obj) {
        // Update Users table first
        super.update(obj);

        String sql = "UPDATE Managers SET managerRank=? WHERE userID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, obj.getRank().name());
            statement.setInt(2, obj.getUserID());
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        // Delete from Managers table first
        String sql = "DELETE FROM Managers WHERE userID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        // Delete from Users table
        super.delete(id);
    }

    @Override
    public List<Manager> getAll() {
        String sql = """
    SELECT u.userID, u.name, u.email, u.password, u.points, u.userType, m.managerRank
    FROM Users u
    JOIN Managers m ON u.userID = m.userID
    """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            List<Manager> managers = new ArrayList<>();

            while (resultSet.next()) {
                managers.add(extractFromResultSet(resultSet));
            }

            return managers;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

}