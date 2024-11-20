package Repository;

import Model.HasId;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import java.io.*;
import java.util.*;
/**
 * Abstract class for file-based repository implementation.
 *
 * @param <T> the type of entity
 */
public abstract class FileRepository<T extends HasId> implements IRepository<T> {
    private final String filePath;
    private Map<Integer, T> data;
    /**
     * Constructs a FileRepository with the specified file path.
     *
     * @param filePath the path to the file
     */
    public FileRepository(String filePath) {
        this.filePath = filePath;
        this.data = loadFromFile();
    }
    /**
     * Creates a new entity in the repository.
     *
     * @param obj the entity to create
     * @throws IllegalArgumentException if an entity with the same ID already exists
     */
    @Override
    public void create(T obj) {
        if (data.containsKey(obj.getId())) {
            throw new IllegalArgumentException("Object with ID " + obj.getId() + " already exists.");
        }
        data.put(obj.getId(), obj);
        saveToFile();
    }
    /**
     * Updates an existing entity in the repository.
     *
     * @param obj the entity to update
     * @throws IllegalArgumentException if the entity does not exist
     */
    @Override
    public void update(T obj) {
        if (!data.containsKey(obj.getId())) {
            throw new IllegalArgumentException("Object with ID " + obj.getId() + " does not exist.");
        }
        data.put(obj.getId(), obj);
        saveToFile();
    }
    /**
     * Deletes an entity from the repository.
     *
     * @param id the ID of the entity to delete
     * @throws IllegalArgumentException if the entity does not exist
     */
    @Override
    public void delete(Integer id) {
        if (!data.containsKey(id)) {
            throw new IllegalArgumentException("Object with ID " + id + " does not exist.");
        }
        data.remove(id);
        saveToFile();
    }
    /**
     * Reads an entity from the repository by its ID.
     *
     * @param id the ID of the entity to read
     * @return the entity with the specified ID, or null if not found
     */
    @Override
    public T read(Integer id) {
        return data.get(id);
    }
    /**
     * Retrieves all entities from the repository.
     *
     * @return a list of all entities
     */
    @Override
    public List<T> getAll() {
        return new ArrayList<>(data.values());
    }
    /**
     * Converts an entity to a string for file storage.
     *
     * @param obj the entity to convert
     * @return the string representation of the entity
     */
    protected abstract String toFile(T obj); // Convert object to string
    /**
     * Converts a string from the file to an entity.
     *
     * @param data the string representation of the entity
     * @return the entity
     */
    protected abstract T fromFile(String data); // Convert string to object

    /**
     * Saves the data to the file.
     */
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (T obj : data.values()) {
                writer.write(toFile(obj));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving to file", e);
        }
    }
    /**
     * Loads the data from the file.
     *
     * @return the data loaded from the file
     */
    private Map<Integer, T> loadFromFile() {
        Map<Integer, T> result = new HashMap<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return result;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                T obj = fromFile(line);
                result.put(obj.getId(), obj);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading from file", e);
        }
        return result;
    }
}