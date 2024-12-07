package Repository.DbRepository;

import Enums.Allergens;
import Model.Desserts;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DessertsDBRepository extends ProductsDBRepository<Desserts> {
    public DessertsDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected Desserts extractFromResultSet(ResultSet resultSet) throws SQLException {
        int productId = resultSet.getInt("productID");
        String productName = resultSet.getString("productName");
        int productPrice = resultSet.getInt("productPrice");
        String allergensStr = resultSet.getString("allergens");
        Allergens allergens = Allergens.valueOf(allergensStr);
        return new Desserts(productName, productPrice, allergens, productId);
    }

    @Override
    public Desserts read(Integer id) {
        String sql = "SELECT * FROM Products p JOIN Desserts d ON p.productID = d.productID WHERE p.productID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            } else {
                return null; // No dessert found
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Desserts WHERE productID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        super.delete(id);
    }

    @Override
    public void update(Desserts dessert) {
        super.update(dessert);
        String sql = "UPDATE Desserts SET allergens = ? WHERE productID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, dessert.getAllergens().name());
            statement.setInt(2, dessert.getProdId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Desserts> getAll() {
        String sql = "SELECT * FROM Products p JOIN Desserts d ON p.productID = d.productID";
        List<Desserts> dessertsList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                dessertsList.add(extractFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dessertsList;
    }
}