package Repository.DbRepository;

import Model.User;
import Repository.DBRepository;
import com.microsoft.sqlserver.jdbc.ISQLServerConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class UserDBRepository<T extends User> extends DBRepository<T> {

    public UserDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(T obj) {
        String sql = "INSERT INTO Users (userID, name, email, password, points, userType) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, obj.getUserID());
            statement.setString(2, obj.getName());
            statement.setString(3, obj.getEmail());
            statement.setString(4, obj.getPassword());
            statement.setInt(5, obj.getPoints());
            statement.setString(6, obj.getUserType());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T read(Integer id) {
        String sql = "SELECT * FROM Users WHERE userID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(T obj) {
        String sql = "UPDATE Users SET name=?, email=?, password=?, points=?, userType=? WHERE userID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, obj.getName());
            statement.setString(2, obj.getEmail());
            statement.setString(3, obj.getPassword());
            statement.setInt(4, obj.getPoints());
            statement.setString(5, obj.getUserType());
            statement.setInt(6, obj.getUserID());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Users WHERE userID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> getAll() {
        String sql = "SELECT * FROM Users";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            List<T> users = new ArrayList<>();

            while (resultSet.next()) {
                users.add(extractFromResultSet(resultSet));
            }

            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract T extractFromResultSet(ResultSet resultSet) throws SQLException;
}