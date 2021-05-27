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
    <tr>
      <td>Контакты:</td>
      <td>
        <c:forEach var="contact" items="${theObject.contacts}">
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
    <tr>
      <td>Контактные лица:</td>
      <td>
        <c:forEach var="cp" items="${theObject.contactPersons}">
          ${cp.name} ${cp.phone}
          <br>
        </c:forEach>
      </td>
    </tr>
  </table>
  
  <form method="get" action="/edit_client">
    <input type="hidden" name="id" value="${theObject.id}"/>
    <button type="submit">Редактировать</button>
  </form>
  
  <form method="post" action="/delete_client">
    <input type="hidden" name="id" value="${theObject.id}"/>
    <button type="submit">Удалить</button>
  </form>
  
</body>
</html>