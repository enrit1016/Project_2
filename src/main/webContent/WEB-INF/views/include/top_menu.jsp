<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var='root' value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>상단 메뉴</title>
<link rel="stylesheet" href="${root}/css/top_menu.css">
<!-- 필요한 CSS 및 아이콘 파일 로드 -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css"
	rel="stylesheet">

<!-- SweetAlert 다크 테마 및 스크립트 추가 -->
    <link href="https://cdn.jsdelivr.net/npm/@sweetalert2/theme-dark@4/dark.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.js"></script>
    
    <script>
    // 검색 함수
    function searchContent() {
        var query = document.getElementById('searchInput').value;
        if (query.trim() !== '') {
            // 검색어가 있을 때만 페이지 이동
            window.location.href = '${root}/search/TopMenuSearch?query=' + encodeURIComponent(query);
        }
    }

    // 페이지가 완전히 로드된 후에 검색창에 엔터키 이벤트 리스너 추가
    document.addEventListener('DOMContentLoaded', function() {
        document.getElementById('searchInput').addEventListener('keyup', function(event) {
            if (event.key === 'Enter') {
                searchContent();  // 엔터키가 눌렸을 때 searchContent 함수 호출
            }
        });
    });
</script>

<style>
:root {
    --body-font: "Inter", sans-serif;
    --theme-bg: #191414;
    --body-color: #808191;
    --button-bg: #353340;
    --border-color: rgba(128, 129, 145, 0.24);
    --video-bg: #252936;
    --delay: 0s;
}
</style>
</head>
<body>
	<div class="navbar fixed-top">
		<!-- 고정된 상단 메뉴 -->
		<!-- 로고 영역 -->
		<div class="navbar-logo">
			<a href="${root}/main" class="logo-link"> <img alt="로고" src="${root}/img/favicon.png" style="display:inline; width: 65px; height: 65px; vertical-align: middle;"> DuckMusic</a>
		</div>

		<!-- 중앙 정렬: 검색창 -->
		<div class="navbar-center">

			<!-- 검색창 -->
			<div class="navbar-search">
				<input type="text" id="searchInput" class="search-input"
					placeholder="어떤 콘텐츠를 감상하고 싶으세요?"> <a
					href="javascript:void(0);" onclick="searchContent();"> <i
					class="bi bi-search search-icon"></i>
				</a>
			</div>
		</div>

		<script>
			function searchContent() {
				var query = document.getElementById('searchInput').value;
				if (query.trim() !== '') {
					// 검색어가 있을 때만 페이지 이동
					window.location.href = '${root}/search/TopMenuSearch?query='
							+ encodeURIComponent(query);
				}
			}
		</script>

		<!-- 우측 로그인 및 구독 -->
        <div class="navbar-right">
            
            
            <!-- 로그인 여부에 따라 동적으로 버튼 변경 -->
            <c:choose>
                <c:when test="${sessionScope.loginMemberBean != null}">
                    <!-- 로그인이 된 상태: 드롭다운 메뉴 추가 -->
                    <div class="dropdown">
                        <a href="javascript:void(0)" class="nav-link nickname">${loginMemberBean.nickname } 님</a>
                        <div class="dropdown-content">
                            <a href="${root}/member/info">내 정보</a>
                            <a href="${root}/temp/slide_popup">상품/결제</a>
                            <a href="${root }/board/main?button=activated">문의하기</a>
                            <a href="${root}/member/logout">로그아웃</a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <!-- 로그아웃 상태 -->
                    <a href="${root}/member/join" class="join-btn">가입하기</a>
                    <a href="${root}/member/login" class="login-btn">로그인</a>
                </c:otherwise>
            </c:choose>
        </div>
	</div>
</body>
</html>
