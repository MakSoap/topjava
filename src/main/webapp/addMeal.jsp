<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <title>Edit meal</title>
</head>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<body>
<form method="post" accept-charset="utf-8">
    <table>
        <tbody>
        <tr>
            <td>DateTime:</td>
            <td><input type="datetime-local" name="datetime" value="${meal.getDateTime()}"></td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><input type="text" name="description" value="${meal.getDescription()}"></td>
        </tr>
        <tr>
            <td>Calories:</td>
            <td><input type="number" name="calories" value="${meal.getCalories()}"></td>
        </tr>
        </tbody>
    </table>
    <button type="submit" value="save">Save</button>
    <button type="reset" value="reset">Reset</button>
    <button type="button" value="cancel" onclick="history.back()">Cancel</button>
</form>
</body>
</html>
