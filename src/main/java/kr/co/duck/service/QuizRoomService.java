package kr.co.duck.service;

import static kr.co.duck.util.StatusCode.ALREADY_PLAYING;
import static kr.co.duck.util.StatusCode.CANT_ENTER;
import static kr.co.duck.util.StatusCode.MEMBER_DUPLICATED;
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
import kr.co.duck.domain.MemberQuery;
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
	private final MemberQuery memberQuery;
	private final MemberCommand memberCommand;
	private final QuizQuery quizQuery;
	private final QuizCommand quizCommand;
	private final SessionRepository sessionRepository;
	private final QuizRoomRepository quizRoomRepository;

	public QuizRoomService(QuizService quizService, MemberQuery memberQuery, MemberCommand memberCommand,
			QuizQuery quizQuery, QuizCommand quizCommand, SessionRepository sessionRepository,
			QuizRoomRepository quizRoomRepository) {
		this.quizService = quizService;
		this.memberQuery = memberQuery;
		this.memberCommand = memberCommand;
		this.quizQuery = quizQuery;
		this.quizCommand = quizCommand;
		this.sessionRepository = sessionRepository;
		this.quizRoomRepository = quizRoomRepository;
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
				Member eachMember = memberQuery.findMemberById(quizRoomAttendee.getMember().getId());
				MemberBean memberBean = new MemberBean(eachMember.getId(), eachMember.getNickname(),
						eachMember.getEmail());
				memberList.add(memberBean);
			}

			// 멤버 리스트를 콤마로 구분된 문자열로 변환
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
	public QuizRoomBean createRoom(QuizRoomBean quizRoomBean) {
		// 더미 사용자 (로그인한 사용자 정보 대신 더미 데이터 사용)
		Member member = memberQuery.findMemberById(1); // ID 1인 사용자를 더미로 가져옴

		member.updateMakeRoom(1);
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
	public Map<String, String> enterQuizRoom(int roomId, Member member) {
		QuizRoom enterQuizRoom = quizQuery.findQuizRoomByRoomIdLock(roomId);

		if (enterQuizRoom.getStatus() == 0) {
			throw new CustomException(ALREADY_PLAYING);
		}

		List<QuizRoomAttendee> quizRoomAttendeeList = quizQuery.findAttendeeByQuizRoom(enterQuizRoom);

		if (quizRoomAttendeeList.size() > 3) {
			throw new CustomException(CANT_ENTER);
		}

		member.updateEnterGame(1);
		memberCommand.saveMember(member);

		for (QuizRoomAttendee quizRoomAttendee : quizRoomAttendeeList) {
			if (member.getId() == quizRoomAttendee.getMember().getId()) {
				throw new CustomException(MEMBER_DUPLICATED);
			}
		}

		QuizRoomAttendee newAttendee = new QuizRoomAttendee(enterQuizRoom, member);
		quizCommand.saveQuizRoomAttendee(newAttendee);

		Map<String, String> roomInfo = new HashMap<>();
		roomInfo.put("quizRoomName", enterQuizRoom.getQuizRoomName());
		roomInfo.put("roomId", String.valueOf(enterQuizRoom.getQuizRoomId()));
		roomInfo.put("owner", enterQuizRoom.getOwner());
		roomInfo.put("status", String.valueOf(enterQuizRoom.getStatus()));

		return roomInfo;
	}

	// 퀴즈방 키워드 조회
	public QuizRoomListBean searchQuizRoom(Pageable pageable, String keyword) {
		Page<QuizRoom> rooms = quizQuery.findQuizRoomByContainingKeyword(pageable, keyword);

		if (rooms.isEmpty()) {
			throw new CustomException(NOT_EXIST_ROOMS);
		}

		List<QuizRoomBean> quizRoomList = new ArrayList<>();
		for (QuizRoom room : rooms) {
			List<QuizRoomAttendee> quizRoomAttendeeList = quizQuery.findAttendeeByQuizRoom(room);
			List<MemberBean> memberList = new ArrayList<>();

			for (QuizRoomAttendee quizRoomAttendee : quizRoomAttendeeList) {
				Member eachMember = memberQuery.findMemberById(quizRoomAttendee.getMember().getId());
				MemberBean memberBean = new MemberBean(eachMember.getId(), eachMember.getNickname(),
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

	// 방장 정보 조회
	public Map<String, String> ownerInfo(int roomId) {
		QuizRoom enterRoom = quizQuery.findQuizRoomByRoomId(roomId);
		String ownerNickname = enterRoom.getOwner();
		Member member = memberQuery.findMemberByNickname(ownerNickname);
		String ownerId = String.valueOf(member.getId());

		Map<String, String> ownerInfo = new HashMap<>();
		ownerInfo.put("ownerId", ownerId);
		ownerInfo.put("ownerNickname", ownerNickname);

		return ownerInfo;
	}

	// 세션 끊김 시 방에서 참가자 정보 정리
	public void exitGameRoomAboutSession(String nickname, int roomId) {
		Member member = memberQuery.findMemberByNickname(nickname);
		List<QuizRoomAttendee> quizRoomAttendeeList = quizQuery.findAttendeeByRoomId(roomId);
		for (QuizRoomAttendee quizRoomAttendee : quizRoomAttendeeList) {
			if (nickname.equals(quizRoomAttendee.getMemberNickname())) {
				roomExit(roomId, member);
			}
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
			member.updateSoloExit(1);
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

		// 방장 변경
		if (member.getNickname().equals(enterQuizRoom.getOwner()) && !existQuizRoomAttendee.isEmpty()) {
			String nextOwner = existQuizRoomAttendee.get((int) (Math.random() * existQuizRoomAttendee.size()))
					.getMemberNickname();
			enterQuizRoom.setOwner(nextOwner);
			quizService.sendQuizMessage(roomId, QuizMessage.MessageType.NEWOWNER, null, null, nextOwner);
		}
	}

	// 퀴즈방 입장 검증
	public void enterVerify(int roomId, UserDetailsImpl userDetails) {
		if (userDetails == null) {
			throw new CustomException(StatusCode.INVALID_TOKEN);
		}

		int cnt = 0;
		List<QuizRoomAttendee> quizRoomAttendeeList = quizQuery.findAttendeeByRoomId(roomId);

		for (QuizRoomAttendee quizRoomAttendee : quizRoomAttendeeList) {
			if (userDetails.getMember().getNickname().equals(quizRoomAttendee.getMemberNickname())) {
				cnt++;
			}
		}

		if (cnt != 1) {
			throw new CustomException(StatusCode.BAD_REQUEST);
		}
	}
}
