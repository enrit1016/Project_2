package kr.co.duck.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.duck.beans.MemberBean;
import kr.co.duck.beans.QuizRoomBean;
import kr.co.duck.beans.QuizRoomListBean;
import kr.co.duck.domain.ChatMessage;
import kr.co.duck.domain.Member;
import kr.co.duck.domain.QuizMusic;
import kr.co.duck.repository.MemberRepository;
import kr.co.duck.service.QuizRoomService;
import kr.co.duck.service.QuizService;
import kr.co.duck.util.CustomException;

@Controller
@RequestMapping("/quiz/rooms")
public class QuizRoomController {

    private final QuizRoomService quizRoomService;
    private final QuizService quizService;
    private final SimpMessageSendingOperations messagingTemplate;  // messagingTemplate 추가
    private static final Logger log = LoggerFactory.getLogger(QuizRoomService.class);
    
    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    public QuizRoomController(QuizRoomService quizRoomService, QuizService quizService, SimpMessageSendingOperations messagingTemplate) {
        this.quizRoomService = quizRoomService;
        this.quizService = quizService;
        this.messagingTemplate = messagingTemplate;  // messagingTemplate 주입
    }

    // **퀴즈방 전체 조회 API**
    @GetMapping
    public ResponseEntity<QuizRoomListBean> getAllQuizRooms(Pageable pageable) {
        QuizRoomListBean quizRooms = quizRoomService.getAllQuizRooms(pageable);
        return ResponseEntity.ok(quizRooms);
    }

    // **퀴즈방 키워드 검색 API**
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchQuizRooms(Pageable pageable,
                                                               @RequestParam(required = false) String keyword) {
        Map<String, Object> response = new HashMap<>();
        if (keyword == null || keyword.isEmpty()) {
            response.put("success", false);
            response.put("message", "검색 키워드가 필요합니다.");
            return ResponseEntity.badRequest().body(response);
        }

        QuizRoomListBean quizRooms = quizRoomService.searchQuizRoom(pageable, keyword);
        response.put("success", true);
        response.put("data", quizRooms);
        response.put("message", quizRooms.getQuizRoomBeanList().isEmpty() ? "검색 결과가 없습니다." : "검색에 성공했습니다.");

        return ResponseEntity.ok(response);
    }

    // **퀴즈방 상세 조회 API**
    @GetMapping("/{roomId}")
    public String getQuizRoom(@PathVariable int roomId, Model model) {
        try {
            QuizRoomBean quizRoom = quizRoomService.findRoomById(roomId);
            model.addAttribute("room", quizRoom);
            model.addAttribute("quizQuestionType", quizRoom.getQuizRoomType());

            return "quiz/quizRoom"; // quizRoom.jsp로 이동
        } catch (CustomException e) {
            model.addAttribute("errorMessage", "방 조회 중 오류가 발생했습니다: " + e.getMessage());
            return "errorPage"; // 에러가 발생하면 에러 페이지로 이동
        }
    }

    // **퀴즈방 생성 API**
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createRoom(@RequestBody QuizRoomBean quizRoomBean, HttpSession session) {
    	Map<String, Object> response = new HashMap<>();
        MemberBean loginMemberBean = (MemberBean) session.getAttribute("loginMemberBean");

        if (loginMemberBean == null || !loginMemberBean.isMemberLogin()) {
            response.put("success", false);
            response.put("message", "로그인 후 사용해 주세요.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        int maxParticipants = quizRoomBean.getMaxCapacity();
        int maxSongs = quizRoomBean.getMaxMusic();

        // 최대 인원 및 최대 곡수 검증
        if (maxParticipants >= 10) {
            response.put("success", false);
            response.put("message", "최대 인원은 10명까지 가능합니다.");
            return ResponseEntity.badRequest().body(response);
        }

        if (maxSongs != 100 && maxSongs != 200 && maxSongs != 300 &&
            maxSongs != 400 && maxSongs != 500) {
            response.put("success", false);
            response.put("message", "최대 곡수는 100, 200, 300, 400, 500 중 하나여야 합니다.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            QuizRoomBean createdRoom = quizRoomService.createRoom(quizRoomBean, loginMemberBean);
            response.put("success", true);
            response.put("roomId", createdRoom.getQuizRoomId());
	
            // 방 생성 후 자신도 포함된 플레이어 목록을 WebSocket으로 전송
            sendRoomPlayersUpdate(createdRoom.getQuizRoomId());

            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            response.put("success", false);
            response.put("message", "방 생성에 실패했습니다: " + e.getMessage());
            return ResponseEntity.status(e.getStatusCode().getHttpStatus()).body(response);
        }
    }


    @PostMapping("/join")
    public ResponseEntity<Map<String, Object>> joinRoom(@RequestBody Map<String, Object> requestData, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        // 세션에서 로그인 상태 확인
        MemberBean loginMemberBean = (MemberBean) session.getAttribute("loginMemberBean");
        if (loginMemberBean == null || !loginMemberBean.isMemberLogin()) {
            response.put("success", false);
            response.put("message", "로그인 후 사용해 주세요.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        try {
            // 요청 데이터에서 roomId 및 roomPassword 가져오기
            int roomId = Integer.parseInt(requestData.get("roomId").toString());
            String roomPassword = (String) requestData.getOrDefault("roomPassword", "");

            // 방에 참가 시도
            quizRoomService.enterQuizRoom(roomId, loginMemberBean, roomPassword);
            response.put("success", true);
            response.put("roomId", roomId);

            // 사용자가 방에 입장했음을 WebSocket으로 알림
            String joinMessage = loginMemberBean.getNickname() + "님이 참가하셨습니다.";
            messagingTemplate.convertAndSend("/sub/chat/" + roomId, new ChatMessage<String>("시스템", joinMessage));

            // 방의 모든 플레이어 목록을 가져옴 (자신 포함)
            List<String> playerNicknames = quizRoomService.getAttendeesNicknamesByRoomId(roomId);
            
            // 방의 모든 플레이어에게 갱신된 목록을 WebSocket으로 전송
            sendRoomPlayersUpdate(roomId);  // 기존 플레이어에게 목록 갱신

            // **새로운 플레이어에게 방의 모든 플레이어 목록을 반환**
            response.put("players", playerNicknames);  // 새로운 플레이어에게 목록 전달

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // 예외 발생 시 처리
            response.put("success", false);
            response.put("message", "방 참여 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

 // 새로운 참가자가 방에 들어온 후 플레이어 목록을 다시 보내주는 메서드 추가
    @GetMapping("/{roomId}/players")
    public ResponseEntity<Map<String, Object>> getRoomPlayers(@PathVariable int roomId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<String> playerNicknames = quizRoomService.getAttendeesNicknamesByRoomId(roomId);
            response.put("success", true);
            response.put("players", playerNicknames);  // 현재 방의 플레이어 목록 반환
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "플레이어 목록을 가져오는 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    public void sendRoomPlayersUpdate(int roomId) {
        // 현재 방에 있는 모든 플레이어의 닉네임 목록을 가져옴
        List<String> playerNicknames = quizRoomService.getAttendeesNicknamesByRoomId(roomId);
        
        // WebSocket을 통해 닉네임 목록을 전송
        ChatMessage<List<String>> playerListMessage = new ChatMessage<>();
        playerListMessage.setRoomId(String.valueOf(roomId));
        playerListMessage.setType("PLAYER_LIST");  // 메시지 타입 설정
        playerListMessage.setMessage(playerNicknames);  // 플레이어 목록을 메시지에 포함

        // WebSocket으로 메시지 전송
        messagingTemplate.convertAndSend("/sub/quizRoom/" + roomId + "/players", playerListMessage);
    }


    @GetMapping("/{roomId}/start")
    public ResponseEntity<Map<String, Object>> quizStart(@PathVariable int roomId, @RequestParam int memberId) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("퀴즈 시작 요청 - 방 ID: {}, 회원 ID: {}", roomId, memberId);

            // 방장만 게임 시작 가능
            if (!quizRoomService.isRoomHost(roomId, memberId)) {
                log.warn("방장 확인 실패 - 방 ID: {}, 회원 ID: {}는 방장이 아닙니다.", roomId, memberId);
                response.put("success", false);
                response.put("message", "방장만 게임을 시작할 수 있습니다.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }

            // 방의 참가자 수 확인
            int playerCount = quizRoomService.getPlayerCountInRoom(roomId);
            log.info("방 ID: {}, 참가자 수: {}", roomId, playerCount);

            // 혼자 있는 경우 바로 게임 시작 가능
            if (playerCount > 1) {
                boolean allPlayersReady = quizRoomService.areAllPlayersReady(roomId, memberId);
                if (!allPlayersReady) {
                    log.warn("모든 참가자가 준비되지 않음 - 방 ID: {}", roomId);
                    response.put("success", false);
                    response.put("message", "모든 참가자가 준비되지 않았습니다.");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
                log.info("모든 참가자가 준비됨 - 방 ID: {}", roomId);
            }

            // 퀴즈 시작 처리
            String quizType = quizService.getQuizTypeForRoom(roomId);
            QuizMusic quiz = quizService.getRandomQuizQuestion(1, quizType);

            log.info("퀴즈 가져옴 - 방 ID: {}, 퀴즈 제목: {}, 퀴즈 정답: {}", roomId, quiz.getName(), quiz.getAnswer());

            response.put("success", true);
            response.put("quiz", quiz);
            return ResponseEntity.ok(response);

        } catch (CustomException e) {
            log.error("CustomException 발생 - 방 ID: {}, 메시지: {}", roomId, e.getMessage());
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            log.error("퀴즈 시작 중 오류 발생 - 방 ID: {}, 오류: {}", roomId, e.getMessage(), e);
            response.put("success", false);
            response.put("message", "퀴즈를 가져오는 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



    @MessageMapping("/quiz/{roomId}/changeType")
    public void changeQuizType(@DestinationVariable int roomId, @Payload Map<String, String> message) {
        String newQuizType = message.get("newQuizType");
        System.out.println("퀴즈 타입 변경 요청 수신: " + newQuizType);

        // 퀴즈 타입 변경 (QuizRoom의 퀴즈 타입을 변경)
        quizRoomService.changeQuizType(roomId, newQuizType);

        // 변경 사항을 WebSocket을 통해 해당 방의 모든 참가자에게 알림
        messagingTemplate.convertAndSend("/sub/quiz/" + roomId + "/changeType", 
                                         Collections.singletonMap("newQuizType", newQuizType));
    }
    
    @MessageMapping("/quiz/{roomId}/ready")
    public void handleReadyStatus(@DestinationVariable int roomId, @Payload Map<String, Object> message) {
        String sender = (String) message.get("sender");
        boolean isReady = (boolean) message.get("isReady");

        // 로그 추가 (메시지 수신 확인)
        System.out.printf("방 ID: {}, 발신자: {}, 준비 상태: {}", roomId, sender, isReady);


        // Member 객체를 조회한 후 준비 상태를 업데이트 (서비스를 통해 처리)
        Member member = memberRepository.findByNickname(sender).orElseThrow(() -> 
            new CustomException("해당 닉네임의 사용자를 찾을 수 없습니다."));
        quizRoomService.setPlayerReadyStatus(roomId, member.getMemberId(), isReady);  // QuizRoomService의 메서드 호출

        // WebSocket으로 상태를 모든 사용자에게 전송
        Map<String, Object> response = new HashMap<>();
        response.put("sender", sender);
        response.put("isReady", isReady);

        messagingTemplate.convertAndSend("/sub/quiz/" + roomId + "/ready", response);

        // 채팅 메시지로도 발송
        Map<String, Object> chatMessage = new HashMap<>();
        chatMessage.put("sender", "시스템");
        chatMessage.put("message", sender + (isReady ? "님이 준비 완료했습니다." : "님이 준비를 취소했습니다."));

        // 채팅방으로 메시지 발송
        messagingTemplate.convertAndSend("/sub/chat/" + roomId, chatMessage);
    }

    
    
    @GetMapping("/{roomId}/checkReady")
    public ResponseEntity<Map<String, Object>> checkReadyStatus(@PathVariable int roomId) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 현재 방의 방장 ID를 가져옵니다.
            int hostId = quizRoomService.getRoomHostId(roomId);

            // 모든 참가자의 준비 상태를 확인 (방장은 제외)
            boolean allPlayersReady = quizRoomService.areAllPlayersReady(roomId, hostId);

            response.put("success", true);
            response.put("allReady", allPlayersReady);
            return ResponseEntity.ok(response);

        } catch (CustomException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "참가자 준비 상태를 확인하는 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    
    @PostMapping("/leave")
    public ResponseEntity<Map<String, Object>> leaveRoom(@RequestBody Map<String, Object> requestData, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        MemberBean loginMemberBean = (MemberBean) session.getAttribute("loginMemberBean");

        if (loginMemberBean == null || !loginMemberBean.isMemberLogin()) {
            response.put("success", false);
            response.put("message", "로그인 후 사용해 주세요.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        try {
            int roomId = Integer.parseInt(requestData.get("roomId").toString());
            quizRoomService.roomExit(roomId, loginMemberBean);  // 방 나가기 처리

            // 방의 모든 플레이어에게 갱신된 플레이어 목록 전송
            sendRoomPlayersUpdate(roomId);  // 플레이어 목록 갱신
            response.put("success", true);
            // 사용자가 방에 입장했음을 WebSocket으로 알림
            String leaveMessage = loginMemberBean.getNickname() + "님이 퇴장하셨습니다.";
            messagingTemplate.convertAndSend("/sub/chat/" + roomId, new ChatMessage<String>("시스템", leaveMessage));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "서버 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
       
    }
}