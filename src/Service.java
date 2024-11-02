import Repository.*;
import Model.*;

import javax.swing.*;
import java.util.List;

public class Service {
    private final IRepository<User> userRepo;

    public Service(IRepository<User> userRepo) {
        this.userRepo = userRepo;
    }

    public void sign_up(String username, String phone) {

    }

}
// sign up
// sign in
// update account
// del account
// make order in-store/online
//