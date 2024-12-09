package Repository.DbRepository;

import Model.ConcreteProduct;
import Model.Offer;
import Model.Product;
import Repository.DBRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffersDBRepository extends DBRepository<Offer> {
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;
    private Connection connection;

    public OffersDBRepository(String dbUrl, String dbUser, String dbPassword) {
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

    private Offer extractFromResultSet(ResultSet resultSet) throws SQLException {
        int offerId = resultSet.getInt("offerId");
        int originalPrice = resultSet.getInt("originalPrice");
        int newPrice = resultSet.getInt("newPrice");

        // Extract Products
        List<Product> products = new ArrayList<>();
        do {
            int productId = resultSet.getInt("productId");
            String productName = resultSet.getString("productName");
            int productPrice = resultSet.getInt("productPrice");
            products.add(new ConcreteProduct(productName, productPrice, productId));
        } while (resultSet.next() && resultSet.getInt("offerId") == offerId);

        return new Offer(originalPrice, newPrice, products, offerId);
    }

    @Override
    public Offer read(Integer offerId) {
        String sql = "SELECT o.offerId, o.originalPrice, o.newPrice, " +
                "p.productId, p.productName, p.productPrice " +
                "FROM Offers o " +
                "JOIN OfferProducts op ON o.offerId = op.offerId " +
                "JOIN Products p ON op.productId = p.productId " +
                "WHERE o.offerId = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, offerId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            } else {
                return null; // No offer found
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Offer offer) {
        String offerSql = "INSERT INTO Offers (offerId, originalPrice, newPrice) VALUES (?, ?, ?)";
        String offerProductsSql = "INSERT INTO OfferProducts (offerId, productId) VALUES (?, ?)";

        try (PreparedStatement offerStatement = connection.prepareStatement(offerSql);
             PreparedStatement offerProductsStatement = connection.prepareStatement(offerProductsSql)) {

            // Insert into Offers table
            offerStatement.setInt(1, offer.getOfferId());
            offerStatement.setInt(2, offer.getOriginalPrice());
            offerStatement.setInt(3, offer.getNewPrice());
            offerStatement.executeUpdate();

            // Insert into OfferProducts table
            for (Product product : offer.getProducts()) {
                offerProductsStatement.setInt(1, offer.getOfferId());
                offerProductsStatement.setInt(2, product.getProdId());
                offerProductsStatement.addBatch();
            }
            offerProductsStatement.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Offer offer) {
        String offerSql = "UPDATE Offers SET originalPrice = ?, newPrice = ? WHERE offerId = ?";
        String deleteOfferProductsSql = "DELETE FROM OfferProducts WHERE offerId = ?";
        String insertOfferProductsSql = "INSERT INTO OfferProducts (offerId, productId) VALUES (?, ?)";

        try (PreparedStatement offerStatement = connection.prepareStatement(offerSql);
             PreparedStatement deleteOfferProductsStatement = connection.prepareStatement(deleteOfferProductsSql);
             PreparedStatement insertOfferProductsStatement = connection.prepareStatement(insertOfferProductsSql)) {

            // Update Offers table
            offerStatement.setInt(1, offer.getOriginalPrice());
            offerStatement.setInt(2, offer.getNewPrice());
            offerStatement.setInt(3, offer.getOfferId());
            offerStatement.executeUpdate();

            // Delete existing OfferProducts entries
            deleteOfferProductsStatement.setInt(1, offer.getOfferId());
            deleteOfferProductsStatement.executeUpdate();

            // Insert new OfferProducts entries
            for (Product product : offer.getProducts()) {
                insertOfferProductsStatement.setInt(1, offer.getOfferId());
                insertOfferProductsStatement.setInt(2, product.getProdId());
                insertOfferProductsStatement.addBatch();
            }
            insertOfferProductsStatement.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer offerId) {
        String deleteOfferProductsSql = "DELETE FROM OfferProducts WHERE offerId = ?";
        String deleteOfferSql = "DELETE FROM Offers WHERE offerId = ?";

        try (PreparedStatement deleteOfferProductsStatement = connection.prepareStatement(deleteOfferProductsSql);
             PreparedStatement deleteOfferStatement = connection.prepareStatement(deleteOfferSql)) {

            // Delete from OfferProducts table
            deleteOfferProductsStatement.setInt(1, offerId);
            deleteOfferProductsStatement.executeUpdate();

            // Delete from Offers table
            deleteOfferStatement.setInt(1, offerId);
            deleteOfferStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Offer> getAll() {
        String sql = "SELECT o.offerId, o.originalPrice, o.newPrice, " +
                "p.productId, p.productName, p.productPrice " +
                "FROM Offers o " +
                "JOIN OfferProducts op ON o.offerId = op.offerId " +
                "JOIN Products p ON op.productId = p.productId";

        List<Offer> offers = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Offer offer = extractFromResultSet(resultSet);
                offers.add(offer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return offers;
    }
}