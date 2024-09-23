<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var='root' value='${pageContext.request.contextPath }/' />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/board.css">
<link rel="stylesheet"
	href="https://cdn.ckeditor.com/ckeditor5/43.1.0/ckeditor5.css">
<style>
.main-container {
	width: auto;
	height: 600px;
	margin-left: auto;
	margin-right: auto;
}
</style>
<title>Insert title here</title>
</head>
<body>
	<c:import url="/WEB-INF/views/include/sidebar.jsp" />
	<div class="container">
		<div style="font-size: 13px; margin-bottom: 20px;">
			<div>(현재 로그인중인 유저)</div>
		</div>
		<div style="margin-left: 30px">
			<div>
				<span class="custom-select"> <select name="category"
					id="drop-down">
						<option value="" disabled selected style="color: black;">카테고리</option>
						<option style="color: #74E885">자유게시판</option>
						<option style="color: #BC5ADC">소식/정보</option>
						<option style="color: yellow;">음악 추천</option>
				</select>
				</span> <input
					style="width: 400px; font-size: 16px; padding: 3px 3px; margin-right: 20px;"
					placeholder="제목"> <span style="float: right;">
					<button class="write-btn">취소</button>
					<button class="write-btn">작성</button>
				</span>
			</div>
			<div class="main-container">
				<div id="editor">
					<p></p>
				</div>
			</div>
		</div>
	</div>
	<script type="importmap">
        {
            "imports": {
                "ckeditor5": "https://cdn.ckeditor.com/ckeditor5/43.1.0/ckeditor5.js",
                "ckeditor5/": "https://cdn.ckeditor.com/ckeditor5/43.1.0/"
            }
        }

    </script>
	<script type="module">
        import {
            ClassicEditor,
            Essentials,
            Paragraph,
            Bold,
            Italic,
            Font
        } from 'ckeditor5';
        ClassicEditor
            .create(document.querySelector('#editor'), {
                plugins: [ Essentials, Paragraph, Bold, Italic, Font ],
                toolbar: [
                    'undo', 'redo', '|', 'bold', 'italic', '|',
                    'fontSize', 'fontFamily', 'fontColor', 'fontBackgroundColor'
                ]
            })
            .then(editor => {
                window.editor = editor;
            })
            .catch(error => {
                console.error(error);
            });
    </script>
</body>
</html>
