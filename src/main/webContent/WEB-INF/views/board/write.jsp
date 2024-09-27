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
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet"> 
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
<title>Insert title here</title>
<style>
.post-form {
    width: 100%;
    height: auto;
    overflow-y: auto;
    resize: none;
}

</style>
</head>
<body>
	<c:import url="/WEB-INF/views/include/sidebar.jsp" />
	<div class="container">
		<div style="font-size: 13px; margin: 20px 0;">
			<div>(현재 로그인중인 유저)</div>
		</div>
		<div style="margin-left: 30px">
		<form:form action="${root}board/write_pro" method="post" modelAttribute="writeContentBean" enctype="multipart/form-data">
			<div style="margin-bottom: 20px;">
				<span class="custom-select"> 
				<form:select path="board_id" id="drop-down">
					<form:option value="-1" disabled="disabled" selected="selected" style="color: black;">카테고리</form:option>
					<form:option value="1" style="color: #74E885">자유게시판</form:option>
					<form:option value="2" style="color: #BC5ADC">소식/정보</form:option>
					<form:option value="3" style="color: yellow;">음악 추천</form:option>
				</form:select>
				</span>
				<span>
				<form:input path="content_title" style="width: 400px; font-size: 16px; padding: 3px; margin-right: 20px;" 
				placeholder="제목" />
				</span>
			</div>
			
			<div class="post-form">
        		<textarea name="content_text" id="summernote">
        		</textarea>
    		</div>
			
			<div>
				<form:input type='file' path="upload_file" class="form-control" accept="image/*"/>
			</div>
			
			<span style="float: right; margin: 30px 0">
				<form:button type="reset" class="write-btn"><a href="${root }board/main">취소</a></form:button>
				<form:button class="write-btn">작성</form:button>
			</span>
		</form:form>
		</div>
	</div>
	<script>
        $(document).ready(function() {
            $('#summernote').summernote({
                height: 500,
                lang: 'ko-KR',
                toolbar: [
                    ['fontsize', ['fontsize']],
                    ['style', ['bold', 'italic', 'underline', 'strikethrough', 'clear']],
                    ['color', ['color']],
                    ['table', ['table']],
                    ['para', ['ul', 'ol', 'paragraph']],
                    ['height', ['height']]
                ],
                fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New', '맑은 고딕', '궁서', '굴림체', '굴림', '돋움체', '바탕체'],
                fontSizes: ['8', '9', '10', '11', '12', '14', '16', '18', '20', '22', '24', '28', '30', '36', '50', '72', '96'],
                focus: true,
                callbacks: {
                    onImageUpload: function(files) {
                        for (var i = 0; i < files.length; i++) {
                            imageUploader(files[i], this);
                        }
                    }
                }
            });
        });

    </script>
</body>
</html>
