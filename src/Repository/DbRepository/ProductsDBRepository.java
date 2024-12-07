package Repository.DbRepository;

import Model.Product;
import Repository.DBRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ProductsDBRepository<T extends Product> extends DBRepository<T> {
    protected String dbUrl;
    protected String dbUser;
    protected String dbPassword;
    protected Connection connection;

    public ProductsDBRepository(String dbUrl, String dbUser, String dbPassword) {
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

    public void create(T product) {
        String sql = "INSERT INTO Products (productID, productName, productPrice, productType) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, product.getProdId());
            statement.setString(2, product.getProductName());
            statement.setDouble(3, product.getProductPrice());
            statement.setString(4, product.getType());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public T read(Integer id) {
        String sql = "SELECT * FROM Products WHERE productID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void update(T product) {
        String sql = "UPDATE Products SET productName = ?, productPrice = ?, productType = ? WHERE productID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getProductName());
            statement.setDouble(2, product.getProductPrice());
            statement.setString(3, product.getType());
            statement.setInt(4, product.getProdId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM Products WHERE productID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<T> getAll() {
        String sql = "SELECT * FROM Products";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<T> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(extractFromResultSet(resultSet));
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract T extractFromResultSet(ResultSet resultSet) throws SQLException;
}