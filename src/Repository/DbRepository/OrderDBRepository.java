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
        double totalPrice = 0;
        do {
            int productID = resultSet.getInt("productID");
            String productName = resultSet.getString("productName");
            int productPrice = resultSet.getInt("productPrice");
            products.add(new ConcreteProduct(productName, productPrice, productID));
            totalPrice += productPrice;
        } while (resultSet.next() && resultSet.getInt("orderID") == orderID);
        Order order = new Order(products, location, client, orderID);
        order.setTotalPrice(totalPrice);
        return order;
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
    public void create(Order order) {
        String orderSql = "INSERT INTO Orders (orderID, locationID, userID) VALUES (?, ?, ?)";
        String orderProductsSql = "INSERT INTO OrderProducts (orderID, productID) VALUES (?, ?)";

        try (PreparedStatement orderStatement = connection.prepareStatement(orderSql);
             PreparedStatement orderProductsStatement = connection.prepareStatement(orderProductsSql)) {

            // Insert into Orders table
            orderStatement.setInt(1, order.getOrderID());
            orderStatement.setInt(2, order.getLocation().getId());
            orderStatement.setInt(3, order.getUser().getUserID());
            orderStatement.executeUpdate();

            // Insert into OrderProducts table
            for (Product product : order.getProducts()) {
                orderProductsStatement.setInt(1, order.getOrderID());
                orderProductsStatement.setInt(2, product.getProdId());
                orderProductsStatement.addBatch();
            }
            orderProductsStatement.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Order order) {
        String orderSql = "UPDATE Orders SET locationID = ?, userID = ? WHERE orderID = ?";
        String deleteOrderProductsSql = "DELETE FROM OrderProducts WHERE orderID = ?";
        String insertOrderProductsSql = "INSERT INTO OrderProducts (orderID, productID) VALUES (?, ?)";

        try (PreparedStatement orderStatement = connection.prepareStatement(orderSql);
             PreparedStatement deleteOrderProductsStatement = connection.prepareStatement(deleteOrderProductsSql);
             PreparedStatement insertOrderProductsStatement = connection.prepareStatement(insertOrderProductsSql)) {

            // Update Orders table
            orderStatement.setInt(1, order.getLocation().getId());
            orderStatement.setInt(2, order.getUser().getUserID());
            orderStatement.setInt(3, order.getOrderID());
            orderStatement.executeUpdate();

            // Delete existing OrderProducts entries
            deleteOrderProductsStatement.setInt(1, order.getOrderID());
            deleteOrderProductsStatement.executeUpdate();

            // Insert new OrderProducts entries
            for (Product product : order.getProducts()) {
                insertOrderProductsStatement.setInt(1, order.getOrderID());
                insertOrderProductsStatement.setInt(2, product.getProdId());
                insertOrderProductsStatement.addBatch();
            }
            insertOrderProductsStatement.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer orderID) {
        String deleteOrderProductsSql = "DELETE FROM OrderProducts WHERE orderID = ?";
        String deleteOrderSql = "DELETE FROM Orders WHERE orderID = ?";

        try (PreparedStatement deleteOrderProductsStatement = connection.prepareStatement(deleteOrderProductsSql);
             PreparedStatement deleteOrderStatement = connection.prepareStatement(deleteOrderSql)) {

            // Delete from OrderProducts table
            deleteOrderProductsStatement.setInt(1, orderID);
            deleteOrderProductsStatement.executeUpdate();

            // Delete from Orders table
            deleteOrderStatement.setInt(1, orderID);
            deleteOrderStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> getAll() {
        String sql = "SELECT o.orderID, o.locationID, l.storeLocation, o.userID, u.email AS clientEmail, u.name AS clientName, u.password AS clientPassword, " +
                "p.productID, p.productName, p.productPrice " +
                "FROM Orders o " +
                "JOIN Location l ON o.locationID = l.id " +
                "JOIN Users u ON o.userID = u.userID " +
                "JOIN OrderProducts op ON o.orderID = op.orderID " +
                "JOIN Products p ON op.productID = p.productID";

        List<Order> orders = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Order order = extractFromResultSet(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orders;
    }
}