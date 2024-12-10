package Repository.DbRepository;

import Enums.ManagerRank;
import Exceptions.DatabaseException;
import Exceptions.EntityNotFoundException;
import Model.Location;
import Enums.Locations;
import Model.Manager;
import Repository.DBRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDBRepository extends DBRepository<Location> {
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;
    private Connection connection;

    public LocationDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        try {
            this.connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private Location extractFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String storeLocationStr = resultSet.getString("storeLocation");
        Locations storeLocation = Locations.valueOf(storeLocationStr);
        int storeManagerId = resultSet.getInt("storeManager");
        Manager storeManager = getManagerById(storeManagerId);
        return new Location(storeLocation, storeManager, id);
    }

    public void create(Location location) {
        String sql = "INSERT INTO Location (id, storeLocation, storeManager) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, location.getId());
            statement.setString(2, location.getStoreLocation().name());
            statement.setInt(3, location.getStoreManager().getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }


    public Location read(Integer id) {
        String sql = "SELECT * FROM Location WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            } else {
                return null; // No location found
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void update(Location location) {
        String sql = "UPDATE Location SET storeLocation = ?, storeManager = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, location.getStoreLocation().name());
            statement.setInt(2, location.getStoreManager().getId());
            statement.setInt(3, location.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM Location WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public List<Location> getAll() {
        String sql = "SELECT * FROM Location";
        List<Location> locations = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                locations.add(extractFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return locations;
    }

    private Manager getManagerById(int id) throws SQLException {
        String sql = "SELECT m.managerRank, u.email, u.name, u.password FROM Managers m JOIN Users u ON m.userID = u.userID WHERE m.userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                ManagerRank rank = ManagerRank.valueOf(resultSet.getString("managerRank"));
                return new Manager(email, name, id, password, rank);
            } else {
                throw new EntityNotFoundException("Manager with ID " + id + " not found");
            }
        }
    }
}