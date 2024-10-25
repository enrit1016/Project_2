package kr.co.duck.repository;

import kr.co.duck.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// 기능 : 유저 정보 레포지토리
public interface MemberRepository extends JpaRepository<Member, Integer> {

    // memberId로 회원 조회 (기존 findById를 대체)
    Optional<Member> findByMemberId(int memberId);

    // 이메일로 회원 조회
    Optional<Member> findByEmail(String email);

    // 닉네임으로 회원 조회
    Optional<Member> findByNickname(String nickname);

    // 이메일 중복 확인
    boolean existsByEmail(String email);

    // 닉네임 중복 확인
    boolean existsByNickname(String nickname);
    
    // sender(닉네임)로 memberId 조회
    default Optional<Integer> getMemberIdBySender(String sender) {
        return findByNickname(sender).map(Member::getMemberId);
    }
    

	// Optional: 카카오 ID로 회원 조회 (필요 시 주석 해제)
	// Optional<Member> findByKakaoId(int kakaoId);

	// Optional: 카카오 ID 중복 확인 (필요 시 주석 해제)
	// boolean existsByKakaoId(int kakaoId);

}


