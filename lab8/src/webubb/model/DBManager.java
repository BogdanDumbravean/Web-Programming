package webubb.model;

import webubb.domain.Answer;
import webubb.domain.Question;
import webubb.domain.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by forest.
 */
public class DBManager {
    private Statement stmt;

    public DBManager() {
        connect();
    }

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/webquiz", "root", "");
            stmt = con.createStatement();
        } catch(Exception ex) {
            System.out.println("Connect Error:"+ex.getMessage());
            ex.printStackTrace();
        }
    }

    public User authenticate(String username, String password) {
        ResultSet rs;
        User u = null;
        System.out.println(username+" "+password);
        try {
            rs = stmt.executeQuery("select * from users where username='"+username+"' and password='"+password+"'");
            if (rs.next()) {
                u = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getInt("highscore"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    public int getHighscore(int userId) {
        ResultSet rs;
        int h = 0;
        try {
            rs = stmt.executeQuery("select highscore from users where id="+userId);
            if (rs.next()) {
                h = rs.getInt("highscore");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return h;
    }

    public void setHighscore(int userId, int newHighscore) {
        try {
            stmt.executeUpdate("update users set highscore=" + newHighscore + " where id="+userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Answer> getAnswers(int questionId) {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("select * from answers where questionId =" + questionId);
            while (rs.next()) {
                Answer a = new Answer(
                        rs.getInt("id"),
                        rs.getInt("questionId"),
                        rs.getString("content"),
                        rs.getBoolean("isCorrect")
                );
                answers.add(a);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answers;
    }

    public ArrayList<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<Question>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("select * from questions");
            while (rs.next()) {
                Question q = new Question(
                        rs.getInt("id"),
                        rs.getString("content"),
                        new ArrayList<Answer>()
                );
                questions.add(q);
            }
            rs.close();
            for(Question q : questions) {
                q.setAnswers(getAnswers(q.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
}