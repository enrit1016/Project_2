package kr.co.duck.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kr.co.duck.beans.MemberBean;

public class MemberValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		
		return MemberBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MemberBean memberBean = (MemberBean) target;
		
		String beanName = errors.getObjectName();
		System.out.println(beanName);

		if (beanName.equals("modifyMemberBean")) {
			if (memberBean.getAuthCode1() != null) {
				if (memberBean.getAuthCode1().equals(memberBean.getAuthCode2()) == false) {
					errors.rejectValue("authCode2", "CodeNotEquals");
				}
			}
		}

		/*
		 * if (beanName.equals("modifyPasswordBean")) { if(memberBean.getAuthCode1() !=
		 * null) { if (memberBean.getAuthCode1().equals(memberBean.getAuthCode2()) ==
		 * false) { errors.rejectValue("authCode2", "CodeNotEquals"); } } if
		 * (memberBean.getPassword().equals(" ") ||
		 * memberBean.getPassword().trim().isEmpty()) { errors.rejectValue("user_pw",
		 * "pwIsEmpty"); } else if (memberBean.getPassword().length() < 8 ||
		 * memberBean.getPassword().length() > 21) { errors.rejectValue("user_pw",
		 * "pwSizeWrong"); } else if
		 * (memberBean.getPassword().equals(memberBean.getPassword2()) == false) {
		 * errors.rejectValue("user_pw", "NotEquals"); errors.rejectValue("user_pw2",
		 * "NotEquals"); } }
		 */
		
		/*
		 * //비밀번호 확인 또는 수정시 if (beanName.equals("joinMemberBean") ||
		 * beanName.equals("modifyMemberBean")) { if
		 * (memberBean.getPassword().equals(memberBean.getPassword2()) == false) {
		 * errors.rejectValue("password", "NotEquals"); errors.rejectValue("password2",
		 * "NotEquals"); }
		 * 
		 * 
		 * //회원가입시 if (beanName.equals("joinMemberBean")) {
		 * 
		 * if (memberBean.isMemberNameExist() == false) {
		 * errors.rejectValue("membername", "DontCheckMemberNameExist"); } } }
		 */
		
		if (beanName.equals("joinMemberBean")) {
			if (memberBean.getPassword().equals(" ") || memberBean.getPassword().trim().isEmpty()) {
				errors.rejectValue("password", "pwIsEmpty");
			}  else if (memberBean.getPassword().equals(memberBean.getPassword2()) == false) {
				errors.rejectValue("password", "NotEquals");
				errors.rejectValue("password2", "NotEquals");
			}

			/*
			 * if (userBean.getAuthCode1() != null) { if
			 * (userBean.getAuthCode1().equals(userBean.getAuthCode2()) == false) {
			 * errors.rejectValue("authCode2", "CodeNotEquals"); } }
			 */

			// 아이디 유효성검사 및 중복체크 검사
			if (memberBean.getMembername().equals(" ") || memberBean.getMembername().trim().isEmpty()) {
				errors.rejectValue("membername", "nameIsEmpty");
			} else if (memberBean.isMemberNameExist() == false) {
				errors.rejectValue("membername", "DontCheckMemberNameExist");
			}
			

			/*
			 * if (userBean.getUser_email().contains(" ") ||
			 * userBean.getUser_email().trim().isEmpty()) { errors.rejectValue("user_email",
			 * "VNotEmpty"); } else if (userBean.isUserEmailExit() == false) {
			 * errors.rejectValue("user_email", "DontcheckedEmail"); }
			 */
		}
		
	}

}
