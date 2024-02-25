<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">
<link rel = "stylesheet" href="style2.css"/>

<title>Strona główna</title>
</head>
<body>
<div class="mainDiv">
<h1>Aplikacja do dodawania i wyświetlania użytkowników</h1>
  <p>Chcesz dodać nowych użytkowników? Kliknij przycisk poniżej!</p>
    <button class="button" onclick="window.location.href='form.jsp'">Dodaj użytkowników</button>
    <p>Chcesz wyświetlić listę wszystkich użytkowników? Kliknij przycisk poniżej!</p>
    <button class="button" onclick="window.location.href='dane.jsp'">Wyświetl użytkowników</button>
    </div>
</body>
</html>