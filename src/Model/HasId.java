package Model;

import java.io.Serializable;
/**
 * Interface representing an entity with an ID.
 */
public interface HasId extends Serializable {
    /**
     * gets the ID
     * @return
     */
    Integer getId();

//    String toFile();
}
