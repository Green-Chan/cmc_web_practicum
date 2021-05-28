<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>
  <c:choose>
    <c:when test="${theObject.id == 0}">Добавить услугу</c:when>
    <c:otherwise>Редактировать услугу</c:otherwise>
  </c:choose>
</title>
</head>

<body>
  <%@ include file="/WEB-INF/jsp/header.jsp" %>

  <h1>
    <c:choose>
      <c:when test="${theObject.id == 0}">Добавить услугу</c:when>
      <c:otherwise>Редактировать услугу</c:otherwise>
    </c:choose>
  </h1>
  
  <form method="post" action="/save_service">  
        <label for="id">ID:</label>
        <c:choose>
          <c:when test="${theObject.id == 0}">
            <input type="hidden" name="id" value="${theObject.id}">
            Пока не присвоен
          </c:when>
          <c:otherwise>
            <input type="text" name="id" value="${theObject.id}" readonly>
          </c:otherwise>
        </c:choose>

        <br>
        
        <label for="name">ID типа услуги:</label>
        <input type="text" name="serviceTypeId" value="${theObject.serviceType.id}">
        
        <br>
        
        <label for="name">ID клиента:</label>
        <input type="text" name="clientId" value="${theObject.client.id}">
        
        <br>

        <label for="beginTimeStr">Дата начала оказания услуги (в формате дд-мм-гггг):</label>
        <input type="text" name="beginTimeStr" value="${beginDateStr}">
        
        <br>

        <label for="endTimeStr">Дата конца оказания услуги (в формате дд-мм-гггг):</label>
        <input type="text" name="endTimeStr" value="${endDateStr}">
        
        <br>
        
        <label for="price">Стоимость услуги:</label>
        <input type="text" name="price" value="${theObject.price}">
        
        <br>
        
        <label for="serviceTasks">Задействованные сотрудники:</label>
        <c:choose>
          <c:when test="${theObject.tasks == null || theObject.tasks.size() == 0}">
            <c:forEach var="i" begin="0" end="4" step="1">
              <p>
                ID сотрудника:
                <input type="text" name="employeeId${i}">
                <br>
                Описание работы:
                <input type="text"  name="description${i}">
              </p>
            </c:forEach>
            <input type="hidden" name="tasksNum" value="5">
          </c:when>
          <c:otherwise>
            <c:forEach var="i" begin="0" end="${theObject.tasks.size() - 1}">
              <p>
                ID сотрудника:
                <input type="text" name="employeeId${i}" value="${theObject.tasks.toArray()[i].id.employee.id}" readonly>
                <br>
                Описание работы:
                <input type="text"  name="description${i}" value="${theObject.tasks.toArray()[i].description}">
              </p>
            </c:forEach>
            <c:forEach var="i" begin="${theObject.tasks.size()}" end="${theObject.tasks.size() + 4}">
              <p>
                ID сотрудника:
                <input type="text" name="employeeId${i}">
                <br>
                Описание работы:
                <input type="text"  name="description${i}">
              </p>
            </c:forEach>
            <input type="hidden" name="tasksNum" value="${theObject.tasks.size() + 5}">  
          </c:otherwise>
        </c:choose>
        
        <br>

        <button type="submit" name="save">Сохранить</button>
  </form>
  
  <c:choose>
    <c:when test="${theObject.id == 0}">
      <form method="get" action="">
        <button type="submit">Отменить</button>
      </form>
    </c:when>
    <c:otherwise>
      <form method="get" action="/service">
        <input type="hidden" name="id" value="${theObject.id}"/>
        <button type="submit">Отменить</button>
      </form>
    </c:otherwise>
  </c:choose>
  
</body>
</html>