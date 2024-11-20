package Repository;
import java.io.*;
import java.util.*;

import Enums.ManagerRank;
import Model.*;
import Model.Client;

/**
 * Uses the FileRepository as schema
 */
public class EmployeeFileRepository extends FileRepository<Employee>{
    public EmployeeFileRepository(String filePath) {
        super(filePath);
    }

    /**
     * Creates a string for the Employee entity
     * @param obj the entity to convert
     * @return
     */
    @Override
    protected String toFile(Employee obj) {
        return obj.getId() + "," + obj.getEmail() + "," +
                obj.getName() + "," + obj.getPassword() + "," +
                obj.getPoints() + "," + obj.getManager().getId()+ "," + obj.getManager().getEmail() + "," +
                obj.getManager().getName() + "," + obj.getManager().getPassword() + "," +
                obj.getManager().getRank();
    }

    /**
     * Creates the object saved in the Employee file
     * @param data the string representation of the entity
     * @return
     */
    @Override
    protected Employee fromFile(String data) {
        String[] parts = data.split(",");
        int id = Integer.parseInt(parts[0]);
        String email = parts[1];
        String name = parts[2];
        String password = parts[3];
        int manID = Integer.parseInt(parts[4]);
        String manEmail = parts[5];
        String manName = parts[6];
        String manPassword = parts[7];
        ManagerRank rank = ManagerRank.valueOf(parts[8]);
        return new Employee(email, name, id, password, new Manager(manEmail, manName, manID, manPassword, rank));
    }
}
