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

	
	
	<p>앨범명 : ${result.albumName }</p>
	<p>아티스트 : ${result.artistName}</p>
	<br />
	<p>설명 : ${result.description}</p>
	<hr />
	<img src="${result.image}" alt="이미지를 불러올 수 없습니다." />
	<br />
 

	<!-- 앨범 이미지 리스트 출력 -->
	<c:forEach var="track" items="${result.trackList}" varStatus="status">
		<p>${track}</p> <br />
	</c:forEach>


</body>
</html>