<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var='root' value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>퀴즈 방</title>
    <link rel="stylesheet" href="<c:url value='${root }/css/quizRoom.css'/>">
    <script src="<c:url value='${root }/js/quizRoom.js'/>"></script>
</head>
<body>
    <div class="quiz-room-container">
        <!-- 퀴즈 방의 헤더 -->
        <div class="header">
            <h1>${room.roomCode} - 퀴즈 방</h1>
            <button id="start-quiz-btn">게임 시작</button>
            <!-- 로비로 이동하는 버튼 추가 -->
            <button id="go-lobby-btn">로비로 이동</button>
        </div>

        <!-- 주제 및 퀴즈 영역 -->
        <div class="quiz-section">
            <h2 id="quiz-topic">주제: <span id="current-topic">음악 퀴즈</span></h2>
            <div id="quiz-area">
                <p id="quiz-text">퀴즈 문제가 여기에 표시됩니다.</p>
                <input type="text" id="quiz-answer" placeholder="정답 입력"/>
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
            <input type="text" id="chat-input" placeholder="채팅 입력..."/>
            <button id="send-chat-btn">전송</button>
        </div>
    </div>
</body>
</html>
