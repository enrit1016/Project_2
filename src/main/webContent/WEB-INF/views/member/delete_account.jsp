<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var='root' value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
        // 비밀번호가 틀렸을 때 알림창을 띄우는 함수
        function showAlert() {
            alert("비밀번호가 일치하지 않습니다. 다시 시도해주세요.");
        }
    </script>
</head>
<body>
<h2>회원 탈퇴</h2>

    <!-- Spring의 form:form 태그를 사용하여 비밀번호 입력 폼을 생성 -->
    <form:form action="${root}member/delete_pro" method="post" modelAttribute="deleteMemberBean">
        
        <!-- 비밀번호 입력 필드 -->
        <label for="password">비밀번호 확인:</label>
        <form:password path="password" id="password" required="true" />
        
        <!-- 제출 버튼 -->
        <input type="submit" value="탈퇴하기" />
    </form:form>

    <!-- fail 값이 true일 경우 자바스크립트로 alert 창 띄우기 -->
    <c:if test="${fail == true}">
        <script type="text/javascript">
            showAlert();
        </script>
    </c:if>
</body>
</html>