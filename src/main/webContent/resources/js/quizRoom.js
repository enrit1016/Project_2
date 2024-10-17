// WebSocket 연결 및 퀴즈 방 관리 JS
let stompClient;  // WebSocket 클라이언트 객체
const roomId = getRoomIdFromPath();  // URL 경로에서 roomId 추출
let isPlaying = false;  // 음악 재생 상태
let currentVideo = null;  // 현재 재생 중인 비디오 코드
let currentAnswers = [];  // 현재 퀴즈 정답 배열
let currentSongName = '';  // 현재 노래 제목
let countdownInterval = null;  // 카운트다운 인터벌
let hintIndex = 0;  // 힌트 인덱스
let currentAnswer = '';  // 현재 퀴즈 정답
let isStoppedByUser = false;  // 사용자가 음악을 중지했는지 여부
const roomName = '방 이름';  // 방 이름 설정 (적절히 초기화 필요)
let currentUserNickname = "익명";  // 기본 닉네임 설정
let currentUserId = "1"; // 기본 사용자 ID 설정 (서버에서 적절히 가져오도록 설정 필요)

// 효과음 객체 생성
const correctAnswerSound = new Audio(`${root}/audio/correct_answer.mp3`);  

// **경로에서 roomId 추출 함수**
function getRoomIdFromPath() {
    const path = window.location.pathname;
    const pathSegments = path.split('/'); 
    const roomIndex = pathSegments.indexOf('rooms');
    return roomIndex !== -1 && pathSegments.length > roomIndex + 1 
        ? pathSegments[roomIndex + 1] 
        : 1;  // 기본값 1 반환
}

// **정답 효과음 재생 함수**
function playCorrectAnswerSound() {
    correctAnswerSound.play().catch(error => {
        console.error('오디오 재생 오류:', error);
    });
}

// **툴팁 표시 함수 (전역으로 정의)**
function showTooltip() {
    const tooltip = document.getElementById('command-tooltip');  
    const maximizeButton = document.getElementById('tooltip-maximize-btn');  

    tooltip.classList.remove('hidden');
    tooltip.classList.add('visible');
    maximizeButton.classList.add('hidden');  
}

document.addEventListener('DOMContentLoaded', () => {
    const tooltip = document.getElementById('command-tooltip');
    const minimizeButton = document.getElementById('tooltip-minimize-btn');
    const maximizeButton = document.getElementById('tooltip-maximize-btn');

    function toggleTooltip() {
    const tooltip = document.getElementById('command-tooltip');
    const maximizeButton = document.getElementById('tooltip-maximize-btn');

    if (tooltip.classList.contains('minimized')) {
        console.log('최대화 버튼 클릭됨');
        tooltip.classList.remove('minimized');
        tooltip.classList.add('visible');
        tooltip.style.display = 'block';
        tooltip.style.visibility = 'visible';
        
        // 툴팁 위치를 고정값으로 리셋
        tooltip.style.top = '62px';  // 초기 top 위치로 리셋
        tooltip.style.left = '62.5%';  // 초기 left 위치로 리셋

        // 최대화 버튼 숨기기
        maximizeButton.style.display = 'none';
    } else {
        console.log('최소화 버튼 클릭됨');
        tooltip.classList.add('minimized');
        tooltip.classList.remove('visible');
        tooltip.style.display = 'none';
        tooltip.style.visibility = 'hidden';
        
        // 최대화 버튼의 위치도 리셋 (최소화된 위치 고정)
        maximizeButton.style.top = '70px';  
        maximizeButton.style.left = '76%';  
        maximizeButton.style.display = 'block';
    }
}

    let isDragging = false;
    let offsetX, offsetY;

    tooltip.addEventListener('mousedown', (e) => {
        isDragging = true;
        dragStart = true;
        offsetX = e.clientX - tooltip.getBoundingClientRect().left;
        offsetY = e.clientY - tooltip.getBoundingClientRect().top;
        tooltip.classList.add('grabbing');
    });

    document.addEventListener('mousemove', (e) => {
        if (isDragging) {
            tooltip.style.left = `${e.clientX - offsetX}px`;
            tooltip.style.top = `${e.clientY - offsetY}px`;
        }
    });

    document.addEventListener('mouseup', () => {
        if (isDragging) {
            isDragging = false;
            tooltip.classList.remove('grabbing');
        }
        dragStart = false;
    });

    maximizeButton.addEventListener('mousedown', (e) => {
        isDragging = true;
        dragStart = true;
        offsetX = e.clientX - maximizeButton.getBoundingClientRect().left;
        offsetY = e.clientY - maximizeButton.getBoundingClientRect().top;
        maximizeButton.classList.add('grabbing');
    });

    document.addEventListener('mousemove', (e) => {
        if (isDragging) {
            maximizeButton.style.left = `${e.clientX - offsetX}px`;
            maximizeButton.style.top = `${e.clientY - offsetY}px`;
        }
    });

    document.addEventListener('mouseup', () => {
        if (isDragging) {
            isDragging = false;
            maximizeButton.classList.remove('grabbing');
        }
        dragStart = false;
    });

    minimizeButton.addEventListener('click', toggleTooltip);
    maximizeButton.addEventListener('click', toggleTooltip);
});

// **이벤트 리스너 설정 (게임 시작 버튼)**
document.addEventListener('DOMContentLoaded', () => {
    const startQuizButton = document.getElementById('start-quiz-btn'); // 게임 시작 버튼

    if (startQuizButton) {
        console.log('게임 시작 버튼이 DOM에서 로드되었습니다.');
        startQuizButton.addEventListener('click', startQuiz);  // 이벤트 리스너 설정
    } else {
        console.error('게임 시작 버튼을 찾을 수 없습니다.');
    }
});

// **플레이어 목록을 HTML에 동적으로 추가하는 함수**
function updatePlayerList(data) {
    const playerList = document.getElementById('players');
    playerList.innerHTML = ''; // 기존 목록 초기화

    // 서버 응답에서 players 배열 추출
    if (data.success && Array.isArray(data.players)) {
        data.players.forEach(player => {
            const li = document.createElement('li');
            li.textContent = player; // 서버에서 받은 플레이어 이름 (닉네임)
            playerList.appendChild(li); // 플레이어 목록에 추가
        });
    } else {
        console.error('서버 응답이 올바르지 않습니다:', data);
    }
}

// 서버로부터 플레이어 목록을 가져오는 함수
async function fetchPlayers(roomId) {
    try {
        const response = await fetch(`${root}/quiz/rooms/${roomId}/players`);
        const data = await response.json(); // 서버에서 응답을 받음
        updatePlayerList(data); // 플레이어 목록 업데이트
    } catch (error) {
        console.error('플레이어 목록을 가져오는 중 오류:', error);
    }
}

// 페이지 로드 시 플레이어 목록을 불러옴
document.addEventListener('DOMContentLoaded', () => {
    fetchPlayers(roomId); // 현재 방의 플레이어 목록 가져오기
});

// 이후에는 let 없이 값을 할당
document.addEventListener('DOMContentLoaded', () => {
    fetchPlayerNicknames();  // 페이지 로드 시 사용자 닉네임 설정
});

async function fetchPlayerNicknames() {
    try {
        const response = await fetch(`${root}/quiz/rooms/${roomId}/players`);
        const data = await response.json();

        if (data.success) {
            currentUserNickname = data.currentUserNickname || '익명'; // 이미 선언된 변수에 값만 할당
            console.log('현재 사용자 닉네임:', currentUserNickname);
        } else {
            console.error('플레이어 목록 가져오기 실패:', data.message);
        }
    } catch (error) {
        console.error('플레이어 닉네임 가져오는 중 오류:', error);
    }
}


// **WebSocket 연결 설정**
const socket = new SockJS(`${root}/ws-stomp`);
stompClient = Stomp.over(socket);

stompClient.connect({}, (frame) => {
    console.log('WebSocket 연결 성공:', frame);

    stompClient.subscribe(`/sub/quiz/${roomId}`, (message) => {
        const msg = JSON.parse(message.body);
        currentVideo = msg.code;
        currentAnswers = msg.answer.map(answer => answer.trim().toLowerCase());
        currentSongName = msg.name;
        currentAnswer = msg.answer[0];
        hintIndex = 0;
        playMusic(msg.code, msg.start);
    });

    stompClient.subscribe(`/sub/chat/${roomId}`, (message) => {
		console.log("수신된 WebSocket 메시지:", message.body);  // 수신된 메시지 확인
        const chat = JSON.parse(message.body);
        displayChatMessage(chat.sender, chat.content);
        processChatMessage(chat.sender, chat.content);
    });

    // **플레이어 목록 갱신 구독**
    stompClient.subscribe(`/sub/quiz/${roomId}/players`, (message) => {
        const players = JSON.parse(message.body);
        clearPlayerList();
        players.forEach(player => addPlayerToList(player.name));
    });
});

// **퀴즈 시작 함수**
async function startQuiz() {
    console.log('게임 시작 버튼이 클릭되었습니다.');
    try {
        const response = await fetch(`${root}/quiz/rooms/${roomId}/random`);
        if (response.ok) {
            const data = await response.json();
            const quiz = data.quiz;
            currentVideo = quiz.code;
            currentAnswers = quiz.answer.map(answer => answer.trim().toLowerCase());
            currentSongName = quiz.name;
            currentAnswer = quiz.answer[0];
            hintIndex = 0;
            playMusic(quiz.code, quiz.start);
        } else {
            console.error('랜덤 퀴즈 가져오기 실패:', response.statusText);
        }
    } catch (error) {
        console.error('퀴즈 가져오는 중 오류:', error);
    }
}

// **음악 재생 함수**
function playMusic(videoCode, startTime) {
    const player = document.getElementById('youtube-player');
    const embedUrl = `https://www.youtube.com/embed/${videoCode}?autoplay=1&start=${startTime}&mute=0`;
    player.src = embedUrl;
    isPlaying = true;
    updatePlayIcon();
}

function togglePlayPause() {
    const player = document.getElementById('youtube-player');
    if (isPlaying) {
        console.log('음악 중지');
        player.src = '';
        isPlaying = false;
        isStoppedByUser = true; // 수동 중지 시 상태 저장
    } else {
        console.log('음악 재개');
        const embedUrl = `https://www.youtube.com/embed/${currentVideo}?autoplay=1`;
        player.src = embedUrl;
        isPlaying = true;
        isStoppedByUser = false; // 재생 시 상태 초기화
    }
    updatePlayIcon();
}

document.querySelector('.quiz-room-music-icon').addEventListener('click', togglePlayPause);


// **재생 아이콘 업데이트**
function updatePlayIcon() {
    const musicIcon = document.querySelector('.quiz-room-music-icon i');
    if (isPlaying) {
        musicIcon.classList.replace('bi-play-circle-fill', 'bi-pause-circle-fill');
    } else {
        musicIcon.classList.replace('bi-pause-circle-fill', 'bi-play-circle-fill');
    }
}

function processChatMessage(sender, content) {
    const message = content.trim().toLowerCase();


    // 명령어 처리
    if (['!스킵', '!skip'].includes(message)) {
        console.log("스킵 명령어 감지됨");  // 스킵 감지 로그
        skipQuiz(sender);  // 스킵 처리 함수 호출
        return;  // 스킵 메시지는 DB에 저장하지 않음
    } else if (['!힌트', '!hint'].includes(message)) {
        console.log("힌트 명령어 감지됨");  // 힌트 감지 로그
        displayHint();  // 힌트 표시 함수 호출
        return;  // 힌트 메시지도 DB에 저장하지 않음
    }

    // 정답 처리
    if (currentAnswers.some(answer => isExactMatch(message, answer))) {
        checkAnswer(sender, message);  // 정답 체크 함수 호출
        return;  // 정답은 DB에 저장하지 않음
    }

    // 일반 채팅 메시지만 저장
    saveChatMessage(roomId, currentUserId, message);
}




// **채팅 메시지 전송 및 저장**
async function sendMessage() {
    const chatInput = document.getElementById('chat-input');
    const message = chatInput.value.trim();

    if (!message) {
        alert('메시지를 입력해주세요.');
        return;
    }

    // WebSocket으로 메시지 전송
    stompClient.send(`/pub/chat/${roomId}`, {}, JSON.stringify({
        sender: currentUserNickname,
        content: message
    }));

    // !힌트나 !스킵 명령어 또는 정답인 경우 DB에 저장하지 않음
    if (['!스킵', '!skip', '!힌트', '!hint'].includes(message.toLowerCase()) ||
        currentAnswers.some(answer => isExactMatch(message, answer))) {
        console.log("명령어 또는 정답, DB에 저장 안 함:", message);
        chatInput.value = ''; // 입력창 초기화
        return; // 저장 제외
    }

    // 일반 채팅 메시지만 DB에 저장
    await saveChatMessage(roomId, currentUserId, message);

    // 채팅 메시지를 화면에 표시
    displayChatMessage('나', message);
    chatInput.value = '';
}



function skipQuiz(sender) {
    console.log(`${sender}님이 문제를 스킵했습니다.`);

    // 타이머가 작동 중이라면 종료합니다.
    if (countdownInterval) {
        clearInterval(countdownInterval);
        countdownInterval = null; // 타이머 초기화
    }

    // 스킵 알림 메시지 전송
    stompClient.send(`/pub/chat/${roomId}`, {}, JSON.stringify({
        sender: '시스템',
        content: `${sender}님이 문제를 스킵했습니다. 다음 문제로 이동합니다!`
    }));

    hideAnswerInfo();
    hideHint();
    startQuiz(); // 즉시 다음 퀴즈 시작
}

// **힌트 표시 함수**
function displayHint() {
    const hintDisplay = document.getElementById('hint-info');
    if (hintIndex >= currentAnswer.length) return;

    let hint = '';
    for (let i = 0; i < currentAnswer.length; i++) {
        hint += i <= hintIndex ? currentAnswer[i] : 'O';
    }
    hintIndex++;
    hintDisplay.textContent = `힌트: ${hint}`;
    hintDisplay.classList.remove('hidden');
}

function skipQuiz(sender) {
    console.log(`${sender}님이 문제를 스킵했습니다.`);

    // 타이머가 작동 중이라면 종료합니다.
    if (countdownInterval) {
        clearInterval(countdownInterval);
        countdownInterval = null; // 타이머 초기화
    }

    // 스킵 알림 메시지 전송
    stompClient.send(`/pub/chat/${roomId}`, {}, JSON.stringify({
        sender: '시스템',
        content: `${sender}님이 문제를 스킵했습니다. 다음 문제로 이동합니다!`
    }));

    // 타이머 표시를 숨김
    hideAnswerInfo(); 
    hideHint();

    startQuiz(); // 즉시 다음 퀴즈 시작
}

// 괄호 안의 문자열과 불필요한 문자를 제거하는 정규 표현식을 사용하여 정답을 처리
function normalizeAnswer(answer) {
    return answer.replace(/\(.*?\)/g, '') // 괄호 안의 내용을 제거
                 .replace(/[^\w\s가-힣]|_/g, '') // 특수문자 제거, 한글 범위 추가
                 .replace(/\s+/g, '') // 모든 공백 제거
                 .trim();  // 양쪽 공백을 제거
}

// 정답과 사용자 입력이 정확히 일치하는지 확인하는 함수
function isExactMatch(userAnswer, correctAnswer) {
    const normalizedUserAnswer = normalizeAnswer(userAnswer);
    const normalizedCorrectAnswer = normalizeAnswer(correctAnswer);
    // 입력한 답과 정답이 정확히 일치하는지 확인
    return normalizedUserAnswer === normalizedCorrectAnswer;
}


function checkAnswer(sender, userAnswer) {
    const normalizedUserAnswer = normalizeAnswer(userAnswer);

    // 정답 리스트를 순회하며 정확한 일치를 확인
    const isCorrect = currentAnswers.some(correctAnswer => {
        const normalizedCorrectAnswer = normalizeAnswer(correctAnswer);
        return normalizedUserAnswer === normalizedCorrectAnswer;  // 전체 일치 확인
    });

    if (isCorrect) {
        //console.log('정답입니다!');
        playCorrectAnswerSound(); // 정답 맞췄을 때 효과음 재생
        displayAnswerInfo(sender); // 정답 정보 표시

        stompClient.send(`/pub/chat/${roomId}`, {}, JSON.stringify({
            sender: '시스템',
            content: `${sender}님이 정답을 맞췄습니다!`
        }));

        hideHint(); // 힌트 숨기기
        startCountdown(); // 카운트다운 시작
    } else {
        //console.log('오답입니다.');
    }
}

// **정답 정보 표시 함수**
function displayAnswerInfo(sender) {
    document.getElementById('correct-player').textContent = `정답자: ${sender}`;
    document.getElementById('song-info').textContent = currentSongName;
    document.getElementById('answer-info').classList.remove('hidden');
    startCountdown();
}


function startCountdown() {
    // 기존 타이머가 있다면 초기화
    if (countdownInterval) {
        clearInterval(countdownInterval);
        countdownInterval = null; // 타이머 초기화
    }

    let timeLeft = 10;
    const countdownDisplay = document.getElementById('next-quiz-timer');
    countdownDisplay.textContent = `다음 퀴즈가 시작됩니다 (${timeLeft})`;
    countdownDisplay.classList.remove('hidden');

    countdownInterval = setInterval(() => {
        timeLeft--;
        countdownDisplay.textContent = `다음 퀴즈가 시작됩니다 (${timeLeft})`;

        // 남은 시간이 0이 되었을 때
        if (timeLeft < 0) {
            clearInterval(countdownInterval); // 타이머 종료
            countdownInterval = null; // 초기화
            hideAnswerInfo();

            // 사용자가 수동으로 음악을 중지했는지 확인
            if (!isStoppedByUser) {
                startQuiz(); // 음악 자동 재생 없이 다음 퀴즈 시작
            } else {
                console.log('사용자가 음악을 중지했습니다.');
                isStoppedByUser = false; // 상태 초기화
            }
        }
    }, 1000);
}

function hideAnswerInfo() {
    const answerInfo = document.getElementById('answer-info');
    const countdownDisplay = document.getElementById('next-quiz-timer');

    document.getElementById('correct-player').textContent = '';
    document.getElementById('song-info').textContent = '';

    // 타이머 표시 숨김
    answerInfo.classList.add('hidden');
    countdownDisplay.classList.add('hidden');
    countdownDisplay.textContent = '';
}


// **힌트 숨기기 함수**
function hideHint() {
    const hintDisplay = document.getElementById('hint-info');
    hintDisplay.classList.add('hidden');
    hintDisplay.textContent = '';
}

// **채팅 메시지 표시 함수**
const MAX_CHAT_MESSAGES = 14;

function displayChatMessage(sender, content) {
    const chatMessages = document.getElementById('chat-messages');
    const messageElement = document.createElement('p');
    messageElement.innerHTML = `<strong>${sender}:</strong> ${content}`;
    chatMessages.appendChild(messageElement);

    while (chatMessages.children.length > MAX_CHAT_MESSAGES) {
        chatMessages.removeChild(chatMessages.firstChild);
    }
}

// **채팅 메시지 전송 및 저장**
async function sendMessage() {
    const chatInput = document.getElementById('chat-input');
    const message = chatInput.value.trim();
    console.log('채팅 입력값:', message); // 입력된 값 확인

    if (!message) {
        alert('메시지를 입력해주세요.');
        return;
    }

    // WebSocket으로 메시지 전송
    stompClient.send(`/pub/chat/${roomId}`, {}, JSON.stringify({
        sender: currentUserNickname,
        content: message
    }));

    // !힌트나 !스킵 명령어 또는 정답인 경우 DB에 저장하지 않음
    if (['!스킵', '!skip', '!힌트', '!hint'].includes(message.toLowerCase()) ||
        currentAnswers.some(answer => isExactMatch(message, answer))) {
        console.log("명령어 또는 정답, DB에 저장 안 함:", message);
        chatInput.value = ''; // 입력창 초기화
        return; // 저장 제외
    }

    // 일반 채팅 메시지는 DB에 저장
    await saveChatMessage(roomId, currentUserId, message);

    // 채팅 메시지를 화면에 표시
    displayChatMessage('나', message);
    chatInput.value = '';
}


document.getElementById('send-chat-btn').addEventListener('click', sendMessage);
document.getElementById('chat-input').addEventListener('keydown', (event) => {
    if (event.key === 'Enter') sendMessage();
});

// **채팅 저장 함수**
async function saveChatMessage(roomId, memberId, message) { // message 인자를 받음
    try {
        const response = await fetch(`${root}/saveChat`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                roomId: parseInt(roomId),  // roomId가 숫자로 전달되도록 수정
                memberId: parseInt(memberId),  // memberId도 숫자로 전달
                content: message,  // message를 content로 전달
                timestamp: new Date().toISOString(),
            }),
        });

        if (!response.ok) {
            const errorMsg = await response.text();
            console.log("roomId:", roomId, "memberId:", memberId, "content:", message);
            console.error('채팅 저장 실패:', errorMsg);
        } else {
            console.log('채팅 저장 성공');
        }
    } catch (error) {
        console.error('채팅 저장 중 오류:', error.message);
    }
}

function sendMessage() {
    const chatInput = document.getElementById('chat-input');
    const message = chatInput.value.trim();
    if (!message) {
        alert('메시지를 입력해주세요.');
        return;
    }

    stompClient.send(`/pub/chat/${roomId}`, {}, JSON.stringify({
        sender: roomName,
        content: message
    }));

    displayChatMessage('나', message);
    chatInput.value = '';
    processChatMessage('나', message);
}

// **게임 시작 버튼 이벤트 리스너 설정**
document.addEventListener('DOMContentLoaded', () => {
    const startQuizButton = document.getElementById('start-quiz-btn'); // 게임 시작 버튼

    if (startQuizButton) {
        console.log('게임 시작 버튼이 DOM에서 로드되었습니다.');
        startQuizButton.addEventListener('click', startQuiz);  // 이벤트 리스너 설정
    } else {
        console.error('게임 시작 버튼을 찾을 수 없습니다.');
    }
});