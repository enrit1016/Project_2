<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Summernote 에디터</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
</head>
<body>
    <div class="container post-form">
        <textarea name="postContent" id="summernote"></textarea>
    </div>
    <script>
        $(document).ready(function() {
            $('#summernote').summernote({
                height: 800,
                lang: 'ko-KR',
                toolbar: [
                    ['fontsize', ['fontsize']],
                    ['style', ['bold', 'italic', 'underline', 'strikethrough', 'clear']],
                    ['color', ['color']],
                    ['table', ['table']],
                    ['para', ['ul', 'ol', 'paragraph']],
                    ['height', ['height']],
                    ['insert', ['picture']]
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

        function imageUploader(file, el) {
        	var formData = new FormData();
        	formData.append('file', file);
          
        	$.ajax({                                                              
        		data : formData,
        		type : "POST",
                // url은 자신의 이미지 업로드 처리 컨트롤러 경로로 설정해주세요.
        		url : '/post/image-upload',  
        		contentType : false,
        		processData : false,
        		enctype : 'multipart/form-data',                                  
        		success : function(data) {   
        			$(el).summernote('insertImage', "${pageContext.request.contextPath}/assets/images/upload/"+data, function($image) {
        				$image.css('width', "100%");
        			});
                    // 값이 잘 넘어오는지 콘솔 확인 해보셔도됩니다.
        			console.log(data);
        		}
        	});
        }
    </script>
</body>
</html>
