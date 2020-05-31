package webubb.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class QuizController2 extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        RequestDispatcher rd = null;
        if(request.getParameter("finish").toString().equals("Log Out")) {
            session.invalidate();
            rd = request.getRequestDispatcher("/index.jsp");
        } else {
            session.removeAttribute("pageQuestions");
            session.removeAttribute("questions");
            rd = request.getRequestDispatcher("/quizEx/prepareQuestions.jsp");
        }
        rd.forward(request, response);
    }
}
