<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<c:set var='root' value="${pageContext.request.contextPath}/" />
	<!-- ���� ���̶� ����ȭ�� �̿��� ������������ 404 ������ �߻��� -->
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<style>
.sidebar {
	position: fixed; /* ���� ��ġ�� ���� (��ũ�� �ÿ��� ��ġ ����) */
	left: -200px; /* �ʱ� ���¿��� ȭ�� ���� �ٱ��� ��ġ */
	z-index: 1000; /* �ٸ� ��ҵ� ���� ǥ�õǵ��� ���� */
	width: 180px; /* ���̵���� �ʺ� */
	padding: 10px; /* ���̵�� ���� ���� */
	flex-direction: column; /* ���� ��ҵ��� �������� ��ġ */
	height: 100vh; /* ȭ�� ��ü ���̿� ���� */
	border-right: 1px solid gray; /* �����ʿ� ȸ�� �׵θ� �߰� */
	transition: left 0.5s ease; /* left �Ӽ� ���� �� �ִϸ��̼� ȿ�� ���� */
	background-color: black; /* ���� */
	color: white; /* ���ڻ� */
	padding-top : 60px;
}

.sidebar.visible {
	left: 0; /* visible Ŭ������ �߰��Ǹ� ȭ�鿡 ���̵��� ���� ��ġ ���� */
}

.sidebar-trigger {
	position: fixed; /* ���� ��ġ�� ���� */
	left: 0; /* ȭ���� ���ʿ� ��ġ */
	width: 40px; /* Ŭ�� ������ ������ �ʺ� */
	height: 100vh; /* Ŭ�� ������ ������ ���� */
	background-color: transparent; /* Ŭ�� ������ ������ �����ϰ� ���� */
}

.close_sidebar {
	text-align: right; /* �ؽ�Ʈ�� ������ ���� */
	cursor: pointer; /* ���콺 Ŀ���� �����ͷ� ���� (Ŭ�� ���� ǥ��) */
}
</style>
<title>Insert title here</title>
<script>
	let lastToggleTime = 0; // ������ ��� �ð�
	const cooldownTime = 500; // ��Ÿ�� 500ms

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
	<div class="sidebar-trigger" onmouseenter="toggleSidebar(true)"
		onmouseleave="toggleSidebar(false)"></div>

	<div class="sidebar">
		<div class="close_sidebar" onclick="toggleSidebar(false)">��</div>
		<div>Ȩ</div>
		<div>Ŀ�´�Ƽ</div>
		<a href="${root }temp/tempMain">To tempMain</a> <br />
		<a href="${root }quiz/quizMain">����</a> <br />
		<a href="${root }temp/maniadbSearch">ManiaDB �˻�</a><br />
		<a href="${root }temp/SCSStest">SCSStest</a><br />
		<a href="${root }temp/modalUI">modalUI</a>
	</div>
</body>
</html>