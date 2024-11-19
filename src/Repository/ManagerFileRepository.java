package Repository;
import Enums.ManagerRank;
import Model.Manager;

public class ManagerFileRepository extends FileRepository<Manager>{
    public ManagerFileRepository(String filePath) {
        super(filePath);
    }

    @Override
    protected String toFile(Manager obj) {
        return obj.getId() + "," + obj.getEmail() + "," +
                obj.getName() + "," + obj.getPassword() + "," +
                obj.getRank();
    }

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
