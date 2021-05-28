<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>

<title>Услуги</title>
</head>

<body>
  <%@ include file="/WEB-INF/jsp/header.jsp" %>

  <h1>Услуги</h1>
  
  <form method="get" action="">
        <label for="name"> ID типа услуги: </label>
        <input type="text" name="serviceTypeId" value="${serviceTypeId}">
        
        <br>

        <label for="position">ФИО / Название клиента:</label>
        <input type="text" name="clientName" value="${clientName}">
        
        <br>
        
        <label for="education">ФИО задействованного служащего:</label>
        <input type="text" name="employeeName" value="${employeeName}">
        
        <br>

        <label for="employeeContact">Дата до начала оказания услуги (в формате дд-мм-гггг):</label>
        <input type="text" name="beginLowerTime" value="${beginLowerTime}">
        
        <br>

        <label for="employeeContact">Дата после начала оказания услуги (в формате дд-мм-гггг):</label>
        <input type="text" name="beginUpperTime" value="${beginUpperTime}">
        
        <br>

        <label for="employeeContact">Дата до конца оказания услуги (в формате дд-мм-гггг):</label>
        <input type="text" name="endLowerTime" value="${endLowerTime}">
        
        <br>

        <label for="employeeContact">Дата после конца оказания услуги (в формате дд-мм-гггг):</label>
        <input type="text" name="endUpperTime" value="${endUpperTime}">
        
        <br>
        
        <label for="education">Стоимость больше чем:</label>
        <input type="text" name="priceLower" value="${priceLower}">
        
        <br>       
        
        <label for="education">Стоимость меньше чем:</label>
        <input type="text" name="priceUpper" value="${priceUpper}">
        
        <br>

        <button type="submit" name="search">Искать</button>
  </form>
  
  <br>
  
  <table>
    <tr>
      <th>ID</th>
      <th>ID типа услуги</th>
      <th>ФИО / Название клиента</th>
      <th>Дата начала оказания услуги</th>
      <th>Дата конца оказания услуги</th>
    </tr>
    <c:if test="${services != null && services.size() > 0}">
    <c:forEach var="i" begin="0" end="${services.size() - 1}">
      <tr>
        <td>
          <a href="service?id=${services.toArray()[i].id}">${services.toArray()[i].id}</a>
        </td>
        <td>${services.toArray()[i].serviceType.id}</td>
        <td>${services.toArray()[i].client.name}</td>
        <td>${beginDateStr.toArray()[i]}</td> 
        <td>${endDateStr.toArray()[i]}</td>
      </tr>
    </c:forEach>
    </c:if>
  </table>
</body>
</html>