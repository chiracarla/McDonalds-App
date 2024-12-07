package UI;

import Model.Client;
import Model.Employee;
import Model.Manager;
import Repository.DbRepository.ClientsDBRepository;
import Repository.DbRepository.EmployeesDBRepository;
import Repository.DbRepository.ManagersDBRepository;
import Repository.DbRepository.UserDBRepository;

public class TestConsoleDB {
    public static void main(String[] args) {
        String dbUrl = "jdbc:sqlserver://localhost:1433;databaseName=McDonalds;encrypt=false";
        String dbUser = "sa";
        String dbPassword = "m@pMcDonalds1";

//        try {
//            // Load the MSSQL JDBC driver
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//
//            ClientsDBRepository clientRepo = new ClientsDBRepository(dbUrl, dbUser, dbPassword);
//
//            Client readClient = clientRepo.read(1);
//            if (readClient != null) {
//                System.out.println("Client read: " + readClient);
//            } else {
//                System.out.println("Client not found");
//            }
//        } catch (ClassNotFoundException e) {
//            System.err.println("MSSQL JDBC Driver not found. Include it in your library path.");
//            e.printStackTrace();
//        }

        UserDBRepository<Manager> managersRepo = new ManagersDBRepository(dbUrl, dbUser, dbPassword);
        Manager manager = managersRepo.read(1);
        if (manager != null) {
            System.out.println("Manager read: " + manager);
//            System.out.println("Manager ID: " + manager.getUserID());
//            System.out.println("Name: " + manager.getName());
//            System.out.println("Email: " + manager.getEmail());
//            System.out.println("Rank: " + manager.getRank());
        } else {
            System.out.println("No manager found with this ID.");
        }

        ClientsDBRepository clientRepo = new ClientsDBRepository(dbUrl, dbUser, dbPassword);

            Client readClient = clientRepo.read(2);
            if (readClient != null) {
                System.out.println("Client read: " + readClient);
            } else {
                System.out.println("Client not found");
            }

        EmployeesDBRepository employeesRepo = new EmployeesDBRepository(dbUrl, dbUser, dbPassword);
        Employee readEmployee = employeesRepo.read(3);
        if(readEmployee != null) {
            System.out.println("Employee read: " + readEmployee);
        } else {
            System.out.println("Employee not found");
        }
    }
}