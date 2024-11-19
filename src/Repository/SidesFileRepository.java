package Repository;

import Enums.DishSize;
import Model.SideDish;

public class SidesFileRepository extends FileRepository<SideDish>{
    public SidesFileRepository(String filePath){
        super(filePath);
    }

    @Override
    protected String toFile(SideDish obj) {
        return obj.getId() + "," + obj.getProductName() + "," +
                obj.getProductPrice() + "," + obj.getSize();
    }

    @Override
    protected SideDish fromFile(String data) {
        String[] parts = data.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        int price = Integer.parseInt(parts[2]);
        DishSize size = DishSize.valueOf(parts[3]);
        return new SideDish(name, price, size, id);
    }
}
