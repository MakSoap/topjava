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
            <fmt:parseDate value="${datetime}" pattern="yyyy-MM-dd'T'HH:mm" var="rawDate" type="date"/>
            <fmt:formatDate value="${rawDate}" pattern="yyyy-MM-dd" var="parsedDate" type="both" dateStyle="medium" timeStyle="short"/>
            <c:out value="${parsedDate}"/>
            <td><input type="date" name="datetime" value="${parsedDate}"></td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><input type="text" name="description" value="${description}"></td>
        </tr>
        <tr>
            <td>Calories:</td>
            <td><input type="number" name="calories" value="${calories}"></td>
        </tr>
        </tbody>
    </table>
    <button type="submit" value="save">Save</button>
    <button type="reset" value="reset">Reset</button>
    <button type="button" value="cancel" onclick="history.back()">Cancel</button>
</form>
</body>
</html>
