<%--
  Created by IntelliJ IDEA.
  User: Bogdan
  Date: 5/10/2020
  Time: 3:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Start Quiz</title>
    <link rel="stylesheet" type="text/css" href="quizEx/quizEx.css">
    <script src="./js/jquery-2.0.3.js"></script>
</head>
<body>

    <form action="QuizController" method="post">
        Choose number of questions:
        <select name="questionNr">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
        </select>
        <br><br>
        Choose number of questions per page:
        <select name="pageQuestions">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
        </select>
        <br><br>
        <input type="submit" value="Start Quiz"/>
    </form>

</body>
</html>
