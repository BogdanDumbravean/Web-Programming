<%@ page import="webubb.domain.User" %>
<%@ page import="org.json.simple.JSONArray" %>
<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: forest
  Date: 5/17/2018
  Time: 7:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
  <head>
      <meta charset="UTF-8">
      <title>Quiz</title>
      <style>
          form {
              margin-left: auto;
              margin-right: auto;
              width: 400px;
          }
      </style>
      <script src="./js/jquery-2.0.3.js"></script>
  </head>
  <body>
  <%! User user; %>
  <%
    user = (User) session.getAttribute("user");
    if (user != null) {
        RequestDispatcher rd = null;

        JSONArray oldQuestions = (JSONArray)session.getAttribute("questions");
        if (oldQuestions!=null) {
            rd = request.getRequestDispatcher("/quizEx/quizEx.jsp");
        } else {
            rd = request.getRequestDispatcher("/quizEx/prepareQuestions.jsp");
        }
        rd.forward(request,response);
    } else {
        %>

  <form action="LoginController" method="post">
    Enter username : <input type="text" name="username"> <BR>
    Enter password : <input type="password" name="password"> <BR>
    <input type="submit" value="Login"/>
  </form>
  <%
      for(Cookie c : request.getCookies()) {
          if(c.getName().equals("username")) {
              %>
              <script>
                  $('input[name="username"]').val('<%=c.getValue()%>');
              </script>
              <%
          }
          if(c.getName().equals("password")) {
              %>
              <script>
                  $('input[name="password"]').val('<%=c.getValue()%>');
              </script>
              <%
              }
      }
  }
  %>
  </body>
</html>
