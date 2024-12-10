package Repository.DbRepository;

import Exceptions.DatabaseException;
import Model.MainDish;
import Enums.DishSize;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MainDishDBRepository extends ProductsDBRepository<MainDish> {
    public MainDishDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected MainDish extractFromResultSet(ResultSet resultSet) throws SQLException {
        int productId = resultSet.getInt("productID");
        String productName = resultSet.getString("productName");
        int productPrice = resultSet.getInt("productPrice");
        int calories = resultSet.getInt("calories");
        String dishSizeStr = resultSet.getString("dishSize");
        DishSize dishSize = DishSize.valueOf(dishSizeStr);
        return new MainDish(productName, productPrice, calories, dishSize, productId);
    }
    @Override
    public void create(MainDish mainDish) {
        super.create(mainDish);
        String sql = "INSERT INTO MainDishes (productID, calories, dishSize) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, mainDish.getProdId());
            statement.setInt(2, mainDish.getCalories());
            statement.setString(3, mainDish.getSize().name());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MainDish read(Integer id) {
        String sql = "SELECT * FROM Products p JOIN MainDishes m ON p.productID = m.productID WHERE p.productID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            } else {
                return null; // No main dish found
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(MainDish mainDish) {
        super.update(mainDish);
        String sql = "UPDATE MainDishes SET calories = ?, dishSize = ? WHERE productID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, mainDish.getCalories());
            statement.setString(2, mainDish.getSize().name());
            statement.setInt(3, mainDish.getProdId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM MainDishes WHERE productID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        super.delete(id);
    }

    public List<MainDish> getAll() {
        String sql = "SELECT * FROM Products p JOIN MainDishes m ON p.productID = m.productID";
        List<MainDish> mainDishesList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                mainDishesList.add(extractFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return mainDishesList;
    }
}