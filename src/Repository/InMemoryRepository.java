package Repository;

import Model.HasId;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * In-memory implementation of the IRepo interface.
 *
 * @param <T> the type of entity
 */
public class InMemoryRepository<T extends HasId> implements IRepository<T> {
    private final Map<Integer, T> data = new HashMap<>();

    @Override
    public void create(T obj) {
        data.putIfAbsent(obj.getId(), obj);
    }

    @Override
    public T read(Integer id) {
        return data.get(id);
    }

    @Override
    public void update(T obj) {
        data.replace(obj.getId(), obj);
    }

    @Override
    public void delete(Integer id) {
        data.remove(id);
    }

    @Override
    public List<T> getAll() {
        return data.values().stream().toList();
    }
}
