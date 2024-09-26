<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var='root' value="${pageContext.request.contextPath }/" />
<!DOCTYPE html>
<html>
<head>
<title>퀴즈 목록</title>
<meta charset="UTF-8">
</head>
<body>
	<h1>퀴즈 목록</h1>

	<c:if test="${empty quizzes}">
		<p>퀴즈가 없습니다.</p>
	</c:if>
	
	<c:if test="${not empty quizzes}">
		<c:forEach var="quiz" items="${quizzes}">
			<h2>${quiz.quiz_title}</h2>
			<p>${quiz.quiz_text}</p>
			<form action="${root}quiz/${quiz.quiz_id}" method="get">
				<button type="submit">퀴즈 풀기</button>
			</form>
			<hr />
		</c:forEach>
	</c:if>
	
</body>
</html>
