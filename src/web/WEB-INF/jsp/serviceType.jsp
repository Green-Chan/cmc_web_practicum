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
      <td>Название:</td>
      <td>${theObject.name}</td>
    </tr>
    <tr>
      <td>Информация:</td>
      <td>${theObject.info}</td>
    </tr>
  </table>
  
  <form method="get" action="/edit_service_type">
    <input type="hidden" name="id" value="${theObject.id}"/>
    <button type="submit">Редактировать</button>
  </form>
  
  <form method="post" action="/delete_service_type">
    <input type="hidden" name="id" value="${theObject.id}"/>
    <button type="submit">Удалить</button>
  </form>
</body>
</html>