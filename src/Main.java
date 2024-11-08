import Model.User;
import Repository.IRepository;
import Repository.InMemoryRepository;

public class Main {
    public static void main(String[] args) {
        IRepository<User> userRepo = new InMemoryRepository<>();
        Service service = new Service(userRepo);
        Controller controller = new Controller(service);

        controller.sign_Up_Manager("klara.orban@yahoo.com", "Orban Klara", "1234", "Top manager" );
        controller.sign_Up_Client("chira.carla@gmail.com", "Chira Carla", "5678");

        controller.sign_In("klara.orban@yahoo.com", "1234");

    }
}