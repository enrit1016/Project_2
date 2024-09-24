<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var='root' value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<!-- 상단바 불러오기 -->
	<c:import url="/WEB-INF/views/include/top_menu.jsp" />
	
	<!-- 사이드 바 불러오기 -->
	<c:import url="/WEB-INF/views/include/sidebar.jsp" />




</body>
</html>