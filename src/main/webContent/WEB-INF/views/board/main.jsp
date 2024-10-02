<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var='root' value='${pageContext.request.contextPath }/' />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/board.css">
<style>
</style>
<title>Board</title>
<script>
	/* 문의버튼js */
	function toggleContent() {
		var content = document.querySelector('.fixed-content');
		if (content.style.display === 'block') {
			content.style.display = 'none';
		} else {
			content.style.display = 'block';
		}
	}
</script>
</head>
<body>
	<header>
		<!-- ============ -->
	</header>
	<c:import url="/WEB-INF/views/include/sidebar.jsp"/>
	<div class="container">
	
		<h1>커뮤니티</h1>
		<div>
			<button class="c_btn" ><a href="${root }board/main">전체</a></button>
			<button class="c_btn" ><a href="${root }board/main_sort?board_id=1">자유게시판</a></button>
			<button class="c_btn" ><a href="${root }board/main_sort?board_id=2">소식/정보</a></button>
			<button class="c_btn" ><a href="${root }board/main_sort?board_id=3">음악 추천</a></button>
			<p></p>
		</div>
		<form:form action="${root}board/write_reply_pro" method="post"
				modelAttribute="writeReplyBean"></form:form>
		<div class="on-table">
			<input id="searchInput" placeholder="search" onkeydown="if(event.key === 'Enter') searchPosts()">
    		<span class="right-align"> <a href="${root }board/write" class="write-btn">글쓰기</a> </span>
		</div>
		
			<div>
				<table>
					<thead>
						<tr>
							<th>카테고리</th>
							<th>제목</th>
							<th>글쓴이</th>
							<th>작성일</th>
							<th>좋아요</th>
						</tr>
					</thead>
					<tbody align="center">
						<c:forEach var='obj' items="${contentList}">
							<tr>
								<td>${obj.board_name }</td>
								<td><a href="${root }board/read?boardpost_id=${obj.boardpost_id}">${obj.content_title}</a></td>
								<td>${obj.membername }</td>
								<td>${obj.writedate }</td>
								<td>${obj.like_count }</td>
								<td style="display: none;">${obj.content_text }</td>							
							</tr>						
						</c:forEach>
					</tbody>
				</table>

			</div>


			<div class="pagination">
				<ul class="pagination justify-content-center">
					<c:choose>

						<c:when test="${pageBean.prevPage <= 0 }">
							<li class="page-item disabled"><a href="#" class="page-link">←</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a
								href="${root }board/main?board_info_idx=${board_info_idx}&page=${pageBean.prevPage}"
								class="page-link">←</a></li>
						</c:otherwise>
					</c:choose>

					<c:forEach var='idx' begin="${pageBean.min }"
						end='${pageBean.max }'>
						<c:choose>
							<c:when test="${idx == pageBean.currentPage }">
								<li class="page-item active"><a
									href="${root }board/main?board_info_idx=${board_info_idx}&page=${idx}"
									class="page-link">${idx+1 }</a></li>
							</c:when>
							<c:otherwise>
								<li class="page-item"><a
									href="${root }board/main?board_info_idx=${board_info_idx}&page=${idx}"
									class="page-link">${idx+1 }</a></li>
							</c:otherwise>
						</c:choose>

					</c:forEach>

					<c:choose>

						<c:when test="${pageBean.max >= pageBean.pageCnt }">
							<li class="page-item disabled"><a href="#" class="page-link">→</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a
								href="${root }board/main?board_info_idx=${board_info_idx}&page=${pageBean.nextPage}"
								class="page-link">→</a></li>
						</c:otherwise>
					</c:choose>

				</ul>
			</div>
		
	</div>
	<div class="showBest">
		<h3 style="margin-right: 120px;">BEST</h3>
		<div class="bestContent">
		<c:forEach var='obj' items="${bestList}">
			<div>
			<span style="margin: 0 20px 0 0;">${obj.content_title}</span>
			<span style="margin: 0;">♡${obj.like_count }</span>
			</div>
		</c:forEach>
		</div>
	</div>
	<div class="fixed-section">
        <button class="fixed-button" onclick="toggleContent()">
        +
        </button>
        <div class="fixed-content">
        <form:form action=""></form:form>
            <h3>문의하기</h3>
            <p>이름</p>
            <textarea rows="1" cols="1"></textarea>
            <p>메일주소</p>
            <textarea rows="1" cols="1"></textarea>
            <p>문의내용</p>
			<textarea style="width: 100%; height: 250px; padding: 0; line-height: normal; margin-bottom: 7px;"></textarea>
            <div style="background:white; display: grid; justify-content: end;"><a href="${root }board/sendMail_pro">메일 보내기</a></div>
        </div>
    </div>
	<footer>
		<!-- ========== -->
	</footer>

<script>
    function searchPosts() {
        const input = document.getElementById('searchInput');
        const filter = input.value.toLowerCase(); // 소문자로 변환하여 대소문자 구분 없이 검색
        const table = document.querySelector('table tbody');
        const rows = table.getElementsByTagName('tr'); // 모든 행 가져오기

        for (let i = 0; i < rows.length; i++) {
            const titleCell = rows[i].cells[1]; // 제목이 있는 셀
            const contentCell = rows[i].cells[5]; // 내용이 있는 셀
            let showRow = false; // 해당 행을 표시할지 여부

            if (titleCell) {
                const titleText = titleCell.textContent || titleCell.innerText; // 셀의 텍스트 가져오기
                // 제목에 입력된 문자열이 포함되는지 확인
                if (titleText.toLowerCase().indexOf(filter) > -1) {
                    showRow = true; // 제목이 일치하는 경우
                }
            }

            if (contentCell) {
                const contentText = contentCell.textContent || contentCell.innerText; // 내용 셀의 텍스트 가져오기
                // 내용에 입력된 문자열이 포함되는지 확인
                if (contentText.toLowerCase().indexOf(filter) > -1) {
                    showRow = true; // 내용이 일치하는 경우
                }
            }

            // 일치 여부에 따라 행 표시 또는 숨김
            rows[i].style.display = showRow ? "" : "none";
        }
    }

</script>

</body>
</html>