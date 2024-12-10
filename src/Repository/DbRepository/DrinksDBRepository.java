package Repository.DbRepository;

import Enums.DrinkVolume;
import Exceptions.DatabaseException;
import Model.Drinks;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DrinksDBRepository extends ProductsDBRepository<Drinks> {
    public DrinksDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected Drinks extractFromResultSet(ResultSet resultSet) throws SQLException {
        int productId = resultSet.getInt("productID");
        String productName = resultSet.getString("productName");
        int productPrice = resultSet.getInt("productPrice");
        String volumeStr = resultSet.getString("volume");
        DrinkVolume volume = DrinkVolume.valueOf(volumeStr);
        return new Drinks(productName, productPrice, volume, productId);
    }

    @Override
    public void create(Drinks drink) {
        super.create(drink);
        String sql = "INSERT INTO Drinks (productID, volume) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, drink.getProdId());
            statement.setString(2, drink.getVolume().name());
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Drinks read(Integer id) {
        String sql = "SELECT * FROM Products p JOIN Drinks d ON p.productID = d.productID WHERE p.productID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Drinks WHERE productID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
//        super.delete(id);
    }

    public void update(Drinks drink) {
        super.update(drink);
        String sql = "UPDATE Drinks SET volume = ? WHERE productID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, drink.getVolume().name());
            statement.setInt(2, drink.getProdId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public List<Drinks> getAll() {
        String sql = "SELECT * FROM Products p JOIN Drinks d ON p.productID = d.productID";
        List<Drinks> drinksList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                drinksList.add(extractFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return drinksList;
    }
}