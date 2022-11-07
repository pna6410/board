<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>

<%@ include file = "/WEB-INF/jsp/egovframework/example/login/menu.jsp" %>
	<c:if test = "${msg == 'success' }">
		${sessionScope.userName }(${sessionScope.userId })님 환영합니다.
	</c:if>

</body>
</html>