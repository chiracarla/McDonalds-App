package Repository;

import Model.HasId;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import java.io.*;
import java.util.*;

public abstract class FileRepository<T extends HasId> implements IRepository<T> {
    private final String filePath;
    private Map<Integer, T> data;

    public FileRepository(String filePath) {
        this.filePath = filePath;
        this.data = loadFromFile();
    }

    @Override
    public void create(T obj) {
        if (data.containsKey(obj.getId())) {
            throw new IllegalArgumentException("Object with ID " + obj.getId() + " already exists.");
        }
        data.put(obj.getId(), obj);
        saveToFile();
    }

    @Override
    public void update(T obj) {
        if (!data.containsKey(obj.getId())) {
            throw new IllegalArgumentException("Object with ID " + obj.getId() + " does not exist.");
        }
        data.put(obj.getId(), obj);
        saveToFile();
    }

    @Override
    public void delete(Integer id) {
        if (!data.containsKey(id)) {
            throw new IllegalArgumentException("Object with ID " + id + " does not exist.");
        }
        data.remove(id);
        saveToFile();
    }

    @Override
    public T read(Integer id) {
        return data.get(id);
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(data.values());
    }

    protected abstract String toFile(T obj); // Convert object to string
    protected abstract T fromFile(String data); // Convert string to object

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