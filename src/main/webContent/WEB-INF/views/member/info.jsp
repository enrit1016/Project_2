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
</head>
<body>
<h1>info</h1>
<div class="wrapper">
	
	<c:import url="/WEB-INF/views/include/top_menu.jsp"></c:import>
	
		<form:form action="${root }member/modify" method="post" modelAttribute="infoMemberBean">
			
			<h1>MyAccount</h1>
			<!-- //유저아이디, 이름, 이메일, 집주소, 전화번호, 생일, 생성일 -->
			<div class="input-box">
				<form:label path="membername">아이디</form:label>
				<form:input path = "membername" placeholder ="ID" readonly = "true"/>
				<i class='bx bxs-user'></i>
			</div>
			<div class="input-box">		
				<form:label path="nickname">닉네임</form:label>	
				<form:input path = "nickname" placeholder = "NickName"  readonly = "true"/>
				<i class='bx bx-edit-alt'></i>
			</div>
			<div class="input-box">		
				<form:label path="real_name">실명</form:label>	
				<form:input path = "real_name" placeholder="Real Name" readonly = "true"/>
				<i class='bx bxs-phone'></i>
			</div>
			<div class="input-box">		
				<form:label path="email">이메일</form:label>	
				<form:input path = "email" placeholder = "Email"  readonly = "true"/>
				<i class='bx bx-envelope'></i>
			</div>
			<div class="input-box">	
				<form:label path="age">나이</form:label>		
				<form:input path = "age" placeholder="Age"  readonly = "true"/>
				<i class='bx bx-home'></i>
			</div>
			<div class="input-box">	
				<form:label path="join_date">가입날짜</form:label>		
				<form:input path = "join_date" placeholder="Join Date" readonly = "true"/>
				<i class='bx bxs-phone'></i>
			</div>
			<div class="input-box">	
				<form:label path="logintype">로그인타입</form:label>		
				<form:input path = "logintype" placeholder="LoginType" readonly = "true"/>
				<i class='bx bxs-phone'></i>
			</div>
			
			<div class="row justify-content-center">
				<form:button class="btn btn-outline-dark" type="button" onclick="location.href='${root }member/delete_account'" style="margin-right:20px; width:100px">회원탈퇴</form:button>
				<!-- 나중에 메인 버튼 탈퇴하기 버튼으로 교체 예정 -->
				<form:button class="btn btn-outline-dark" type="submit" style="margin-left:20px; width:100px;">수정하기</form:button>
			</div>
		</form:form>
	</div>
</body>
</html>