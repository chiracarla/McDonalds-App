import Repository.*;
import Model.*;
import java.util.Optional;

public class Service {
    private final IRepository<User> userRepo;

    public Service(IRepository<User> userRepo) {
        this.userRepo = userRepo;
    }

    public void sign_up_client(String email, String name, String password) {
        Optional<User> existingUser = userRepo.getAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();

        if (existingUser.isPresent()) {
            System.out.println("This email is already in use");
            return;
        }

        int newUserID = generateNewUserId();
        User newUser = new Client(email, name, newUserID, password);
        userRepo.create(newUser);
        System.out.println("Client signed up successfully!");
    }

    public void sign_up_manager(String email, String name, String password, String rank) {
        Optional<User> existingUser = userRepo.getAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();

        if (existingUser.isPresent()) {
            System.out.println("This email is already in use");
            return;
        }

        int newUserID = generateNewUserId();
        User newUser = new Manager(email, name, newUserID, password, rank);
        userRepo.create(newUser);
        System.out.println("Manager signed up successfully!");
    }

    public void sign_up_employee(String email, String name, String password, Manager manager) {
        Optional<User> existingUser = userRepo.getAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();

        if (existingUser.isPresent()) {
            System.out.println("This email is already in use");
            return;
        }

        int newUserID = generateNewUserId();
        User newUser = new Employee(email, name, newUserID, password, manager);
        userRepo.create(newUser);
        System.out.println("Client signed up successfully!");
    }


        private int generateNewUserId() {
        return userRepo.getAll().stream()
                .mapToInt(User::getUserID)
                .max()
                .orElse(0) + 1;
        }

    public void sign_in(String email, String password) {
        Optional<User> existingUser = userRepo.getAll().stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password)) // Check for email and password
                .findFirst();

        if (existingUser.isPresent()) {
            User user = existingUser.get();

            if (user instanceof Client) {
                System.out.println("Successfully signed in as Client!");
            }
            else if (user instanceof Employee) {
                System.out.println("Successfully signed in as Employee!");
            }
            else if (user instanceof Manager) {
                System.out.println("Successfully signed in as Manager!");
            }
            else {
                System.out.println("User type is unknown!");
            }
        } else {
            System.out.println("User not found or incorrect password!");
        }
    }


    public void delete_account(String email, String password) {
        Optional<User> existingUser = userRepo.getAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();

        if (existingUser.isPresent()) {
            userRepo.delete(existingUser.get().getUserID());
            System.out.println("Account successfully deleted!");
        }
        else {
            System.out.println("User not found or incorrect credentials!");
        }
    }

}
// update account
// make order in-store/online