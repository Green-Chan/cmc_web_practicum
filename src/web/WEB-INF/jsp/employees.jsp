<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>

<title>Служащие</title>
</head>

<body>
  <%@ include file="/WEB-INF/jsp/header.jsp" %>

  <h1>Служащие</h1>
  
  <form method="get" action="">
        <label for="name"> ФИО: </label>
        <input type="text" name="name" value="${name}">
        
        <br>

        <label for="position">Должность:</label>
        <input type="text" name="position" value="${position}">
        
        <br>
        
        <label for="education">Образование:</label>
        <input type="text" name="education" value="${education}">
        
        <br>

        <label for="employeeContact">Контакт (телефон, почта, адрес):</label>
        <input type="text" name="employeeContact" value="${employeeContact}">
        
        <br>
        
        Служащий оказывал услугу, ID типа которой 
        <input type="text" name="serviceTypeId" value="${serviceTypeId}">
        <br>
        Служащий оказывал некоторую услугу в пределах от
        <input type="text" name="beginLowerTimeStr" value="${beginLowerTimeStr}">
        до
        <input type="text" name="endUpperTimeStr" value="${endUpperTimeStr}">
        (формат даты дд-мм-гггг)
        <br>
        Служащий оказывал некоторую услугу клиенту с ФИО / названием
        <input type="text" name="clientName" value="${clientName}">
        
        <br>

        <button type="submit">Искать</button>
  </form>
  
  <br>
  
  <table>
    <tr>
      <th>ID</th>
      <th>ФИО</th>
      <th>Должность</th>
      <th>Телефон</th>
    </tr>
    <c:forEach var="employee" items="${employees}">
      <tr>
        <td>
          <a href="employee?id=${employee.id}">${employee.id}</a>
        </td>
        <td>${employee.name}</td>
        <td>${employee.position}</td>
        <td>
          <c:forEach var="contact" items="${employee.contacts}">
            <c:choose>
              <c:when test="${contact.contactType.toString().equals('phone')}">
                ${contact.contact}
                <br>
              </c:when>
              <c:otherwise></c:otherwise>
            </c:choose>
          </c:forEach>
        </td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>