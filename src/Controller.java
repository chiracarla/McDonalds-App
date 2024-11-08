import Model.*;
public class Controller {
    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    public void sign_Up_Client(String email, String name, String password) {
        service.sign_up_client(email, name, password);
    }

    public void sign_Up_Manager(String email, String name, String password, String rank) {
        service.sign_up_manager(email, name, password, rank);
    }

    public void sign_Up_Employee(String email, String name, String password, Manager manager) {
        service.sign_up_employee(email, name, password, manager);
    }

    public void sign_In(String email, String password) {
        service.sign_in(email, password);
    }

    public void delete_Account(String email, String password) {
        service.delete_account(email, password);
    }

}
