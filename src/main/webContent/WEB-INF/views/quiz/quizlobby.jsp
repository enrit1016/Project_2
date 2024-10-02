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
	window.root = '${root}';
</script>
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
							<span class="room-name"><c:out
									value="${room.quizRoomName}" /></span> <span class="room-users">
								<c:out value="${room.memberCount}" /> / 10명 <!-- 최대 인원 표시 -->
							</span>
							<!-- 방에 비밀번호가 있는지 확인하여 data-requires-password 속성 설정 -->
							<button class="join-room-btn" data-room-id="${room.quizRoomId}"
								data-requires-password="${room.quizRoomPassword != null && !room.quizRoomPassword.isEmpty()}">참여</button>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<script src="${root}/js/quizlobby.js"></script>
</body>
</html>
