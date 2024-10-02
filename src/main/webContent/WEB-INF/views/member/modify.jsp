<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>

	var myVar = true;

	$(document).ready(function(){
		$('#changeBtn').click(function(){
			$('.wrapper').css('height', '300px');
			var email=$("#email").val();
			
			if(myVar){
				myVar = false;
				$('.infoID').addClass('non');
				$('.info').removeClass('non');
			}
			
			$.ajax({
				url: '${root}member/modifyCertificationCode/' + email,
				dataType : 'text',
				success: function(data){
					console.log("data : "+data);
					code = data;
					$("#authCode1").val(code);
					alert('인증코드가 발송되었습니다.');
				}
			});
		   // 기본적으로 submit 버튼을 비활성화 상태로 설정
		    $('form button[type="submit"]').prop('disabled', true);

		    // authCode2의 값이 변경될 때마다 체크하여 버튼 활성화/비활성화
		    $('form input[name="authCode2"]').on('input', function() {
		        var authCode2Value = $(this).val();

		        // authCode2 값이 존재하는지 확인하여 submit 버튼 상태 변경
		        if (authCode2Value.trim() !== '') {
		            $('form button[type="submit"]').prop('disabled', false);
		        } else {
		            $('form button[type="submit"]').prop('disabled', true);
		        }
		    });
		});
	});
</script>
</head>
<body>
	<c:import url="/WEB-INF/views/include/top_menu.jsp" />
	<div class="wrapper">
	<form:form action="${root }member/modify_pro" method="post"
		modelAttribute="modifyMemberBean">
		<form:hidden path="authCode1"/>
		<form:hidden path="membername"/>
		<form:hidden path="password"/>
		<form:hidden path="email"/>
		<form:hidden path="join_date"/>
		<form:hidden path="role"/>
		<form:hidden path="logintype"/>
		
		<hr />
			<div class="input-box infoID">
				<form:label path="age">나이</form:label>
				<form:input path="age" value=""></form:input>
				<form:errors path="age"></form:errors>
				<i class='bx bx-home'></i>
			</div>
						
			<div class="input-box infoID">
				<form:label path="real_name">실명</form:label>
				<form:input path="real_name" value=""></form:input>				
				<form:errors path="real_name"></form:errors>				
				<i class='bx bxs-user'></i>
			</div>
							
			<div class="input-box infoID">
				<form:label path="nickname">닉네임</form:label>
				<form:input path="nickname" placeholder = "닉네임" value=""></form:input>
				<form:errors path="nickname"></form:errors>
				<i class='bx bxs-phone'></i>
			</div>					

			<button type="button" id = "changeBtn" class="btn infoID" style="width:80%; color:#303080; margin-left:31px;">정보수정</button>
		
		<div class="input-box info non">
				<form:input path="authCode2" type="text" placeholder="Certification Code"/>
				<form:errors path = "authCode2"/>
				<i class='bx bx-envelope'></i>
			<form:button type="submit" class="btn" style=" width: 80%; margin-top: 50px; height: 40px; margin-left: 33px;">확인</form:button>
		</div>

	</form:form>
	</div>
</body>
</html>