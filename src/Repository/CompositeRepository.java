package Repository;

import Model.HasId;

import java.util.List;
/**
 * Interface representing a repository for managing entities.
 *Uses two repositories to manage the entities
 * InMemoryRepo and InFileRepo
 * @param <T> the type of entity
 */
public class CompositeRepository<T extends HasId> implements IRepository<T> {
    private final IRepository<T> inMemoryRepo;
    private final IRepository<T> inFileRepo;

    public CompositeRepository(IRepository<T> inMemoryRepo, IRepository<T> inFileRepo) {
        this.inMemoryRepo = inMemoryRepo;
        this.inFileRepo = inFileRepo;
    }

    @Override
    public void create(T obj) {
        inMemoryRepo.create(obj);
        inFileRepo.create(obj);
    }

    @Override
    public T read(Integer id) {
        return inMemoryRepo.read(id); // Prioritize in-memory for read speed

    }

    @Override
    public List<T> getAll() {
        return inMemoryRepo.getAll(); // Return in-memory data
    }

    @Override
    public void update(T obj) {
        inMemoryRepo.update(obj);
        inFileRepo.update(obj);
    }

    @Override
    public void delete(Integer id) {
        inMemoryRepo.delete(id);
        inFileRepo.delete(id);
    }

}
