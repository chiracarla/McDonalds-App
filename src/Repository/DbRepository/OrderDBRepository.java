package Repository.DbRepository;

import Enums.Locations;
import Enums.ManagerRank;
import Model.*;
import Repository.DBRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDBRepository extends DBRepository<Order> {
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;
    private Connection connection;

    public OrderDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        try {
            this.connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Order extractFromResultSet(ResultSet resultSet) throws SQLException {
        int orderID = resultSet.getInt("orderID");

        // Extract Location
        int locationID = resultSet.getInt("locationID");
        String locationName = resultSet.getString("storeLocation");
        Location location = new Location(Locations.valueOf(locationName), new Manager("email", "name", locationID, "password", ManagerRank.Junior), locationID);

        // Extract Client
        int clientID = resultSet.getInt("userID");
        String clientEmail = resultSet.getString("clientEmail");
        String clientName = resultSet.getString("clientName");
        String clientPassword = resultSet.getString("clientPassword");
        Client client = new Client(clientEmail, clientName, clientID, clientPassword);

        // Extract Products
        List<Product> products = new ArrayList<>();
        do {
            int productID = resultSet.getInt("productID");
            String productName = resultSet.getString("productName");
            int productPrice = resultSet.getInt("productPrice");
            products.add(new ConcreteProduct(productName, productPrice, productID));
        } while (resultSet.next() && resultSet.getInt("orderID") == orderID);

        return new Order(products, location, client, orderID);
    }

    @Override
    public Order read(Integer orderID) {
        String sql = "SELECT o.orderID, o.locationID, l.storeLocation, o.userID, u.email AS clientEmail, u.name AS clientName, u.password AS clientPassword, " +
                "p.productID, p.productName, p.productPrice " +
                "FROM Orders o " +
                "JOIN Location l ON o.locationID = l.id " +
                "JOIN Users u ON o.userID = u.userID " +
                "JOIN OrderProducts op ON o.orderID = op.orderID " +
                "JOIN Products p ON op.productID = p.productID " +
                "WHERE o.orderID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            } else {
                return null; // No order found
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void create(Order obj) {

    }

    @Override
    public void update(Order obj) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Order> getAll() {
        return List.of();
    }
}