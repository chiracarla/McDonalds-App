package Repository;

import Model.HasId;

import java.util.List;
/**
 * Interface representing a repository for managing entities.
 *
 * @param <T> the type of entity
 */
public interface IRepository <T extends HasId> {
    /**
     * Adds an entity to the repository.
     *
     * @param obj the entity to add
     */
    void create(T obj);

    /**
     * Reads the elements
     * @param id
     * @return
     */
    T read(Integer id);

    /**
     * Updates the entity in the repository
     * @param obj
     */
    void update(T obj);
    /**
     * Removes an entity from the repository.
     *
     * @param id the entity with the given id to remove
     */
    void delete(Integer id);
    /**
     * Gets all entities in the repository.
     *
     * @return a list of all entities
     */
    List<T> getAll();
}
