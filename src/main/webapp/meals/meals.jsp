<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang="ru">
<style>
    table, th, td {
        border: 1px solid black;
    }
</style>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="meal" items="${meals}" >
        <c:set var="color" value="${meal.isExcess() ? '#FF0000' : '#008000'}"/>
        <tr style="color: ${color}">
            <td>
                <fmt:parseDate value="${meal.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="rawDate" type="date"/>
                <fmt:formatDate value="${rawDate}" var="parsedDate" type="both" dateStyle="medium" timeStyle="short"/>
                <c:out value="${parsedDate}"/>
            </td>
            <td><c:out value="${meal.getDescription()}"/></td>
            <td><c:out value="${meal.getCalories()}"/></td>

        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>