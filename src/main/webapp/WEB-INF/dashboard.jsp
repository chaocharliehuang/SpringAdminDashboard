<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Dashboard</title>
</head>
<body>
	<form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout" />
    </form>
    
	<h1>Welcome <c:out value="${currentUser.firstName}"/>!</h1>
	
	<p>First Name: <c:out value="${currentUser.firstName}"/></p>
	<p>Last Name: <c:out value="${currentUser.lastName}"/></p>
	<p>Email: <c:out value="${currentUser.username}"/></p>
	<p>Sign up date: <fmt:formatDate pattern="MMMM d, yyyy" value="${currentUser.createdAt}"/></p>
	<p>Last sign in: <fmt:formatDate pattern="MMMM d, yyyy" value="${lastLogin}"/></p>
	
	<c:if test="${currentUser.getRoles().contains(adminRole)}">
	<p><a href="/admin">Admin dashboard</a></p>
	</c:if>
</body>
</html>