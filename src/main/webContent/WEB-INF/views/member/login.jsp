<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var='root' value="${pageContext.request.contextPath }/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 상단 메뉴 부분 -->
	<c:import url="/WEB-INF/views/include/top_menu.jsp" />

	<div class="container" style="margin-top: 100px">
		<div class="row">
			<div class="col-sm-3"></div>
			<div class="col-sm-6">
				<div class="card shadow">
					<div class="card-body">
						<c:if test="${fail == true }">
							<div class="alert alert-danger">
								<h3>로그인 실패</h3>
								<p>아이디 비밀번호를 확인해주세요</p>
							</div>
						</c:if>
						<form:form action="${root }member/login_pro" method='post'
							modelAttribute="tempLoginMemberBean">
							<div class="form-group">
								<form:label path="membername">아이디</form:label>
								<form:input path="membername" class="form-control" />
								<form:errors path="membername" style="color:red" />
							</div>
							<div class="form-group">
								<form:label path="password">비밀번호</form:label>
								<form:password path="password" class="form-control" />
								<form:errors path="password" style="color:red" />
							</div>
							<div class="form-group text-right">
								<form:button class='btn btn-primary'>로그인</form:button>
								<a href="${root }member/join" class="btn btn-danger">회원가입</a>
							</div>
							<div class="googleSignUp">
								<button type ="button" onclick="location.href='${root}member/getGoogleAuthUrl'">구글로그인</button>
							</div>
						</form:form>
					</div>
				</div>
			</div>
			<div class="col-sm-3"></div>
		</div>
	</div>

	<c:import url="/WEB-INF/views/include/bottom_info.jsp" />
</body>
</html>