<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>
  <c:choose>
    <c:when test="${theObject.id == 0}">Добавить служащего</c:when>
    <c:otherwise>Редактировать служащего</c:otherwise>
  </c:choose>
</title>
</head>

<body>
  <%@ include file="/WEB-INF/jsp/header.jsp" %>

  <h1>
    <c:choose>
      <c:when test="${theObject.id == 0}">Добавить служащего</c:when>
      <c:otherwise>Редактировать служащего</c:otherwise>
    </c:choose>
  </h1>
  
  <form method="post" action="/save_employee">  
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
        
        <label for="name">ФИО:</label>
        <input type="text" name="name" value="${theObject.name}">
        
        <br>
        
        <label for="name">Должность:</label>
        <input type="text" name="position" value="${theObject.position}">
        
        <br>
        
        <label for="name">Образование:</label>
        <input type="text" name="education" value="${theObject.education}">
        
        <br>
        
        <label for="employeeContactType">Контакты:</label>
        <c:choose>
          <c:when test="${theObject.contacts == null || theObject.contacts.size() == 0}">
            <c:forEach var="i" begin="0" end="4" step="1">
              <p>
                <input type="radio" name="contactType${i}" value="phone">Телефон
                <input type="radio" name="contactType${i}" value="email">e-mail
                <input type="radio" name="contactType${i}" value="address">Адрес
                <input type="text"  name="contactValue${i}">
                <br>
                <input type="radio" name="contactType${i}" value="null" checked> Не заполнять (удалить) этот контакт
              </p>
            </c:forEach>
            <input type="hidden" name="contactNum" value="5">
          </c:when>
          <c:otherwise>
            <c:forEach var="i" begin="0" end="${theObject.contacts.size() - 1}">
              <p>
                <c:choose>
                  <c:when test="${theObject.contacts.toArray()[i].contactType.toString().equals('phone')}">
                    <input type="radio" name="contactType${i}" value="phone" checked>Телефон
                    <input type="radio" name="contactType${i}" value="email">e-mail
                    <input type="radio" name="contactType${i}" value="address">Адрес
                  </c:when>
                  <c:when test="${theObject.contacts.toArray()[i].contactType.toString().equals('email')}">
                    <input type="radio" name="contactType${i}" value="phone">Телефон
                    <input type="radio" name="contactType${i}" value="email" checked>e-mail
                    <input type="radio" name="contactType${i}" value="address">Адрес
                  </c:when>
                  <c:otherwise>
                    <input type="radio" name="contactType${i}" value="phone">Телефон
                    <input type="radio" name="contactType${i}" value="email">e-mail
                    <input type="radio" name="contactType${i}" value="address" checked>Адрес
                  </c:otherwise>
                </c:choose>
                <input type="text"  name="contactValue${i}" value="${theObject.contacts.toArray()[i].contact}">
                <br>
                <input type="radio" name="contactType${i}" value="null"> Не заполнять (удалить) этот контакт
              </p>
            </c:forEach>
            <c:forEach var="i" begin="${theObject.contacts.size()}" end="${theObject.contacts.size() + 4}">
              <p>
                <input type="radio" name="contactType${i}" value="phone">Телефон
                <input type="radio" name="contactType${i}" value="email">e-mail
                <input type="radio" name="contactType${i}" value="address">Адрес
                <input type="text"  name="contactValue${i}">
                <br>
                <input type="radio" name="contactType${i}" value="null" checked> Не заполнять (удалить) этот контакт
              </p>
            </c:forEach>
            <input type="hidden" name="contactNum" value="${theObject.contacts.size() + 5}">  
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
      <form method="get" action="/employee">
        <input type="hidden" name="id" value="${theObject.id}"/>
        <button type="submit">Отменить</button>
      </form>
    </c:otherwise>
  </c:choose>
  
</body>
</html>