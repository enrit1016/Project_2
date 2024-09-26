<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="root" value="${pageContext.request.contextPath }/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <title>Insert title here</title>
  
  <!-- jQuery 라이브러리 추가 -->
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  
  
</head>
<script>
	function checkMemberNameExist(){
		var membername = $("#membername").val()
		
		if(membername.length == 0){
			alert('아이디를 입력해주세요')
			 return
		}
		
		$.ajax({
			url : '${root}member/checkMemberNameExist/' + membername,
			type : 'get',
			dataType : 'text',
			success : function(result){
				if(result.trim() == 'true'){
					alert('사용할 수 있는 아이디 입니다')
					$("#memberNameExist").val('true')
				}else if(result.trim() == 'false') {
					alert('사용할 수 없는 아이디 입니다')
					$("#memberNameExist").val('false')
				}
			}
		})		
	}
	function resetMemberNameExist(){
		$("#memberNameExist").val('false')
	}	
	
	//사용자가 입력한 비밀번호 실시간으로 받아서 검증
	function checkPasswordSecGrade(){
		var password = document.getElementById("password").value;
		var gradeElement = document.querySelector('.checkOutGrade');
		
		if(password.length === 0){
			gradeElement.textContent = "보안 등급 : ";
			return;
		}
		
		var grade = calculatePasswordGrade(password);
		
		switch(grade){
		
		case "weak":
			gradeElement.textContent = "보안 등급 : 낮음";
			break;
		case "medium":
			gradeElement.textContent = "보안 등급 : 중간";
			break;
		case "strong":
			gradeElement.textContent = "보안 등급 : 높음";
			break;
		case "very strong":
			gradeElement.textContent = "보안 등급 : 매우 높음";
			break;
		default:
			gradeElement.textContent = "보안 등급 : "
		}
		
	}
	
    function calculatePasswordGrade(password) {
        var grade = "weak";

        if (password.length < 5) {
            return grade;
        }

        var hasUpperCase = /[A-Z]/.test(password);
        var hasLowerCase = /[a-z]/.test(password);
        var hasNumber = /\d/.test(password);
        var hasSpecialChars = /[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]/.test(password);

        if (hasUpperCase && hasLowerCase && hasNumber && hasSpecialChars) {
            grade = "very strong";
        } else if (hasUpperCase && hasLowerCase && hasNumber || (hasSpecialChars && hasLowerCase && hasNumber)) {
            grade = "strong";
        } else if ((hasUpperCase && hasLowerCase) || (hasNumber && hasSpecialChars)) {
            grade = "medium";
        }

        return grade;
    }
	
</script>
<body>

<c:import url="/WEB-INF/views/include/top_menu.jsp"/>

<div class="container" style="margin-top:100px">
	<div class="row">
		<div class="col-sm-3"></div>
		<div class="col-sm-6">
			<div class="card shadow">
				<div class="card-body">
					<form:form action="${root }member/join_pro" method="post" modelAttribute="joinMemberBean">
						<form:hidden path="memberNameExist"/>
						<div class="form-group">
							<form:label path="nickname">닉네임</form:label>
							<form:input path="nickname" class="form-control"/>
							<form:errors path="nickname" style='color:red'/>
						</div>
						<div class="form-group">
							<form:label path="membername">아이디</form:label>
							<div class="input-group">
								<form:input path="membername" class='form-control' onkeypress="resetMemberNameExist()"/>
								<div class="input-group-append">
									<button type="button" class="btn btn-primary" onclick="checkMemberNameExist()">중복확인</button>
								</div>
							</div> 
							<form:errors path="membername" style='color:red'/>
						</div>
						<div class="form-group">
							<form:label path="age">나이</form:label>
							<form:input path="age" class="form-control"/>
							<form:errors path="age" style='color:red'/>
						</div>
						<div class="form-group">
							<form:label path="email">이메일</form:label>
							<form:input path="email" class="form-control"/>
							<form:errors path="email" style='color:red'/>
						</div>
						<div class="form-group">
							<form:label path="real_name">실명</form:label>
							<form:input path="real_name" class="form-control"/>
							<form:errors path="real_name" style='color:red'/>
						</div>
						
						<div class="form-group">						
							<form:label path="password">비밀번호</form:label>
							<form:password path="password" class="form-control" oninput="checkPasswordSecGrade(); checkPasswordEquals();"/>							
							<form:errors path="password" style='color:red'/>
						</div>
						<div class="checkOutGrade">
								보안 등급 : 
						</div>
						<div class="form-group">
							<form:label path="password2">비밀번호 확인</form:label>
							<form:password path="password2" class="form-control"/>
							<form:errors path="password2" style='color:red'/>
						</div>
						<div class="form-group">
							<div class="text-right">
								<form:button type="submit" class="btn btn-primary">회원가입</form:button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
		<div class="col-sm-3"></div>
	</div>
</div>

<c:import url="/WEB-INF/views/include/bottom_info.jsp"/>

</body>
</html>