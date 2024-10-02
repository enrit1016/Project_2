package kr.co.duck.domain;

import static kr.co.duck.util.StatusCode.NOT_FOUND_MEMBER;

import org.springframework.stereotype.Service;

import kr.co.duck.repository.MemberRepository;
import kr.co.duck.util.CustomException;

// 기능 : 회원 도메인 관련 DB Read 관리
@Service
public class MemberQuery {
	private final MemberRepository memberRepository;

	// 생성자
	public MemberQuery(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	// Email로 데이터 검증
	public boolean isMemberDuplicateByEmail(String email) {
		return memberRepository.existsByEmail(email);
	}

	// Nickname으로 데이터 검증
	public boolean isMemberDuplicateByNickname(String nickname) {
		return memberRepository.existsByNickname(nickname);
	}

	// 닉네임으로 Member 객체 갖고오기
	public Member findMemberByNickname(String nickname) {
		return memberRepository.findByNickname(nickname).orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER));
	}

	// 이메일로 Member 객체 찾아오기
	public Member findMemberByEmail(String email) {
		return memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER));
	}

	// 이메일로 Member 객체 존재여부 확인
	public boolean existsMemberByEmail(String email) {
		return memberRepository.existsByEmail(email);
	}

	// 카카오 아이디로 Member 객체 찾아오기
	//public Member findMemberByKakaoId(int kakaoId) {
	//	return memberRepository.findByKakaoId(kakaoId).orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER));
	//}

	// 멤버 ID로 Member 객체 찾아오기
	public Member findMemberById(int memberId) {
		return memberRepository.findById(memberId).orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER));
	}

	// 카카오 아이디로 존재여부 확인
	//public boolean existsMemberByKakaoId(int kakaoId) {
		//return memberRepository.existsByKakaoId(kakaoId);
	//}
}
