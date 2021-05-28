<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>
  <c:choose>
    <c:when test="${theObject.id == null}">Добавить тип услуги</c:when>
    <c:otherwise>Редактировать тип услуги</c:otherwise>
  </c:choose>
</title>
</head>

<body>
  <%@ include file="/WEB-INF/jsp/header.jsp" %>

  <h1>
    <c:choose>
      <c:when test="${theObject.id == null}">Добавить тип услуги</c:when>
      <c:otherwise>Редактировать тип услуги</c:otherwise>
    </c:choose>
  </h1>
  
  <form method="post" action="/save_service_type">
        <input type="hidden" name="oldId" value="${theObject.id}"/>
  
        <label for="id">ID:</label>
        <c:choose>
          <c:when test="${theObject.id == null}">
            <input type="text" name="id" value="${theObject.id}">
          </c:when>
          <c:otherwise>
            <input type="text" name="id" value="${theObject.id}" readonly>
          </c:otherwise>
        </c:choose>

        <br>
        
        <label for="name">Название:</label>
        <input type="text" name="name" value="${theObject.name}">
        
        <br>

        <label for="info">Информация:</label>
        <input type="text" name="info" value="${theObject.info}">
        
        <br>

        <button type="submit" name="save">Сохранить</button>
  </form>
  
  <c:choose>
    <c:when test="${theObject.id == null}">
      <form method="get" action="">
        <button type="submit">Отменить</button>
      </form>
    </c:when>
    <c:otherwise>
      <form method="get" action="/service_type">
        <input type="hidden" name="id" value="${theObject.id}"/>
        <button type="submit">Отменить</button>
      </form>
    </c:otherwise>
  </c:choose>
  
</body>
</html>