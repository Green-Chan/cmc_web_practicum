<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>

<title>Клиенты</title>
</head>

<body>
  <%@ include file="/WEB-INF/jsp/header.jsp" %>

  <h1>Клиенты</h1>
  
  <form method="get" action="">
        <label for="name"> ФИО / Название: </label>
        <input type="text" name="name" value="${name}">
        
        <br>

        <label for="clientType">Тип клиента:</label>
        
        <c:choose>
          <c:when test='${clientTypeStr == null || clientTypeStr.equals("both")}'>
            <p><input type="radio" name="clientTypeStr" value="person">person</p>
            <p><input type="radio" name="clientTypeStr" value="organization">organization</p>
            <p><input type="radio" name="clientTypeStr" value="both" checked>любой</p>
          </c:when>
          <c:when test='${clientTypeStr.equals("person")}'>
            <p><input type="radio" name="clientTypeStr" value="person" checked>person</p>
            <p><input type="radio" name="clientTypeStr" value="organization">organization</p>
            <p><input type="radio" name="clientTypeStr" value="both">любой</p>
          </c:when>
          <c:otherwise>
            <p><input type="radio" name="clientTypeStr" value="person">person</p>
            <p><input type="radio" name="clientTypeStr" value="organization" checked>organization</p>
            <p><input type="radio" name="clientTypeStr" value="both">любой</p>
          </c:otherwise>
        </c:choose>
        
        <br>

        <label for="clientContact">Контакт (телефон, почта, адрес):</label>
        <input type="text" name="clientContact" value="${clientContact}">
        
        <br>
        
        Клиенту оказывалась услуга, ID типа которой 
        <input type="text" name="serviceTypeId" value="${serviceTypeId}">
        <br>
        Клиенту оказывалась некоторая услуга в пределах от
        <input type="text" name="beginLowerTimeStr" value="${beginLowerTimeStr}">
        до
        <input type="text" name="endUpperTimeStr" value="${endUpperTimeStr}">
        (формат даты дд-мм-гггг)
        <br>
        Клиенту некоторую услугу оказывал служащий с ФИО
        <input type="text" name="employeeName" value="${employeeName}">
        <br>

        <button type="submit">Искать</button>
  </form>
  
  <br>
  
  <table>
    <tr>
      <th>ID</th>
      <th>ФИО / Название</th>
      <th>Тип клиента</th>
      <th>Телефон</th>
    </tr>
    <c:forEach var="client" items="${clients}">
      <tr>
        <td>
          <a href="client?id=${client.id}">${client.id}</a>
        </td>
        <td>${client.name}</td>
        <td>${client.type}</td>
        <td>
          <c:forEach var="contact" items="${client.contacts}">
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