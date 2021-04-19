<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Типы услуг</title>
</head>
<body>
<main>
    <ul>
        <c:forEach var="serviceType" items="${serviceTypes}">
            <li>
                ${serviceType.id} ${serviceType.name}
            </li>
        </c:forEach>
    </ul>
</main>
</body>
</html>