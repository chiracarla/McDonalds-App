package Repository;
import java.io.*;
import java.util.*;

import Enums.ManagerRank;
import Model.*;
public class UserFileRepository extends FileRepository<User>{
    public UserFileRepository(String filePath) {
        super(filePath);
    }

    @Override
    protected String toFile(User obj) {
        // Assuming that User class has a type (Client, Manager, etc.)
        // This will handle serialization of the common fields (id, name, email, etc.)
        String userType = obj.getUserType();
        switch (userType) {
            case "Client":
                return obj.getId() + "," + obj.getEmail() + "," +
                        obj.getName() + "," + obj.getPassword() + "," + obj.getUserType(); // assuming Client constructor
            case "Manager":
                Manager manager = (Manager) obj;
                return obj.getId() + "," + obj.getEmail() + "," +
                        obj.getName() + "," + obj.getPassword() + "," +
                        ((Manager) obj).getRank() + "," + obj.getUserType(); // assuming Manager constructor
            case "Employee":
                Employee employee = (Employee) obj;
                return obj.getId() + "," + obj.getEmail() + "," +
                        obj.getName() + "," + obj.getPassword() + "," +
                        obj.getPoints() + "," + employee.getManager().getId()+ "," + employee.getManager().getEmail() + "," +
                        employee.getManager().getName() + "," + employee.getManager().getPassword() + "," +
                        employee.getManager().getRank();
            default:
                throw new IllegalArgumentException("Unknown user type: " + userType);
        }
//        return obj.getId() + "," + obj.getEmail() + "," + obj.getName() + "," + obj.getPassword() + "," + obj.getUserType();
    }

    @Override
    protected User fromFile(String data) {
        // Split the data string into parts based on comma separator
        String[] parts = data.split(",");

        // Extract individual fields
        int id = Integer.parseInt(parts[0]);
        String email = parts[1];
        String name = parts[2];
        String password = parts[3];
        String userType = parts[parts.length - 1]; // Manager, Client, etc.

        // Instantiate the specific subclass based on the userType
        switch (userType) {
            case "Client":
                return new Client(email, name, id, password); // assuming Client constructor
            case "Manager":
                ManagerRank rank = ManagerRank.valueOf(parts[4]);
                return new Manager(email, name, id, password, rank); // assuming Manager constructor
            case "Employee":
                int manID = Integer.parseInt(parts[4]);
                String manEmail = parts[5];
                String manName = parts[6];
                String manPassword = parts[7];
                ManagerRank manRank = ManagerRank.valueOf(parts[8]);
                return new Employee(email, name, id, password, new Manager(manEmail, manName, manID, manPassword, manRank));
            default:
                throw new IllegalArgumentException("Unknown user type: " + userType);
        }
    }
}
