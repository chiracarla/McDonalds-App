package Model;

//import java.lang.runtime.ReferenceKey;
import java.util.ArrayList;
import java.util.List;

public abstract class User implements HasId {
    private int points;
    private int userID;
    private String email;
    private String name;
    private String password;
    List<Offer> offers;
    /**
     * Constructs a User with the specified email, name, user ID, and password.
     *
     * @param email the email of the user
     * @param name the name of the user
     * @param userID the ID of the user
     * @param password the password of the user
     */
    public User(String email, String name, int userID, String password) {
        this.email = email;
        this.name = name;
        this.userID = userID;
        this.password = password;
        offers = new ArrayList<>();
    }
    /**
     * Gets the points of the user.
     *
     * @return the points of the user
     */
    public int getPoints() {
        return points;
    }
    /**
     * Sets the points of the user.
     *
     * @param points the points to set
     */
    public void setPoints(int points) {
        this.points = points;
    }
    /**
     * Gets the email of the user.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets the email of the user.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     *Other kind of Getters and Setters used for the User
     *
     */
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID){ this.userID = userID; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addOffer(Offer offer) {
        offers.add(offer);
    }

    public void removeOffer(Offer offer) {
        offers.remove(offer);
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int subtractPoints(int points) {
        this.points -= points;
        return this.points - points;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    /**
     * Returns a string representation of the user.
     *
     * @return a string representation of the user
     */
    @Override
    public String toString() {
        return "User{" +
                "points=" + points +
                ", userID=" + userID +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", offers=" + offers +
                '}';
    }

}