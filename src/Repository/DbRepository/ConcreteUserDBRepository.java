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
//        return new ConcreteUser(email, name, id, password);
//    }
//}
package Repository.DbRepository;

import Enums.ManagerRank;
import Exceptions.EntityNotFoundException;
import Model.*;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                String employeeSql = "SELECT e.managerId, u.email, u.name, u.password FROM Employees e JOIN Users u ON e.userID = u.userID WHERE e.userID = ?";
                try (PreparedStatement employeeStmt = resultSet.getStatement().getConnection().prepareStatement(employeeSql)) {
                    employeeStmt.setInt(1, id);
                    try (ResultSet employeeResultSet = employeeStmt.executeQuery()) {
                        if (employeeResultSet.next()) {
                            int managerId = employeeResultSet.getInt("managerId");
                            email = employeeResultSet.getString("email");
                            name = employeeResultSet.getString("name");
                            password = employeeResultSet.getString("password");
                            String managerSql = "SELECT m.managerRank, u.email, u.name, u.password FROM Managers m JOIN Users u ON m.userID = u.userID WHERE m.userID = ?";
                            try (PreparedStatement managerStmt = resultSet.getStatement().getConnection().prepareStatement(managerSql)) {
                                managerStmt.setInt(1, managerId);
                                try (ResultSet managerResultSet = managerStmt.executeQuery()) {
                                    if (managerResultSet.next()) {
                                        String managerEmail = managerResultSet.getString("email");
                                        String managerName = managerResultSet.getString("name");
                                        String managerPassword = managerResultSet.getString("password");
                                        String rankStr = managerResultSet.getString("managerRank");
                                        ManagerRank rank = ManagerRank.valueOf(rankStr);
                                        Manager manager = new Manager(managerEmail, managerName, managerId, managerPassword, rank);
                                        return new Employee(email, name, id, password, manager);
                                    } else {
                                        throw new EntityNotFoundException("Manager not found with ID: " + managerId);
                                    }
                                }
                            }
                        } else {
                            throw new EntityNotFoundException("Employee not found with ID: " + id);
                        }
                    }
                }
            case "Manager":
                String managerSql = "SELECT m.managerRank, u.email, u.name, u.password FROM Managers m JOIN Users u ON m.userID = u.userID WHERE m.userID = ?";
                try (PreparedStatement managerStmt = resultSet.getStatement().getConnection().prepareStatement(managerSql)) {
                    managerStmt.setInt(1, id);
                    try (ResultSet managerResultSet = managerStmt.executeQuery()) {
                        if (managerResultSet.next()) {
                            String rankStr = managerResultSet.getString("managerRank");
                            ManagerRank rank = ManagerRank.valueOf(rankStr);
                            email = managerResultSet.getString("email");
                            name = managerResultSet.getString("name");
                            password = managerResultSet.getString("password");
                            return new Manager(email, name, id, password, rank);
                        } else {
                            throw new EntityNotFoundException("Manager not found with ID: " + id);
                        }
                    }
                }
            default:
                throw new IllegalArgumentException("Unknown user type: " + type);
        }
    }
}