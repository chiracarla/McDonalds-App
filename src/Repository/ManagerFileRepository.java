package Repository;
import Enums.ManagerRank;
import Model.Manager;

/**
 * The {@code ManagerFileRepository} class extends the {@code FileRepository} class
 * and provides specific methods for managing {@code Manager} entities in the application.
 */
public class ManagerFileRepository extends FileRepository<Manager>{
    public ManagerFileRepository(String filePath) {
        super(filePath);
    }

    /**
     * Converts a {@code Manager} entity to a string representation for storage in a file.
     * @param obj the entity to convert
     * @return
     */
    @Override
    protected String toFile(Manager obj) {
        return obj.getId() + "," + obj.getEmail() + "," +
                obj.getName() + "," + obj.getPassword() + "," +
                obj.getRank();
    }

    /**
     * Creates a {@code Manager} entity from a string representation in a file.
     * @param data the string representation of the entity
     * @return
     */
    @Override
    protected Manager fromFile(String data) {
        String[] parts = data.split(",");
        int id = Integer.parseInt(parts[0]);
        String email = parts[1];
        String name = parts[2];
        String password = parts[3];
        ManagerRank rank = ManagerRank.valueOf(parts[4]);
        return new Manager(email, name, id, password, rank);
    }
}
