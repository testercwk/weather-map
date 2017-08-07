<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Weather Map</title>
</head>
<body>
<h1>Weather Map</h1>

<c:choose>
    <c:when test="${empty errorMessage}">
        <table>
            <tr>
                <td>Date</td>
                <td><c:out value="${now}"/></td>
            </tr>
            <tr>
                <td>City</td>
                <td><c:out value="${city}"/></td>
            </tr>
            <tr>
                <td>Weather</td>
                <td>
                    <c:out value="${weather}"/>
                </td>
            </tr>
            <tr>
                <td>Temperature</td>
                <td><c:out value="${temperature}"/></td>
            </tr>
            <tr>
                <td>Sunrise</td>
                <td><c:out value="${sunrise}"/></td>
            </tr>
            <tr>
                <td>Sunset</td>
                <td><c:out value="${sunset}"/></td>
            </tr>
        </table>
    </c:when>
    <c:otherwise>
        <h2>${errorMessage}</h2>
    </c:otherwise>
</c:choose>
</body>
</html>