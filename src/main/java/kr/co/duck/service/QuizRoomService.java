package kr.co.duck.service;

import static kr.co.duck.util.StatusCode.NOT_EXIST_ROOMS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.duck.beans.MemberBean;
import kr.co.duck.beans.QuizRoomBean;
import kr.co.duck.beans.QuizRoomListBean;
import kr.co.duck.domain.Member;
import kr.co.duck.domain.MemberCommand;
import kr.co.duck.domain.MemberGameStats;
import kr.co.duck.domain.QuizCommand;
import kr.co.duck.domain.QuizMessage;
import kr.co.duck.domain.QuizQuery;
import kr.co.duck.domain.QuizRoom;
import kr.co.duck.domain.QuizRoomAttendee;
import kr.co.duck.repository.QuizRoomRepository;
import kr.co.duck.repository.SessionRepository;
import kr.co.duck.util.CustomException;
import kr.co.duck.util.StatusCode;
import kr.co.duck.util.UserDetailsImpl;

@Service
public class QuizRoomService {
	private final QuizService quizService;
	private final MemberCommand memberCommand;
	private final QuizQuery quizQuery;
	private final QuizCommand quizCommand;
	private final SessionRepository sessionRepository;
	private final QuizRoomRepository quizRoomRepository;

	public QuizRoomService(QuizService quizService, MemberCommand memberCommand, QuizQuery quizQuery,
			QuizCommand quizCommand, SessionRepository sessionRepository, QuizRoomRepository quizRoomRepository) {
		this.quizService = quizService;
		this.memberCommand = memberCommand;
		this.quizQuery = quizQuery;
		this.quizCommand = quizCommand;
		this.sessionRepository = sessionRepository;
		this.quizRoomRepository = quizRoomRepository;
	}

	// MemberGameStats가 null인 경우 생성하여 설정하는 메서드
	private void ensureMemberGameStats(Member member) {
		if (member.getMemberGameStats() == null) {
			member.setMemberGameStats(new MemberGameStats(member));
		}
	}

	// 퀴즈방 ID로 퀴즈방 조회
	@Transactional(readOnly = true)
	public QuizRoomBean findRoomById(int roomId) {
		QuizRoom quizRoom = quizQuery.findQuizRoomByRoomId(roomId);
		if (quizRoom == null) {
			throw new CustomException(NOT_EXIST_ROOMS);
		}

		List<QuizRoomAttendee> quizRoomAttendeeList = quizQuery.findAttendeeByQuizRoom(quizRoom);
		List<MemberBean> memberList = new ArrayList<>();
		for (QuizRoomAttendee quizRoomAttendee : quizRoomAttendeeList) {
			Member eachMember = memberCommand.findMemberById(quizRoomAttendee.getMember().getMemberId());
			MemberBean memberBean = new MemberBean(eachMember.getMemberId(), eachMember.getNickname(),
					eachMember.getEmail());
			memberList.add(memberBean);
		}

		String membersString = memberList.stream().map(member -> String.valueOf(member.getMember_id()))
				.reduce((a, b) -> a + "," + b).orElse("");

		return new QuizRoomBean(quizRoom.getQuizRoomId(), quizRoom.getQuizRoomName(), quizRoom.getQuizRoomPassword(),
				quizRoom.getOwner(), quizRoom.getStatus(), memberList.size(), membersString);
	}

	// 퀴즈방 목록 조회
	@Transactional
	public QuizRoomListBean getAllQuizRooms(Pageable pageable) {
		Page<QuizRoom> rooms = quizQuery.findQuizRoomByPageable(pageable);
		List<QuizRoomBean> quizRoomList = new ArrayList<>();

		for (QuizRoom room : rooms) {
			List<QuizRoomAttendee> quizRoomAttendeeList = quizQuery.findAttendeeByQuizRoom(room);
			List<MemberBean> memberList = new ArrayList<>();

			for (QuizRoomAttendee quizRoomAttendee : quizRoomAttendeeList) {
				Member eachMember = memberCommand.findMemberById(quizRoomAttendee.getMember().getMemberId());
				MemberBean memberBean = new MemberBean(eachMember.getMemberId(), eachMember.getNickname(),
						eachMember.getEmail());
				memberList.add(memberBean);
			}

			String membersString = memberList.stream().map(member -> String.valueOf(member.getMember_id()))
					.reduce((a, b) -> a + "," + b).orElse("");

			QuizRoomBean quizRoomBean = new QuizRoomBean(room.getQuizRoomId(), room.getQuizRoomName(),
					room.getQuizRoomPassword(), room.getOwner(), room.getStatus(), memberList.size(), membersString);
			quizRoomList.add(quizRoomBean);
		}

		int totalPage = rooms.getTotalPages();
		return new QuizRoomListBean(totalPage, quizRoomList);
	}

	// 퀴즈방 검색
	public QuizRoomListBean searchQuizRoom(Pageable pageable, String keyword) {
		Page<QuizRoom> rooms = quizRoomRepository.findByQuizRoomNameContaining(pageable, keyword);

		if (rooms.isEmpty()) {
			throw new CustomException(NOT_EXIST_ROOMS);
		}

		List<QuizRoomBean> quizRoomList = new ArrayList<>();
		for (QuizRoom room : rooms) {
			List<QuizRoomAttendee> quizRoomAttendeeList = quizQuery.findAttendeeByQuizRoom(room);
			List<MemberBean> memberList = new ArrayList<>();

			for (QuizRoomAttendee quizRoomAttendee : quizRoomAttendeeList) {
				Member eachMember = memberCommand.findMemberById(quizRoomAttendee.getMember().getMemberId());
				MemberBean memberBean = new MemberBean(eachMember.getMemberId(), eachMember.getNickname(),
						eachMember.getEmail());
				memberList.add(memberBean);
			}

			String membersString = memberList.stream().map(member -> String.valueOf(member.getMember_id()))
					.reduce((a, b) -> a + "," + b).orElse("");

			QuizRoomBean quizRoomBean = new QuizRoomBean(room.getQuizRoomId(), room.getQuizRoomName(),
					room.getQuizRoomPassword(), room.getOwner(), room.getStatus(), memberList.size(), membersString);
			quizRoomList.add(quizRoomBean);
		}

		int totalPage = rooms.getTotalPages();
		return new QuizRoomListBean(totalPage, quizRoomList);
	}

	// 퀴즈방 생성
	@Transactional
	public QuizRoomBean createRoom(QuizRoomBean quizRoomBean, Member member) {
		ensureMemberGameStats(member);

		// 게임 통계 업데이트
		MemberGameStats stats = member.getMemberGameStats();
		stats.setMakeRoomNum(stats.getMakeRoomNum() + 1);

		// Member와 MemberGameStats를 저장
		memberCommand.saveMember(member);

		// QuizRoom 객체 생성
		QuizRoom quizRoom = new QuizRoom(quizRoomBean.getQuizRoomName(), quizRoomBean.getQuizRoomPassword(),
				member.getNickname(), 1, quizRoomBean.getMemberCount(), quizRoomBean.getMembers());

		quizCommand.saveQuizRoom(quizRoom);

		// 퀴즈 방 참가자 객체 생성
		QuizRoomAttendee quizRoomAttendee = new QuizRoomAttendee(quizRoom, member);
		quizCommand.saveQuizRoomAttendee(quizRoomAttendee);

		return new QuizRoomBean(quizRoom.getQuizRoomId(), quizRoom.getQuizRoomName(), quizRoom.getQuizRoomPassword(),
				quizRoom.getOwner(), quizRoom.getStatus(), quizRoom.getMemberCount(), quizRoomBean.getMembers());
	}

	// 퀴즈룸 입장
	@Transactional
	public Map<String, String> enterQuizRoom(int roomId, Member member, String roomPassword) {
		QuizRoom enterQuizRoom = quizQuery.findQuizRoomByRoomIdLock(roomId);

		// 비밀번호 검증
		if (!enterQuizRoom.getQuizRoomPassword().equals(roomPassword)) {
			throw new CustomException(StatusCode.BAD_REQUEST, "비밀번호가 올바르지 않습니다.");
		}

		ensureMemberGameStats(member);

		// 게임 통계 업데이트
		MemberGameStats stats = member.getMemberGameStats();
		stats.setEnterGameNum(stats.getEnterGameNum() + 1);
		memberCommand.saveMember(member);

		QuizRoomAttendee newAttendee = new QuizRoomAttendee(enterQuizRoom, member);
		quizCommand.saveQuizRoomAttendee(newAttendee);

		Map<String, String> roomInfo = new HashMap<>();
		roomInfo.put("quizRoomName", enterQuizRoom.getQuizRoomName());
		roomInfo.put("roomId", String.valueOf(enterQuizRoom.getQuizRoomId()));
		roomInfo.put("owner", enterQuizRoom.getOwner());
		roomInfo.put("status", String.valueOf(enterQuizRoom.getStatus()));

		return roomInfo;
	}

	// 퀴즈방 입장 검증
	public void enterVerify(int roomId, Member member) {
		// 멤버가 null인 경우 예외 처리
		if (member == null) {
			throw new CustomException(StatusCode.INVALID_TOKEN, "유효하지 않은 토큰입니다. 로그인 후 시도해주세요.");
		}

		// 방 존재 여부 확인
		QuizRoom room = quizQuery.findQuizRoomByRoomId(roomId);
		if (room == null) {
			throw new CustomException(StatusCode.NOT_EXIST_ROOMS, "해당 ID의 방이 존재하지 않습니다.");
		}

		// 방 참가자 목록 가져오기
		List<QuizRoomAttendee> quizRoomAttendeeList = quizQuery.findAttendeeByRoomId(roomId);

		// 이미 참여한 경우 확인
		boolean isAlreadyParticipant = quizRoomAttendeeList.stream()
				.anyMatch(quizRoomAttendee -> member.getNickname().equals(quizRoomAttendee.getMemberNickname()));

		if (isAlreadyParticipant) {
			throw new CustomException(StatusCode.MEMBER_DUPLICATED, "이미 방에 참여하였습니다.");
		}

		// 최대 인원 제한 확인
		final int MAX_CAPACITY = 10; // 설정할 최대 인원수
		if (quizRoomAttendeeList.size() >= MAX_CAPACITY) {
			throw new CustomException(StatusCode.CANT_ENTER, "방의 최대 참여 인원을 초과하였습니다.");
		}
	}

	// 방 나가기
	@Transactional
	public void roomExit(int roomId, Member member) {
		QuizRoom enterQuizRoom = quizQuery.findQuizRoomByRoomId(roomId);
		QuizRoomAttendee quizRoomAttendee = quizQuery.findAttendeeByMember(member);
		quizCommand.deleteQuizRoomAttendee(quizRoomAttendee);

		List<QuizRoomAttendee> existQuizRoomAttendee = quizQuery.findAttendeeByQuizRoom(enterQuizRoom);

		if (existQuizRoomAttendee.isEmpty()) {
			ensureMemberGameStats(member);

			// 게임 통계 업데이트
			MemberGameStats stats = member.getMemberGameStats();
			stats.setSoloExitNum(stats.getSoloExitNum() + 1);
			memberCommand.saveMember(member);

			quizCommand.deleteQuizRoom(enterQuizRoom);
			sessionRepository.deleteAllClientsInRoom(roomId);
		}

		if (enterQuizRoom.getStatus() == 0) {
			quizService.forcedEndQuiz(roomId, member.getNickname());
		}

		Map<String, Object> contentSet = new HashMap<>();
		contentSet.put("memberCount", existQuizRoomAttendee.size());
		contentSet.put("alert", member.getNickname() + " 님이 방을 나가셨습니다!");

		quizService.sendQuizMessage(roomId, QuizMessage.MessageType.LEAVE, contentSet, null, member.getNickname());

		if (member.getNickname().equals(enterQuizRoom.getOwner()) && !existQuizRoomAttendee.isEmpty()) {
			String nextOwner = existQuizRoomAttendee.get((int) (Math.random() * existQuizRoomAttendee.size()))
					.getMemberNickname();
			enterQuizRoom.setOwner(nextOwner);
			quizService.sendQuizMessage(roomId, QuizMessage.MessageType.NEWOWNER, null, null, nextOwner);
		}
	}
}
