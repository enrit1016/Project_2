<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var='root' value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${root}/css/sideBar.css" rel="stylesheet" type="text/css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css"
	rel="stylesheet">


</head>

<body>
<div class="sidebar-content">
	<div class="side-wrapper">
		<h2 class="sidebar-header">MENU</h2>
		<div class="side-menu">
			<a class="sidebar-link" href="${root}/board/main">Board</a> <a
				class="sidebar-link" href="${root}/temp/tempMain">Temp</a> <a
				class="sidebar-link" href="${root}/quiz/quizlobby">Quiz</a> <a
				class="sidebar-link" href="${root}/temp/payment">payment</a> <a
				class="sidebar-link" href="${root}/playlist">Playlist</a> <a
				class="sidebar-link" href="${root}/playlist/list">PlaylistList</a> <a
				class="sidebar-link" href="${root}/temp/slide_popup">결제</a>

		</div>
	</div>
</div>
</body>
</html>