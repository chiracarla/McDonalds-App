package Repository.DbRepository;

import Enums.ManagerRank;
import Exceptions.DatabaseException;
import Model.Employee;
import Model.Manager;
import com.microsoft.sqlserver.jdbc.ISQLServerConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeesDBRepository extends UserDBRepository<Employee> {

    public EmployeesDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(Employee obj) {
        // Insert into Users table first
        super.create(obj);

        String sql = "INSERT INTO Employees (userID, managerID) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, obj.getUserID());
            statement.setInt(2, obj.getManager().getUserID());
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    protected Employee extractFromResultSet(ResultSet resultSet) throws SQLException {
        int userID = resultSet.getInt("userID");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String managerEmail = resultSet.getString("managerEmail");
        String managerName = resultSet.getString("managerName");
        int managerID = resultSet.getInt("managerID");
        String managerPassword = resultSet.getString("managerPassword");
        String managerRankStr = resultSet.getString("managerRank");
        ManagerRank managerRank = ManagerRank.valueOf(managerRankStr);

        Manager manager = new Manager(managerEmail, managerName, managerID, managerPassword, managerRank);

        return new Employee(email, name, userID, password, manager);
    }

    @Override
    public Employee read(Integer id) {
        String sql = """
        SELECT 
            u.userID, u.name, u.email, u.password, mUser.userID AS managerID, mUser.name AS managerName, mUser.email AS managerEmail, mUser.password AS managerPassword, mRank.managerRank
        FROM Users u
        JOIN Employees e ON u.userID = e.userID
        JOIN Users mUser ON e.managerID = mUser.userID
        JOIN Managers mRank ON mUser.userID = mRank.userID
        WHERE u.userID = ?
    """;


        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            } else {
                return null; // No employee found
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void update(Employee obj) {
        // Update Users table first
        super.update(obj);

        String sql = "UPDATE Employees SET managerID=? WHERE userID=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, obj.getManager().getUserID());
            statement.setInt(2, obj.getUserID());
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        // Delete from Employees table first
        String sql = "DELETE FROM Employees WHERE userID=?";

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
    public List<Employee> getAll() {
        String sql = """
    SELECT
        u.userID, u.name, u.email, u.password, mUser.userID AS managerID, mUser.name AS managerName, mUser.email AS managerEmail, mUser.password AS managerPassword, mRank.managerRank
    FROM Users u
    JOIN Employees e ON u.userID = e.userID
    JOIN Users mUser ON e.managerID = mUser.userID
    JOIN Managers mRank ON mUser.userID = mRank.userID
    """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            List<Employee> employees = new ArrayList<>();

            while (resultSet.next()) {
                employees.add(extractFromResultSet(resultSet));
            }

            return employees;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

}