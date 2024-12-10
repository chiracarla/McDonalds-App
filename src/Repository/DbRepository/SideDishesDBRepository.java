package Repository.DbRepository;

import Exceptions.DatabaseException;
import Model.SideDish;
import Enums.DishSize;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SideDishesDBRepository extends ProductsDBRepository<SideDish> {
    public SideDishesDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected SideDish extractFromResultSet(ResultSet resultSet) throws SQLException {
        int productId = resultSet.getInt("productID");
        String productName = resultSet.getString("productName");
        int productPrice = resultSet.getInt("productPrice");
        String dishSizeStr = resultSet.getString("dishSize");
        DishSize dishSize = DishSize.valueOf(dishSizeStr);
        return new SideDish(productName, productPrice, dishSize, productId);
    }

    @Override
    public void create(SideDish sideDish) {
        super.create(sideDish);
        String sql = "INSERT INTO SideDishes (productID, dishSize) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, sideDish.getProdId());
            statement.setString(2, sideDish.getSize().name());
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public SideDish read(Integer id) {
        String sql = "SELECT * FROM Products p JOIN SideDishes s ON p.productID = s.productID WHERE p.productID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            } else {
                return null; // No side dish found
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void update(SideDish sideDish) {
        super.update(sideDish);
        String sql = "UPDATE SideDishes SET dishSize = ? WHERE productID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, sideDish.getSize().name());
            statement.setInt(2, sideDish.getProdId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM SideDishes WHERE productID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        super.delete(id);
    }

    public List<SideDish> getAll() {
        String sql = "SELECT * FROM Products p JOIN SideDishes s ON p.productID = s.productID";
        List<SideDish> sideDishesList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                sideDishesList.add(extractFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return sideDishesList;
    }
}