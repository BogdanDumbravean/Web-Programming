package webubb.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import webubb.domain.Answer;
import webubb.domain.Question;
import webubb.domain.User;
import webubb.model.DBManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class QuizController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        if (user==null || user.equals("")) {
                return;
        }

        if ((action != null) && action.equals("getQuiz")) {
            JSONArray oldQuestions = (JSONArray)session.getAttribute("questions");
            if (oldQuestions!=null && !oldQuestions.equals("")) {
                PrintWriter out = new PrintWriter(response.getOutputStream());
                out.println(oldQuestions.toJSONString());
                out.flush();
                return;
            }


            int questionCount = (int)session.getAttribute("questionNr");
            //System.out.println("QuizController questionCount: " + questionCount);
            //int questionCount = Integer.parseInt(request.getParameter("questionCount"));

            response.setContentType("application/json");
            DBManager dbmanager = new DBManager();
            ArrayList<Question> questions = dbmanager.getQuestions();
            Collections.shuffle(questions);

            JSONArray jsonQuestions = new JSONArray();
            for (int i = 0; i < questionCount; i++) {
                JSONObject jObj = new JSONObject();
                JSONArray jsonAnswers = new JSONArray();
                //JSONObject correctAnswer = new JSONObject();
                for (Answer a: questions.get(i).getAnswers()) {
                    JSONObject answer = new JSONObject();
                    answer.put("id", a.getId());
                    answer.put("content", a.getContent());
                    answer.put("isCorrect", a.isCorrect());
                    jsonAnswers.add(answer);
                    /*if(a.isCorrect()) {
                        correctAnswer.put("id", a.getId());
                        correctAnswer.put("content", a.getContent());
                    }*/
                }
                jObj.put("id", questions.get(i).getId());
                jObj.put("content", questions.get(i).getContent());
                jObj.put("answers", jsonAnswers);
                //jObj.put("correctAnswer", correctAnswer);
                jsonQuestions.add(jObj);
            }
            session.setAttribute("questions", jsonQuestions);
            PrintWriter out = new PrintWriter(response.getOutputStream());

            //System.out.println("QuizController send pageQuestions (not sending): " + (int)session.getAttribute("pageQuestions"));
            //out.println((int)session.getAttribute("pageQuestions"));
            out.println(jsonQuestions.toJSONString());
            out.flush();

        } else if ((action) != null && action.equals("highscore")) {
            // If we get highscore the test is finished
            session.removeAttribute("pageQuestions");
            session.removeAttribute("questions");

            // We update an asset
            int score = Integer.parseInt(request.getParameter("score"));

            DBManager dbmanager = new DBManager();
            int highscore = dbmanager.getHighscore(user.getId());
            if(score > highscore) {
                highscore = score;
                dbmanager.setHighscore(user.getId(), score);
            }
            PrintWriter out = new PrintWriter(response.getOutputStream());
            out.println(highscore);

            out.flush();
        } else if ((action != null) && action.equals("finishQuiz")) {
            session.removeAttribute("pageQuestions");
            session.removeAttribute("questions");
            //System.out.println("QC finishQuiz reached");
//            RequestDispatcher rd = null;
//            getServletContext().getRequestDispatcher("/quizEx/prepareQuestions.jsp").forward(request, response);
//            response.sendRedirect("/quizEx/prepareQuestions.jsp");
//            rd = request.getRequestDispatcher("/quizEx/prepareQuestions.jsp");
//            rd.forward(request, response);
        } else if ((action != null) && action.equals("getQuestionsPerPage")) {
            JSONArray oldQuestions = (JSONArray)session.getAttribute("questions");
            if (oldQuestions!=null && !oldQuestions.equals("")) {
                PrintWriter out = new PrintWriter(response.getOutputStream());
                out.println((int)session.getAttribute("pageQuestions"));
                out.flush();
                return;
            }


            PrintWriter out = new PrintWriter(response.getOutputStream());
            out.println(session.getAttribute("pageQuestions"));

            out.flush();
        }
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        int questionNr = Integer.parseInt(request.getParameter("questionNr"));
        int pageQuestions = Integer.parseInt(request.getParameter("pageQuestions"));
        //String path = request.getParameter("target").toString();

        //System.out.println("QuizController.questionsNr:" + questionNr);
        //System.out.println("QuizController.pageQuestions:" + pageQuestions);
        //System.out.println("QuizController.path:" + path);

        HttpSession session = request.getSession();
        //if(path.equals("/quizEx/quizEx.jsp")) {
        session.setAttribute("questionNr", questionNr);
        session.setAttribute("pageQuestions", pageQuestions);
//        } else {
//            session.removeAttribute("pageQuestions");
//            session.removeAttribute("questions");
//        }
        RequestDispatcher rd = null;
        rd = request.getRequestDispatcher("/quizEx/quizEx.jsp");
        rd.forward(request, response);
    }

    /*protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ((action != null) && action.equals("update")) {
            // We update an asset
            Asset asset = new Asset(Integer.parseInt(request.getParameter("id")),
                    Integer.parseInt(request.getParameter("userid")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("value")));
            DBManager dbmanager = new DBManager();
            Boolean result = dbmanager.updateAsset(asset);
            PrintWriter out = new PrintWriter(response.getOutputStream());
            if (result == true) {
                out.println("Update asset succesfully.");
            } else {
                out.println("Error updating asset!");
            }
            out.flush();
        } else if ((action != null) && action.equals("getAll")) {
            int userid = Integer.parseInt(request.getParameter("userid"));

            response.setContentType("application/json");
            DBManager dbmanager = new DBManager();
            ArrayList<Asset> assets = dbmanager.getUserAssets(userid);
            JSONArray jsonAssets = new JSONArray();
            for (int i = 0; i < assets.size(); i++) {
                JSONObject jObj = new JSONObject();
                jObj.put("id", assets.get(i).getId());
                jObj.put("userid", assets.get(i).getUserid());
                jObj.put("description", assets.get(i).getDescription());
                jObj.put("value", assets.get(i).getValue());
                jsonAssets.add(jObj);
            }
            PrintWriter out = new PrintWriter(response.getOutputStream());
            out.println(jsonAssets.toJSONString());
            out.flush();
        }
    }*/
}
