package webubb.controller;

/**
 * Created by forest.
 */


import webubb.domain.User;
import webubb.model.DBManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;


public class LoginController extends HttpServlet {

    public LoginController() {
        super();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        RequestDispatcher rd = null;

        DBManager dbmanager = new DBManager();
        User user = dbmanager.authenticate(username, password);
        if (user != null) {
            Cookie c = new Cookie("username", user.getUsername());
            Cookie c2 = new Cookie("password", user.getPassword());
            response.addCookie(c);
            response.addCookie(c2);
            rd = request.getRequestDispatcher("/quizEx/prepareQuestions.jsp");
            //request.setAttribute("user", user);
            // Here we should set the "user" attribute on the session like this:
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            // .. and then, in all JSP/Servlet pages we should check if the "user" attribute exists in the session
            // and if not, we should return/exit the method:
            // HttpSession session = request.getSession();
            // String user = session.getAttribute("user");
            // if (user==null || user.equals("")) {
            //        return;
            // }
        } else {
            rd = request.getRequestDispatcher("/error.jsp");
        }
        rd.forward(request, response);
    }

}