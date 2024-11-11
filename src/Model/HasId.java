package Model;

import java.io.Serializable;
//oare nu putem sa l lasam cu serializable si sa nu mai facem aceasta interfata?
public interface HasId extends Serializable {
    Integer getId();
}
