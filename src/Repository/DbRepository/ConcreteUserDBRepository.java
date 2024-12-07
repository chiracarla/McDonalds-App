package Repository.DbRepository;

import Model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
public class ConcreteUserDBRepository extends UserDBRepository<ConcreteUser> {
    public ConcreteUserDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected ConcreteUser extractFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("userID");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String type = resultSet.getString("userType");
//userID, name, email, password, points, userType
        return new ConcreteUser(email, name, id, password);
    }
}
