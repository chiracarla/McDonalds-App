package Repository;
import Model.Client;
/**
 * The {@code ClientFileRepository} class extends the {@code FileRepository} class
 * and provides specific implementation for the {@code Client} entity.
 */
public class ClientFileRepository extends FileRepository<Client>{
    public ClientFileRepository(String filePath) {
        super(filePath);
    }

    /**
     * Converts a {@code Client} entity to a string representation for storage.
     * @param obj the entity to convert
     * @return
     */
    @Override
    protected String toFile(Client obj) {
        return obj.getId() + "," + obj.getEmail() + "," +
                obj.getName() + "," + obj.getPassword();
    }

    /**
     * Converts a string representation of a {@code Client} entity to an actual entity.
     * @param data the string representation of the entity
     * @return
     */
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
