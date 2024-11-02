package Model;

public abstract class User implements HasId {
    private int points;
    private int userID;
    private String email;
    private String name;

    Offer offers;
    Order order;

    public User(String email, String name, int userID) {
        this.email = email;
        this.name = name;
        this.userID = userID;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

}