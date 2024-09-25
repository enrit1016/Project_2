<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
.sidebar {
	position: fixed; /* 고정 위치로 설정 (스크롤 시에도 위치 유지) */
	left: -200px; /* 초기 상태에서 화면 왼쪽 바깥에 위치 */
	z-index: 1000; /* 다른 요소들 위에 표시되도록 설정 */
	width: 180px; /* 사이드바의 너비 */
	padding: 10px; /* 사이드바 내부 여백 */
	flex-direction: column; /* 내부 요소들을 수직으로 배치 */
	height: 100vh; /* 화면 전체 높이에 맞춤 */
	border-right: 1px solid gray; /* 오른쪽에 회색 테두리 추가 */
	transition: left 0.5s ease; /* left 속성 변경 시 애니메이션 효과 설정 */
	background-color: black; /* 배경색 */
	color: white; /* 글자색 */
}

.sidebar.visible {
	left: 0; /* visible 클래스가 추가되면 화면에 보이도록 왼쪽 위치 조정 */
}

.sidebar-trigger {
	position: fixed; /* 고정 위치로 설정 */
	left: 0; /* 화면의 왼쪽에 위치 */
	width: 40px; /* 클릭 가능한 영역의 너비 */
	height: 100vh; /* 클릭 가능한 영역의 높이 */
	background-color: transparent; /* 클릭 가능한 영역을 투명하게 설정 */
}

.close_sidebar {
	text-align: right; /* 텍스트를 오른쪽 정렬 */
	cursor: pointer; /* 마우스 커서를 포인터로 변경 (클릭 가능 표시) */
}
</style>
<title>Insert title here</title>
<script>
	let lastToggleTime = 0; // 마지막 토글 시간
	const cooldownTime = 500; // 쿨타임 500ms

	function toggleSidebar(visible) {
		const sidebar = document.querySelector('.sidebar');
		const currentTime = new Date().getTime();

		if (currentTime - lastToggleTime > cooldownTime) {
			lastToggleTime = currentTime;
			if (visible) {
				sidebar.classList.add('visible');
			} else {
				sidebar.classList.remove('visible');
			}
		}
	}
</script>
</head>
<body>
	<div class="sidebar-trigger" 
		onmouseenter="toggleSidebar(true)"
		onmouseleave="toggleSidebar(false)">
	</div>
	
	<div class="sidebar">
		<div class="close_sidebar" onclick="toggleSidebar(false)">◀</div>
		<div>홈</div>
		<div>커뮤니티</div>
		<div>퀴즈</div>
	</div>
</body>
</html>