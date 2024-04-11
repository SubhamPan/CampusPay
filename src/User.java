// this class will store the user information
// user has id and password
// this will be used throughout the application, so we will make it a singleton

public class User {
    private static User user;
    private String id;

    private int role;
    private String password;

    private User() {
        // private constructor
    }

    public static User getInstance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getRole() {
        return role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void clear() {
        id = null;
        role = -1;
        password = null;
    }
}
