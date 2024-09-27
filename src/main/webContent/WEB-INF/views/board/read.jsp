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
<title>Insert title here</title>
</head>
<body>
<c:import url="/WEB-INF/views/include/sidebar.jsp"/>
<div class="container">
<h1>커뮤니티</h1>
		<div>
			<button class="c_btn">전체</button>
			<button class="c_btn">자유게시판</button>
			<button class="c_btn">소식/정보</button>
			<button class="c_btn">음악 추천</button>
			<p></p>
		</div>
		<div>
		<div>
		<h3>${readContentBean.content_title }</h3>
		</div>
		<div style="font-size: 13px;">
			<div>${readContentBean.membername }</div>
			<div>${readContentBean.write_date }</div>
		</div>
		<div class="content_text">
		${readContentBean.content_text } <br />
		강의 시청 링크는 맨 아래에!<br />
		<br />
		프로작곡가가 되고싶다면?<br />
		NEXT PRODUCER!
		<br />
		<br />
		이미지를 클릭해서 '작곡가 레슨' 더 알아보기! <br />
		<c:if test="${not empty readContentBean.image}">
		<img src="${root }upload/${readContentBean.image }" width="15%"/>
		</c:if>
		</div>
		<div>
		<div style="display: flex;">♡${readContentBean.like_count } 
		<span style="margin-left: auto;">
		<a href="${root }board/modify" class="write-btn">수정</a> | 
		<a href="${root }board/delete" class="write-btn">삭제</a>
		</span></div>
		</div>
		<div class="comment-container">
		<c:forEach var='obj' items="${replyList }">
			<div style="font-size: 14px;">
			<span>${obj.membername }</span>
			<span style="color: gray;">${obj.reply_date }</span>
			</div>
			<div>${obj.reply_text }</div>
		</c:forEach>
		</div>
		<div style="border: 1px solid gray; width: 100%; height: 100px; position: relative;">
			<input
				style="border: none; outline: none; width: 100%; box-sizing: border-box;"
				placeholder="로그인이 필요합니다.">
			<button class="write-btn"
				style="position: absolute; right: 10px; bottom: 10px;">작성</button>
		</div>
			<p></p>
			<button class="write-btn">목록</button>
		</div>
</div>
<div class="showBest">
		<h3 style="margin-right: 120px;">BEST</h3>
		<div class="bestContent">
			<span>제목2123123</span>
			<span style="margin-left: auto; margin-right: 10px;">♡9</span>
		</div>
	</div>
</body>
</html>