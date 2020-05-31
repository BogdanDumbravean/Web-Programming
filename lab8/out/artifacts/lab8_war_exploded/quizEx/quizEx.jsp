<%@ page import="webubb.domain.User" %><%--
  Created by IntelliJ IDEA.
  User: Bogdan
  Date: 5/9/2020
  Time: 4:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Quiz Example</title>

    <link rel="stylesheet" type="text/css" href="quizEx/quizEx.css">

    <script src="./js/jquery-2.0.3.js"></script>
    <script src="./js/ajax-utils.js"></script>
    <script src="/quizEx/quizEx.js"></script>
</head>
<body>

    <form action="QuizController2" method="post">
        <input name="finish" id="logout" style="left:0px;" type="submit" value="Log Out"/>
    </form>
    <div class="quiz-container">
        <div id="quiz"></div>
    </div>
    <button id="previous">Previous Page</button>
    <button id="next">Next Page</button>
    <button id="submit">Submit Quiz</button>
    <form action="QuizController2" method="post">
        <input name="finish" id="finish" style="display: none" type="submit" value="Finish Quiz"/>
    </form>
    <div id="results"></div>

</body>
</html>
