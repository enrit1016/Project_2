<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Melon Chart</title>
</head>
<body>
    <h1>Melon Chart Top 100</h1>
    <table border="1">
        <thead>
            <tr>
                <th>Rank</th>
                <th>Title</th>
                <th>Artist</th>
                <th>Album</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="song" items="${songs}">
                <tr>
                    <td>${song.rank}</td>
                    <td>${song.title}</td>
                    <td>${song.artist}</td>
                    <td>${song.album}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
