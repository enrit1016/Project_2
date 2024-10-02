<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var='root' value='${pageContext.request.contextPath }/' />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/board.css">
<title>Insert title here</title>
<style>
.content_text {
	all: unset;
	background: black;
	color: white;
	margin: 20px;
}

.content_text>* {
	background: black;
	color: white;
}

.content_text>table {
	border-collapse: collapse;
}

.content_text>table>th, td {
	border: 1px solid white;
}

.content_text>a {
	all: unset !important;
	text-decoration: underline;
	color: blue;
	cursor: pointer;
}
</style>
</head>
<body>
	<c:import url="/WEB-INF/views/include/sidebar.jsp" />
	<div class="container">
		<h1>커뮤니티</h1>
		<div>
			<button class="c_btn" ><a href="${root }board/main">전체</a></button>
			<button class="c_btn" ><a href="${root }board/main_sort?board_id=1">자유게시판</a></button>
			<button class="c_btn" ><a href="${root }board/main_sort?board_id=2">소식/정보</a></button>
			<button class="c_btn" ><a href="${root }board/main_sort?board_id=3">음악 추천</a></button>
			<p></p>
		</div>
		<div style="padding-left: 45px;">
			<div class="content_title">${readContentBean.content_title }</div>

			<div style="font-size: 13px; margin: 10px 20px;">
				<div>${readContentBean.membername }</div>
				<div>${readContentBean.writedate }</div>
			</div>
			<div class="content_text">${readContentBean.content_text }</div>

			<div style="display: flex; margin: 20px 0 20px">
				<span id="likeButton_${readContentBean.boardpost_id}"
					style="cursor: pointer;">♡</span> 
				<span id="likeCount">${readContentBean.like_count}</span>

				<c:if
					test="${loginMemberBean.member_id == readContentBean.member_id}">
					<span style="margin-left: auto;"> <a
						href="${root }board/modify?boardpost_id=${readContentBean.boardpost_id}"
						class="write-btn">수정</a> | <a
						href="${root }board/delete?boardpost_id=${readContentBean.boardpost_id}"
						class="write-btn">삭제</a>
					</span>
				</c:if>
			</div>

			<div class="comment-container">
				<c:forEach var='obj' items="${replyList }">
					<div style="font-size: 14px;">
						<span>${obj.reply_writer_name }</span> <span style="color: gray;">${obj.reply_date }</span>
					</div>

					<div>
						${obj.reply_text }
						<c:if test="${loginMemberBean.member_id == obj.member_id}">
							<span style="float: right;"> 
								<a href="${root }board/modify_rep?boardpost_id=${readContentBean.boardpost_id}&reply_id=${obj.reply_id}">수정</a>
								| 
								<a href="${root }board/delete_rep?boardpost_id=${readContentBean.boardpost_id}&reply_id=${obj.reply_id}">삭제</a>
							</span>
						</c:if>
					</div>
				</c:forEach>
			</div>

			<form:form action="${root}board/write_reply_pro" method="post"
				modelAttribute="writeReplyBean">
				<input type="hidden" name="boardpost_id"
					value="${readContentBean.boardpost_id}" />
				<div
					style="border: 1px solid gray; width: 100%; height: 100px; position: relative;">
					<input type="text" name="reply_text"
						style="border: none; outline: none; width: 100%; box-sizing: border-box; background: black; color: white;">
					<button class="write-btn"
						style="position: absolute; right: 10px; bottom: 10px;">작성</button>
				</div>
			</form:form>

			<p></p>
			<button class="write-btn">
				<a href="${root }board/main" style="color: black;">목록</a>
			</button>
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

	<script>
	document.addEventListener('DOMContentLoaded', function() {
	    const member_id = "${loginMemberBean.member_id}";
	    const boardpost_id = "${readContentBean.boardpost_id}";
	    const likeButton = document.getElementById(`likeButton_${boardpost_id}`);
	    const likeKey = `liked_${loginMemberBean.member_id}_${boardpost_id}`;
	    
	    console.log(member_id);
	    console.log(boardpost_id);
	    console.log(`likeButton_${boardpost_id}`);
	    console.log(`liked_${loginMemberBean.member_id}_${boardpost_id}`);

	    if (likeButton) {
	        console.log('Like button found!');
	        if (localStorage.getItem(likeKey)) {
	            likeButton.innerText = '♥';
	        }

	        likeButton.addEventListener('click', function() {
	            console.log('Like button clicked');
	            const isLiked = localStorage.getItem(likeKey);
	            const url = isLiked ? 
	                `${root}board/remove_like` : 
	                `${root}board/add_like`;
	            const data = { boardpost_id: boardpost_id, member_id: member_id };

	            fetch(url, {
	                method: 'POST',
	                headers: { 'Content-Type': 'application/json' },
	                body: JSON.stringify(data)
	            })
	            .then(response => response.json())
	            .then(result => {
	                if (result.success) {
	                    if (isLiked) {
	                        likeButton.innerText = '♡';
	                        localStorage.removeItem(likeKey);
	                    } else {
	                        likeButton.innerText = '♥';
	                        localStorage.setItem(likeKey, 'true');
	                    }
	                    document.getElementById('likeCount').innerText = result.newLikeCount;
	                } else {
	                    console.error('Error updating like count:', result.message);
	                }
	            })
	            .catch(error => console.error('Error:', error));
	        });
	    } else {
	        console.error(`Button with ID likeButton_${boardpost_id} not found`);
	    }
	});

</script>

</body>
</html>