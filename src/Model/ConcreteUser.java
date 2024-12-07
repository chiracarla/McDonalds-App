package Model;
import Model.User;

public class ConcreteUser extends User{
    public ConcreteUser(String email, String name, int id, String password) {
        super(email, name, id, password);
    }

    @Override
    public String getUserType() {
        return "ConcreteUser";
    }

    @Override
    public Integer getId() {
        return getUserID();
    }
}
