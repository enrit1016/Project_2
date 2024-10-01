<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var='root' value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>퀴즈 로비</title>
<link rel="stylesheet" href="${root}/css/quizlobby.css">
<script>
	// root 변수를 JS로 전달
	const root = '${root}';
</script>
<script src="${root}/js/quizlobby.js"></script>
</head>
<body>
	<div class="lobby-container">
		<div class="header">
			<h1>퀴즈 로비</h1>
			<button id="create-room-btn">방 생성</button>
		</div>
		<div class="room-list-container">
			<h2>방 목록</h2>
			<ul id="room-list">
				<c:forEach var="room" items="${rooms}">
					<li>
						<div class="room-info">
							<span class="room-name">${room.quizRoomName}</span> <span
								class="room-users">${room.memberCount}명</span>
							<button onclick="joinRoom('${room.quizRoomId}')">참여</button>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</body>
</html>
