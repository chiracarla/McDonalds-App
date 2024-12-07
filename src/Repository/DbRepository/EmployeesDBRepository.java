package Repository.DbRepository;

import Enums.ManagerRank;
import Model.Employee;
import Model.Manager;
import com.microsoft.sqlserver.jdbc.ISQLServerConnection;

import java.sql.*;

public class EmployeesDBRepository extends UserDBRepository<Employee> {

    public EmployeesDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
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
            throw new RuntimeException(e);
        }
    }

}