package Repository;
import Model.Client;
public class ClientFileRepository extends FileRepository<Client>{
    public ClientFileRepository(String filePath) {
        super(filePath);
    }

    @Override
    protected String toFile(Client obj) {
        return obj.getId() + "," + obj.getEmail() + "," +
                obj.getName() + "," + obj.getPassword();
    }

    @Override
    protected Client fromFile(String data) {
        String[] parts = data.split(",");
        int id = Integer.parseInt(parts[0]);
        String email = parts[1];
        String name = parts[2];
        String password = parts[3];
        return new Client(email, name, id, password);
    }
}
