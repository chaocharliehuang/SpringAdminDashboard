<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Admin Page</title>
	<style>
		table {
		    border-collapse: collapse;
		    width: 100%;
		}
		
		td, th {
		    border: 1px solid #dddddd;
		    text-align: left;
		    padding: 8px;
		}
		
		tr:nth-child(even) {
		    background-color: #dddddd;
		}
	</style>
</head>
<body>
    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
    
    <h1>Welcome <c:out value="${currentUser.firstName}"/>!</h1>
    
    <table>
    		<tr>
    			<th>Name</th>
    			<th>Email</th>
    			<th>Action</th>
    		</tr>
    		<c:forEach items="${users}" var="user">
    			<tr>
    				<td>${user.firstName} ${user.lastName}</td>
    				<td>${user.username}</td>
    				<td>
    					<c:choose>
    						<c:when test="${user.getRoles().contains(adminRole)}">
    						admin
    						</c:when>
    						<c:otherwise>
    						<a href="/admin/delete/${user.id}">delete</a> | 
    						<a href="/admin/makeadmin/${user.id}">make admin</a>
    						</c:otherwise>
    					</c:choose>
    				</td>
    			</tr>
    		</c:forEach>
    </table>
</body>
</html>