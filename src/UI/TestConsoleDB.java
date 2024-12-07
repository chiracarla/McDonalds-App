package UI;

import Enums.ManagerRank;
import Model.Client;
import Model.Employee;
import Model.Manager;
import Repository.DbRepository.ClientsDBRepository;
import Repository.DbRepository.EmployeesDBRepository;
import Repository.DbRepository.ManagersDBRepository;
import Repository.DbRepository.UserDBRepository;

import java.util.List;

public class TestConsoleDB {
    public static void main(String[] args) {
        String dbUrl = "jdbc:sqlserver://localhost:1433;databaseName=McDonalds;encrypt=false";
//        String dbUrl = "jdbc:sqlserver://localhost:1433;databaseName=McDonalds;encrypt=false";
        String dbUser = "sa";
        String dbPassword = "m@pMcDonalds1";


        UserDBRepository<Manager> managersRepo = new ManagersDBRepository(dbUrl, dbUser, dbPassword);
        Manager manager = managersRepo.read(5);
        if (manager != null) {
            System.out.println("Manager read: " + manager.getName());
        } else {
            System.out.println("No manager found with this ID.");
        }

        ClientsDBRepository clientRepo = new ClientsDBRepository(dbUrl, dbUser, dbPassword);

            Client readClient = clientRepo.read(6);
            if (readClient != null) {
                System.out.println("Client read: " + readClient.getName());
            } else {
                System.out.println("Client not found");
            }

        EmployeesDBRepository employeesRepo = new EmployeesDBRepository(dbUrl, dbUser, dbPassword);
//        Employee newEmployee = new Employee("new.email@example.com", "New Employee", 4, "password123", manager);
//        employeesRepo.create(newEmployee);

//        Manager newManager = new Manager("manager.email@example.com", "New Manager", 5, "password123", ManagerRank.Senior);
//        managersRepo.create(newManager);
//
//        Client newClient = new Client("client.email@example.com", "New Client", 6, "password123");
//        clientRepo.create(newClient);

//        Employee readEmployee = employeesRepo.read(4);
//        if(readEmployee != null) {
//            System.out.println("Employee read: " + readEmployee.getName());
//            readEmployee.setName("Updated Employee");
//            System.out.println("Employee updated: " + readEmployee.getName());
//        } else {
//            System.out.println("Employee not found");
//        }
    }
}