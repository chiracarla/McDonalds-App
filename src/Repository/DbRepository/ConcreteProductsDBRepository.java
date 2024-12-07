package Repository.DbRepository;

import Model.ConcreteProduct;
import Model.Product;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConcreteProductsDBRepository extends ProductsDBRepository<Product> {

    public ConcreteProductsDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected Product extractFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("productID");
        String name = resultSet.getString("productName");
        int price = resultSet.getInt("productPrice");

        return new ConcreteProduct(name, price, id);
    }
}