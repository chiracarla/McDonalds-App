//package Repository.DbRepository;
//
//import Model.*;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//public class ConcreteUserDBRepository extends UserDBRepository<ConcreteUser> {
//    public ConcreteUserDBRepository(String dbUrl, String dbUser, String dbPassword) {
//        super(dbUrl, dbUser, dbPassword);
//    }
//
//    @Override
//    protected ConcreteUser extractFromResultSet(ResultSet resultSet) throws SQLException {
//        int id = resultSet.getInt("userID");
//        String name = resultSet.getString("name");
//        String email = resultSet.getString("email");
//        String password = resultSet.getString("password");
//        String type = resultSet.getString("userType");
////userID, name, email, password, points, userType
//        return new ConcreteUser(email, name, id, password); //TODO:problem
//    }
//}
package Repository.DbRepository;

import Enums.ManagerRank;
import Model.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ConcreteUserDBRepository extends UserDBRepository<User> {
    public ConcreteUserDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected User extractFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("userID");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String type = resultSet.getString("userType");

        switch (type) {
            case "Client":
                return new Client(email, name, id, password);
            case "Employee":
                int managerId = resultSet.getInt("managerId");
                String managerSql = "SELECT * FROM Managers WHERE userID = ?";
                try (PreparedStatement managerStmt = resultSet.getStatement().getConnection().prepareStatement(managerSql)) {
                    managerStmt.setInt(1, managerId);
                    try (ResultSet managerResultSet = managerStmt.executeQuery()) {
                        if (managerResultSet.next()) {
                            String managerEmail = managerResultSet.getString("email");
                            String managerName = managerResultSet.getString("name");
                            String managerPassword = managerResultSet.getString("password");
                            String rankStr = managerResultSet.getString("rank");
                            ManagerRank rank = ManagerRank.valueOf(rankStr);
                            Manager manager = new Manager(managerEmail, managerName, managerId, managerPassword, rank);
                            return new Employee(email, name, id, password, manager);
                        } else {
                            throw new SQLException("Manager not found with ID: " + managerId);
                        }
                    }
                }
            case "Manager":
                String rankStr = resultSet.getString("rank");
                ManagerRank rank = ManagerRank.valueOf(rankStr);
                return new Manager(email, name, id, password, rank);
            default:
                throw new IllegalArgumentException("Unknown user type: " + type);
        }
    }
}