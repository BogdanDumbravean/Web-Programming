package webubb.domain;

/**
 * Created by forest.
 */
public class User {
    private int id;
    private String username;
    private String password;
    private int highscore;

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public User(int id, String username, String password, int highscore){
        this.id = id;
        this.username = username;
        this.password = password;
        this.highscore = highscore;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id=id; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}