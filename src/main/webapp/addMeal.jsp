<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title>Edit meal</title>
</head>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>${pageContext.request.getParameter("mealId") == null ? "Add" : "Edit"} meal</h2>
<body>
<form method="post" accept-charset="utf-8">
    <table>
        <tbody>
        <tr>
            <td>DateTime:</td>
            <td><input type="datetime-local" name="datetime" value="${meal.dateTime}"></td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><input type="text" name="description" value="${meal.description}"></td>
        </tr>
        <tr>
            <td>Calories:</td>
            <td><input type="number" name="calories" value="${meal.calories}"></td>
        </tr>
        </tbody>
    </table>
    <button type="submit" value="save">Save</button>
    <button type="button" value="cancel" onclick="history.back()">Cancel</button>
</form>
</body>
</html>
