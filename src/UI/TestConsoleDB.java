package UI;

import Controller.ProductController;
import Enums.DishSize;
import Enums.DrinkVolume;
import Enums.ManagerRank;
import Model.*;
import Repository.*;
import Repository.DbRepository.*;
import Service.ProductService;

import java.util.List;

public class TestConsoleDB {
    public static void main(String[] args) {
//        String dbUrl = "jdbc:sqlserver://localhost:1433;databaseName=McDonalds;encrypt=false";
        String dbUrl = "jdbc:sqlserver://192.168.4.213:1433;databaseName=McDonalds;encrypt=false";
//        String dbUrl = "jdbc:sqlserver://localhost:1433;databaseName=McDonalds;encrypt=false";
        String dbUser = "sa";
        String dbPassword = "m@pMcDonalds1";

        IRepository<Manager> managersRepo = new ManagersDBRepository(dbUrl, dbUser, dbPassword);
        IRepository<Client> clientRepo = new ClientsDBRepository(dbUrl, dbUser, dbPassword);
        IRepository<Employee> employeesRepo = new EmployeesDBRepository(dbUrl, dbUser, dbPassword);
        IRepository<Desserts> dessertsRepo = new DessertsDBRepository(dbUrl, dbUser, dbPassword);
        IRepository<Drinks> drinkRepo = new DrinksDBRepository(dbUrl, dbUser, dbPassword);
        IRepository<MainDish> mainsRepo = new MainDishDBRepository(dbUrl, dbUser, dbPassword);
        IRepository<SideDish> sidesRepo = new SideDishesDBRepository(dbUrl, dbUser, dbPassword);
        IRepository<Product> prodsRepo = new ConcreteProductsDBRepository(dbUrl, dbUser, dbPassword);
        IRepository<Order> orderRepo = new OrderDBRepository(dbUrl, dbUser, dbPassword);
        IRepository<Offer> offerRepo = new OffersDBRepository(dbUrl, dbUser, dbPassword);

        ProductService productService = new ProductService(prodsRepo, mainsRepo, sidesRepo, dessertsRepo, drinkRepo);
        ProductController productController = new ProductController(productService);

        System.out.println(productService.readProduct(10).getProductName());
        Desserts dessert = dessertsRepo.read(8);
        if(dessert != null) {
            System.out.println("Dessert read: " + dessert.getProductName());
        } else {
            System.out.println("No dessert found with this ID.");
        }

//        Drinks newDrink = new Drinks("Sprite", 7, DrinkVolume._200ML,  10);
//        drinkRepo.create(newDrink);
        Drinks drink = drinkRepo.read(10);
        if(drink != null) {
            System.out.println("Drink read: " + drink.getProductName());
        } else {
            System.out.println("No Drink found with this ID.");
        }

        MainDish main = mainsRepo.read(3);
        if(main != null) {
            System.out.println("Mains read: " + main.getProductName());
        } else {
            System.out.println("No Main Dish found with this ID.");
        }

//        SideDish newSide = new SideDish("Fries", 5, DishSize.MEDIUM, 11);
//        sidesRepo.create(newSide);
        SideDish side = sidesRepo.read(11);
        if(side != null) {
            System.out.println("Sides read: " + side.getProductName());
        } else {
            System.out.println("No Side Dish found with this ID.");
        }

//        Manager newManager = new Manager("manager.email@example.com", "New Manager", 5, "password123", ManagerRank.Senior);
//        managersRepo.create(newManager);
        Manager manager = managersRepo.read(5);
        if (manager != null) {
            System.out.println("Manager read: " + manager.getName());
        } else {
            System.out.println("No manager found with this ID.");
        }

//        Client newClient = new Client("client.email@example.com", "New Client", 6, "password123");
//        clientRepo.create(newClient);
        Client readClient = clientRepo.read(6);
        if (readClient != null) {
            System.out.println("Client read: " + readClient.getName());
        } else {
            System.out.println("Client not found");
        }

//        Employee newEmployee = new Employee("new.email@example.com", "New Employee", 4, "password123", manager);
//        employeesRepo.create(newEmployee);
        Employee readEmployee = employeesRepo.read(4);
        if(readEmployee != null) {
            System.out.println("Employee read: " + readEmployee.getName());
            readEmployee.setName("Updated Employee");
            System.out.println("Employee updated: " + readEmployee.getName());
        } else {
            System.out.println("Employee not found");
        }

        System.out.println(orderRepo.read(1).getUser().getName());
        List<Order> orders = orderRepo.getAll();
        for (Order order : orders) {
            System.out.println(order.getProducts());
        }

        System.out.println("Offers:");
        System.out.println(offerRepo.read(1).getProducts());
        List<Offer> offers = offerRepo.getAll();
        for (Offer offer : offers) {
            System.out.println(offer.getProducts());
        }

    }
}