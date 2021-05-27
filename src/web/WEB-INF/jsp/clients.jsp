<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>

<title>${theObject.name}</title>
</head>

<body>
  <%@ include file="/WEB-INF/jsp/header.jsp" %>

  <h1>${theObject.name}</h1>
  
  <table>
    <tr>
      <td>ID:</td>
      <td>${theObject.id}</td>
    </tr>
    <tr>
      <td>Имя / Название:</td>
      <td>${theObject.name}</td>
    </tr>
    <tr>
      <td>Тип клиента:</td>
      <td>${theObject.type}</td>
    </tr>
  </table>
  
  <form method="get" action="">
        <label for="name"> Имя / Название: </label>
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

        <label for="info">Контакт (телефон, почта, адрес):</label>
        <input type="text" name="clientContact" value="${clientContact}">
        
        <br>

        <button type="submit">Искать</button>
  </form>
  
  <br>
  
  <table>
    <tr>
      <th>ID</th>
      <th>Имя / Название</th>
      <th>Тип клиента</th>
      <th>Контакты</th>
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
              <c:when test="${contact.contactType.toString().equals('phone')}">тел.: </c:when>
              <c:when test="${contact.contactType.toString().equals('email')}">e-mail: </c:when>
              <c:otherwise>адрес: </c:otherwise>
            </c:choose>
            ${contact.contact}
            <br>
          </c:forEach>
        </td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>