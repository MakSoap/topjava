<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<a href="${pageContext.request.contextPath}/meals?method=add">Add meal</a>
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
        <tr style="color: ${meal.isExcess() ? '#FF0000' : '#008000'}">
            <td>
                <fmt:parseDate value="${meal.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="rawDate" type="date"/>
                <fmt:formatDate value="${rawDate}" pattern="yyyy-MM-dd HH:mm" var="parsedDate" type="both" dateStyle="medium" timeStyle="short"/>
                ${parsedDate}
            </td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
            <td><a href="${pageContext.request.contextPath}/meals?method=edit&mealId=${meal.getId()}">Edit meal</a></td>
            <td><a href="${pageContext.request.contextPath}/meals?method=delete&mealId=${meal.getId()}">Delete meal</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>