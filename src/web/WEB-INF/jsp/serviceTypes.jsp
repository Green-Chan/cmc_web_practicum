<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Типы услуг</title>
</head>

<body>
  <%@ include file="/WEB-INF/jsp/header.jsp" %>

  <h1>Типы услуг</h1>
  
  <form method="get" action="">
        <label for="name">Название:</label>
        <input type="text" name="name" value="${searchName}">
        
        <br>

        <label for="info">Информация:</label>
        <input type="text" name="info" value="${searchInfo}">
        
        <br>

        <button type="submit" name="search">Искать</button>
  </form>
  
  <br>
  
  <table>
    <tr>
      <th>ID</th>
      <th>Название</th>
    </tr>
    <c:forEach var="serviceType" items="${serviceTypes}">
      <tr>
        <td>
          <a href="service_type?id=${serviceType.id}">${serviceType.id}</a>
        </td>
        <td>${serviceType.name}</td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>