package kr.co.duck.domain;

import org.springframework.stereotype.Service;
import kr.co.duck.repository.MemberRepository;
import kr.co.duck.repository.QuizRoomAttendeeRepository;
import kr.co.duck.repository.RewardRepository;

@Service
public class MemberCommand {
	private final MemberRepository memberRepository;
	private final QuizRoomAttendeeRepository quizRoomAttendeeRepository; // QuizRoom과 관련된 레포지토리
	private final RewardRepository rewardRepository;

	// 생성자
	public MemberCommand(MemberRepository memberRepository, QuizRoomAttendeeRepository quizRoomAttendeeRepository,
			RewardRepository rewardRepository) {
		this.memberRepository = memberRepository;
		this.quizRoomAttendeeRepository = quizRoomAttendeeRepository;
		this.rewardRepository = rewardRepository;
	}

	// 멤버 객체 저장하기
	public void saveMember(Member member) {
		// 기존 멤버 확인
		Member existingMember = memberRepository.findById(member.getMemberId()).orElse(null);

		if (existingMember == null) {
			// 기존 멤버가 없으면 저장
			memberRepository.save(member);
		} else {
			// 기존 멤버가 있으면 병합(업데이트)
			existingMember.setNickname(member.getNickname());
			existingMember.setEmail(member.getEmail());
			// 필요한 필드를 업데이트하고 저장
			memberRepository.save(existingMember);
		}
	}

	// 멤버 객체로 데이터 삭제하기
	public void deleteMember(Member member) {
		memberRepository.delete(member);
	}

	// 회원 탈퇴하며 모든 정보를 정리하기
	public void removeMemberInfo(Member member) {
		// 퀴즈룸 참여 여부 확인
		if (quizRoomAttendeeRepository.existsByMember(member)) {
			quizRoomAttendeeRepository.deleteAllByMember(member);
		}

		// 리워드 여부 확인
		if (rewardRepository.existsByMember(member)) {
			rewardRepository.deleteAllByMember(member);
		}

		// 회원 삭제
		deleteMember(member);
	}

	// 특정 ID로 멤버 찾기
	public Member findMemberById(int memberId) {
		return memberRepository.findById(memberId).orElse(null);
	}
}
