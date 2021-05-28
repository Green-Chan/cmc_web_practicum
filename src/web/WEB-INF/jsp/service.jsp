<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Услуга ${theObject.id}</title>
</head>

<body>
  <%@ include file="/WEB-INF/jsp/header.jsp" %>

  <h1>Услуга ${theObject.id}</h1>
  
  <table>
    <tr>
      <td>ID:</td>
      <td>${theObject.id}</td>
    </tr>
    <tr>
      <td>Тип услуги:</td>
      <td>
        ID: <a href="service_type?id=${theObject.serviceType.id}">${theObject.serviceType.id}</a> 
        <br>
        Название: ${theObject.serviceType.name}
      </td>
    </tr>
    <tr>
      <td>Клиент:</td>
      <td>
        ID: <a href="client?id=${theObject.client.id}">${theObject.client.id}</a>  
        <br>
        ФИО / Название: ${theObject.client.name}
      </td>
    </tr>
    <tr>
      <td>Дата оказания услуги:</td>
      <td>
        Начало: ${beginDateStr}
        <br>
        Конец: ${endDateStr}
      </td>
    </tr>
    <tr>
      <td>Стоимость:</td>
      <td>${theObject.price}</td>
    </tr>
  </table>
  
  <br>
  
  Задействованные служащие: <br>
  <table>
    <tr>
      <th>ID</th>
      <th>ФИО</th>
      <th>Описание работы</th>
    </tr>
    <c:forEach var="task" items="${theObject.tasks}">
      <tr>
        <td> <a href="employee?id=${task.id.employee.id}">${task.id.employee.id}</a> </td>
        <td> ${task.id.employee.name} </td>
        <td> ${task.description} </td>
      </tr>
    </c:forEach>
  </table>
  
  <form method="get" action="/edit_service">
    <input type="hidden" name="id" value="${theObject.id}"/>
    <button type="submit" name="edit">Редактировать</button>
  </form>
  
  <form method="post" action="/delete_service">
    <input type="hidden" name="id" value="${theObject.id}"/>
    <button type="submit" name="delete">Удалить</button>
  </form>
  
</body>
</html>