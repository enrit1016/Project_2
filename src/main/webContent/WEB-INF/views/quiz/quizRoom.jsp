<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var='root' value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>${room.quizRoomName}- 퀴즈 방</title>
<link rel="stylesheet" href="${root}/css/quizRoom.css">
<script>
        // root 및 방 정보를 JS로 전달
        const root = '${root}';
        const roomId = '${room.quizRoomId != null ? room.quizRoomId : 0}';
        const roomName = '${room.quizRoomName != null ? room.quizRoomName : "새로운 퀴즈방"}';
        const memberCount = '${room.memberCount != null ? room.memberCount : 0}';
        const maxCapacity = 10; // 최대 인원 설정
    </script>
</head>
<body>
	<div class="quiz-room-container">
		<!-- 퀴즈 방의 헤더 -->
		<div class="header">
			<h1>${room.quizRoomName}- 퀴즈 방 (${memberCount}/${maxCapacity})</h1>
			<button id="start-quiz-btn">게임 시작</button>
			<button id="go-lobby-btn">로비로 이동</button>
		</div>

		<!-- 주제 및 퀴즈 영역 -->
		<div class="quiz-section">
			<h2 id="quiz-topic">
				주제: <span id="current-topic">음악 퀴즈</span>
			</h2>
			<div id="quiz-area">
				<p id="quiz-text">퀴즈 문제가 여기에 표시됩니다.</p>
				<input type="text" id="quiz-answer" placeholder="정답 입력" />
				<button id="submit-answer-btn">제출</button>
			</div>
		</div>

		<!-- 카메라 영역 -->
		<div class="camera-section">
			<div id="local-video-container">
				<video id="local-video" autoplay muted></video>
			</div>
			<div id="remote-video-container"></div>
		</div>

		<!-- 채팅 영역 -->
		<div class="chat-section">
			<div id="chat-messages"></div>
			<input type="text" id="chat-input" placeholder="채팅 입력..." />
			<button id="send-chat-btn">전송</button>
		</div>
	</div>

	<!-- JavaScript 파일을 바디 끝에 로드하여 모든 요소가 렌더링된 후에 스크립트 실행 -->
	<script src="${root}/js/quizRoom.js"></script>
	<script>
        document.addEventListener('DOMContentLoaded', () => {
            // 게임 시작 버튼 클릭 이벤트
            document.getElementById('start-quiz-btn').addEventListener('click', () => {
                alert('게임을 시작합니다!'); // 실제 게임 시작 로직을 여기에 추가하세요.
            });

            // 로비로 이동 버튼 클릭 이벤트
            document.getElementById('go-lobby-btn').addEventListener('click', () => {
                window.location.href = `${root}/quiz/lobby`; // 로비 페이지로 이동
            });
        });
    </script>
</body>
</html>
