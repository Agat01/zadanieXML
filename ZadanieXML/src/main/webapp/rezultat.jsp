<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">

<link rel = "stylesheet" href="style2.css"/>
<title>Rezultat</title>
</head>
<body>

<%@ page import="java.util.*" %>
<%@ page import="encje.*" %>

<div class="mainContent">
<h1>Wyniki operacji dodawania użytkowników:</h1>
<br>
    <h3>Udane operacje:</h3>
    <%
    int number=1;
        ArrayList<User> successfulUsers = (ArrayList<User>) session.getAttribute("successfulUsers");
        if (successfulUsers != null && !successfulUsers.isEmpty()) {
        	out.println("<table id='table'><th> </th><th>Imię</th><th>Nazwisko</th><th>Login</th>");
            for (User user : successfulUsers) {
                out.println("<tr><td>"+number+"</td><td>" + user.getName()+"</td><td>"+user.getSurname()+"</td><td>"+user.getLogin() + "</td></tr>");
                number++;
            }
            out.println("</table>");
        } else {
            out.println("<p>Brak udanych operacji.</p>");
        }
    %>

    <h3>Nieudane operacje:</h3>
    <%
        ArrayList<User> unsuccessfulUsers = (ArrayList<User>) session.getAttribute("unsuccessfulUsers");
        if (unsuccessfulUsers != null && !unsuccessfulUsers.isEmpty()) {
        	out.println("<table id='table'><th> </th><th>Imię</th><th>Nazwisko</th><th>Login</th>");
        	for (User user : unsuccessfulUsers) {
                out.println("<tr><td>"+number+"</td><td>" + user.getName()+"</td><td>"+user.getSurname()+"</td><td>"+user.getLogin() + "</td></tr>");
                number++;
        	}
        	out.println("</table>");
        } else {
            out.println("<p>Brak nieudanych operacji.</p>");
        }
    %>
    <button class="button" onclick="window.location.href='dane.jsp'" style="width: 250px;">Wyświetl wszystkich użytkowników</button>
</div>

</body>
</html>