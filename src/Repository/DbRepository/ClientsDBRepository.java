package Repository.DbRepository;

import Model.Client;
import Model.Manager;
import com.microsoft.sqlserver.jdbc.ISQLServerConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientsDBRepository extends UserDBRepository<Client> {

    public ClientsDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(Client obj) {
        super.create(obj);
        String sql = "INSERT INTO Clients (userID) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, obj.getUserID());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Client extractFromResultSet(ResultSet resultSet) throws SQLException {
        int userID = resultSet.getInt("userID");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        return new Client(email, name, userID, password);
    }

    @Override
    public Client read(Integer id) {
        String sql = """
    SELECT u.userID, u.name, u.email, u.password, u.points, u.userType
    FROM Users u
    JOIN Clients m ON u.userID = m.userID
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

    @Override
    public void update(Client obj){
        super.update(obj);
    }

    @Override
    public void delete(Integer id) {
        // Delete from Clients table first
        String sql = "DELETE FROM Clients WHERE userID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Delete from Users table
        super.delete(id);
    }

    @Override
    public List<Client> getAll() {
        String sql = """
    SELECT u.userID, u.name, u.email, u.password, u.points, u.userType
    FROM Users u
    JOIN Clients c ON u.userID = c.userID
    """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            List<Client> clients = new ArrayList<>();

            while (resultSet.next()) {
                clients.add(extractFromResultSet(resultSet));
            }

            return clients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}